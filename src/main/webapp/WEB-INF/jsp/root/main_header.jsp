<%-- 
    Document   : header
    Created on : Apr 10, 2014, 12:18:00 PM
    Author     : qbsstation12
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String path = request.getContextPath();
    String role = (String) request.getAttribute("role");
%>

<script>
    var wins = [];
    
    function OpenInNewTab(url) {
        var win = window.open(url, '_blank');

        if (win != null) {
            wins.push(win);
            
            win.focus();
        }
    }
</script>

<div class="logo"></div>
<div class="main_version">Software version 1.0.0</div>
<div class="headmenu">
    <input type="button" class="button-header" value="Project" onclick="OpenInNewTab('<%=path%>/project/snapshot');"/>
</div>
<div class="headmenu">
    <input type="button" class="button-header" value="Stock" onclick="OpenInNewTab('<%=path%>/stock/snapshot');"/>
</div>
<div class="headmenu">
    <input type="button" class="button-header" value="Company" onclick="OpenInNewTab('<%=path%>/company/snapshot');"/>
</div>
<div class="headmenu">
    <input type="button" class="button-header" value="Vessel" onclick="OpenInNewTab('<%=path%>/vessel/snapshot');"/>
</div>
<div class="headmenu">
    <input type="button" class="button-header" value="Contact" onclick="OpenInNewTab('<%=path%>/contact/snapshot');"/>
</div>
<% if (role.equals("SUPER ADMIN")) {%>
<div class="headmenu">
    <input type="button" class="button-header" value="Debug" onclick="OpenInNewTab('<%=path%>/debug/snapshot');"/>
</div>
<%}%>
<div id="userHead">
    <div class="loginInfo"> 
        <div class="status">${full_name}</div>
        <p></p><div>Last login: ${last_login}</div>
    </div>

    <div class="logout">
        <input type="button" class="button-header" value="Exit" onclick="closeAllWindows(wins);"/>
    </div>
</div>