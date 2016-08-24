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
                        <td><input type="text" readonly>${bms-name}</td>
                    </tr>
                    <tr>
                        <td><label>Project</label></td>
                        <td><input type="text" readonly>${bms-project}</td>
                    </tr>
                    <tr>
                        <td><label>Complete</label></td>
                        <td><td><input type="text" readonly>${bms-complete}</td></td>
                    </tr>
                    <tr>
                        <td><label>Note</label></td>
                        <td><td><textarea readonly>${bms-project}</textarea></td></td>
                    </tr>
                    <tr>
                        <td><label>Items</label></td>
                    </tr>
                    <tr>
                        <td>
                            <table class="table tablesorter">
                                <thead id="bmsi-header"></thead>
                                <tbody id="bmsi-body"></tbody>
                                <tfoot id="bmsi-footer"></tfoot>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="formLayout">
            <h3>Request Quotation</h3>
            <table class="table tablesorter">
                <thead id="rfq-header"></thead>
                <tbody id="rfq-body"></tbody>
                <tfoot id="rfq-footer"></tfoot>
            </table>
            <div id="dlg-view-project" hidden="true" title="View Request Quotation">
                <form>
                    <div><jsp:include page="ViewRequestQuotationInfo.jsp"/></div>
                </form>
            </div>
        </div>
    </body>
</html>
