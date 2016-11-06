<%-- 
    Document   : Quotation
    Created on : Mar 11, 2016, 12:51:28 AM
    Author     : antonia
--%>

<div id="header" class="formLayout">
    <div id="containerIntro">
        <table>
            <tbody>
                <tr>
                    <td><h1>QUOTATION - REF:${projectReference} - Customer REF:</h1></td>
                    <td><input type="text" id="customer-reference" value=${customerReference}></td>
                </tr>
            </tbody>
        </table>
    </div>
    <input type="hidden" id="request-quotation-id" value=${requestQuotationId} />
    <h2>Project Info</h2>
    <table>
        <tbody>
            <tr>
                <td><label>Type</label></td>
                <td><input type="text" id="type" value="${type}" style="width: 500px" readonly></td>
            </tr>
            <tr>
                <td><label>Company</label></td>
                <td><input type="text" id="company" value="${company}" style="width: 500px" readonly></td>
            </tr>
            <tr>
                <td><label>Customer</label></td>
                <td><input type="text" id="customer" value="${customer}" style="width: 500px" readonly></td>
            </tr>
            <tr>
                <td><label>Vessel</label></td>
                <td><input type="text" id="vessel" value="${vessel}" style="width: 500px" readonly></td>
            </tr>
            <tr>
                <td><label>Contact</label></td>
                <td><input type="text" id="contact" value="${contact}" style="width: 500px" readonly></td>
            </tr>
        </tbody>
    </table>
    <h2>Select Subproject</h2>
    <table>
        <tbody>
            <tr>
                <td>
                    <label class="custom-select-large">
                        <select id="subproject" onchange="changeQuotaSubproject('q')">${subprojects}</select>
                    </label>
                </td>
            </tr>
        </tbody>
    </table>
    <h3>Welcome</h3>
    <textarea id="welcome-note" rows="10" style="width: 100%">${welcome}</textarea>
    <h2>Quotation Summary</h2>
    <h3>Select Location</h3>
    <table>
        <tbody>
            <tr>
                <td>
                    <label class="custom-select">
                        <select id="locations">${location}</select>
                    </label>
                </td>
                <td>
                    <input type="button" class="button" value="Add Item(s)" onclick="addItem()"/>
                </td>
                <td>
                    <input type="button" class="button alarm" value="Complete" onclick="completeQuotation()" style="${display}"/>
                </td>
            </tr>
        </tbody>
    </table>
    <h3>Select Currency</h3>
    <table>
        <tbody>
            <tr>
                <td>
                    <label class="custom-select">
                        <select id="currency">${currency}</select>
                    </label>
                </td>
                <td>
                    <input type="button" class="button" value="Change Currency" onclick="changeQCurrency()"/>
                </td>
            </tr>
        </tbody>
    </table>
    <div>
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Location</th>
                    <th>Total</th>
                    <th>Currency</th>
                </tr>
            </thead>
            <tbody id="quotation">${quotation}</tbody>
        </table>
    </div>
    <h2>Quotation Details</h2>
    <div>
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>A/A</th>
                    <th>Description</th>
                    <th>Code</th>
                    <th>Quantity</th>
                    <th>Price Unit*</th>
                    <th>Discount(%)*</th>
                    <th>Net Total</th>
                </tr>
            </thead>
            <tbody id="quotation-item">${quotationItem}</tbody>
        </table>
    </div>
    <h3>Remarks</h3>
    <textarea id="remarks-note" rows="10" style="width: 100%">${remark}</textarea>
    <h3>Additional Details</h3>
    <table>
        <tbody>
            <tr><td><label>Availability</label></td><td><input type="text" id="availability" style="width: 500px" value="${availability}"></td></tr>
            <tr><td><label>Delivery</label></td><td><input type="text" id="delivery" style="width: 500px" value="${delivery}"></td></tr>
            <tr><td><label>Packing</label></td><td><input type="text" id="packing" style="width: 500px" value="${packing}"></td></tr>
            <tr><td><label>Payment</label></td><td><input type="text" id="payment" style="width: 500px" value="${payment}"></td></tr>
            <tr><td><label>Validity</label></td><td><input type="text" id="validity" style="width: 500px" value="${validity}"></td></tr>
        </tbody>
    </table>
    <h3>Notes</h3>
    <textarea id="note" rows="10" style="width: 100%">${note}</textarea>
    <div>
        <input type="button" id="clear" value="Clear" class="button" onclick="getQuotation('clear')">
        <input type="button" id="calculate" value="Calculate" class="button" onclick="getQuotation('calculate')">
        <input type="button" id="save" value="Save" class="button alarm" onclick="qSave()" disabled>
        <input type="button" value="Remove" class="button" onclick="qRemove()">
        <input type="button" id="email" value="Send eMail" class="button alarm" onclick="qEmail()" disabled>
    </div>
</div>