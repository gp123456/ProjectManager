<%-- 
    Document   : ProjectBill
    Created on : Aug 5, 2014, 11:45:05 PM
    Author     : user1
--%>

<script>
    $(function () {
        $("#expired").datepicker({dateFormat: 'dd/mm/yy'});

        var data = "id=" + $('#bill-project-id').val();

        $.ajax({
            type: "POST",
            url: "/ProjectManager/project/bill-material-service/content",
            data: data,
            success: function (response) {
                var content = JSON.parse(response);

                $("#subproject").html(content.subprojects);
                $("#item").html(content.items);
                $("#supplier").html(content.suppliers);
                $("#bill-material-service").html(content.billMaterialService);
                $("#note").val(content.noteBillMaterialService);
                $("#bill-material-service-item").html(content.billMaterialServiceItems);
                $("#info-type").val(content.type);
                $("#company").val(content.company);
                $("#customer").val(content.customer);
                $("#vessel").val(content.vessel);
                $("#contact").val(content.contact);
            },
            error: function (xhr, status, error) {
                alert(error);
            }
        });
    });
</script>

<div id="bill-header" class="formLayout">
    <h1>Bill of materials or services - REF:${project_reference}</h1>
    <input type="hidden" id="bill-project-id" value=${p_id} />
    <div style="overflow-y: scroll">
        <h3>Project Info</h3>
        <table>
            <tbody>
                <tr>
                    <td><label>Type</label></td>
                    <td><input type="text" id="info-type" readonly></td>
                </tr>
                <tr>
                    <td><label>Company</label></td>
                    <td><input type="text" id="company" readonly></td>
                </tr>
                <tr>
                    <td><label>Customer</label></td>
                    <td><label class="custom-select"><input type="text" id="customer" readonly></label></td>
                </tr>
                <tr>
                    <td><label>Vessel</label></td>
                    <td><label class="custom-select"><input type="text" id="vessel" readonly></label></td>
                </tr>
                <tr>
                    <td><label>Contact</label></td>
                    <td><label class="custom-select"><input type="text" id="contact" readonly></label></td>
                </tr>
            </tbody>
        </table>
        <h3>Select Subproject</h3>
        <table>
            <tbody>
                <tr>
                    <td>
                        <label class="custom-select-large">
                            <select id="subproject" onchange="getProjectBillItems()"></select>
                        </label>
                    </td>
                    <td>
                        <input type="button" class="button" value="Add New Subproject" onclick="addSubProject()"/>
                    </td>
                </tr>
            </tbody>
        </table>
        <div class="formLayout" hidden="true" id="new-subproject" title="Add New Subproject">
            <p class="validateTips">All form fields are required.</p>
            <table>
                <tbody>
                    <!- Company ->
                    <tr>
                        <td><label>Company</label></td>
                        <td>
                            <label class="custom-select">
                                <select id="subproject-company"></select>
                            </label>
                        </td>
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
                    </tr>
                    <!- Expire date ->
                    <tr>
                        <td><label>Expired date</label></td>
                        <td><input type="text" id="expired" ></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br><h3>Select Item</h3>
        <table>
            <tbody>
                <tr>
                    <td>
                        <label class="custom-select-large">
                            <select id="item"></select>
                        </label>
                    </td>
                    <td>
                        <input type="button" class="button" value="Submit" onclick="insertItem()"/>
                    </td>
                    <td>
                        <input type="button" class="button" value="Add New Item" onclick="addItem()"/>
                        <div id="add-item" hidden="true" title="Add New Item">
                            <form class="go-bottom">
                                <p class="validateTips" id="validate-add-item">All form fields are required.</p>
                                <div>
                                    <input type="text" id="item-imno" required>
                                    <label class="go-bottom-label" for="item-imno">IMNO</label>
                                </div>
                                <div>
                                    <input type="text" id="item-desc" required>
                                    <label class="go-bottom-label" for="item-desc">Description</label>
                                </div>
                                <div>
                                    <input type="text" id="item-quantity" required>
                                    <label class="go-bottom-label" for="item-quantity">Quantity</label>
                                </div>
                                <div>
                                    <input type="text" id="item-price" required>
                                    <label class="go-bottom-label" for="item-price">Price</label>
                                </div>
                                <p>
                                    <label class="custom-select">
                                        <select id="item-currency"></select>
                                    </label>
                                </p>
                                <p>
                                    <label class="custom-select">
                                        <select id="item-location"></select>
                                    </label>
                                </p>
                                <p>
                                    <label class="custom-select">
                                        <select id="item-supplier"></select>
                                    </label>
                                </p>
                            </form>
                        </div>
                        <input type="hidden" id="item_id" name="id"/>
                    </td>
                </tr>
            </tbody>
        </table>
        <table>
            <tbody>
                <tr>
                    <td><label>Availability</label></td>
                    <td><input type="text" id="availability" readonly></td>
                </tr>
                <tr>
                    <td><label>Price</label></td>
                    <td><input type="text" id="price" readonly></td>
                </tr>
            </tbody>
        </table>
    </div>
    <h2>Bill of Material Summary</h2>
    <div>
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th style="display:none">Project Id</th>
                    <th>Name*</th>
                    <th>Subproject</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody id="bill-material-service"></tbody>
        </table>
    </div>
    <div id="replace-bill-material-service" hidden="true" title="Add Bill Material or Service Items">
        <form class="go-bottom">
            <div id="lst-bill-material-service-item"></div>
        </form>
    </div>
    <h2>Bill of Material Detail</h2>
    <div>
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Code</th>
                    <th>Stock</th>
                    <th>Quantity*</th>
                    <th>Edit</th>
                    <th>Save</th>
                    <th>Remove</th>
                </tr>
            </thead>
            <tbody id="bill-material-service-item"></tbody>
        </table>
    </div>
    <div><h2>Note</h2><textarea id="note" name="notes" rows="10" style="width: 100%"></textarea></div>
    <div>${button_save}${button_save_pdf}${button_save_excel}${button_send_email}</div>
    <div>${button_action_message}</div>
</div>