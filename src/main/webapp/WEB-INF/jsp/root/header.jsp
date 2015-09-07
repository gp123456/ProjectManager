<%-- 
    Document   : header
    Created on : Apr 10, 2014, 12:18:00 PM
    Author     : qbsstation12
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%String path = request.getContextPath();%>

<script>
    function OpenInNewTab(url) {
        var win = window.open(url, '_blank');

        win.focus();
    }
</script>

<div class="logo"></div>
<div class="version">Software version 3.36.0</div>
<div class="headmenu">
    <h2 id="project" class="headmenuclickers" onclick="OpenInNewTab('<%=path%>/project/snapshot');">
        <label id="lbl_projects">Project</label>
    </h2>
</div>
<div class="headmenu">
    <h2 id="stock" class="headmenuclickers" onclick="OpenInNewTab('<%=path%>/stock/view');">
        <label>Stock</label>
    </h2>
</div>
<div class="headmenu">
    <h2 id="company" class="headmenuclickers" onclick="OpenInNewTab('<%=path%>/company/view');">
        <label>Company</label>
    </h2>
</div>
<div class="headmenu">
    <h2 id="vessel" class="headmenuclickers" onclick="OpenInNewTab('<%=path%>/vessel/view');">
        <label>Vessel</label>
    </h2>
</div>
<div class="headmenu">
    <h2 id="contact" class="headmenuclickers" onclick="OpenInNewTab('<%=path%>/contact/view');">
        <label>Contact</label>
    </h2>
</div>
<div id="userHead">
    <!--<div class="userHead-element login"></div>-->

    <div class="loginInfo"> 
        <div class="status">${full_name}</div>
        <p></p><div>Last login: ${last_login}</div>
    </div>

    <div class="logout">
        <a href="<%=path%>"><img src="/images/projectmanager/common/logout.png"></a>
    </div>
</div>
</div>