<%-- 
    Document   : header
    Created on : Jul 19, 2016, 12:21:24 PM
    Author     : gpatitakis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String path = request.getContextPath();
    String role = (String) request.getAttribute("role");
%>

<div class="brochure"></div>
<div class="version">Software version 1.0.0</div>
<div id="userHead">
    <div class="loginInfo"> 
        <div class="status">${full_name}</div>
        <p></p><div>Last login: ${last_login}</div>
    </div>

    <div class="logout">
        <a href="<%=path%>"><img src="../images/projectmanager/common/logout.png"></a>
        <br/><label>Exit</label>
    </div>
</div>