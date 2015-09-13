<%--
    Document   : new-project
    Created on : Jan 28, 2015, Jan 28, 2015 12:36:16 AM
    Author     : antonia
--%>

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
        <label style="font:icon;size:12;display:block">Company</label>
        <select id="new-project-company"></select>
    </div>
    <div class="critDivs">
        <label style="font:icon;size:12;display:block">Type</label>
        <select id="new-project-type"></select>
    </div>
    <div class="critDivs">
        <label style="font:icon;size:12;display:block">Expired date</label>
        <input type="text" id="new-project-expired" value="${expired}">
    </div>
    <div class="critDivs">
        <label style="font:icon;size:12;display:block">Vessel</label>
        <select id="new-project-vessel" onchange="projectFilterVessel()"></select>
        <input type="button" value="Add" id="new-project-add-vessel" onclick="addVessel()"/>
        <div id="add-vessel" hidden="true" title="Add Vessel">
            <p class="validateTips">All form fields are required.</p>
            <form>
                <fieldset style="padding:0; border:0; margin-top:25px;">
                    <label for="name" style="display:block">Name</label>
                    <input type="text" id="vessel-name" style="display:block; margin-bottom:12px; width:95%; padding:.4em">
                    <label for="surname" style="display:block">IMO</label>
                    <input type="text" id="vessel-imo" style="display:block; margin-bottom:12px; width:95%; padding:.4em">
                    <input type="submit" tabindex="-1" style="position:absolute; top:-1000px; display:block">
                </fieldset>
            </form>
        </div>
    </div>
    <div class="critDivs">
        <label style="font:icon;size:12;display:block">Customer</label>
        <select id="new-project-customer"></select>
        <input type="button" value="Add" id="new-project-add-customer" onclick="addCustomer()"/>
        <div id="add-customer" hidden="true" title="Add Customer">
            <p class="validateTips">All form fields are required.</p>
            <form>
                <fieldset style="padding:0; border:0; margin-top:25px;">
                    <label for="name" style="display:block">Name</label>
                    <input type="text" id="customer-name" style="display:block; margin-bottom:12px; width:95%; padding:.4em">
                    <label for="surname" style="display:block">Reference Number</label>
                    <input type="text" id="customer-reference-number" style="display:block; margin-bottom:12px; width:95%; padding:.4em">
                    <input type="submit" tabindex="-1" style="position:absolute; top:-1000px; display:block">
                </fieldset>
            </form>
        </div>
    </div>
    <div class="critDivs">
        <label style="font:icon;size:12;display:block">Contact</label>
        <select id="new-project-contact"></select>
        <input type="button" value="Add" id="new-project-add-contact" onclick="addContact()"/>
        <div id="add-contact" hidden="true" title="Add Contact">
            <p class="validateTips">All form fields are required.</p>
            <form>
                <fieldset style="padding:0; border:0; margin-top:25px;">
                    <label for="name" style="display:block">Name</label>
                    <input type="text" id="contact-name" style="display:block; margin-bottom:12px; width:95%; padding:.4em">
                    <label for="surname" style="display:block">Surname</label>
                    <input type="text" id="contact-surname" style="display:block; margin-bottom:12px; width:95%; padding:.4em">
                    <label for="phone" style="display:block">Phone</label>
                    <input type="text" id="contact-phone" style="display:block; margin-bottom:12px; width:95%; padding:.4em">
                    <label for="email" style="display:block">eMail</label>
                    <input type="text" id="contact-email" style="display:block; margin-bottom:12px; width:95%; padding:.4em">
                    <input type="submit" tabindex="-1" style="position:absolute; top:-1000px; display:block">
                </fieldset>
            </form>
        </div>
    </div>
    <div class="critDivs">
        <input type="button" value="Save" id="project-save" onclick="saveProject()"/>
    </div>
    <table class="table tablesorter">
        <thead id="project-header"></thead>
        <tbody id="project-body"></tbody>
    </table>
</div>
