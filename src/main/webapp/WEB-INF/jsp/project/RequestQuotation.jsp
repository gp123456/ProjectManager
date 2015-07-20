<%-- 
    Document   : SaleService
    Created on : Aug 5, 2014, 11:45:05 PM
    Author     : user1
--%>

<script>
    $(function () {
        refreshSearchContent();
    });
</script>

<div class="searchCriteria"><jsp:include page="../project/searchCritiria.jsp"/></div>
<h1>REQUEST FOR QUOTATION - REF : 13421/GP</h1>
<table border="1">
    <thead>
        <tr>
            <th>ITEM</th>
            <th>CODE</th>
            <th>DESCRIPTION</th>
            <th>QUANTITY</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>1</td>
            <td>CA10</td>
            <td>BOX1245</td>
            <td>10</td>
        </tr>
        <tr>
            <td>2</td>
            <td>AA35</td>
            <td>BOX3212</td>
            <td>5</td>
        </tr>
    </tbody>
</table>
<div><p><label>Notes</label></p><textarea name="Notes" rows="10" cols="80"></textarea></div>
<div class="critDivs" style="left:20px;">
    <input type="button" class="btnsubmit content_align" value="Save to Excel" id="inquiry-quotation-xls" onclick=""/>
    <input type="button" class="btnsubmit content_align" value="Save to PDF" id="inquiry-quotation-pdf" onclick=""/>
</div>