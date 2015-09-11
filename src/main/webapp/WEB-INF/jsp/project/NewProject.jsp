<%--
    Document   : new-project
    Created on : Jan 28, 2015, Jan 28, 2015 12:36:16 AM
    Author     : antonia
--%>

<%String path = request.getContextPath();%>

<script>
    $(function () {
        $("#new-project-expired").datepicker();
        
        refreshSearchContent();

        var id = $("#project-edit-id").attr("value");
        var data = "project=-1&type=none&status=none&company=&vessel=-1&customer=&offset=0&size=10";

        $.ajax({
            type: "POST",
            url: "content",
            data: data,
            success: function (response) {
                var content = JSON.parse(response);

                $("#new-project-company").html(content.company);
                $("#new-project-type").html(content.type);
                $("#new-project-vessel").html(content.vessel);
                $("#new-project-customer").html(content.customer);
                $("#new-project-contact").html(content.contact);
                if (content.project_header != null && content.project_body != null) {
                    $("#project-header").html(content.project_header);
                    $("#project-body").html(content.project_body);
                }
            },
            error: function (e) {
            }
        });
    });
</script>

<div><jsp:include page="../project/searchCritiria.jsp"/></div>
<input type="hidden" id="project-edit-id" value="${project_reference}"/>
<h1 id="project-reference">${project_reference}</h1>
<div>
    <div class="critDivs">
        <label style="font: icon;size: 12">Company</label>
        <select id="new-project-company"></select>
    </div>
    <div class="critDivs">
        <label style="font: icon;size: 12">Type</label>
        <select id="new-project-type"></select>
    </div>
    <div class="critDivs">
        <label style="font: icon;size: 12">Expired date</label>
        <input type="text" id="new-project-expired" value="${expired}">
    </div>
    <div class="critDivs">
        <label style="font: icon;size: 12">Vessel</label>
        <select id="new-project-vessel" onchange="projectFilterVessel()"></select>
        <input type="button" value="Add" id="new-project-add-vessel" onclick="addContact('<%=path%>/vessel/add')"/>
    </div>
    <div class="critDivs">
        <label style="font: icon;size: 12">Customer</label>
        <select id="new-project-customer"></select>
        <input type="button" value="Add" id="new-project-add-customer" onclick="addContact('<%=path%>/company/add')"/>
    </div>
    <div class="critDivs">
        <label style="font: icon;size: 12">Contact</label>
        <select id="new-project-contact"></select>
        <input type="button" value="Add" id="new-project-add-contact" onclick="addContact('<%=path%>/contact/add')"/>
    </div>
    <div class="critDivs">
        <input type="button" value="Save" id="project-save" onclick="saveProject()"/>
    </div>
    <table class="table tablesorter">
        <thead id="project-header"></thead>
        <tbody id="project-body"></tbody>
    </table>
</div>
