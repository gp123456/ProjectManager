<%-- 
    Document   : ViewProjectInfo
    Created on : Aug 24, 2016, 4:00:01 PM
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
            <h3>Bill of Material</h3>
            <table>
                <tbody>
                    <tr>
                        <td><label>Name</label></td>
                        <td><textarea id="bms-name" readonly></textarea></td>
                    </tr>
                    <tr>
                        <td><label>Project</label></td>
                        <td><input type="text" id="bms-project" readonly></td>
                    </tr>
                    <tr>
                        <td><label>Complete</label></td>
                        <td><input type="text" id="bms-complete" readonly></td>
                    </tr>
                    <tr>
                        <td><label>Note</label></td>
                        <td><textarea id="bms-note" readonly></textarea></td>
                    </tr>
                </tbody>
            </table>
            <label>Items</label>
            <table class="table tablesorter">
                <thead id="bmsi-header"></thead>
                <tbody id="bmsi-body"></tbody>
                <tfoot id="bmsi-footer"></tfoot>
            </table>
        </div>
        <div class="formLayout" id="frm-rfq" hidden="true">
            <h3>Request for Quotation</h3>
            <table class="table tablesorter">
                <thead id="rfq-header"></thead>
                <tbody id="rfq-body"></tbody>
                <tfoot id="rfq-footer"></tfoot>
            </table>
            <div id="dlg-view-rfq" hidden="true" title="View Request for Quotation">
                <form>
                    <div><jsp:include page="HistoryRequestQuotation.jsp"/></div>
                </form>
            </div>
        </div>
        <div class="formLayout" id="frm-q" hidden="true">
            <h3>Quotation</h3>
            <table class="table tablesorter">
                <thead id="q-header"></thead>
                <tbody id="q-body"></tbody>
                <tfoot id="q-footer"></tfoot>
            </table>
            <div id="dlg-view-q" hidden="true" title="View Quotation">
                <form>
                    <div><jsp:include page="HistoryQuotation.jsp"/></div>
                </form>
            </div>
        </div>
    </body>
</html>
