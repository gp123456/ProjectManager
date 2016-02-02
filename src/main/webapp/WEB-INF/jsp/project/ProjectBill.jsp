<%-- 
    Document   : ProjectBill
    Created on : Aug 5, 2014, 11:45:05 PM
    Author     : user1
--%>

<%@page import="com.allone.projectmanager.entities.Item"%>
<%@page import="com.allone.projectmanager.entities.ProjectBillItem"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>

<script>
    $(function () {
        var data = "id=" + $('#bill-project-id').val();

        $.ajax({
            type: "POST",
            url: "/ProjectManager/project/project-bill/content",
            data: data,
            success: function (response) {
                var content = JSON.parse(response);

                $("#bill-subproject").html(content.subprojects);
                $("#bill-item").html(content.items);
                $("#project-bill").html(content.projectBill);
                $("#project-bill-items").html(content.projectBillItems);
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
        <h3>Select Subproject</h3>
        <p>
            <label class="custom-select">
                <select id="bill-subproject" onchange="getProjectBillItems()"></select>
            </label>
        </p>
        <input type="button" class="button" value="Add New Subproject" id="project-bill-add-item" onclick="addSubProject()"/>
        <div class="formLayout" id="new-subproject" hidden="true" title="Add New Subproject">
            <p class="validateTips">All form fields are required.</p>
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
                        <td><input type="date" id="new-project-expired"></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br><h3>Select Item</h3>
        <p>
            <label class="custom-select">
                <select id="bill-item" onchange="insertItem(this)"></select>
            </label>
        </p>
        <input type="button" class="button" value="Add New Item" id="project-bill-add-item" onclick="addItem()"/>
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
    </div>
    <h2>Bill of Material Summary</h2>
    <div>
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th style="display:none">Project Id</th>
                    <th>Total Cost</th>
                    <th>Average Discount(%)</th>
                    <th>Sales Price</th>
                    <th>Total Net Price</th>
                    <th>Currency</th>
                    <th>Location</th>
                    <th>Subproject</th>
                    <th>Replace</th>
                    <th>Save</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody id="project-bill"></tbody>
        </table>
    </div>
    <div id="replace-project-bill-items" hidden="true" title="Add Project Bill Items">
        <form class="go-bottom">
            <div id="lst-project-bill-items"></div>
        </form>
    </div>
    <h2>Bill of Material Detial</h2>
    <div>
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Code</th>
                    <th>Stock</th>
                    <th >Quantity*</th>
                    <th>Cost/PC*</th>
                    <th>Total Cost</th>
                    <th>Percentage(%)*</th>
                    <th>Discount(%)*</th>
                    <th>Sales Price/PC</th>
                    <th>Sales Price</th>
                    <th>Total Net Price</th>
                    <th>Currency</th>
                    <th>Subproject</th>
                    <th>Location</th>
                    <th>Edit</th>
                    <th>Refresh</th>
                    <th>Save</th>
                    <th>Remove</th>
                </tr>
            </thead>
            <tbody id="project-bill-items"></tbody>
        </table>
    </div>
    <div><h2>Notes</h2><textarea id="notes" name="notes" rows="10" style="width: 100%"></textarea></div>
    <div>${button_save_pdf}${button_save_excel}${button_send_email}</div>
    <div>${button_action_message}</div>
</div>