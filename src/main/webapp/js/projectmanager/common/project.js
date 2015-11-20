/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function searchContent() {
    var data = "id=" + $("#search-reference option:selected").attr("value") + "&type=" +
            $("#search-type option:selected").attr("value") + "&status=" +
            $("#search-status option:selected").attr("value") + "&vessel=" +
            $("#search-vessel option:selected").attr("value") + "&customer=" +
            $("#search-customer option:selected").attr("value") + "&company=" +
            $("#search-company option:selected").attr("value") + "&offset=0&size=10";

    $.ajax({
        type: "POST",
        url: "search",
        data: data,
        success: function (response) {
            var content = JSON.parse(response)

            $("#project-header").html(content.project_header);
            $("#project-footer").html(content.project_footer);
            $("#project-body").html(content.project_body);
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
        success: function (response) {
            location.reload();
        },
        error: function (e) {
        }
    });
}

function removeRow(id) {
    $.ajax({
        type: "POST",
        url: "remove",
        data: "id=" + id + "&offset=0&size=10",
        success: function (response) {
            var content = JSON.parse(response)

            $("#search-reference").html(content.reference);
            $("#project-header").html(content.project_header);
            $("#project-body").html(content.project_body);
        },
        error: function (e) {
        }
    });
}

function editRow(pdId) {
    var company = $("#new-project-company option:selected").attr("value");
    var type = $("#new-project-type option:selected").attr("value");
    var expired = $("#new-project-expired").val();
    var vessel = $("#new-project-vessel option:selected").attr("value");
    var customer = $("#new-project-customer option:selected").attr("value");
    var contact = $("#new-project-contact option:selected").attr("value");
    var data = "";

    if (pdId == -1) {
        alert("No edit the project detail with id:" + id);
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
    if (expired != "") {
        data += "&expired=" + expired;
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

    $.ajax({
        type: "POST",
        url: "project-edit",
        data: data,
        success: function (response) {
            var content = JSON.parse(response)

            $("#project-header").html(content.project_header);
            $("#project-body").html(content.project_body);
        },
        error: function (e) {
        }
    });
}

function createTo(id) {
    $.ajax({
        type: "POST",
        url: "createpdf",
        data: "id=" + id + "&offset=0&size=10",
        success: function (response) {
            var content = JSON.parse(response)

            $("#project-header").html(content.project_header);
            $("#project-body").html(content.project_body);
        },
        error: function (e) {
        }
    });
}

function printTo(id) {
    $("#print-to-" + id).button().click(function () {
        alert("Running the last action");
    }).next().button({
        text: false,
        icons: {
            primary: "ui-icon-triangle-1-s"
        }
    }).click(function () {
        var menu = $(this).parent().next().show().position({
            my: "left top",
            at: "left bottom",
            of: this
        });
        $(document).one("click", function () {
            menu.hide();
        });
        return false;
    }).parent().buttonset().next().hide().menu();

    $("#doc-to-" + id).button().click(function () {
        alert($(this).text());
    });

//    $.ajax({
//        type: "POST",
//        url: "printpdf",
//        data: "id=" + id + "&offset=0&size=10",
//        success: function (response) {
//            var content = JSON.parse(response)
//
//            $("#project-header").html(content.project_header);
//            $("#project-body").html(content.project_body);
//        },
//        error: function (e) {
//        }
//    });
}

function createXLS(id) {
    $.ajax({
        type: "POST",
        url: "createxls",
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

function printXLS(id) {
    $.ajax({
        type: "POST",
        url: "printxls",
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

function sendEnail(id) {
    $.ajax({
        type: "POST",
        url: "sendemail",
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

function saveProject() {
    var type = $("#new-project-type option:selected").attr("value");
    var expired = $("#new-project-expired").val();
    var customer = $("#new-project-customer option:selected").attr("value");
    var vessel = $("#new-project-vessel option:selected").attr("value");
    var company = $("#new-project-company option:selected").attr("value");
    var contact = $("#new-project-contact option:selected").attr("value");

    if (company == "none") {
        alert("you must select company");
        return;
    }
    if (type == "none") {
        alert("you must select type");
        return;
    }
    if (vessel == -1) {
        alert("you must select a vessel or add one");
        return;
    }
    if (customer == -1) {
        alert("you must select a customer or add one");
        return;
    }
    if (contact == -1) {
        alert("you must select a contact or add one");
        return;
    }

    $.ajax({
        type: "POST",
        url: "save",
        data: "type=" + type + "&expired=" + expired + "&customer=" + customer
                + "&vessel=" + vessel + "&company=" + company + "&contact=" +
                contact,
        success: function (response) {
            var content = JSON.parse(response)

            if (content.project_header)
                $("#project-header").html(content.project_header);
            if (content.project_body)
                $("#project-body").html(content.project_body);
            $("#project-reference").text(content.project_reference);
        },
        error: function (e) {
        }
    });
}

function projectFirstPage() {
    alert("projectFirstPage");
}

function projectPreviousPage() {
    alert("projectPreviousPage");
}

function projectNextPage() {
    alert("projectNextPage");
}

function projectLastPage() {
    alert("projectLastPage");
}

function projectPackingList(id) {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/packing-list/id",
        data: "id=" + id,
        success: function (response) {
            var content = JSON.parse(response)

            $("#project-header").html(content.project_header);
            $("#project-body").html(content.project_body);
            $("#project-footer").html(content.project_footer);
        },
        error: function (e) {
        }
    });
}

function projectFilterCustomer() {
    $.ajax({
        type: "POST",
        url: "filter-customer",
        data: "customer=" + $("#new-project-customer option:selected").attr("value"),
        success: function (response) {
            var content = JSON.parse(response)

            $("#new-project-vessel").html(content.vessel);
            $("#new-project-contact").html(content.contact);
        },
        error: function (e) {
        }
    });
}

//function projectFilterVessel() {
//    $.ajax({
//        type: "POST",
//        url: "filter-vessel",
//        data: "vessel=" + $("#new-project-vessel option:selected").attr("value"),
//        success: function (response) {
//            var content = JSON.parse(response)
//
//            $("#new-project-customer").html(content.customer);
//            $("#new-project-contact").html(content.contact);
//        },
//        error: function (e) {
//        }
//    });
//}

function plotStatusInfo(id, title, info) {
    var chart = new CanvasJS.Chart(id, {
        title: {
            text: title
        },
        data: [{
                type: "pie",
                dataPoints: [
                    {label: "Create[" + info[1] + "]", y: info[1]},
                    {label: "Bill Material[" + info[2] + "]", y: info[2]},
                    {label: "Request Quota[" + info[3] + "]", y: info[3]},
                    {label: "Purchase Order[" + info[4] + "]", y: info[4]},
                    {label: "Work Order[" + info[5] + "]", y: info[5]},
                    {label: "Ack Order[" + info[6] + "]", y: info[6]},
                    {label: "Packing List[" + info[7] + "]", y: info[7]},
                    {label: "Delivery Note[" + info[8] + "]", y: info[8]},
                    {label: "Ship Invoice[" + info[9] + "]", y: info[9]}
                ]
            }
        ]
    });

    chart.render();
}

function plotCompanyInfo(id, title, info) {
    var chart = new CanvasJS.Chart(id, {
        title: {
            text: title
        },
        data: [{
                type: "pie",
                dataPoints: [
                    {label: "WCS[" + info[1] + "]", y: info[1]},
                    {label: "WCS HELLAS[" + info[2] + "]", y: info[2]},
                    {label: "WCS LTD[" + info[3] + "]", y: info[3]},
                    {label: "MTS[" + info[4] + "]", y: info[4]}
                ]
            }
        ]
    });

    chart.render();
}

function dashboardView() {
    $.ajax({
        type: "POST",
        url: "view",
        success: function (response) {
            var content = JSON.parse(response);

            if (content.OpenProjectSaleStatus != null &&
                    content.OpenProjectServiceStatus != null &&
                    content.OpenProjectSaleCompany != null &&
                    content.OpenProjectServiceCompany != null) {
                var totalSaleStatus = content.OpenProjectSaleStatus[0];
                var totalServiceStatus = content.OpenProjectServiceStatus[0];
                var totalSaleCompany = content.OpenProjectSaleCompany[0];
                var totalServiceCompany = content.OpenProjectServiceCompany[0];

                plotStatusInfo("open-project-sale-status",
                        "Open Projects of Sale per Status[" + totalSaleStatus + "]",
                        content.OpenProjectSaleStatus);
                plotStatusInfo("open-project-service-status",
                        "Open Projects of Services per Status[" + totalServiceStatus
                        + "]", content.OpenProjectServiceStatus);
                plotCompanyInfo("open-project-sale-company",
                        "Open Projects of Sale per Company[" + totalSaleCompany
                        + "]", content.OpenProjectSaleCompany);
                plotCompanyInfo("open-project-service-company",
                        "Open Projects of Services per Company[" +
                        totalServiceCompany + "]", content.OpenProjectServiceCompany);
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

function setProjectBill(path) {
    var id = $('input[name = "radio-project"]:checked').val();
    
    window.location.href = path + "?id=" + id;

    $("#dlg-edit-project").dialog("close");
}

function dlgProject(status, dlg_id, div_id, dest_path) {
    getProjectByStatus(status, div_id);

    $(dlg_id).dialog({
        autoOpen: true,
        modal: true,
        title: status,
        width: 374,
        buttons: {
            "submit": function () {
                setProjectBill(dest_path);
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

function dlgEditProject() {
    getStatuses()

    $("#dlg-edit-project").dialog({
        autoOpen: true,
        modal: true,
        width: 372,
        buttons: {
            "submit": function () {
                var status = $('input[name = "radio-project"]:checked').val();
                var dlg_id = "#dlg-edit-project";
                var div_id = "#lst-edit-project";
                var dest_path = null;
                
                if (status == 'Create') {
                    dest_path = "/ProjectManager/project/edit-form";
                }
                else if (status == 'Project Bill') {
                    dest_path = "/ProjectManager/project/project-bill";
                }

                dlgProject(status, dlg_id, div_id, dest_path);
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
