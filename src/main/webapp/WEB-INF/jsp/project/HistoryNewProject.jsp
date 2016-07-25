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

<!--<h1>History of Projects</h1>-->
<div class="searchCriteria"><jsp:include page="../project/searchCritiria.jsp"/></div>
<div class="formLayout">
    <table class="table tablesorter">
        <thead id="header"></thead>
        <tbody id="body"></tbody>
        <tfoot id="footer"></tfoot>
    </table>
</div>