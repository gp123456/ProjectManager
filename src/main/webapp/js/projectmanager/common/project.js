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
    alert(id);

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

function editRow(id) {
    var data = "id=" + id + "&company=" + $("#company option:selected").attr("value") + "&vessel=" +
            $("#vessel option:selected").attr("value") + "&customer=" +
            $("#customer option:selected").attr("value") + "&contact=" +
            $("#contact option:selected").attr("value") + "&offset=0&size=10";

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

function createPDF(id) {
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

function printPDF(id) {
    $.ajax({
        type: "POST",
        url: "printpdf",
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
    alert($("#new-project-expired").val());
    
    var data = "type=" + $("#new-project-type option:selected").attr("value")  +
            "&expired=" + $("#new-project-expired").val() +
            "&customer=" + $("#new-project-customer option:selected").attr("value") +
            "&vessel=" + $("#new-project-vessel option:selected").attr("value") +
            "&company=" + $("#new-project-company option:selected").attr("value") +
            "&contact=" + $("#new-project-contact option:selected").attr("value") +
            "&offset=0&size=10";

    $.ajax({
        type: "POST",
        url: "save",
        data: data,
        success: function (response) {
            var content = JSON.parse(response)

            $("#search-reference").html(content.reference);
            $("#project-header").html(content.project_header);
            $("#project-body").html(content.project_body);
            $("#project-reference1").text(content.project_reference);
            if (content.project_type == "SERVICE" || content.project_type == "SALE") {
                $(content.project_size_id).html(content.project_size);
                $(content.project_id).html(content.project_info);
            } else if (content.project_type == "MTS") {
                $(content.project_sale_size_id).html(content.project_sale_size);
                $(content.project_sale_id).html(content.project_sale_info);
                $(content.project_mts_size_id).html(content.project_mts_size);
                $(content.project_mts_id).html(content.project_mts_info);
            }
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

function projectFilterVessel() {
    $.ajax({
        type: "POST",
        url: "filter-vessel",
        data: "vessel=" + $("#new-project-vessel option:selected").attr("value"),
        success: function (response) {
            var content = JSON.parse(response)

            $("#new-project-customer").html(content.customer);
            $("#new-project-contact").html(content.contact);
        },
        error: function (e) {
        }
    });
}