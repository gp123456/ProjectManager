<%-- 
    Document   : index
    Created on : Oct 3, 2014, 1:51:08 AM
    Author     : antonia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%String path = request.getContextPath();%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${title}</title>

        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/core/main.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/core/header.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/core/input-text.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/core/select.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/core/button.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/menu.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/table.css">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

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
        <script type="text/javascript" src="<%=path%>/js/projectmanager/canvasjs-1.7.0/canvasjs.min.js"></script>

        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/project.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/ProjectBill.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/contact.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/company.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/vessel.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/common.js"></script>
    </head>
    <body>
        <div id="container">
            <c:if test="${project_header != null}" >
                <div class="top-bar"><jsp:include page="${project_header}"/></div>
            </c:if>
            <div class="maincontent">
                <c:if test="${side_bar != null}" >
                    <div class="leftmenucontainer">
                        <jsp:include page="${side_bar}"/>
                    </div>
                </c:if>
                <c:choose>
                    <c:when test="${login != null}" >
                        <div class="login-content"><jsp:include page="${content}"/></div>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${content != null}" >
                            <div class="content"><jsp:include page="${content}"/></div>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
</html>
