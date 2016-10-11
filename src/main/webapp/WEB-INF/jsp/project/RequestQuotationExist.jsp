<%-- 
    Document   : ExistRequestQuoation
    Created on : Aug 23, 2016, 3:15:42 PM
    Author     : gpatitakis
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="header" class="formLayout">
    <c:choose>
        <c:when test="${is_quotation == 0}" >
            <h1>REQUEST FOR QUOTATION - SUBPROJECT REF:${projectReference}</h1>
        </c:when>
    </c:choose>
    <br><h2>Select Request for Quotation</h2>
    <label class="custom-select-large">
        <select id="request-quotations" onchange="changeRequestQuotalion(false)">${requestQuotations}</select>
    </label>
    <br><table>
        <tbody>
            <tr>
                <td><label>Supplier</label></td>
                <td><input type="text" id="supplier" value="${supplier}" readonly></td>
            </tr>
            <tr>
                <td><label>Currency</label></td>
                <td><input type="text" id="currency" value="${currency}" readonly></td>
            </tr>
        </tbody>
    </table>
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
            <tbody id="request-quotation">${requestQuotation}</tbody>
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
    <div><p><h2>Marpo Group Notes</h2><textarea id="note" rows="10" style="width: 100%" readonly>${noteRequestQuotation}</textarea></div>
    <div><p><h2>Vendor Notes</h2><textarea id="supplier-note" rows="10" style="width: 100%" readonly>${noteSupplierRequestQuotation}</textarea></div>
</div>