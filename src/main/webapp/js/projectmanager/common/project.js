/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function searchContent(version) {
    var data = "version=" + version + "&reference=" +
            $("#search-reference").attr("value") + "&type=" +
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
        data: "id=" + id,
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

function plotStatusInfo(id, title, info) {
    var chart = new CanvasJS.Chart(id, {
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
                    {label: info[8]["name"] + "[" + info[8]["value"] + "]", y: info[8]["value"]}
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
            var totalSaleStatus = null, totalServiceStatus = null, totalSaleCompany = null,
                    totalServiceCompany = null;
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
                        "Open Projects of Sale per Status[" + totalSaleStatus + "]",
                        SaleStatus.sort(sortNumber));
            }
            if (totalServiceStatus !== null) {
                plotStatusInfo("open-project-service-status",
                        "Open Projects of Services per Status[" + totalServiceStatus
                        + "]", ServiceStatus.sort(sortNumber));
            }
            if (totalSaleCompany !== null) {
                plotCompanyInfo("open-project-sale-company",
                        "Open Projects of Sale per Company[" + totalSaleCompany
                        + "]", SaleCompany.sort(sortNumber));
            }
            if (totalServiceCompany !== null) {
                plotCompanyInfo("open-project-service-company",
                        "Open Projects of Services per Company[" +
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

function setProjectBill(path) {
    var id = $('input[name = "radio-project"]:checked').val();

    window.location.href = path + "?id=" + id;

    $("#dlg-edit-project").dialog("close");
}

function dlgProject(version, status, dlg_id, div_id, dest_path) {
    if (version == 'new') {
        getProjectByStatus(status, div_id);
    }

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
                } else if (status == 'Project Bill') {
                    dest_path = "/ProjectManager/project/project-bill";
                }

                dlgProject('new', status, dlg_id, div_id, dest_path);
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

function fillSearchCriteriaProject() {
    $.ajax({
        type: "POST",
        url: "search-criteria",
        success: function (response) {
            var content = JSON.parse(response)

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
