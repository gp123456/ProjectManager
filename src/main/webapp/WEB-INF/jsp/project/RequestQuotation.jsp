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
        var mode = $('#mode').val();

        if (mode === "EDIT") {
            $("#existing-rfq").hide();
            $("#request-quotations").show();
            $("#select-request-quotation").show();
        } else {
            $("#existing-rfq").show();
            $("#request-quotations").hide();
            $("#select-request-quotation").hide();
        }

        var data = "pId=" + $('#project-id').val() + "&mode=" + mode;

        $.ajax({
            type: "POST",
            url: "/ProjectManager/project/request-quotation/content",
            data: data,
            success: function (response) {
                var content = JSON.parse(response);

                $("#subproject").html(content.subprojects);
                $("#request-quotations").html(content.requestQuotations);
                $("#request-quotation").html(content.requestQuotation);
                $("#request-quotation-items").html(content.itemRequestQuotation);
                $("#supplier").html(content.suppliers);
                $("#currency").html(content.currency);
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
    <h1>
        REQUEST FOR QUOTATION - REF:${projectReference}
        <input type="button" class="button" value="Complete RFQ" onclick="completeRFQ()"/>
        <input type="button" class="button" id="existing-rfq" value="Existing RFQ" onclick="existingRequestQuotations('<%=path%>')"/>
    </h1>
    <input type="hidden" id="project-id" value=${projectId} />
    <input type="hidden" id="mode" value=${mode} />
    <!--<div style="overflow-y: scroll">-->
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
    <br/><h2 id="select-request-quotation">Select Request Quotation</h2>
    <label class="custom-select-large">
        <select id="request-quotations" onchange="changeRequestQuotalion(true)"></select>
    </label>
    <br><h2>Select Supplier</h2>
    <p>
        <label class="custom-select-large">
            <!--<select id="supplier" onchange="changeRequestQuotationSupplier()"></select>-->
            <select id="supplier" style="width:350px"></select>
        </label>
    </p>
    <!--        <table class="table tablesorter">
                <tbody id="request-quotation-supplier"></tbody>
            </table>-->
    <br><h2>Select Currency</h2>
    <p>
        <label class="custom-select">
            <select id="currency"></select>
        </label>
    </p>
    <!--</div>-->
    <br><h2>Request for Quotation Summary</h2>
    <div>
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Delivery Cost*</th>
                    <th>Other Expenses*</th>
                    <th>Material Cost</th>
                    <th>Grand Total</th>
                </tr>
            </thead>
            <tbody id="request-quotation"></tbody>
        </table>
    </div>
    <!--<div>-->
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
    <!--</div>-->
    <div><p><h2>Marpo Group Notes</h2><textarea id="note" rows="10" style="width: 100%">${noteRequestQuotation}</textarea></div>
    <div><p><h2>Vendor Notes</h2><textarea id="supplier-note" rows="10" style="width: 100%" readonly="readonly">${noteSupplierRequestQuotation}</textarea></div>
    <div>${buttonSave}${buttonSendEmail}${buttonSavePDF}${buttonSaveExcel}</div>
</div>