<%--
    Document   : new-project
    Created on : Jan 28, 2015, Jan 28, 2015 12:36:16 AM
    Author     : antonia
--%>

<script>
    $(function () {
        $("#new-project-expired").datepicker();

        var id = $('#edit-projectdetail-id').val();

        $.ajax({
            type: "POST",
            url: "content",
            data: "id=" + id,
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
            error: function (xhr, status, error) {
                alert(error);
            }
        });
    });
</script>

<div class="formLayout">
    <h1 id="project-reference">${project_reference}</h1>
    <input type="hidden" id="edit-projectdetail-id" value=${pd_id} />
    <table>
        <tbody>
            <!- Company ->
            <tr>
                <td><label>Company</label></td>
                <td>
                    <p>
                        <label class="custom-select">
                            <select id="new-project-company"></select>
                        </label>
                    </p>
                </td>
                <td></td>
            </tr>
            <!- Type ->
            <tr>
                <td><label>Type</label></td>
                <td>
                    <p>
                        <label class="custom-select">
                            <select id="new-project-type"></select>
                        </label>
                    </p>
                </td>
                <td></td>
            </tr>
            <!- Expire date ->
            <tr>
                <td><label>Expired date</label></td>
                <td><input type="text" id="new-project-expired" value="${expired}"></td>
                <td></td>
            </tr>
            <!- Vessel ->
            <tr>
                <td><label>Vessel</label></td>
                <td width="150px">
                    <p>
                        <label class="custom-select">
                            <select id="new-project-vessel" onchange="projectFilterVessel()"></select>
                        </label>
                    </p>
                </td>
                <td><input type="button" class="button" value="Add" id="new-project-add-vessel" onclick="addVessel()"/>
                    <div id="add-vessel" hidden="true" title="Add Vessel">
                        <form class="go-bottom">
                            <div>
                                <input type="text" id="vessel-name" name="vessel-name" required>
                                <label class="go-bottom-label" for="vessel-name">Name</label>
                            </div>
                            <div>
                                <input type="text" id="vessel-imo" name="vessel-imo" required>
                                <label class="go-bottom-label" for="vessel-imo">IMO</label>
                            </div>
                            <div>
                                <p>
                                    <label class="custom-select">
                                        <select id="new-vessel-customer"></select>
                                    </label>
                                    <input type="button" class="button" value="Add Customer" id="new-project-add-customer" onclick="addVesselCustomer()">
                                </p>
                            </div>
                        </form>
                    </div>
                </td>
            </tr>
            <!- Customer ->
            <tr>
                <td width="150px"><label>Customer</label></td>
                <td>
                    <p>
                        <label class="custom-select">
                            <select id="new-project-customer"></select>
                        </label>
                    </p>
                </td>
                <td><input type="button" class="button" value="Add" id="new-project-add-customer" onclick="addCustomer()"/>
                    <div id="add-customer" hidden="true" title="Add Customer">
                        <form class="go-bottom">
                            <div>
                                <input type="text" id="customer-name" required>
                                <label class="go-bottom-label" for="customer-name">Name</label>
                            </div>
                            <div>
                                <input type="text" id="customer-reference-number" required>
                                <label class="go-bottom-label" for="customer-reference-number">Reference Number</label>
                            </div>
                        </form>
                    </div>
                </td>
            </tr>
            <!- Contact ->
            <tr>
                <td width="150px"><label>Contact</label></td>
                <td>
                    <p>
                        <label class="custom-select">
                            <select id="new-project-contact"></select>
                        </label>
                    </p>
                </td>
                <td><input type="button" class="button" value="Add" id="new-project-add-contact" onclick="addContact()"/>
                    <div id="add-contact" hidden="true" title="Add Contact">
                        <form class="go-bottom">
                            <div>
                                <input type="text" id="contact-name" required>
                                <label class="go-bottom-label" for="contact-name">Name</label>
                            </div>
                            <div>
                                <input type="text" id="contact-surname" required>
                                <label class="go-bottom-label" for="contact-surname" >Surname</label>
                            </div>
                            <div>
                                <input type="text" id="contact-phone" required>
                                <label class="go-bottom-label" for="contact-phone">Phone</label>
                            </div>
                            <div>
                                <input type="text" id="contact-email" required>
                                <label class="go-bottom-label" for="contact-email">eMail</label>
                            </div>
                        </form>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
    <div class="critDivs">
        <input type="button" class="button" id="${project_button_id}" onclick="${project_button_action}" value=${project_button_value} />
    </div>
</div>
<!- Results ->
<table class="table tablesorter">
    <thead id="project-header"></thead>
    <tbody id="project-body"></tbody>
</table>