<%-- 
    Document   : PackingList
    Created on : Oct 3, 2014, 1:51:08 AM
    Author     : antonia
--%>
<script>
    $(function () {
        refreshSearchContent();

        var data = "id=-1&type=1&status=-1&vessel=-1&customer=-1&company=-1&offset=0&size=10&mode=view";

        $.ajax({
            type: "POST",
            url: "search",
            data: data,
            success: function (response) {
                var content = JSON.parse(response)

                $("#project-header").html(content.project_header);
                $("#project-footer").html(content.project_footer);
                $("#project-body").html(content.project_body);
            },
            error: function (e) {
            }
        });
    });
</script>
<div class="searchCriteria"><jsp:include page="../project/searchCritiria.jsp"/></div>
<h1>Packing List - REF : 14171/GP</h1>
<div>
    <table class="table tablesorter">
        <thead>
            <tr>
                <th>Customer</th>
                <th>Order Number</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>SOUTHER STEAMSHIPS (LONDON)</td>
                <td><div contenteditable>EPI/14/L39</div></td>
            </tr>
        </tbody>
    </table>
</div>
<div class="criteriacontent">
    <div class="criteriacontentup">
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Consignee</th>
                    <th>Telephone</th>
                    <th>Pic</th>
                    <th>Mobile</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><div contenteditable>Master M/V EPIPHANIA Ship spares in Transit c/o GAC Forwarding & Shipping (Shanghai)</div></td>
                    <td><div contenteditable>0086 2123108134</div></td>
                    <td><div contenteditable>Angela Li</div></td>
                    <td><div contenteditable>0086 13764513025</div></td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
<h3>DATE: SEPTEMBER 02, 2014</h3>
<div class="criteriacontent">
    <div class="criteriacontentup">
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Description of Project Bill</th>
                    <th>Quantity (pcs)</th>
                    <th>Gross Weight (kgs)</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>CU Anodes 120x580</td>
                    <td><div contenteditable>2</div></td>
                    <td><div contenteditable></div></td>
                </tr>
                <tr>
                    <td>AL 120x480</td>
                    <td><div contenteditable>2</div></td>
                    <td><div contenteditable></div></td>
                </tr>
                <tr>
                    <td>Total Weight</td>
                    <td>4</td>
                    <td>148</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
<div><p><label>Other Remarks</label></p><textarea name="remarks" rows="4" style="width: 100%">4 carton boxes: 2 x (800x130x130mm) & 2 x (700x130x130)
total weight = 148 kgs</textarea></div>
<div>
    <table class="table tablesorter">
        <thead id="project-header"></thead>
        <tfoot id="project-footer"></tfoot>
        <tbody id="project-body"></tbody>
    </table>
</div>