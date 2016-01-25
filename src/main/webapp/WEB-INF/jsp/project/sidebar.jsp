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

                $("#project-new-size").html(content.project_new_size);
                $("#project-bill-size").html(content.project_bill_size);
                $("#project-quota-size").html(content.project_quota_size);
                $("#project-purchase-size").html(content.project_purchase_size);
                $("#project-work-size").html(content.project_work_size);
                $("#project-ack-size").html(content.project_ack_size);
                $("#project-packing-size").html(content.project_packing_size);
                $("#project-delivery-size").html(content.project_delivery_size);
                $("#project-shipping-size").html(content.project_shipping_size);
                $("#project-invoice-size").html(content.project_invoice_size);
                $("#project-box-size").html(content.project_box_size);
                $("#project-credit-size").html(content.project_credit_size);
            },
            error: function (e) {
            }
        });
    });
</script>

<nav>
    <div class="menu-item">
        <h3 id="project-edit-size" onclick="dlgEditProject()">Edit</h3>
        <div id="dlg-edit-project" hidden="true" title="Select statuses to edit">
            <form>
                <div id="lst-edit-project" class="radio"></div>
                <input type="hidden" id="edit-path" value=${edit_path} />
            </form>
        </div>
    </div>
    <div class="menu-item">
        <h3 id="project-new-size" onclick="window.location.href = '<%=path%>/project/new';">New</h3>
        <ul id="project-new"></ul>
    </div>
    <div class="menu-item">
        <h3 id="project-bill-size" onclick="dlgProject('new', 'Create', '#dlg-bill-project',
                        '#lst-bill-project', '/ProjectManager/project/project-bill')">Bill of Material or Service</h3>
        <div id="dlg-bill-project" hidden="true" title="Project Create">
            <form>
                <div id="lst-bill-project" class="radio"></div>
            </form>
        </div>
    </div>
    <div class="menu-item">
        <h3 id="project-quota-size" onclick="dlgProject('new', 'Project Bill', '#dlg-quota-project',
                        '#lst-quota-project', '/ProjectManager/project/request-quotation')">Request for Quotation</h3>
        <div id="dlg-quota-project" hidden="true" title="Project Bill">
            <form>
                <fieldset style="padding:0; border:0; margin-top:25px;">
                    <div id="lst-quota-project"></div>
                </fieldset>
            </form>
        </div>
    </div>
    <div class="menu-item">
        <h3 id="project-purchase-size" onclick="dlgProject('new', 'Project Bill', '#dlg-purchase-project',
                        '#lst-purchase-project', '/ProjectManager/project/purchase-order')">Purchase Order</h3>
        <div id="dlg-purchase-project" hidden="true" title="Project Bill">
            <form>
                <fieldset style="padding:0; border:0; margin-top:25px;">
                    <div id="lst-purchase-project"></div>
                </fieldset>
            </form>
        </div>
    </div>
    <div class="menu-item">
        <h3 id="project-ack-size" onclick="">Order Acknowledge</h3>
    </div>
    <div class="menu-item">
        <h3 id="project-work-size" onclick="">Work Order</h3>
    </div>
    <div class="menu-item">
        <h3 id="project-packing-size" onclick="">Packing List</h3>
    </div>
    <div class="menu-item">
        <h3 id="project-delivery-size" onclick="">Delivery Note</h3>
    </div>
    <div class="menu-item">
        <h3 id="project-shipping-size" onclick="">Shipping Invoice</h3>
    </div>
    <div class="menu-item">
        <h3 id="project-invoice-size" onclick="">Invoice</h3>
    </div>
    <div class="menu-item">
        <h3 id="project-box-size" onclick="">Box Markings</h3>
    </div>
    <div class="menu-item">
        <h3 id="project-credit-size" onclick="">Credit Note</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="dlgProject('old','', '#dlg-statistics-project',
                        '#lst-statistics-project', '/ProjectManager/project/statistics')">Statistics of New Project</h3>
        <div id="dlg-statistics-project" hidden="true" title="Project Statistics">
            <form>
                <fieldset style="padding:0; border:0; margin-top:25px;">
                    <div id="lst-statistics-project"></div>
                </fieldset>
            </form>
        </div>
        <!--        <ul id="project-statistics">
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
        <h3 onclick="dlgProject('old','', '#dlg-statistics-project',
                        '#lst-statistics-project', '/ProjectManager/project/statistics')">Statistics of Old Project</h3>
        <div id="dlg-statistics-project" hidden="true" title="Project Statistics">
            <form>
                <fieldset style="padding:0; border:0; margin-top:25px;">
                    <div id="lst-statistics-project"></div>
                </fieldset>
            </form>
        </div>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/project/history-new-project';">History of New Project</h3>
    </div>
    <div class="menu-item">
        <h3 onclick="window.location.href = '<%=path%>/project/history-old-project';">History of Old Project</h3>
    </div>
</nav>
