<%-- 
    Document   : HistoryRequestQuotation
    Created on : Aug 25, 2016, 2:38:15 AM
    Author     : gpatitakis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div class="formLayout">
            <h3>Request for Quotation</h3>
            <table>
                <tbody>
                    <tr>
                        <td><label>Name</label></td>
                        <td><textarea id="rfq-name" readonly></textarea></td>
                    </tr>
                    <tr>
                        <td><label>Complete</label></td>
                        <td><input type="text" id="rfq-complete" readonly></td>
                    </tr>
                    <tr>
                        <td><label>Discard</label></td>
                        <td><input type="text" id="rfq-discard" readonly></td>
                    </tr>
                    <tr>
                        <td><label>Supplier</label></td>
                        <td><textarea id="rfq-supplier" readonly></textarea></td>
                    </tr>
                    <tr>
                        <td><label>Currency</label></td>
                        <td><input type="text" id="rfq-currency" readonly></td>
                    </tr>
                    <tr>
                        <td><label>Material Cost</label></td>
                        <td><input type="text" id="rfq-material-cost" readonly></td>
                    </tr>
                    <tr>
                        <td><label>Delivery Cost</label></td>
                        <td><input type="text" id="rfq-delivery-cost" readonly></td>
                    </tr>
                    <tr>
                        <td><label>Other Expenses</label></td>
                        <td><input type="text" id="rfq-other-expenses" readonly></td>
                    </tr>
                    <tr>
                        <td><label>Grand Total</label></td>
                        <td><input type="text" id="rfq-grand-total" readonly></td>
                    </tr>
                    <tr>
                        <td><label>Note</label></td>
                        <td><textarea id="rfq-note" readonly></textarea></td>
                    </tr>
                    <tr>
                        <td><label>Supplier Note</label></td>
                        <td><textarea id="rfq-supplier-note" readonly></textarea></td>
                    </tr>
                </tbody>
            </table>
            <label>Items</label>
            <table class="table tablesorter">
                <thead id="rfqi-header"></thead>
                <tbody id="rfqi-body"></tbody>
                <tfoot id="rfqi-footer"></tfoot>
            </table>
        </div>
    </body>
</html>
