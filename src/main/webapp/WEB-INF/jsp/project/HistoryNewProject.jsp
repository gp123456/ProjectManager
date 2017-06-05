<%-- 
    Document   : History
    Created on : Aug 5, 2014, 11:45:05 PM
    Author     : user1
--%>

<script>
    $(function () {
        fillSearchCriteriaProject("new");
        setTimeout(function () {
            location.reload(1);
        }, 600000);
    });
</script>

<div id="default-example" data-collapse>
    <h2>
        <table>
            <tbody>
                <tr>
                    <td>Search Criteria</td>
                    <td><input type="button" class="button" value="Clear" id="search-clear" onclick="searchClear()"/></td>
                </tr>
            </tbody>
        </table>
    </h2>
    <div class="searchCriteria"><jsp:include page="../project/searchCritiria.jsp"/></div>
</div>
<h2 id="count">Results
    <input type="button" class="button" value="Create Excel" onclick="createAllExcel()"/>
</h2>
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
