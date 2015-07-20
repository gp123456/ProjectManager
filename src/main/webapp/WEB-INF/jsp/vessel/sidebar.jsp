<%-- 
    Document   : snapshot
    Created on : Oct 3, 2014, 1:10:53 AM
    Author     : antonia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>

<nav>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/vessel/new';">New</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/vessel/edit';">Edit</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/vessel/history';">History</h3>
    </div>
</nav>
