<%-- 
    Document   : SaleService
    Created on : Aug 5, 2014, 11:45:05 PM
    Author     : user1
--%>

<script>
    $(function () {
        var data = "rqId=" + $('#request-quotation-id').val();

        $.ajax({
            type: "POST",
            url: "/ProjectManager/project/request-quotation/content-supplier",
            data: data,
            success: function (response) {
                var content = JSON.parse(response);
                
                $("#subproject").val(content.subproject);
                $("#supplier").val(content.supplier);
                $("#currency").val(content.currency);
                $("#request-quotation").html(content.requestQuotation);
                $("#request-quotation-items").html(content.itemRequestQuotation);
                $("#note").val(content.note);
            },
            error: function (xhr, status, error) {
                alert(error);
            }
        });
    });
</script>

<div id="bill-header" class="formLayout">
    <h1>REQUEST FOR QUOTATION - REF:${projectReference}</h1>
    <h2 style="color: red">Please fill in the cells with black color only.<br> Push 'Refresh' button to verify and 'Send eMail' button to submit your offer</h2>
    <input type="hidden" id="request-quotation-id" value=${requestQuotationId} />
    <div style="overflow-y: scroll">
        <h2>Subproject</h2>
        <table>
            <tbody>
                <tr>
                    <td><label>Subproject</label></td>
                    <td><input type="text" id="subproject" readonly></td>
                </tr>
                <tr>
                    <td><label>Supplier</label></td>
                    <td><input type="text" id="supplier" readonly></td>
                </tr>
                <tr>
                    <td><label>Currency</label></td>
                    <td><input type="text" id="currency" readonly></td>
                </tr>
            </tbody>
        </table>
    </div>
    <h2>Request of Quotation Summary</h2>
    <div>
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Delivery Cost*</th>
                    <th>Other Expenses*</th>
                    <th>Material Cost</th>
                    <th>Grand Total</th>
                </tr>
            </thead>
            <tbody id="request-quotation"></tbody>
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
                    <th>Quantity</th>
                    <th>Price Unit*</th>
                    <th>Discount(%)*</th>
                    <th>Availability*</th>
                    <th>Net Total</th>
                </tr>
            </thead>
            <tbody id="request-quotation-items">${itemRequestQuotation}</tbody>
        </table>
    </div>
    <div><p><h2>Note</h2><textarea id="note" name="note" rows="10" style="width: 100%">${noteRequestQuotation}</textarea></div>
    <div>${buttonCancel}${buttonRefresh}${buttonSendEmail}</div>
</div>