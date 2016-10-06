<%-- 
    Document   : SaleService
    Created on : Aug 5, 2014, 11:45:05 PM
    Author     : user1
--%>

<%
    String path = request.getContextPath();
%>

<script>
    $(function () {
        var data = "rqId=" + $('#request-quotation-id').val();

        $.ajax({
            type: "POST",
            url: "/ProjectManager/project/request-quotation/content-nocomplete",
            data: data,
            success: function (response) {
                var content = JSON.parse(response);

                $("#project-reference").html(content.reference);
                $("#subproject").val(content.subprojects);
                $("#supplier").val(content.suppliers);
                $("#currency").val(content.currency);
                $("#request-quotation").html(content.requestQuotation);
                $("#request-quotation-items").html(content.itemRequestQuotation);
                $("#note").html(content.notes);
                $("#supplier-note").html(content.notesSupplier);
            },
            error: function (xhr, status, error) {
                alert(error);
            }
        });
    });
</script>

<div id="header" class="formLayout">
    <h1 id="project-reference"></h1>
    <input type="hidden" id="project_detail-id" value=${projectDetailId} />
    <input type="hidden" id="request-quotation-id" value=${requestQuotationId} />
    <input type="hidden" id="currency-id" value=${currencyId} />
    <h2>Subproject</h2>
    <label class="custom-select">
        <input type="text" id="subproject" />
    </label>
    <br/><h2>Supplier</h2>
        <label class="custom-select-large">
            <input type="text" id="supplier" style="width:350px"/>
        </label>
    <br><h2>Currency</h2>
        <label class="custom-select">
            <input type="text" id="currency"/>
        </label>
    <br><h2>Request for Quotation Summary</h2>
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
        <tbody id="request-quotation-items"></tbody>
    </table>
    <div><p><h2>Marpo Group Notes</h2><textarea id="note" rows="10" style="width: 100%"></textarea></div>
    <div><p><h2>Vendor Notes</h2><textarea id="supplier-note" rows="10" style="width: 100%" readonly="readonly"></textarea></div>
    <div>${buttonSendEmail}${buttonDiscard}</div>
</div>