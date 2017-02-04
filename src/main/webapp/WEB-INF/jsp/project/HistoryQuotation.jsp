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
            <h3>Quotation</h3>
            <table>
                <tbody>
                    <tr>
                        <td><label>Name</label></td>
                        <td><textarea id="q-name" readonly></textarea></td>
                    </tr>
                    <tr>
                        <td><label>Complete</label></td>
                        <td><input type="text" id="q-complete" readonly></td>
                    </tr>
                    <tr>
                        <td><label>Discard</label></td>
                        <td><input type="text" id="q-discard" readonly></td>
                    </tr>
                    <tr>
                        <td><label>Customer</label></td>
                        <td><textarea id="q-customer" readonly></textarea></td>
                    </tr>
                    <tr>
                        <td><label>Customer Ref</label></td>
                        <td><input type="text" id="q-customer-ref" readonly></td>
                    </tr>
                    <tr>
                        <td><label>Location</label></td>
                        <td><input type="text" id="q-location" readonly></td>
                    </tr>
                    <tr>
                        <td><label>Currency</label></td>
                        <td><input type="text" id="q-currency" readonly></td>
                    </tr>
                    <tr>
                        <td><label>Grand Total</label></td>
                        <td><input type="text" id="q-grand-total" readonly></td>
                    </tr>
                    <tr>
                        <td><label>Availability</label></td>
                        <td><textarea id="q-availability" readonly></textarea></td>
                    </tr>
                    <tr>
                        <td><label>Delivery</label></td>
                        <td><textarea id="q-delivery" readonly></textarea></td>
                    </tr>
                    <tr>
                        <td><label>Packing</label></td>
                        <td><textarea id="q-packing" readonly></textarea></td>
                    </tr>
                    <tr>
                        <td><label>Payment</label></td>
                        <td><textarea id="q-payment" readonly></textarea></td>
                    </tr>
                    <tr>
                        <td><label>Validity</label></td>
                        <td><textarea id="q-validity" readonly></textarea></td>
                    </tr>
                    <tr>
                        <td><label>Welcome</label></td>
                        <td><textarea id="q-welcome" readonly></textarea></td>
                    </tr>
                    <tr>
                        <td><label>Remarks</label></td>
                        <td><textarea id="q-remarks" readonly></textarea></td>
                    </tr>
                    <tr>
                        <td><label>Notes</label></td>
                        <td><textarea id="q-notes" readonly></textarea></td>
                    </tr>
                </tbody>
            </table>
            <label>Items</label>
            <table class="table tablesorter">
                <thead id="qi-header"></thead>
                <tbody id="qi-body"></tbody>
                <tfoot id="qi-footer"></tfoot>
            </table>
        </div>
    </body>
</html>
