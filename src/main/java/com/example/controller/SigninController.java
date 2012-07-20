package com.example.controller;

import com.example.model.Network;
import com.example.service.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.dao.DataIntegrityViolationException;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;


@Controller
public class SigninController {

    private OAuthConsumer consumer;
    private OAuthProvider provider;

    @Autowired
    private NetworkService networkService;


    /**
     * Action to trigger the Oauth process with twitter
     *
     * @return the jsp path
     */
    @RequestMapping("/twitter")
    public String signInTwitter() {
        try {

            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("environment.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            consumer = new CommonsHttpOAuthConsumer(
                    properties.getProperty("twitter.consumerKey"),
                    properties.getProperty("twitter.consumerSecret"));

            provider = new DefaultOAuthProvider(
                    "http://twitter.com/oauth/request_token",
                    "http://twitter.com/oauth/access_token",
                    "http://twitter.com/oauth/authorize");

            return new StringBuffer("redirect:").append(provider.retrieveRequestToken(consumer, properties.getProperty("callback"))).toString();

        } catch (OAuthException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    /**
     * Callback url from twitter
     *
     * @param oauth_token
     * @param oauth_verifier
     * @param request
     * @param response
     * @return the jsp path
     */
    @RequestMapping("/callback")
    public String callback(@RequestParam String oauth_token, @RequestParam String oauth_verifier, HttpServletRequest request, HttpServletResponse response) {
        try {

            // retrieve params from the access callback
            provider.retrieveAccessToken(consumer, oauth_verifier);
            HttpParameters resp = provider.getResponseParameters();

            // create a new network based on the callback data
            Network n = new Network();
            n.setProvider("twitter");
            n.setUid(resp.get("user_id").first());
            n.setName(resp.get("screen_name").first());
            n.setToken(consumer.getToken());
            n.setTokenSecret(consumer.getTokenSecret());

            //get the image url based on the screen_name
            String url = new StringBuffer("http://api.twitter.com/1/users/profile_image/").append(n.getName()).append(".jpg").toString();
            URLConnection con = new URL(url).openConnection();
            con.connect();
            InputStream is = con.getInputStream();
            n.setImage(con.getURL().toString());
            is.close();

            // store in db the new network
            try {
                networkService.addNetwork(n);
            } catch (DataIntegrityViolationException e) {
                // record already exists
            }

            // store in session the new network
            HttpSession session = request.getSession(true);
            session.setAttribute("network", n);

        } catch (OAuthException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    /**
     * Clean up the session...
     *
     * @param request
     * @param response
     * @return the jsp path
     */
    @RequestMapping("/signout")
    public String signout(HttpServletRequest request, HttpServletResponse response) {
        // logout the user by clearing the session
        HttpSession session = request.getSession(true);
        session.invalidate();
        return "redirect:/";
    }
}