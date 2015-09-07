<%-- 
    Document   : searchCritiria
    Created on : Apr 25, 2014, 9:58:20 AM
    Author     : qbsstation12
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script>
    function refreshSearchContent() {
        $.ajax({
            type: "POST",
            url: "refresh",
            data: "offset=0&size=10",
            success: function (response) {
                var content = JSON.parse(response)

                $("#search-reference").html(content.reference);
                $("#search-type").html(content.type);
                $("#search-status").html(content.status);
                $("#search-vessel").html(content.vessel);
                $("#search-customer").html(content.customer);
                $("#search-company").html(content.company);

                response;
            },
            error: function (e) {
            }
        });
    }
</script>

<div class="criteriacontent">
    <label>Reference</label>
    <select id="search-reference"></select>
    <label>Type</label>
    <select id="search-type" ></select>
    <label>Status</label>
    <select id="search-status"></select>
    <label>Vessel</label>
    <select id="search-vessel"></select><p/>
    <label>Customer</label>
    <select id="search-customer"></select>
    <label>Company</label>
    <select id="search-company"></select>
    <label>Start date</label>
    <input name="sdate" class="content_align" type="text" id="Datefrom" value="${sdateVal}">
    <span class="dateico"></span>
    <label>End date</label>
    <input name="edate" class="content_align" type="text" id="Dateto" value="${edateVal}"><p/>
    <input type="button" class="btnsubmit content_align" value="Search" id="search" onclick="searchContent()"/>
    <input type="button" class="btnsubmit content_align" value="Clear" id="search-clear" onclick="searchClear()"/>
</div>