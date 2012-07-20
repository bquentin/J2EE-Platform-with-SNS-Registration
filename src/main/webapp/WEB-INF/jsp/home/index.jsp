<!DOCTYPE html>

<%@ page import="com.example.model.Network" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Spring MVC Hibernate Oauth connect and provider</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="/assets/css/bootstrap.css" rel="stylesheet">
    <link href="/assets/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="/assets/css/home.css" rel="stylesheet">
</head>

<body>

<% Network network = (Network) session.getAttribute("network"); %>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a href="/" class="brand">Jade Sample App: Spring MVC / Hibernate / Oauth Authentication / JQuery</a>

            <c:if test="${network != null}">
                <div class="btn-group pull-right">
                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="icon-user"></i>${network.getName()}
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="/signin/signout">SignOut</a></li>
                    </ul>
                </div>
            </c:if>

        </div>
    </div>
</div>

<div class="container main">
    <c:if test="${network == null}">
        <div class="row">
            <div class="span6">
                <h3>This is a J2EE platform sample featuring:</h3>
            </div>
        </div>
        <hr>

        <div class="row">
            <div class="span4">
                <h3>1 - SNS registration</h3>
                Users can register to the platform with their SNS credentials.
                <b>Click the twitter icon to register/logon !!!</b>

                <div class="auth_networks">
                    <a href="/signin/twitter" class="auth_provider">
                        </abbr><img src="/assets/images/twitter_32.png">
                    </a>
                </div>
            </div>
            <div class="span4">
                <h3>2 - Store Oauth Access</h3>
                Granted SNS access are stored - SNS can be used on the behalf of the user.
            </div>
            <div class="span4">
                <h3>3 - Display registered networks</h3>
                The "Wall" displays the SNS profile registered so far...
            </div>
        </div>
        <hr>
    </c:if>

    <div class="row">
        <div class="span6" style="padding-top:30px;padding-bottom:45px">
            <h4>Wall of registered networks:</h4>
        </div>
    </div>

    <div class="row">
        <div class="span12">
            <c:forEach items="${networks}" var="network">
                <img src="${network.getImage()}" id="image_${network.getUid()}" class="profile_preview"
                     data_preview="${network.getUid()}"/>

                <div id="content_${network.getUid()}" style="display:none">
                     <span style="display: inline-block;">
                         <img src="${network.getImage()}" class="size_64"/>
                     </span>
                     <span style="display: inline-block;vertical-align:top;">
                         <b>${network.getName()}</b>
                     </span>
                </div>
            </c:forEach>
        </div>
    </div>
    <div id="customer_profile" class="triangle-border"></div>

    <hr>

    <footer>
        <p>&copy; bquentin 2012</p>
    </footer>
</div>

<script src="/assets/js/jquery.js"></script>
<script src="/assets/js/bootstrap-dropdown.js"></script>
<script src="/assets/js/bootstrap-modal.js"></script>
<script src="/assets/js/bootstrap-tab.js"></script>
<script src="/assets/js/members.js"></script>
</body>
</html>
