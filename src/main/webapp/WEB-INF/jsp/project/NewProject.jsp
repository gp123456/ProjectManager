<%--
    Document   : new-project
    Created on : Jan 28, 2015, Jan 28, 2015 12:36:16 AM
    Author     : antonia
--%>

<script>
    $(function () {
        $("#expired").datepicker({dateFormat:'dd/mm/yy'});

        var id = $('#edit-project-id').val();

        $.ajax({
            type: "POST",
            url: "content",
            data: "id=" + id,
            success: function (response) {
                var content = JSON.parse(response);

                $("#company").html(content.company);
                $("#type").html(content.type);
                $("#vessel").html(content.vessel);
                $("#customer").html(content.customer);
                $("#contact").html(content.contact);
                if (content.header != null && content.body != null) {
                    $("#header").html(content.header);
                    $("#body").html(content.body);
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
    <input type="hidden" id="edit-project-id" value=${p_id} />
    <table>
        <tbody>
            <!- Company ->
            <tr>
                <td><label>Company</label></td>
                <td>
                    <p>
                        <label class="custom-select">
                            <select id="company"></select>
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
                            <select id="type"></select>
                        </label>
                    </p>
                </td>
                <td></td>
            </tr>
            <!- Expire date ->
            <tr>
                <td><label>Expired date</label></td>
                <td><input type="text" id="expired" value="${expired}"></td>
                <td></td>
            </tr>
            <!- Customer ->
            <tr>
                <td width="150px"><label>Customer</label></td>
                <td width="300px">
                    <p>
                        <label class="custom-select-large">
                            <select id="customer" onchange="projectFilterCustomer()"></select>
                        </label>
                    </p>
                </td>
                <td><input type="button" class="button" value="Add" id="new-project-add-customer" onclick="addCompany('CUSTOMER')"/>
                    <div id="add-company" hidden="true" title="Add Customer">
                        <form class="go-bottom">
                            <div>
                                <input type="text" id="company-name" required>
                                <label class="go-bottom-label" for="company-name">Name</label>
                            </div>
                            <div>
                                <input type="text" id="company-email" required>
                                <label class="go-bottom-label" for="company-email">eMail</label>
                            </div>
                        </form>
                    </div>
                </td>
            </tr>
            <!- Vessel ->
            <tr>
                <td><label>Vessel</label></td>
                <td width="150px">
                    <p>
                        <label class="custom-select-large">
                            <select id="vessel"></select>
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
                        </form>
                    </div>
                </td>
            </tr>
            <!- Contact ->
            <tr>
                <td width="150px"><label>Contact</label></td>
                <td>
                    <p>
                        <label class="custom-select-large">
                            <select id="contact"></select>
                        </label>
                    </p>
                </td>
                <td><input type="button" class="button" value="Add" id="new-project-add-contact" onclick="addContact()"/>
                    <div id="add-contact" hidden="true" title="Add Contact">
                        <form class="go-bottom">
                            <div>
                                <input type="text" id="contact-department" required>
                                <label class="go-bottom-label" for="contact-department">Department</label>
                            </div>
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
    <input type="button" class="button" id="${button_id}" onclick="${button_action}" value=${button_value} />
    <!- Results ->
    <table class="table tablesorter">
        <thead id="header"></thead>
        <tbody id="body"></tbody>
    </table>
</div>
