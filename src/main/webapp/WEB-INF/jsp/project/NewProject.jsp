<%--
    Document   : new-project
    Created on : Jan 28, 2015, Jan 28, 2015 12:36:16 AM
    Author     : antonia
--%>

<%String path = request.getContextPath();%>

<script>
    $(function () {
        refreshSearchContent();

        var id = $("#project-edit-id").attr("value");
        var data = "id=" + id + "&type=-1&status=-1&vessel=-1&customer=-1&company=-1&offset=0&size=10";

        $.ajax({
            type: "POST",
            url: "content",
            data: data,
            success: function (response) {
                var content = JSON.parse(response);

                $("#company").html(content.company);
                $("#vessel").html(content.vessel);
                $("#customer").html(content.customer);
                if (content.project_header != null && content.project_body != null) {
                    $("#project-header").html(content.project_header);
                    $("#project-body").html(content.project_body);
                    $("#project-save").hide();
                } else {
                    $("#project-save").show();
                }
            },
            error: function (e) {
            }
        });
    });
</script>

<div class="searchCriteria"><jsp:include page="../project/searchCritiria.jsp"/></div>
<input type="hidden" id="project-edit-id" value="${project_id}"/>
<h1 id="project-reference1">${project_reference}</h1>
<div class="criteriacontentup">
    <div class="critDivs custom">
        <label style="font: icon;size: 12">Company</label>
        <select id="company"></select>
    </div>
    <div class="critDivs custom">
        <label style="font: icon;size: 12">Vessel</label>
        <select id="vessel"></select>
    </div>
    <div class="critDivs custom">
        <label style="font: icon;size: 12">Customer</label>
        <select id="customer"></select>
    </div>
    <div class="critDivs custom">
        <label style="font: icon;size: 12">Contact</label>
        <input id="contact" type="text"></input>
    </div>
    <div>
        <table class="table tablesorter">
            <thead id="project-header"></thead>
            <tbody id="project-body"></tbody>
        </table>
    </div>
    <div class="critDivs">
        <input type="button" value="Save" id="project-save" onclick="saveProject()"/>
    </div>
</div>
