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

<div class="collapsecriteria">Search Criteria <div class="inputArrow ico1"></div></div>
<div class="criteriacontent">
    <div class="criteriacontentup">
        <div class="critDivs custom"">
            <label style="font: icon;size: 12">Reference</label>
            <select id="search-reference"></select>
        </div>
        <div class="critDivs custom">
            <label style="font: icon;size: 12">Type</label>
            <select id="search-type" ></select>
        </div>
        <div class="critDivs custom">
            <label style="font: icon;size: 12">Status</label>
            <select id="search-status"></select>
        </div>
        <div class="critDivs custom">
            <label style="font: icon;size: 12">Vessel</label>
            <select id="search-vessel"></select>
        </div>
        <div class="critDivs custom">
            <label style="font: icon;size: 12">Customer</label>
            <select id="search-customer"></select>
        </div>
        <div class="critDivs custom">
            <label style="font: icon;size: 12">Company</label>
            <select id="search-company"></select>
        </div>
        <div class="critDivs custom date">
            <label style="font: icon;size: 12">Starting date</label>
            <input name="sdate" class="content_align" type="text" id="Datefrom" value="${sdateVal}">
            <span class="dateico"></span>
        </div>
        <div class="critDivs custom date">
            <label style="font: icon;size: 12">Ending date</label>
            <input name="edate" class="content_align"  type="text" id="Dateto" value="${edateVal}">
            <span class="dateico"></span>
        </div>
        <div class="critDivs" style="left:20px;">
            <input type="button" class="btnsubmit content_align" value="Search" id="search" onclick="searchContent()"/>
        </div>
        <div class="critDivs" style="left:20px;">
            <input type="button" class="btnsubmit content_align" value="Clear" id="search-clear" onclick="searchClear()"/>
        </div>
    </div>
</div>
