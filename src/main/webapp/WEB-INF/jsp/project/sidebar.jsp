<%-- 
    Document   : snapshot
    Created on : Oct 3, 2014, 1:10:53 AM
    Author     : antonia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>

<script>
    $(function () {
        $.ajax({
            type: "POST",
            url: "menu-info",
            success: function (response) {
                var content = JSON.parse(response);

                $(content.project_service_size_id).html(content.project_service_size);
                $(content.project_service_id).html(content.project_service_info);
                $(content.project_sale_size_id).html(content.project_sale_size);
                $(content.project_sale_id).html(content.project_sale_info);
                $(content.project_mts_size_id).html(content.project_mts_size);
                $(content.project_mts_id).html(content.project_mts_info);
            },
            error: function (e) {
            }
        });
    });
</script>

<nav>
    <div class="menu-item">
        <h3 id="project-new-size" onclick="window.location.href = '<%=path%>/project/new';">New</h3>
        <ul id="project-new"></ul>
    </div>
    <div class="menu-item">
        <h3 id="project-edit-size" onclick="window.location.href = '<%=path%>/project/edit-form';">Edit</h3>
        <ul id="project-edit"></ul>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/project/project-bill';">Bill</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/project/request-quotation';">Request for Quotation</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/project/purchase-order';">Purchase Order</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/project/work-order';">Work Order</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/project/order-acknowledge';">Order Acknowledge</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/project/packing-list';">Packing List</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/project/delivery-note';">Delivery Note</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/project/shipping-invoice';">Shipping Invoice</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/project/invoice';">Invoice</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/project/box-markings';">Box Markings</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/project/credit-note';">Credit Note</h3>
    </div>
    <div class="menu-item">
        <h3>Statistics</h3>
        <ul id="project-statistics">
            <li onclick="window.location.href = '<%=path%>/project/statistics?year=2007';">2007</li>
            <li onclick="window.location.href = '<%=path%>/project/statistics?year=2008';">2008</li>
            <li onclick="window.location.href = '<%=path%>/project/statistics?year=2009';">2009</li>
            <li onclick="window.location.href = '<%=path%>/project/statistics?year=2010';">2010</li>
            <li onclick="window.location.href = '<%=path%>/project/statistics?year=2011';">2011</li>
            <li onclick="window.location.href = '<%=path%>/project/statistics?year=2012';">2012</li>
            <li onclick="window.location.href = '<%=path%>/project/statistics?year=2013';">2013</li>
            <li onclick="window.location.href = '<%=path%>/project/statistics?year=2014';">2014</li>
            <li onclick="window.location.href = '<%=path%>/project/statistics?year=2015';">2015</li>
        </ul>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/project/history';">History</h3>
    </div>
</nav>