<%-- 
    Document   : SaleService
    Created on : Aug 5, 2014, 11:45:05 PM
    Author     : user1
--%>

<h1>REQUEST FOR QUOTATION - REF:${projectReference}</h1>
<input type="hidden" id="quota-projectdetail-id" value=${pdId} />
<div>
    <table>
        <tbody>
            <tr>
                <td width="150px"><label><h2>Supplier</h2></label></td>
                <td>
                    <p>
                        <label class="custom-select">
                            <select id="company">${supplier}</select>
                        </label>
                    </p>
                </td>
                <td><input type="button" class="button" value="Add" id="add-company" onclick="addCompany()"/>
                    <div id="add-company" hidden="true" title="Add Supplier">
                        <form class="go-bottom">
                            <div>
                                <input type="text" id="company-name" required>
                                <label class="go-bottom-label" for="company-name">Name</label>
                            </div>
                            <div>
                                <input type="text" id="company-email" required>
                                <label class="go-bottom-label" for="supplier-email">eMail</label>
                            </div>
                        </form>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
</div>
<div>
    <h2>Request of Quotation</h2>
    <table class="table tablesorter">
        <thead>
            <tr>
                <th>Currency</th>
                <th>Delivery(day)*</th>
                <th>Packing(day)*</th>
                <th>Payment(day)*</th>
                <th>Validity*</th>
                <th>Sum Value</th>
                <th>Sum Discount</th>
                <th>Payable</th>
                <th>Charges</th>
                <th>Total Value</th>
                <th>Save</th>
            </tr>
        </thead>
        <tbody>${requestQuotation}</tbody>
    </table>
</div>
<div>
    <h2>Request of Quotation Detail</h2>
    <table class="table tablesorter">
        <thead>
            <tr>
                <th>Item</th>
                <th>Code</th>
                <th>Description</th>
                <th>Currency</th>
                <th>Bill Quantity</th>
                <th>Stock Quantity</th>
                <th>Quantity*</th>
                <th>Bill Price</th>
                <th>Stock Price</th>
                <th>Unit Price*</th>
                <th>Discount(%)*</th>
                <th>Total</th>
                <th>Edit</th>
                <th>Refresh</th>
                <th>Remove</th>
                <th>Save</th>
            </tr>
        </thead>
        <tbody id="request-quotation-items">${projectBillItems}</tbody>
    </table>
</div>
<div><p><h2>Notes</h2><textarea id="notes" name="notes" rows="10" style="width: 100%">${projectBillNote}</textarea></div>
<div>${buttonSavePDF}${buttonSaveExcel}${buttonSendEmail}</div>