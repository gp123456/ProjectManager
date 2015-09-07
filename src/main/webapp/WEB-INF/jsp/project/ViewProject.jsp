<%-- 
    Document   : ViewProjects
    Created on : Mar 1, 2015, Mar 1, 2015 12:55:56 AM
    Author     : antonia
--%>

<%String path = request.getContextPath();%>

<script>
    $(function () {
        refreshSearchContent();
//        var project_detail = {
//            project: -1,
//            type: "none",
//            status: "none",
//            company: "none",
//            vessel: -1,
//            customer: "none"
//        };
//        var info = {
//            prjd: project_detail,
//            offset: 0,
//            size: 10
//        };

//        info = JSON.stringify(info);

        var info = "project=-1&type=none&status=none&company=none&vessel=-1&customer=none&offset=0&size=10";

        $.ajax({
//            contentType: "application/json",
//            dataType: "json",
            type: "POST",
            url: "view",
            data: info,
            success: function (response) {
                var content = JSON.parse(response);
                $("#project-header").html(content.project_header);
                $("#project-footer").html(content.project_footer);
                $("#project-body").html(content.project_body);
            },
            error: function (xhr, status, error) {
                alert(error);
            }
        });
    });
</script>

<jsp:include page="../project/searchCritiria.jsp"/>
<div>
    <table class="table tablesorter">
        <thead id="project-header"></thead>
        <tfoot id="project-footer"></tfoot>
        <tbody id="project-body"></tbody>
    </table>
</div>