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

<!--style="display:block; margin-bottom:12px; width:95%; padding:.4em"-->

<h1 id="project-reference">${project_reference}</h1>
<input type="hidden" id="edit-projectdetail-id" value=${pd_id} />
<div>
    <table>
        <tbody>
            <tr>
                <td><label style="font:icon;size:12;display:block">Company</label></td>
                <td>
                    <p>
                        <label class="custom-select">
                            <select id="new-project-company"></select>
                        </label>
                    </p>
                </td>
                <td></td>
            </tr>
            <tr>
                <td><label style="font:icon;size:12;display:inline">Type</label></td>
                <td>
                    <p>
                        <label class="custom-select">
                            <select id="new-project-type"></select>
                        </label>
                    </p>
                </td>
                <td></td>
            </tr>
            <tr>
                <td><label style="font:icon;size:12;display:block">Expired date</label></td>
                <td><input type="text" id="new-project-expired" value="${expired}"></td>
                <td></td>
            </tr>
            <tr>
                <td><label style="font:icon;size:12;display:block">Vessel</label></td>
                <td>
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
                                <label for="vessel-name">Name</label>
                            </div>
                            <div>
                                <input type="text" id="vessel-company" required>
                                <label for="vessel-company">Company</label>
                            </div>
                            <div>
                                <input type="text" id="vessel-imo" name="vessel-imo" required>
                                <label for="vessel-imo">IMO</label>
                            </div>
                            <input type="submit" tabindex="-1" style="position:absolute; top:-1000px; display:block">
                        </form>
                    </div>
                </td>
            </tr>
            <tr>
                <td><label style="font:icon;size:12;display:block">Customer</label></td>
                <td>
                    <p>
                        <label class="custom-select">
                            <select id="new-project-customer" onchange="projectFilterVessel()"></select>
                        </label>
                    </p>
                </td>
                <td><input type="button" class="button" value="Add" id="new-project-add-customer" onclick="addCustomer()"/>
                    <div id="add-customer" hidden="true" title="Add Customer">
                        <form class="go-bottom">
                            <div>
                                <input type="text" id="customer-name" required>
                                <label for="customer-name">Name</label>
                            </div>
                            <div>
                                <input type="text" id="customer-reference-number" required>
                                <label for="customer-reference-number">Reference Number</label>
                            </div>
                            <input type="submit" tabindex="-1" style="position:absolute; top:-1000px; display:block">
                        </form>
                    </div>
                </td>
            </tr>
            <tr>
                <td><label style="font:icon;size:12;display:block">Contact</label></td>
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
                                <label for="contact-name">Name</label>
                            </div>
                            <div>
                                <input type="text" id="contact-surname" required>
                                <label for="contact-surname" >Surname</label>
                            </div>
                            <div>
                                <input type="text" id="contact-phone" required>
                                <label for="contact-phone">Phone</label>
                            </div>
                            <div>
                                <input type="text" id="contact-email" required>
                                <label for="contact-email">eMail</label>
                            </div>
                            <input type="submit" class="button" tabindex="-1" style="position:absolute; top:-1000px; display:block">
                        </form>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
    <div class="critDivs">
        <input type="button" class="button" id="${project_button_id}" onclick="${project_button_action}" value=${project_button_value} />
    </div>
    <table class="table tablesorter">
        <thead id="project-header"></thead>
        <tbody id="project-body"></tbody>
    </table>
</div>
