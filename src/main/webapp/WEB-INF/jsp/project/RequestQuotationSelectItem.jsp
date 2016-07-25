<%-- 
    Document   : Bill
    Created on : Oct 3, 2014, 1:11:08 AM
    Author     : antonia
--%>
<div class="formLayout">
    <h1>REQUEST FOR QUOTATION - SELECT ITEMS - REF:${projectReference}</h1>
    <input type="hidden" id="project-id" value=${projectId} />
    <h2>Select Subproject</h2>
    <label class="custom-select">
        <select id="subproject" onchange="changeBMSSubproject()">${subproject}</select>
    </label>
    <br>
    <h2>Bill of Material(s) Summary</h2>
    <div>
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Subproject</th>
                </tr>
            </thead>
            <tbody id="bill-material-service">${billMaterialService}</tbody>
        </table>
    </div>
    <div>
        <h2>Bill of Material(s) Details</h2>
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Code</th>
                    <th>Stock</th>
                    <th>Quantity</th>
                    <th>Include<p></p><input type='checkbox' onclick='handleAll(this);'></th>
                </tr>
            </thead>
            <tbody id="bill-material-service-item">${billMaterialServiceItems}</tbody>
        </table>
    </div>
    <br>
    <div>
        <input type="button" value="Submit" class="button" onclick="submitRequestQuotation()">
    </div>
</div>
