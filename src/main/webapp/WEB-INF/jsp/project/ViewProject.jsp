<%-- 
    Document   : ViewProjects
    Created on : Mar 1, 2015, Mar 1, 2015 12:55:56 AM
    Author     : antonia
--%>

<%String path = request.getContextPath();%>

<script>
    $(function () {
        refreshSearchContent();

        var data = "id=-1&type=-1&status=-1&vessel=-1&customer=-1&company=-1&offset=0&size=10";

        $.ajax({
            type: "POST",
            url: "view",
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
<div>
    <table class="table tablesorter">
        <thead id="project-header"></thead>
        <tfoot id="project-footer"></tfoot>
        <tbody id="project-body"></tbody>
    </table>
</div>