/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function searchContent(offset, size) {
    var reference = $("#search-reference").val();
    var type = $("#search-type option:selected").attr("value");
    var status = $("#search-status option:selected").attr("value");
    var vessel = $("#search-vessel").val();
    var customer = $("#search-customer").val();
    var company = $("#search-company option:selected").attr("value");
    var date_start = $("#date-start").val();
    var date_end = $("#date-end").val();
    var data = "";

    data = "reference=" + reference;
    data += "&type=" + type;
    data += "&status=" + status;
    data += "&customer=" + customer;
    data += "&company=" + company;
    data += "&vesselName=" + vessel;
    data += "&date_start=" + date_start;
    data += "&date_end=" + date_end;
    data += "&offset=" + offset;
    data += "&size=" + size;

    $.ajax({
        type: "POST",
        url: "search",
        data: data,
        success: function (response) {
            var content = JSON.parse(response)

            $("#header").html(content.header);
            $("#body").html(content.body);
            $("#footer").html(content.footer);
            $("#count").html("Results[" + content.count + "]<input type='button' class='button' value='Create Excel' onclick='createAllExcel()'/>");
        },
        error: function (e) {
        }
    });
}

function searchClear() {
    location.reload();
}

function getProjectById(id) {
    $.ajax({
        type: "POST",
        url: "id",
        data: "id=" + id,
        success: function (response) {
            var content = JSON.parse(response)

            $("#project-header").html(content.project_header);
            $("#project-body").html(content.project_body);
        },
        error: function (e) {
        }
    });
}

function getProjectStatistics(year) {
    $.ajax({
        type: "POST",
        url: "statistics",
        data: "year=" + year,
        success: function () {
            location.reload();
        },
        error: function () {
        }
    });
}

function removeRow(id) {
    $.ajax({
        type: "POST",
        url: "remove",
        data: "id=" + id,
        success: function (response) {
            var content = JSON.parse(response)

            $("#search-reference").html(content.reference);
            $("#header").html(content.project_header);
            $("#body").html(content.project_body);
        },
        error: function (e) {
        }
    });
}

function createAllExcel() {
    var reference = $("#search-reference").val();
    var type = $("#search-type option:selected").attr("value");
    var status = $("#search-status option:selected").attr("value");
    var vessel = $("#search-vessel").val();
    var customer = $("#search-customer").val();
    var company = $("#search-company option:selected").attr("value");
    var date_start = $("#date-start").val();
    var date_end = $("#date-end").val();
    var data = "";

    data = "reference=" + reference;
    data += "&type=" + type;
    data += "&status=" + status;
    data += "&customer=" + customer;
    data += "&company=" + company;
    data += "&vesselName=" + vessel;
    data += "&date_start=" + date_start;
    data += "&date_end=" + date_end;

    $.ajax({
        type: "POST",
        url: "create-all-excel",
        data: data,
        success: function (response) {
            alert(response);
        },
        error: function (e) {
        }
    });
}

function createExcel(pdId) {
    var data = "pdId=" + pdId;

    $.ajax({
        type: "POST",
        url: "create-excel",
        data: data,
        success: function (response) {
            alert(response);
        },
        error: function (e) {
        }
    });
}

function saveProject(searchOpenExist) {
    console.log(searchOpenExist);

    var type = $("#type option:selected").attr("value");
    var expired = $("#expired").datepicker({dateFormat: 'yyyy-mm-dd'}).val();
    var customer = $("#customer option:selected").attr("value");
    var vessel = $("#vessel option:selected").attr("value");
    var company = $("#company option:selected").attr("value");
    var contact = $("#contact option:selected").attr("value");

    if (company === "none") {
        alert("you must select company");
        return;
    }
    if (type === "none") {
        alert("you must select type");
        return;
    }
    if (customer === "none") {
        alert("you must select a customer or add one");
        return;
    }

    $.ajax({
        type: "POST",
        url: "save",
        data: "type=" + type
                + "&customer=" + customer
                + "&vessel=" + vessel
                + "&company=" + company
                + "&contact=" + contact
                + "&dateExpired=" + expired
                + "&searchOpenExist=" + searchOpenExist,
        success: function (response) {
            var content = JSON.parse(response)

            if (typeof content.exist !== "undefined") {
                if (!confirm(content.exist)) {
                    saveProject(0);
                }
            }
            if (content.header) {
                $("#header").html(content.header);
            }
            if (content.body) {
                $("#body").html(content.body);
            }

            $("h1").text(content.projectReference);
            $("#save").attr('class', 'button');
            $("#save").attr('disabled', 'disabled');
            setTimeout(function () {
                location.href = "http://localhost:8081/ProjectManager/project/history-new-project";
            }, 5000);
        },
        error: function (e) {
        }
    });
}

function projectFirstPage(offset, size) {
    searchContent(offset, size)
}

function projectPreviousPage(offset, size) {
    searchContent(--offset, size);
}

function projectNextPage(offset, size) {
    searchContent(++offset, size);
}

function projectLastPage(offset, size) {
    searchContent(offset, size);
}

function projectPackingList(id) {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/packing-list/id",
        data: "id=" + id,
        success: function (response) {
            var content = JSON.parse(response)

            $("#header").html(content.project_header);
            $("#body").html(content.project_body);
            $("#footer").html(content.project_footer);
        },
        error: function (e) {
        }
    });
}

function projectFilterCustomer() {
    $.ajax({
        type: "POST",
        url: "filter-customer",
        data: "customer=" + $("#customer option:selected").attr("value"),
        success: function (response) {
            var content = JSON.parse(response)

            $("#vessel").html(content.vessel);
            $("#contact").html(content.contact);
            $("#edit").attr('class', 'button alarm');
        },
        error: function (e) {
        }
    });
}

function plotStatusInfo(id, title, info) {
    console.log(info);

    var chart = new CanvasJS.Chart(id, {
        width: 600,
        title: {
            text: title
        },
        data: [{
                type: "pie",
                dataPoints: [
                    {label: info[0]["name"] + "[" + info[0]["value"] + "]", y: info[0]["value"]},
                    {label: info[1]["name"] + "[" + info[1]["value"] + "]", y: info[1]["value"]},
                    {label: info[2]["name"] + "[" + info[2]["value"] + "]", y: info[2]["value"]},
                    {label: info[3]["name"] + "[" + info[3]["value"] + "]", y: info[3]["value"]},
                    {label: info[4]["name"] + "[" + info[4]["value"] + "]", y: info[4]["value"]},
                    {label: info[5]["name"] + "[" + info[5]["value"] + "]", y: info[5]["value"]},
                    {label: info[6]["name"] + "[" + info[6]["value"] + "]", y: info[6]["value"]},
                    {label: info[7]["name"] + "[" + info[7]["value"] + "]", y: info[7]["value"]},
                    {label: info[8]["name"] + "[" + info[8]["value"] + "]", y: info[8]["value"]},
                    {label: info[9]["name"] + "[" + info[9]["value"] + "]", y: info[9]["value"]}
                ]
            }
        ]
    });

    chart.render();
}

function plotCompanyInfo(id, title, info) {
    var chart = new CanvasJS.Chart(id, {
        width: 600,
        title: {
            text: title
        },
        data: [{
                type: "pie",
                dataPoints: [
                    {label: info[0]["name"] + "[" + info[0]["value"] + "]", y: info[0]["value"]},
                    {label: info[1]["name"] + "[" + info[1]["value"] + "]", y: info[1]["value"]},
                    {label: info[2]["name"] + "[" + info[2]["value"] + "]", y: info[2]["value"]},
                    {label: info[3]["name"] + "[" + info[3]["value"] + "]", y: info[3]["value"]}
                ]
            }
        ]
    });

    chart.render();
}

function sortNumber(a, b) {
    return b["value"] - a["value"];
}

function dashboardView() {
    $.ajax({
        type: "POST",
        url: "view",
        success: function (response) {
            var content = JSON.parse(response);
            var totalSaleStatus = null, totalServiceStatus = null, totalSaleCompany = null, totalServiceCompany = null;
            var SaleStatus = [], ServiceStatus = [], SaleCompany = [], ServiceCompany = [];

            if (content.OpenProjectSaleStatus.length !== 0) {
                totalSaleStatus = content.OpenProjectSaleStatus[0]["value"];
                for (var i = 1; i < content.OpenProjectSaleStatus.length; i++) {
                    SaleStatus.push(content.OpenProjectSaleStatus[i]);
                }
            }

            if (content.OpenProjectServiceStatus.length !== 0) {
                totalServiceStatus = content.OpenProjectServiceStatus[0]["value"];
                for (var i = 1; i < content.OpenProjectServiceStatus.length; i++) {
                    ServiceStatus.push(content.OpenProjectServiceStatus[i]);
                }
            }

            if (content.OpenProjectSaleCompany.length !== 0) {
                totalSaleCompany = content.OpenProjectSaleCompany[0]["value"];
                for (var i = 1; i < content.OpenProjectSaleCompany.length; i++) {
                    SaleCompany.push(content.OpenProjectSaleCompany[i]);
                }
            }

            if (content.OpenProjectServiceCompany.length !== 0) {
                totalServiceCompany = content.OpenProjectServiceCompany[0]["value"];
                for (var i = 1; i < content.OpenProjectServiceCompany.length; i++) {
                    ServiceCompany.push(content.OpenProjectServiceCompany[i]);
                }
            }

            if (totalSaleStatus !== null) {
                plotStatusInfo("open-project-sale-status",
                        "Projects of Sale per Status[" + totalSaleStatus + "]",
                        SaleStatus.sort(sortNumber));
            }
            if (totalServiceStatus !== null) {
                plotStatusInfo("open-project-service-status",
                        "Projects of Services per Status[" + totalServiceStatus
                        + "]", ServiceStatus.sort(sortNumber));
            }
            if (totalSaleCompany !== null) {
                plotCompanyInfo("open-project-sale-company",
                        "Projects of Sale per Company[" + totalSaleCompany
                        + "]", SaleCompany.sort(sortNumber));
            }
            if (totalServiceCompany !== null) {
                plotCompanyInfo("open-project-service-company",
                        "Projects of Services per Company[" +
                        totalServiceCompany + "]", ServiceCompany.sort(sortNumber));
            }
        },
        error: function (xhr, status, error) {
            alert(error);
        }
    });
}

function getProjectByStatus(status, div_id) {
    $.ajax({
        type: "POST",
        url: "lst-project",
        data: "status=" + status,
        success: function (response) {
            $(div_id).html(response);
        },
        error: function (e) {
        }
    });
}

function getStatuses() {
    $.ajax({
        type: "POST",
        url: "lst-status",
        success: function (response) {
            $("#lst-edit-project").html(response);
        },
        error: function (e) {
        }
    });
}

function setProjectByStatus(dlg_id, dest_path, mode) {
    var id = $('input[name = "radio-project"]:checked').val();

    if (typeof id !== "undefined") {
        if (typeof dest_path === "undefined") {
            $.ajax({
                type: "POST",
                url: "check-flag-rfq",
                data: "id=" + id,
                success: function (response) {
                    location.href = response + "?id=" + id + "&mode=" + mode;
                },
                error: function (e) {
                }
            });
        } else {
            window.location.href = dest_path + "?id=" + id + "&mode=" + mode;
        }
    } else {
        window.location.href = "/ProjectManager/project/snapshot";
    }

    $(dlg_id).dialog("close");
}

function dlgProject(mode, version, status, dlg_id, div_id, dest_path) {
    if (version === 'new') {
        getProjectByStatus(status, div_id);
    }

    $(dlg_id).dialog({
        autoOpen: true,
        modal: true,
        title: status,
        width: 374,
        buttons: {
            "submit": function () {
                setProjectByStatus(dlg_id, dest_path, mode);
            }
        },
        show: {
            effect: "blind",
            duration: 1000
        },
        hide: {
            effect: "explode",
            duration: 1000
        }
    });
}

function editProject() {
    var reference = $("#prj-reference").val();

    var data = "reference=" + reference;

    console.log(data);

    $.ajax({
        type: "GET",
        url: "/ProjectManager/project/edit-project",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);

            if (content.status === "OK") {
                if (content.value === "Quotation") {
                    if (confirm("Edit RFQ?")) {
                        location.href = "/ProjectManager/project/request-quotation?id=" + content.pId + "&mode=EDIT-PROJECT"
                    } else {
                        location.href = "/ProjectManager/project/quotation?id=" + content.pId + "&mode=EDIT-PROJECT";
                    }
                } else {
                    location.href = "/ProjectManager/project/" + content.value;
                }
            }
        },
        error: function (e) {
        }
    });

    $("#dlg-edit-project").dialog("close");
}

function dlgEditProject() {
    $("#dlg-edit-project").dialog({
        autoOpen: true,
        modal: true,
        width: 400,
        buttons: {
            "submit": function () {
                editProject();
            }
        },
        show: {
            effect: "blind",
            duration: 1000
        },
        hide: {
            effect: "explode",
            duration: 1000
        }
    });
}

function dlgEditStatus() {
    getStatuses();

    $("#dlg-edit-status").dialog({
        autoOpen: true,
        modal: true,
        width: 372,
        buttons: {
            "submit": function () {
                var status = $('input[name = "radio-project"]:checked').val();
                var dlg_id = "#dlg-edit-status";
                var div_id = "#lst-edit-project";
                var dest_path = null;
                var mode = null;

                if (status === 'Create') {
                    dest_path = "/ProjectManager/project/edit-form";
                    mode = "C-EDIT";
                } else if (status === 'Bill of Materials or Services') {
                    dest_path = "/ProjectManager/project/bill-material-service";
                    mode = "BMS-EDIT";
                } else if (status === 'Quotation') {
                    dest_path = "/ProjectManager/project/quotation";
                    mode = "Q-EDIT";
                } else {
                    dest_path = "/ProjectManager/project/snapshot";
                    mode = "NO-EDIT";
                }

                dlgProject(mode, 'new', status, dlg_id, div_id, dest_path);
            }
        },
        show: {
            effect: "blind",
            duration: 1000
        },
        hide: {
            effect: "explode",
            duration: 1000
        }
    });
}

function fillSearchCriteriaProject(version) {
    $.ajax({
        type: "POST",
        data: "version=" + version,
        url: "search-criteria",
        success: function (response) {
            var content = JSON.parse(response)

            $("#search-type").html(content.type);
            $("#search-status").html(content.status);
            $("#search-company").html(content.company);

            searchContent(0, 10);
        },
        error: function (e) {
        }
    });
}

function alarmEdit() {
    $("#edit").attr('class', 'button alarm');
}

function getProjectInfo(id) {
    var data = "pdId=" + id;

    $.ajax({
        type: "POST",
        data: data,
        url: "history-new-project/bms-info",
        success: function (response) {
            var content = JSON.parse(response);

            $("#bms-name").val(content.bmsName);
            $("#bms-project").val(content.bmsProject);
            $("#bms-complete").val(content.bmsComplete);
            $("#bms-note").val(content.bmsNote);
            $("#bmsi-header").html(content.bmsiHeader);
            $("#bmsi-body").html(content.bmsiBody);
            $("#bmsi-footer").html(content.bmsiFooter);
            if (content.rfqHeader) {
                $("#frm-rfq").show();
                $("#rfq-header").html(content.rfqHeader);
                $("#rfq-body").html(content.rfqBody);
                $("#rfq-footer").html(content.qFooter);
            }
            if (content.qHeader) {
                $("#frm-q").show();
                $("#q-header").html(content.qHeader);
                $("#q-body").html(content.qBody);
                $("#q-footer").html(content.qFooter);
            }
        },
        error: function (xhr, status, error) {
            console.log(error);
        }
    });
}

function dlgViewProject(pdid) {
    $("#project-detail-id").val(pdid);

    getProjectInfo(pdid);

    $("#dlg-view-project").dialog({
        autoOpen: true,
        modal: true,
        width: 760,
        position: {
            my: "top - 25"
        },
        show: {
            effect: "blind",
            duration: 1000
        },
        hide: {
            effect: "explode",
            duration: 1000
        }
    });
}

function getRFQInfo(id) {
    var data = "rqId=" + id;

    $.ajax({
        type: "POST",
        data: data,
        url: "history-new-project/rfq-info",
        success: function (response) {
            var content = JSON.parse(response);

            $("#rfq-name").val(content.rfqName);
            $("#rfq-complete").val(content.rfqComplete);
            $("#rfq-discard").val(content.rfqDiscard);
            $("#rfq-supplier").val(content.rfqSupplier);
            $("#rfq-currency").val(content.rfqCurrency);
            $("#rfq-material-cost").val(content.rfqMaterialCost);
            $("#rfq-delivery-cost").val(content.rfqDeliveryCost);
            $("#rfq-other-expenses").val(content.rfqOtherExpenses);
            $("#rfq-grand-total").val(content.rfqGrandTotal);
            $("#rfq-note").val(content.rfqNote);
            $("#rfq-supplier-note").val(content.rfqSupplierNote);
            $("#rfqi-header").html(content.rfqiHeader);
            $("#rfqi-body").html(content.rfqiBody);
            $("#rfqi-footer").html(content.rfqiFooter);
        },
        error: function (xhr, status, error) {
            console.log(error);
        }
    });
}

function getQInfo(id) {
    var data = "qId=" + id;

    $.ajax({
        type: "POST",
        data: data,
        url: "history-new-project/q-info",
        success: function (response) {
            var content = JSON.parse(response);

            $("#q-name").val(content.qName);
            $("#q-complete").val(content.qComplete);
            $("#q-discard").val(content.qDiscard);
            $("#q-customer").val(content.qCustomer);
            $("#q-customer-ref").val(content.qCustomerRef);
            $("#q-location").val(content.qLocation);
            $("#q-currency").val(content.qCurrency);
            $("#q-availability").val(content.qAvailability);
            $("#q-delivery").val(content.qDelivery);
            $("#q-packing").val(content.qPacking);
            $("#q-payment").val(content.qPayment);
            $("#q-validity").val(content.qValidity);
            $("#q-grand-total").val(content.qGrandTotal);
            $("#q-welcome").val(content.qWelcome);
            $("#q-remarks").val(content.qRemarks);
            $("#q-notes").val(content.qNote);
            $("#qi-header").html(content.qiHeader);
            $("#qi-body").html(content.qiBody);
            $("#qi-footer").html(content.qiFooter);
        },
        error: function (xhr, status, error) {
            console.log(error);
        }
    });
}

function dlgViewRFQ(id) {
    getRFQInfo(id);

    $("#dlg-view-rfq").dialog({
        autoOpen: true,
        modal: true,
        width: 760,
        position: {
            my: "top - 25"
        },
        show: {
            effect: "blind",
            duration: 1000
        },
        hide: {
            effect: "explode",
            duration: 1000
        }
    });
}

function dlgViewQ(id) {
    getQInfo(id);

    $("#dlg-view-q").dialog({
        autoOpen: true,
        modal: true,
        width: 760,
        position: {
            my: "top - 25"
        },
        show: {
            effect: "blind",
            duration: 1000
        },
        hide: {
            effect: "explode",
            duration: 1000
        }
    });
}

function closeAllWindows() {
    wins.forEach(function (win) {
        win.close();
    });

    window.location.href = "/ProjectManager";
}

function getSubProject() {
    var data = "id=" + $('#edit-project-id').val();

    $.ajax({
        type: "POST",
        data: data,
        url: "/ProjectManager/project/lst-sub-project",
        success: function (response) {
            var content = JSON.parse(response);

            if (content.count > 1) {
                $("#lst-sub-project").html(content.value);
                dlgEditSubProject(null);
            } else {
                editSubProject(content.value);
            }
        },
        error: function (xhr, status, error) {
            console.log(error);
        }
    });
}

function editSubProject(id) {
    var pdId = (id === null) ? $('input[name = "radio-sub-project"]:checked').val() : id;
    var company = $("#company option:selected").attr("value");
    var type = $("#type option:selected").attr("value");
    var expired = $("#expired").datepicker({dateFormat: 'yy-mm-dd'}).val();
    var vessel = $("#vessel option:selected").attr("value");
    var customer = $("#customer option:selected").attr("value");
    var contact = $("#contact option:selected").attr("value");
    var data = "";

    if (pdId == -1) {
        alert("No edit the project detail with id:" + pdId);
        return;
    } else {
        data += "id=" + pdId;
    }
    if (company != "none") {
        data += "&company=" + company;
    }
    if (type != "none") {
        data += "&type=" + type;
    }
    if (vessel != -1) {
        data += "&vessel=" + vessel;
    }
    if (customer != "none") {
        data += "&customer=" + customer;
    }
    if (contact != -1) {
        data += "&contact=" + contact;
    }
    if (expired != "") {
        data += "&dateExpired=" + expired;
    }

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/edit-sub-project",
        data: data,
        success: function (response) {
            var content = JSON.parse(response)

            $("#header").html(content.header);
            $("#body").html(content.body);
            $("#edit").attr('class', 'button');
            setTimeout(function () {
                window.location = content.location;
            }, 5000);
        },
        error: function () {
        }
    });
}

function dlgEditSubProject(url) {
    $("#dlg-sub-project").dialog({
        autoOpen: true,
        modal: true,
        width: 374,
        buttons: {
            "submit": function () {
                $("#dlg-sub-project").dialog("close");
                editSubProject(url);
            }
        },
        show: {
            effect: "blind",
            duration: 1000
        },
        hide: {
            effect: "explode",
            duration: 1000
        }
    });
}