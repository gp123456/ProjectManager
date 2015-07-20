<%-- 
    Document   : DeliveryNote
    Created on : Oct 3, 2014, 1:53:22 AM
    Author     : antonia
--%>
<%
    Object obj = request.getAttribute("project_reference");

    String prj_reference = (obj != null) ? (String) obj : null;
%>
<script>
    $(function () {
        refreshSearchContent();
    });
</script>
<div class="searchCriteria"><jsp:include page="../project/searchCritiria.jsp"/></div>
<h1>Delivery Note - REF : <%=prj_reference%></h1>
<div>
    <table class="table tablesorter">
        <thead>
            <tr>
                <th>DELIVERY NOTE NUMBER</th>
                <th>DATE</th>
                <th>ORDER NUMBER</th>
                <th>OWNERS O N.</th>
                <th>VESSEL NAME</th>
                <th>DELIVERY</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><div contenteditable>1028</div></td>
                <td><div contenteditable>29/06/2015</div></td>
                <td><div contenteditable>13811/MM</div></td>
                <td><div contenteditable>2015-QU-WT-0006</div></td>
                <td><div contenteditable>M/V NAVIOS ALEGRIA</div></td>
                <td><div contenteditable>CHINA</div></td>
            </tr>
        </tbody>
    </table>
</div>
<div>
    <table class="table tablesorter">
        <thead>
            <tr>
                <th>Quantity Order</th>
                <th>Quantity Shipping</th>
                <th>Item Code</th>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>2 pcs</td>
                <td>2 pcs</td>
                <td>2E4</td>
                <td>WWCK RMP OLD STROFADES</td>
            </tr>
            <tr>
                <td>4 pcs</td>
                <td>4 pcs</td>
                <td>1B11</td>
                <td>KONEKTORAS 9 AKIDWN ARSENIKOS</td>
            </tr>
        </tbody>
    </table>
</div>
<div>
    <p><label>COMMENTS</label></p>
    <textarea name="comments" rows="10" style="width: 100%">
MASTER M/V NAVIOS ALEGRIA
SHIPS SPARES IN TANSIT
C/O GAC FORWARDING & SHIPPING (SHANGHAI)
TEL: 0086 21 23108195
MOB: 0086 136 61766896
PIC: JELLY XU - jelly.xu@gac.com
    </textarea>
</div>
<div>
    <table class="table tablesorter">
        <thead>
            <tr>
                <th><div contenteditable>Received All A/M in Good Condition By:</div></th>
            </tr>
        </thead>
    </table>
</div>