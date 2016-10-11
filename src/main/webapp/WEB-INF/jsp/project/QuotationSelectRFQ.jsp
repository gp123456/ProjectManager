<%-- 
    Document   : Bill
    Created on : Oct 3, 2014, 1:11:08 AM
    Author     : antonia
--%>
<div id="header" class="formLayout">
    <h1>QUOTATION - SELECT REQUEST FOR QUOTATION - REF:${projectReference}</h1>
    <input type="hidden" id="project-id" value=${projectId} />
    <h2>Select Subproject</h2>
    <label class="custom-select">
        <select id="subproject" onchange="">${subprojects}</select>
    </label>
    <div class="searchCriteria"><jsp:include page="../project/RequestQuotationExist.jsp"/></div>
    <div>
        <input type="button" value="Submit" class="button" onclick="submit()">
    </div>
</div>
