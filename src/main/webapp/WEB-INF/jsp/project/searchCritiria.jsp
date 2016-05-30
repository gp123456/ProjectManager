<%-- 
    Document   : searchCritiria
    Created on : Apr 25, 2014, 9:58:20 AM
    Author     : qbsstation12
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script>
    $(function () {
        $("#date-start").datepicker();
        $("#date-start").val("");
        $("#date-end").datepicker();
        $("#date-end").val("");
        $("#search-reference").val("");
        $("#search-vessel-custom").val("");
        $("#search-vessel-imo").val("");
        $("#search-customer-custom").val("");
    });
</script>

<label class="custom-select">Reference
    <input type="text" id="search-reference" value="">
</label>
<label class="custom-select">Type
    <select id="search-type" ></select>
</label>
<label class="custom-select">Status
    <select id="search-status"></select>
</label>
<label class="custom-select">Vessel
    <select id="search-vessel"></select>
    <input type="text" id="search-vessel-custom" value="">
</label>
<label class="custom-select">Customer
    <select id="search-customer"></select>
    <input type="text" id="search-customer-custom" value="">
</label>
<label class="custom-select">Company
    <select id="search-company"></select>
</label><br\>
<label class="custom-select">Start date
    <input name="sdate" type="text" id="date-start" value="${sdateVal}">
</label>
<span class="dateico"></span>
<label class="custom-select">End date
    <input name="edate" type="text" id="date-end" value="${edateVal}">
</label><br>
<input type="button" class="button" value="Search" id="search" onclick="searchContent($('#project-version').val(), 'view', 0, 10)"/>
<input type="button" class="button" value="Clear" id="search-clear" onclick="searchClear()"/>