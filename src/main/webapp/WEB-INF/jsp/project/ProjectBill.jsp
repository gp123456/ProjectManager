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
        var data = "id=" + $('#bill-projectdetail-id').val() +
                "&project=" + $('#bill-project-id').val();

        $.ajax({
            type: "POST",
            url: "/ProjectManager/project/project-bill/content",
            data: data,
            success: function (response) {
                var content = JSON.parse(response);

                $("#bill-subproject").html(content.subprojects);
                $("#bill-item").html(content.items);
            },
            error: function (xhr, status, error) {
                alert(xhr);
                alert(status);
                alert(error);
            }
        });
    });
</script>

<div class="formLayout">
    <h1>Bill of Material - REF:${project_reference}</h1>
    <input type="hidden" id="bill-project-id" value=${p_id} />
    <input type="hidden" id="bill-projectdetail-id" value=${pd_id} />
    <div style="overflow-y: scroll">
        <h3>Select Subproject</h3>
        <p>
            <label class="custom-select">
                <select id="bill-subproject"></select>
            </label>
        </p>
        <input type="button" class="button" value="Add" id="project-bill-add-item" onclick="addSubProject()"/>
        <div class="formLayout" id="new-subproject" hidden="true" title="New Sub Project">
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
                        <td></td>
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
                        <td></td>
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
        </div>
        <br><h3>Select Item</h3>
        <p>
            <label class="custom-select">
                <select id="bill-item" onchange="insertItem(this)"></select>
            </label>
        </p>
        <input type="button" class="button" value="Add" id="project-bill-add-item" onclick="addItem()"/>
        <div id="add-item" hidden="true" title="Add Item">
            <form class="go-bottom">
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
                    <th>Total Cost(&#8364)</th>
                    <th>Average Discount(%)</th>
                    <th>Sales Price(&#8364)</th>
                    <th>Total Net Price(&#8364)</th>
                    <th>Currency</th>
                    <th>Subproject</th>
                    <th>Save</th>
                    <th>Delete</th>
                    <th>Save to PDF</th>
                    <th>Print to PDF</th>
                    <th>Save to Excel</th>
                    <th>Send eMail</th>
                </tr>
            </thead>
            <tbody id="project-bill"></tbody>
        </table>
    </div>
    <h2>Bill of Material Detial</h2>
    <div>
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Code</th>
                    <th>Available</th>
                    <th>Price(&#8364)</th>
                    <th >Quantity</th>
                    <th>Cost/PC(&#8364)</th>
                    <th>Total Cost(&#8364)</th>
                    <th>Percentage(%)</th>
                    <th>Discount(%)</th>
                    <th>Sales Price/PC(&#8364)</th>
                    <th>Sales Price(&#8364)</th>
                    <th>Total Net Price(&#8364)</th>
                    <th>Currency</th>
                    <th>Subproject</th>
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
</div>