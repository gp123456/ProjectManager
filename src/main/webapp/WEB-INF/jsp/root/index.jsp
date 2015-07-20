<%-- 
    Document   : index
    Created on : Oct 3, 2014, 1:51:08 AM
    Author     : antonia
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%String path = request.getContextPath();%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${title}</title>
        
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/core/main.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/core/header.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/menu.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/table.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/views/challengecms.css">

        <script type="text/javascript" src="<%=path%>/js/projectmanager/jquery.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/ui.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/chosen.jquery.min.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/jquery.dataTables.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/jstree/jquery.jstree.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/critiria.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/expandAvailEvents.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/getToggleInput.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/setCustomDurationChallenge.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/layout.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/themeFunctions.js"></script>
        
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/project.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/ProjectBill.js"></script>
    </head>
    <body>
        <div id="container">
            <div class="top-bar"><jsp:include page="header.jsp"/></div>
            <div class="maincontent">
                <div class="leftmenucontainer">
                    <jsp:include page="${side_bar}"/>
                </div>
                <div class="content"><jsp:include page="${content}"/></div>
            </div>
        </div>
    </body>
</html>
