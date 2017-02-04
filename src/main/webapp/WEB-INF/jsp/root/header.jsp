<%-- 
    Document   : header
    Created on : Jul 19, 2016, 12:21:24 PM
    Author     : gpatitakis

CLOCK AVAILABLE OPTIONS:
timestamp: a valid timestamp
langSet: "en"(=default),"fr","es","it","de","ru" (or customized)
format: 12(=default),24
calendar: "true"(=default),"false"
"destroy"
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String path = request.getContextPath();
    String role = (String) request.getAttribute("role");
%>

<script>
    $(function () {
        $("#clockDisplay").clock({"format": "24", "calendar": "false"});
    });
</script>

<div class="logo"></div>
<div class="version">Software version 1.0.0</div>
<div id="userHead">
    <div class="loginInfo"> 
        <div>${full_name}</div>
        <div>Last Login: ${last_login}</div>
        <span>Current Time:<label id="clockDisplay"/></span>
        
    </div>
</div>