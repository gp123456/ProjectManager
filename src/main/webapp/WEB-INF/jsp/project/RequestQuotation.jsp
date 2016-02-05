<%-- 
    Document   : SaleService
    Created on : Aug 5, 2014, 11:45:05 PM
    Author     : user1
--%>

<h1>REQUEST FOR QUOTATION - REF:${project_reference}</h1>
<input type="hidden" id="quota-projectdetail-id" value=${pd_id} />
<div>${project-bill-items}</div>
<div><p><label>Notes</label></p><textarea name="Notes" rows="10" cols="80"></textarea></div>
<div class="critDivs" style="left:20px;">
    <input type="button" class="btnsubmit content_align" value="Save to Excel" id="inquiry-quotation-xls" onclick=""/>
    <input type="button" class="btnsubmit content_align" value="Save to PDF" id="inquiry-quotation-pdf" onclick=""/>
</div>