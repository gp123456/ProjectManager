
<%-- 
    Document   : snapshot
    Created on : Oct 3, 2014, 1:10:53 AM
    Author     : antonia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>

<script>
    $(function () {
        $.ajax({
            type: "POST",
            url: "menu-info",
            success: function (response) {
                var content = JSON.parse(response);

                $("#new-size").html(content.sizeNew);
                $("#bill-material-service-size").html(content.sizeBillMaterialService);
                $("#quotation-size").html(content.sizeQuotation);
                $("#purchase-size").html(content.sizePurchase);
                $("#order-acknowledge-size").html(content.sizeOrderAcknowledge);
                $("#work-order-size").html(content.sizeWorkOrder);
                $("#forwarding-docs-size").html(content.sizeForwardingDocs);
                $("#invoice-size").html(content.sizeInvoice);
                $("#credit-note-size").html(content.sizeCreditNote);
                $("#final-size").html(content.sizeFinal);
            },
            error: function (e) {
            }
        });
    });
</script>

<nav>
    <input type="hidden" id="location"/>
    <input type="hidden" id="email-rq-id" />
    <div id="dlg-email" hidden="true" title="Send eMail">
        <form>
            <fieldset style="padding:0; border:0; margin-top:25px;">
                <input type="text" id="email-address" required />
            </fieldset>
        </form>
    </div>
    <div class="menu-item">
        <h3 onclick="dlgEditStatus()">Edit Status</h3>
        <div id="dlg-edit-status" hidden="true" title="Select the status">
            <form>
                <div id="lst-edit-project" class="radio"></div>
                <input type="hidden" id="edit-path" value=${edit_path} />
            </form>
        </div>
    </div>
    <div class="menu-item">
        <h3 onclick="dlgEditProject()">Edit Project</h3>
        <div id="dlg-edit-project" hidden="true" title="Select the project">
            <form>
                <div>
                    <input type="text" id="prj-reference" placeholder="Reference" style="width: 355px" required>
                </div>
            </form>
        </div>
    </div>
    <div class="menu-item">
        <h3 id="new-size" onclick="window.location.href = '<%=path%>/project/new';">New</h3>
        <ul id="project-new"></ul>
    </div>
    <div class="menu-item">
        <h3 id="bill-material-service-size" onclick="dlgProject('NEW', 'new', 'Create', '#dlg-bill-project',
                        '#lst-bill-project', '/ProjectManager/project/bill-material-service')">Bill of materials or services</h3>
        <div id="dlg-bill-project" hidden="true" title="Project Create">
            <form>
                <div id="lst-bill-project" class="radio"></div>
            </form>
        </div>
    </div>
    <div class="menu-item">
        <h3 id="quotation-size" onclick="dlgProject('NEW', 'new', 'Bill of Materials or Services', '#dlg-quotation', '#lst-quotation')">Quotation</h3>
        <div id="dlg-quotation" hidden="true" title="Bill Material Service">
            <form>
                <fieldset style="padding:0; border:0; margin-top:25px;">
                    <div id="lst-quotation"></div>
                </fieldset>
            </form>
        </div>
    </div>
    <div class="menu-item">
        <h3 id="purchase-size" onclick="dlgProject('NEW', 'new', 'Bill Material Service', '#dlg-purchase-order',
                        '#lst-purchase-order', '/ProjectManager/project/purchase-order')">Purchase Order</h3>
        <div id="dlg-purchase-project" hidden="true" title="Quotation">
            <form>
                <fieldset style="padding:0; border:0; margin-top:25px;">
                    <div id="lst-purchase-order"></div>
                </fieldset>
            </form>
        </div>
    </div>
    <div class="menu-item">
        <h3 id="order-acknowledge-size" onclick="">Order Acknowledge</h3>
    </div>
    <div class="menu-item">
        <h3 id="work-order-size" onclick="">Work Order</h3>
    </div>
    <div class="menu-item">
        <h3 id="forwarding-docs-size" onclick="">Forwarding Documents</h3>
    </div>
    <div class="menu-item">
        <h3 id="invoice-size" onclick="">Invoice</h3>
    </div>
    <div class="menu-item">
        <h3 id="credit-note-size" onclick="">Credit Note</h3>
    </div>
    <div class="menu-item">
        <h3 id="final-size" onclick="">Final</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="dlgProject('old', '', '#dlg-statistics-project', '#lst-statistics-project', '/ProjectManager/project/statistics')">Statistics</h3>
        <div id="dlg-statistics-project" hidden="true" title="Project Statistics">
            <form>
                <fieldset style="padding:0; border:0; margin-top:25px;">
                    <div id="lst-statistics-project"></div>
                </fieldset>
            </form>
        </div>
        <!--        <ul id="statistics">
                    <li onclick="window.location.href = '/project/statistics?year=2007';">2007</li>
                    <li onclick="window.location.href = '/project/statistics?year=2008';">2008</li>
                    <li onclick="window.location.href = '/project/statistics?year=2009';">2009</li>
                    <li onclick="window.location.href = '/project/statistics?year=2010';">2010</li>
                    <li onclick="window.location.href = '/project/statistics?year=2011';">2011</li>
                    <li onclick="window.location.href = '/project/statistics?year=2012';">2012</li>
                    <li onclick="window.location.href = '/project/statistics?year=2013';">2013</li>
                    <li onclick="window.location.href = '/project/statistics?year=2014';">2014</li>
                    <li onclick="window.location.href = '/project/statistics?year=2015';">2015</li>
                </ul>-->
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/project/history-new-project';">History</h3>
    </div>
</nav>
