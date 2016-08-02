<%-- 
    Document   : SaleService
    Created on : Aug 5, 2014, 11:45:05 PM
    Author     : user1
--%>

<script>
    $(function () {
        var data = "pId=" + $('#project-id').val();

        $.ajax({
            type: "POST",
            url: "/ProjectManager/project/request-quotation/content",
            data: data,
            success: function (response) {
                var content = JSON.parse(response);

                $("#subproject").html(content.subprojects);
                $("#supplier").html(content.suppliers);
                $("#request-quotation").html(content.requestQuotation);
                $("#request-quotation-items").html(content.itemRequestQuotation);
                $("#currency").html(content.currency);
                $("#note").val(content.note);
            },
            error: function (xhr, status, error) {
                alert(error);
            }
        });
    });
</script>

<div id="header" class="formLayout">
    <h1>REQUEST FOR QUOTATION - REF:${projectReference}</h1>
    <input type="hidden" id="project-id" value=${projectId} />
    <div style="overflow-y: scroll">
        <h2>Select Subproject</h2>
        <table>
            <tbody>
                <tr>
                    <td>
                        <label class="custom-select">
                            <select id="subproject" onchange="changeSubproject()"></select>
                        </label>
                    </td>
                    <td>
                        <input type="button" class="button" value="Bill Material Service Items" onclick="selectBMSI()"/>
                    </td>
                </tr>
            </tbody>
        </table>
        <br><h2>Select Supplier</h2>
        <p>
            <label class="custom-select-large">
                <select id="supplier" onchange="changeRequestQuotationSupplier()"></select>
            </label>
        </p>
        <table class="table tablesorter">
            <tbody id="request-quotation-supplier"></tbody>
        </table>
        <br><h2>Select Currency</h2>
        <p>
            <label class="custom-select">
                <select id="currency"></select>
            </label>
        </p>
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
    <div><p><h2>Notes</h2><textarea id="note" name="note" rows="10" style="width: 100%">${noteRequestQuotation}</textarea></div>
    <div>${buttonSendEmail}${buttonSavePDF}${buttonSaveExcel}</div>
</div>