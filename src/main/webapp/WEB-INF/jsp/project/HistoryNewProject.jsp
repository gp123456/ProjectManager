<%-- 
    Document   : History
    Created on : Aug 5, 2014, 11:45:05 PM
    Author     : user1
--%>

<script>
    $(function () {
        fillSearchCriteriaProject("new");
    });
</script>

<div id="default-example" data-collapse>
    <h1>Search Criteria</h1>
    <div class="searchCriteria"><jsp:include page="../project/searchCritiria.jsp"/></div>
</div>
<h1 id="count">Results
    <input type="button" class="button" value="Create Excel" onclick="createAllExcel()"/>
</h1>
<div class="formLayout">
    <input type="hidden" id="project-detail-id"/>
    <table class="table tablesorter">
        <thead id="header"></thead>
        <tbody id="body"></tbody>
        <tfoot id="footer"></tfoot>
    </table>
    <div id="dlg-view-project" hidden="true" title="View Project">
        <input type="button" class="button" value="Create Excel" onclick="createExcel($('#project-detail-id').val())"/>
        <form>
            <div><jsp:include page="HistoryBillMaterialService.jsp"/></div>
        </form>
    </div>
</div>