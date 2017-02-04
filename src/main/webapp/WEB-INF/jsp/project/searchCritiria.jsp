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

<form>
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
        <input type="text" id="search-reference" placeholder="Reference" value="">
    </div>
    <div>
        <input type="text" id="search-vessel" value="" placeholder="Vessel">
    </div>
    <div>
        <input type="text" id="search-customer" value="" placeholder="Customer">
    </div>
    <div>
        <input name="sdate" type="text" id="date-start" placeholder="Start Date">
    </div>
    <div>
        <input name="edate" type="text" id="date-end" placeholder="End Date">
    </div>
    <input type="button" class="button" value="Search" id="search" onclick="searchContent(0, 10)"/>
</form>