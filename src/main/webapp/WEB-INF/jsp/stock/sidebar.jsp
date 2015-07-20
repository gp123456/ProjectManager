
<%-- 
    Document   : snapshot
    Created on : Oct 3, 2014, 1:10:53 AM
    Author     : antonia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>

<nav>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/stock/new';">New</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/stock/edit';">Edit</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/stock/bill';">Stock Bill</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/stock/request-quotation';">Request for Quotation</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/stock/purchase-order';">Purchase Order</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/stock/qc-check-logging';">QC Check & Logging</h3>
    </div>
    <div class="menu-item">
        <h3>Statistics</h3>
        <ul id="project-statistics">
            <li onclick="window.location.href = '<%=path%>/stock/statistic?year=2007';">2007</li>
            <li onclick="window.location.href = '<%=path%>/stock/statistic?year=2008';">2008</li>
            <li onclick="window.location.href = '<%=path%>/stock/statistic?year=2009';">2009</li>
            <li onclick="window.location.href = '<%=path%>/stock/statistic?year=2010';">2010</li>
            <li onclick="window.location.href = '<%=path%>/stock/statistic?year=2011';">2011</li>
            <li onclick="window.location.href = '<%=path%>/stock/statistic?year=2012';">2012</li>
            <li onclick="window.location.href = '<%=path%>/stock/statistic?year=2013';">2013</li>
            <li onclick="window.location.href = '<%=path%>/stock/statistic?year=2014';">2014</li>
            <li onclick="window.location.href = '<%=path%>/stock/statistic?year=2015';">2015</li>
        </ul>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/stock/history';">History</h3>
    </div>
</nav>
