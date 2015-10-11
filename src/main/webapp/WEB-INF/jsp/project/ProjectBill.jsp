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

<%
    Object obj = request.getAttribute("items");
    List<Item> infoItem = (obj != null) ? (List<Item>) obj : null;
%>

<h1>Project Bill - REF:${project_reference}</h1>
<input type="hidden" id="bill-projectdetail-id" value=${pd_id} />
<div style="overflow-y: scroll">
    <h3>Select Subproject</h3>
    <select id="project" data-placeholder="Choose type" tabindex="1" onchange="insertItem(this)">
        <option value="-1" selected="selected">Select</option>
        <%
            if (infoItem != null && infoItem.isEmpty() == false && infoItem.get(0) != null) {
                for (Iterator<Item> it = infoItem.iterator(); it.hasNext();) {
                    Item i = it.next();
        %>
        <option value="<%=i.getId()%>"><%=i.getImno()%>[<%=i.getQuantity()%>,<%=i.getPrice()%>]</option>
        <%
                }
            }
        %>
    </select>
    <input type="button" value="Add" id="project-bill-add-item" onclick="addSubProject()"/>
    <div id="new-subproject" hidden="true" title="New Sub Project">
        <p class="validateTips">All form fields are required.</p>
        <form>
            <fieldset style="padding:0; border:0; margin-top:25px;">
                <label>Company</label>
                <div id="new-subproject-company"></div>
                <label>Type</label>
                <div id="new-subproject-type"></div>
                <label>Expired date</label>
                <input type="text" id="new-subproject-expired" value="${expired}">
                <label>Vessel</label>
                <div id="new-subproject-vessel"></div>
                <input type="button" value="Add" id="new-project-add-vessel" onclick="addVessel()"/>
                <div id="add-vessel" hidden="true" title="Add Vessel">
                    <p class="validateTips">All form fields are required.</p>
                    <form>
                        <fieldset>
                            <label>Name</label>
                            <input type="text" id="vessel-name">
                            <label>IMO</label>
                            <input type="text" id="vessel-imo">
                            <input type="submit" tabindex="-1" style="position:absolute; top:-1000px; display:block">
                        </fieldset>
                    </form>
                </div><br/>
                <label>Customer</label>
                <div id="new-subproject-customer"></div>
                <input type="button" value="Add" id="new-project-add-customer" onclick="addCustomer()"/>
                <div id="add-customer" hidden="true" title="Add Customer">
                    <p class="validateTips">All form fields are required.</p>
                    <form>
                        <fieldset style="padding:0; border:0; margin-top:25px;">
                            <label>Name</label>
                            <input type="text" id="customer-name">
                            <label>Reference Number</label>
                            <input type="text" id="customer-reference-number">
                            <input type="submit" tabindex="-1" style="position:absolute; top:-1000px; display:block">
                        </fieldset>
                    </form>
                </div><br/>
                <label>Contact</label>
                <div id="new-subproject-contact"></div>
                <input type="button" value="Add" id="new-project-add-contact" onclick="addContact()"/>
                <div id="add-contact" hidden="true" title="Add Contact">
                    <p class="validateTips">All form fields are required.</p>
                    <form>
                        <fieldset style="padding:0; border:0; margin-top:25px;">
                            <label for="name">Name</label>
                            <input type="text" id="contact-name">
                            <label for="surname">Surname</label>
                            <input type="text" id="contact-surname">
                            <label for="phone">Phone</label>
                            <input type="text" id="contact-phone">
                            <label for="email">eMail</label>
                            <input type="text" id="contact-email">
                            <input type="submit" tabindex="-1" style="position:absolute; top:-1000px; display:block">
                        </fieldset>
                    </form>
                </div>
            </fieldset>
        </form>
    </div>
    <h3>Select Item</h3>
    <select id="project" data-placeholder="Choose type" tabindex="1" onchange="insertItem(this)">
        <option value="-1" selected="selected">Select</option>
        <%
            if (infoItem != null && infoItem.isEmpty() == false && infoItem.get(0) != null) {
                for (Iterator<Item> it = infoItem.iterator(); it.hasNext();) {
                    Item i = it.next();
        %>
        <option value="<%=i.getId()%>"><%=i.getImno()%>[<%=i.getQuantity()%>,<%=i.getPrice()%>]</option>
        <%
                }
            }
        %>
    </select>
    <input type="button" value="Add" id="project-bill-add-item" onclick="addItem()"/>
    <div id="add-item" hidden="true" title="Add Item">
        <p class="validateTips">All form fields are required.</p>
        <form>
            <fieldset>
                <label>IMNO</label>
                <input type="text" id="item-imno">
                <label>Description</label>
                <input type="text" id="item-desc">
                <label>Locations</label>
                <div id="item-location"></div>
                <input type="button" value="Add" onclick="addLocation()"/>
                <div id="add-location" hidden="true" title="Add Location">
                    <p class="validateTips">All form fields are required.</p>
                    <form>
                        <fieldset>
                            <label>Location</label>
                            <input type="text" id="location">
                            <label>Note</label>
                            <textarea id="note">Note</textarea>
                            <input type="submit" tabindex="-1" style="position:absolute; top:-1000px; display:block">
                        </fieldset>
                    </form>
                </div><br/>
                <label>Quantity</label>
                <input type="text" id="item-quantity">
                <label>Price</label>
                <input type="text" id="item-price">
                <label>Suppliers</label>
                <div id="item-supplier"></div>
                <input type="button" value="Add" id="new-project-add-customer" onclick="addSupplier()"/>
                <div id="add-customer" hidden="true" title="Add Customer">
                    <p class="validateTips">All form fields are required.</p>
                    <form>
                        <fieldset style="padding:0; border:0; margin-top:25px;">
                            <label>Name</label>
                            <input type="text" id="supplier-name">
                            <label>Reference Number</label>
                            <input type="text" id="supplier-reference-number">
                            <input type="submit" tabindex="-1" style="position:absolute; top:-1000px; display:block">
                        </fieldset>
                    </form>
                </div><br/>
            </fieldset>
        </form>
    </div>
    <input type="hidden" id="item_id" name="id"/>
</div>
<div>
    <table class="table tablesorter">
        <thead>
            <tr>
                <th>Total Cost(&#8364)</th>
                <th>Average Discount(%)</th>
                <th>Sales Price(&#8364)</th>
                <th>Total Net Price(&#8364)</th>
                <th>Currency</th>
                <th>Subproject</th>
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
<h2>Project Bill Items</h2>
<div>
    <table class="table tablesorter">
        <thead>
            <tr>
                <th>Code</th>
                <th>Description</th>
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
<div><p><label>Notes</label></p><textarea id="notes" name="notes" rows="10" style="width: 100%"></textarea></div>
<div class="critDivs" style="left:20px;">
    <!--    <input type="button" class="btnsubmit content_align" value="New" id="inquiry-quotation-xls" onclick=""/>-->
    <input type="button" class="btnsubmit content_align" value="Save" id="inquiry-quotation-xls" onclick="saveProjectBill()"/>
</div>
