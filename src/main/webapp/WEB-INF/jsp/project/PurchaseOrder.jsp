<%-- 
    Document   : PurchaseOrder
    Created on : Sep 5, 2014, 1:55:46 AM
    Author     : antonia
--%>

<script>
    $(function () {
        refreshSearchContent();
    });
</script>

<div class="searchCriteria"><jsp:include page="../project/searchCritiria.jsp"/></div>
<h1>Purchase Order - REF : 14171/GP</h1>
<h3>Customer Info</h3>
<div>
    <table class="table tablesorter">
        <thead>
            <tr>
                <th>Customer</th>
                <th>to Attention</th>
                <th>Fax/Phone(s)</th>
                <th>Date</th>
                <th>Subject</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>ANANGEL MARITIME</td>
                <td>SPARES DEPT</td>
                <td>(+30) 2104432170 / (+30) 4432171, (+23) 2214500341</td>
                <td>03/09/2014</td>
                <td>M/V ANANGEL VOYAGER</td>
            </tr>
        </tbody>
    </table>
</div>
<h3>Supplier</h3>
<div class="criteriacontent">
    <div class="criteriacontentup">
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Item</th>
                    <th>Quantity</th>
                    <th>Unit Price (?)</th>
                    <th>Total</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>CU 120x490</td>
                    <td>1</td>
                    <td>700,00</td>
                    <td>700,00</td>
                </tr>
                <tr>
                    <td>AL 120x400</td>
                    <td>2</td>
                    <td>85,00</td>
                    <td>170,00</td>
                </tr>
                <tr>
                    <td>Summary</td>
                    <td>3</td>
                    <td></td>
                    <td>870,00</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
<h3>Customer Offer</h3>
<div class="criteriacontent">
    <div class="criteriacontentup">
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Item</th>
                    <th>Quantity</th>
                    <th>Unit Price (?)</th>
                    <th>Total</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>CU 120x490</td>
                    <td>1</td>
                    <td>980,00</td>
                    <td>980,00</td>
                </tr>
                <tr>
                    <td>AL 120x400</td>
                    <td>2</td>
                    <td>140,00</td>
                    <td>280,00</td>
                </tr>
                <tr>
                    <td></td>
                    <td>3</td>
                    <td></td>
                    <td>1260,00</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
<h3>Customer Discount</h3>
<div class="criteriacontent">
    <div class="criteriacontentup">
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Discount (%)</th>
                    <th>Net Amount (?)</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>10</td>
                    <td>1134</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
<h3>Other Info</h3>
<div class="criteriacontent">
    <div class="criteriacontentup">
        <table class="table tablesorter">
            <thead>
                <tr>
                    <th>Shipping/Storage</th>
                    <th>Delivery Date</th>
                    <th>Delivery Place</th>
                    <th>Payment</th>
                    <th>Customer's Order Number</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>TBA</td>
                    <td>ASAP</td>
                    <td>CHINA</td>
                    <td>30 DAYS</td>
                    <td>VOY183</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
<div><p><label>Other Remarks</label></p><textarea name="remarks" rows="4" style="width: 100%"></textarea></div>
<div class="critDivs" style="left:20px;">
    <input type="button" class="btnsubmit content_align" value="New" id="inquiry-quotation-xls" onclick=""/>
    <input type="button" class="btnsubmit content_align" value="Save" id="inquiry-quotation-xls" onclick=""/>
    <input type="button" class="btnsubmit content_align" value="Edit" id="inquiry-quotation-xls" onclick=""/>
    <input type="button" class="btnsubmit content_align" value="Save to Excel" id="inquiry-quotation-xls" onclick=""/>
    <input type="button" class="btnsubmit content_align" value="Save to PDF" id="inquiry-quotation-pdf" onclick=""/>
    <input type="button" class="btnsubmit content_align" value="Print to PDF" id="inquiry-quotation-pdf" onclick=""/>
</div>