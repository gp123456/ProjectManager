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

<a href="#demo" data-toggle="collapse"><h1>Search Criteria</h1></a>
<!--<div id="demo" class="collapse">-->
<div class="searchCriteria"><jsp:include page="../project/searchCritiria.jsp"/></div>
<!--</div>-->
<h1>Results</h1>
<div class="formLayout">
    <table class="table tablesorter">
        <thead id="header"></thead>
        <tbody id="body"></tbody>
        <tfoot id="footer"></tfoot>
    </table>
    <div id="dlg-view-project" hidden="true" title="View Project">
        <form>
            <div><jsp:include page="HistoryBillMaterialService.jsp"/></div>
        </form>
    </div>
</div>