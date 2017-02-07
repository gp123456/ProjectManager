<%--
    Document   : new-project
    Created on : Jan 28, 2015, Jan 28, 2015 12:36:16 AM
    Author     : antonia
--%>

<script>
    $(function () {
        $("#expired").datepicker({dateFormat: 'dd/mm/yy'});

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
                            <select id="company" onchange="alarmEdit()"></select>
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
                            <select id="type" onchange="alarmEdit()"></select>
                        </label>
                    </p>
                </td>
                <td></td>
            </tr>
            <!- Expire date ->
            <tr>
                <td><label>Expired date</label></td>
                <td><input type="text" id="expired" value="${expired}" onchange="alarmEdit()"></td>
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
                                <input type="text" id="company-name" style="width: 360px" required>
                                <label class="go-bottom-label" for="company-name" style="width: 360px">Name</label>
                            </div>
                            <div>
                                <input type="text" id="company-email" style="width: 360px" required>
                                <label class="go-bottom-label" for="company-email" style="width: 360px">eMail</label>
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
                                <input type="text" id="vessel-name" name="vessel-name" style="width: 360px" required>
                                <label class="go-bottom-label" for="vessel-name" style="width: 360px">Name</label>
                            </div>
                            <div>
                                <input type="text" id="vessel-imo" name="vessel-imo" style="width: 360px" required>
                                <label class="go-bottom-label" for="vessel-imo" style="width: 360px">IMO</label>
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
                                <input type="text" id="contact-department" style="width: 360px" required>
                                <label class="go-bottom-label" for="contact-department" style="width: 360px">Department</label>
                            </div>
                            <div>
                                <input type="text" id="contact-name" style="width: 360px" required>
                                <label class="go-bottom-label" for="contact-name" style="width: 360px">Name</label>
                            </div>
                            <div>
                                <input type="text" id="contact-surname" style="width: 360px" required>
                                <label class="go-bottom-label" for="contact-surname" style="width: 360px">Surname</label>
                            </div>
                            <div>
                                <input type="text" id="contact-phone" style="width: 360px" required>
                                <label class="go-bottom-label" for="contact-phone" style="width: 360px">Phone</label>
                            </div>
                            <div>
                                <input type="text" id="contact-email" style="width: 360px" required>
                                <label class="go-bottom-label" for="contact-email" style="width: 360px">eMail</label>
                            </div>
                        </form>
                    </div>
                </td>
            </tr>
            <tr><td></td>
                <td><input type='button' class='button' id='new' onclick='location.reload();' value='Clear All' /></td>
                <td style="padding-top:10px; padding-bottom:10px">
                    ${button_save}
                </td>
            </tr>
        </tbody>
    </table>
    <!- Results ->
    <table class="table tablesorter">
        <thead id="header"></thead>
        <tbody id="body"></tbody>
    </table>
    <div id="dlg-sub-project" hidden="true" title="Select Sub Project">
        <form>
            <div id="lst-sub-project" class="radio"></div>
        </form>
    </div>
</div>
