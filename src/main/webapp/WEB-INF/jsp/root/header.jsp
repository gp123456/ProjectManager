<%-- 
    Document   : header
    Created on : Apr 10, 2014, 12:18:00 PM
    Author     : qbsstation12
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%String path = request.getContextPath();%>

<script>
    function OpenInNewTab(url, title) {
        var win = window.open(url, '_blank');

        win.focus();
    }
</script>

<div class="logo"></div>
<div class="version">Software version 3.36.0</div>
<div class="headmenu">
    <h2 id="projects" class="headmenuclickers" onclick="OpenInNewTab('<%=path%>/project/snapshot', 'project');">
        <label id="lbl_projects">Project</label>
    </h2>
</div>
<div class="headmenu">
    <h2 id="stock" class="headmenuclickers" onclick="OpenInNewTab('<%=path%>/stock/view', 'stock');">
        <label>Stock</label>
    </h2>
</div>
<div class="headmenu">
    <h2 id="service_report" class="headmenuclickers" onclick="OpenInNewTab('<%=path%>/company/view', 'company');">
        <label>Company</label>
    </h2>
</div>
<div class="headmenu">
    <h2 id="logs" class="headmenuclickers" onclick="OpenInNewTab('<%=path%>/vessel/view', 'vessel');">
        <label>Vessel</label>
    </h2>
</div>
<div class="headmenu">
    <h2 id="logs" class="headmenuclickers" onclick="OpenInNewTab('<%=path%>/contact/view', 'contact');">
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
        <a href="<%=path%>"><img src="images/projectmanager/common/logout.png"></a>
    </div>
</div>
</div>