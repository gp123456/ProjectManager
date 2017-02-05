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
                if (content.modeCurrency === "lst") {
                    $("#txt-currency").hide();
                    $("#lst-currency").html(content.currency);
                } else if (content.modeCurrency === "txt") {
                    $("#lst-currency").hide();
                    $("#txt-currency").val(content.currency);
                }
                $("#request-quotation").html(content.requestQuotation);
                $("#request-quotation-items").html(content.itemRequestQuotation);
            },
            error: function (xhr, status, error) {
                alert(error);
            }
        });
    });
</script>

<div id="header" class="formLayout">
    <h1>REQUEST FOR QUOTATION - REF:${projectReference}</h1>
    <h2 style="color: red">
        You are kindly requested to fill in the information requested in the red colored cells.<br>
        When finished, push the "calculate" button and the form will automatically calculate the total amounts.<br>
        If you have any comments or clarifications, please mention in the "vendor notes" section and push the "submit" button in order to upload your
        offer.
    </h2>
    <input type="hidden" id="request-quotation-id" value=${requestQuotationId} />
    <input type="hidden" id="request-quotation-items-id" value=${requestQuotationItemsId} />
    <input type="hidden" id="email-sender" value=${emailSender} />
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
                    <td><label class="custom-select"><select id="lst-currency"></select></label>
                        <input type="text" id="txt-currency" readonly>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <h2>Request for Quotation Summary</h2>
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
        <h2>Request for Quotation Details</h2>
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Item</th>
                    <th>Code</th>
                    <th>Description</th>
                    <th>Quantity</th>
                    <th>Price Unit*</th>
                    <th>Discount(%)*</th>
                    <th>Availability (days)*</th>
                    <th>Net Total</th>
                </tr>
            </thead>
            <tbody id="request-quotation-items">${itemRequestQuotation}</tbody>
        </table>
    </div>
    <div><p><h2>Marpo Group Notes</h2><textarea id="note" name="note" rows="10" style="width: 100%" readonly="readonly">${noteRequestQuotation}</textarea></div>
    <div><p><h2>Vendor Notes</h2><textarea id="supplier-note" name="note" rows="10" style="width: 100%">${noteSupplierRequestQuotation}</textarea></div>
    <div>${buttonClearAll}${buttonRefresh}${buttonSendEmail}</div>
</div>