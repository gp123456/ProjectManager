<%-- 
    Document   : ProjectBill
    Created on : Aug 5, 2014, 11:45:05 PM
    Author     : user1
--%>

<%@page import="com.allone.projectmanager.entities.Item"%>
<%@page import="com.allone.projectmanager.entities.ProjectBillItem"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>

<%
    String path = request.getContextPath();
    Object obj = request.getAttribute("items");
    List<Item> infoItem = (obj != null) ? (List<Item>) obj : null;

    obj = request.getAttribute("project_reference");

    String prj_reference = (obj != null) ? (String) obj : null;
%>

<script>
    $(function () {
        refreshSearchContent();
        
        var data = "id=-1&type=1&status=-1&vessel=-1&customer=-1&company=-1&offset=0&size=10&mode=view";

        $.ajax({
            type: "POST",
            url: "search",
            data: data,
            success: function (response) {
                var content = JSON.parse(response)

                $("#project-header").html(content.project_header);
                $("#project-footer").html(content.project_footer);
                $("#project-body").html(content.project_body);
            },
            error: function (e) {
            }
        });
    });
</script>

<div class="searchCriteria"><jsp:include page="../project/searchCritiria.jsp"/></div>
<h3>Select Item</h3>
<div style="height: 200px; overflow-y: scroll">
    <select id="project" data-placeholder="Choose type" tabindex="1" onchange="insertItem(this)">
        <option value="-1" selected="selected">Select</option>
        <%
            if (infoItem != null && infoItem.isEmpty() == false && infoItem.get(0) != null) {
                for (Iterator<Item> it = infoItem.iterator(); it.hasNext();) {
                    Item i = it.next();
        %>
        <option value="<%=i.getId()%>"><%=i.getImno()%>[<%=i.getQuantity()%>,<%=i.getPrice()%>]</option>
        <%
                }
            }
        %>
    </select>
    <input type="hidden" id="item_id" name="id"/>
</div>
<h1>Project Bill - REF:<%=prj_reference%></h1>
<div>
    <table class="table tablesorter">
        <thead>
            <tr>
                <th>Total Cost(?)</th>
                <th>Average Discount(%)</th>
                <th>Sales Price(?)</th>
                <th>Total Net Price(?)</th>
                <th>Edit</th>
                <th>Delete</th>
                <th>Save to PDF</th>
                <th>Print to PDF</th>
                <th>Save to Excel</th>
                <th>Send eMail</th>
            </tr>
        </thead>
        <tbody id="project-bill"></tbody>
    </table>
</div>
<h2>Project Bill Items</h2>
<div>
    <table class="table tablesorter">
        <thead>
            <tr>
                <th>Code</th>
                <th>Description</th>
                <th>Available</th>
                <th>Price(?)</th>
                <th>Quantity</th>
                <th>Cost/PC(?)</th>
                <th>Total Cost(?)</th>
                <th>Percentage(%)</th>
                <th>Discount(%)</th>
                <th>Sales Price/PC(?)</th>
                <th>Sales Price(?)</th>
                <th>Total Net Price(?)</th>
                <th>Refresh</th>
                <th>Save</th>
                <th>Remove</th>
            </tr>
        </thead>
        <tbody id="project-bill-items"></tbody>
    </table>
</div>
<div><p><label>Express</label></p><textarea id="express" name="express" rows="4" style="width: 100%"></textarea></div>
<div><p><label>Notes</label></p><textarea id="notes" name="notes" rows="10" style="width: 100%"></textarea></div>
<div class="critDivs" style="left:20px;">
<!--    <input type="button" class="btnsubmit content_align" value="New" id="inquiry-quotation-xls" onclick=""/>-->
    <input type="button" class="btnsubmit content_align" value="Save" id="inquiry-quotation-xls" onclick="saveProjectBill()"/>
</div>
<div>
    <label>Open Projects</label>
    <table class="table tablesorter">
        <thead id="project-header"></thead>
        <tfoot id="project-footer"></tfoot>
        <tbody id="project-body"></tbody>
    </table>
</div>