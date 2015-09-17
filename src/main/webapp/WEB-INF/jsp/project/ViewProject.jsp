<%-- 
    Document   : ViewProjects
    Created on : Mar 1, 2015, Mar 1, 2015 12:55:56 AM
    Author     : antonia
--%>

<%String path = request.getContextPath();%>

<script>
    $(function () {
        refreshSearchContent();

        var info = "project=-1&type=none&status=none&company=none&vessel=-1&customer=none&offset=0&size=10";

        $.ajax({
            type: "POST",
            url: "view",
            data: info,
            success: function (response) {
                var content = JSON.parse(response);

                $("#project-header").html(content.project_header);
                $("#project-body").html(content.project_body);
                $("#project-footer").html(content.project_footer);
            },
            error: function (xhr, status, error) {
                alert(error);
            }
        });
    });
</script>
<style>
    .ui-menu { position: absolute; width: 100px; }
</style>

<div class="searchCriteria"><jsp:include page="../project/searchCritiria.jsp"/></div>
<div>
    <table class="table tablesorter">
        <thead id="project-header"></thead>
        <tbody id="project-body"></tbody>
        <tfoot id="project-footer"></tfoot>
    </table>
    <div>
        <div>
            <button id="print-to-2" onclick="printTo(2)">start</button>
            <button id="select-print-to-2" onclick="printTo(2)"></button>
        </div>
        <ul>
            <li>PDF</li>
            <li>XML</li>
        </ul>
    </div>
</div>