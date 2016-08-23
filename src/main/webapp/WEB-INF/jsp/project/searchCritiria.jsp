<%-- 
    Document   : searchCritiria
    Created on : Apr 25, 2014, 9:58:20 AM
    Author     : qbsstation12
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script>
    $(function () {
        $("#date-start").datepicker({dateFormat: 'dd/mm/yy'});
        $("#date-start").val("");
        $("#date-end").datepicker({dateFormat: 'dd/mm/yy'});
        $("#date-end").val("");
        $("#search-reference").val("");
        $("#search-vessel").val("");
        $("#search-customer").val("");
    });
</script>

<form id="demo" class="go-bottom">
    <label class="custom-select">Type
        <select id="search-type" ></select>
    </label>
    <label class="custom-select">Status
        <select id="search-status"></select>
    </label>
    <label class="custom-select">Company
        <select id="search-company"></select>
    </label>
    <div>
        <input type="text" id="search-reference" value="">
        <label class="go-bottom-label" for="search-reference">Reference</label>
    </div>
    <div>
        <input type="text" id="search-vessel" value="">
        <label class="go-bottom-label" for="search-vessel">Vessel</label>
    </div>
    <div>
        <input type="text" id="search-customer" value="">
        <label class="go-bottom-label" for="search-customer">Customer</label>
    </div>
    <div>
        <input name="sdate" type="text" id="date-start" value="${sdateVal}">
        <label class="go-bottom-label" for="date-start">Start Date</label>
    </div>
    <div>
        <input name="edate" type="text" id="date-end" value="${edateVal}">
        <label class="go-bottom-label" for="date-end">End Date</label>
    </div>
    <input type="button" class="button" value="Search" id="search" onclick="searchContent(0, 10)"/>
    <input type="button" class="button" value="Clear" id="search-clear" onclick="searchClear()"/>
</form>