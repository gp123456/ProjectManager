<%-- 
    Document   : login
    Created on : Apr 8, 2014, 3:39:42 PM
    Author     : qbsstation5
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%String path = request.getContextPath();%>

<!DOCTYPE html>
<html>
    <title>Project Manager Login</title>
    <head>
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/core/login.css">
    </head>
    <body>
        <div class="bganimate2 image1 "></div>
        <div class="bganimate"> </div>	
        <div class="login-container">
            <div class='logo'></div>
            <form id="frm" action="home" method="post">
                <p class="lTitle">Username</p> <input id="UN" type="text" class="login" name="username" autofocus="true"><br>
                <p class="lTitle">Password</p> <input type="password" class="login" name="password"><br>
                <input type="submit" class="submit" value="Login">
            </form>
            <div class="ver"> v.1.1.0</div>
            <div class="loginText">
            </div>
        </div>
    </body>
</html>