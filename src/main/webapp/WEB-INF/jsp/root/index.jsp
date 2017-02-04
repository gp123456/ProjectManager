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
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/core/checkbox.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/core/radio-button.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/core/form-layout.css">
       <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/image.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/menu.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/projectmanager/table.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/js/projectmanager/jquery-ui-1.12.1.custom/jquery-ui.min.css">

        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.0.272/jspdf.debug.js"></script>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/canvasjs-1.7.0/canvasjs.min.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/jquery.collapse.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/Project.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/BillMaterialService.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/RequestQuotation.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/Quotation.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/contact.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/company.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/vessel.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/common.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/debug.js"></script>
        <script type="text/javascript" src="<%=path%>/js/projectmanager/common/jqClock.min.js"></script>
    </head>
    <body>
        <div id="container">
            <div class="top-bar"><jsp:include page="${project_header}"/></div>
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
                            <c:choose>
                                <c:when test="${classContent != null}" >
                                    <div class="${classContent}"><jsp:include page="${content}"/></div>
                                </c:when>
                                <c:otherwise>
                                    <div class="content"><jsp:include page="${content}"/></div>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
</html>
