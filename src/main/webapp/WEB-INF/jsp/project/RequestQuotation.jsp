<%-- 
    Document   : SaleService
    Created on : Aug 5, 2014, 11:45:05 PM
    Author     : user1
--%>

<div id="bill-header" class="formLayout">
    <h1>REQUEST FOR QUOTATION - REF:${projectReference}</h1>
    <input type="hidden" id="project-id" value=${projectId} />
    <div style="overflow-y: scroll">
        <table>
            <tbody>
                <tr>
                    <td><label>Supplier</label></td>
                    <td id="supplier">${supplier}</td>
                </tr>
            </tbody>
            <tbody>
                <tr>
                    <td><label>Currency</label></td>
                    <td id="currency">${currency}</td>
                </tr>
            </tbody>
        </table>
        <h3>Select Subproject</h3>
        <p>
            <label class="custom-select">
                <select id="subproject" onchange="changeSubproject()">${subproject}</select>
            </label>
        </p>
        <br><h3>Select Location</h3>
        <p>
            <label class="custom-select">
                <select id="location" onchange="changeLocationRequestQuotation()">${location}</select>
            </label>
        </p>
    </div>
    <div>
        <h2>Request of Quotation Detail</h2>
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Item</th>
                    <th>Code</th>
                    <th>Description</th>
                    <th>Quantity</th>
                    <th>Availability*</th>
                    <th>Delivery Cost*</th>
                    <th>Other Expenses*</th>
                    <th>Unit Price*</th>
                    <th>Discount(%)*</th>
                    <th>Total*</th>
                </tr>
            </thead>
            <tbody id="request-quotation-items">${itemBillMaterialService}</tbody>
        </table>
    </div>
    <div><p><h2>Note</h2><textarea id="note" name="note" rows="10" style="width: 100%">${noteBillMaterialService}</textarea></div>
    <div>${buttonSavePDF}${buttonSaveExcel}${buttonSendEmail}</div>
</div>