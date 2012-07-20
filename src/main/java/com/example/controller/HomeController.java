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

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private NetworkService networkService;

    /**
     * Action that handle the landing pages. It pass down all the networks registered so far.
     *
     * @param  map  contains params to be passed down the jsp
     * @return      the jsp path
     */
    @RequestMapping("/")
    public String index(Map<String, Object> map) {
        map.put("networks", networkService.listNetworks());
        return "home/index";
    }     
}

