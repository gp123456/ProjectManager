/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//insert new item in Project Bill table
function insertItem(sel) {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/new",
        data: "id=" + sel.value,
        success: function (response) {
            $("#project-bill-items").html(response);
        },
        error: function (e) {
        }
    });
}

function newProjectBillInfo(id) {
    var available = Number($("#available" + id).text());
    var quantity = Number($("#quantity" + id).text());

    if (available < quantity) {
        alert("The quantity must is less or equal than quantity of item [" + available + "," + quantity + "]");
    } else {
        var data = "id=" + id + "&quantity=" + $("#quantity" + id).text() + "&cost=" + $("#cost" + id).text() +
                "&totalCost=" + $("#total_cost" + id).text() + "&percentage=" + $("#percentage" + id).text() +
                "&discount=" + $("#discount" + id).text() + "&salePrice=" + $("#sale_price" + id).text() +
                "&totalSalePrice=" + $("#total_sale_price" + id).text() + "&totalNetPrice=" +
                $("#total_net_price" + id).text();

        $.ajax({
            type: "POST",
            url: "/ProjectManager/project/project-bill/new/info",
            data: data,
            success: function (response) {
                var content = JSON.parse(response);

                $("#project-bill").html(content.project_bill);
                $("#project-bill-items").html(content.project_bill_items);
            },
            error: function (e) {
            }
        });
    }
}

function removeProjectBillInfo(id) {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/remove/info",
        data: "id=" + id,
        success: function (response) {
            var content = JSON.parse(response);

            $("#project-bill").html(content.project_bill);
            $("#project-bill-items").html(content.project_bill_items);
        },
        error: function (e) {
        }
    });
}

function saveProjectBill() {

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/save",
        data: "project=" + $("#bill-projectdetail-id").val() +
                "&total_cost=" + $("#m_total_cost").text() +
                "&averange_discount=" + $("#m_average_discount").text() +
                "&total_sale_price=" + $("#m_sale_price").text() +
                "&total_net_price=" + $("#m_net_sale_price").text() +
                "&note=" + $("#notes").val(),
        success: function () {
            location.reload();
        },
        error: function (e) {
        }
    });
}

function createProjectBillPDF() {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/createpdf",
        success: function () {
        },
        error: function (e) {
        }
    });
}

function changeValues(id) {
    var data = "id=" + id + "&code=" + $("#code" + id).text() + "&description=" + $("#description" + id).text() +
            "&available=" + $("#available" + id).text() + "&price=" + $("#price" + id).text() +
            "&quantity=" + $("#quantity" + id).text() + "&cost=" + $("#cost" + id).text() +
            "&percentage=" + $("#percentage" + id).text() + "&discount=" + $("#discount" + id).text();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/changevalues",
        data: data,
        success: function (response) {
            $("#project-bill-items").html(response);
        },
        error: function (e) {
        }
    });
}

function addItem() {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/add-item",
        success: function (response) {
            var content = JSON.parse(response);

            $("#item-location").html(content.location);
            $("#item-supplier").html(content.supplier);
            dlgNewItem();
        },
        error: function (e) {
        }
    });
}

function insertNewItem() {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/insert-new-item",
        data: "imno=" + $("#item-imno").val() +
                "description=" + $("#item-desc").val() +
                "location=" + $("#item-location option:selected").val() +
                "quantity=" + $("#item-quantity").val() +
                "price=" + $("#item-price").val() +
                "company=" + $("#item-location option:selected").val(),
        success: function (response) {
            $("#project-bill-items").html(response);
        },
        error: function (e) {
        }
    });

    $("#add-item").dialog("close");
}

function dlgNewItem() {
    $("#add-item").dialog({
        autoOpen: true,
        modal: true,
        buttons: {
            "submit": function () {
                insertNewItem();
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

function editValues(id) {
    $("#quantity" + id).html("<div contenteditable></div>");
    $("#cost" + id).html("<div contenteditable></div>");
    $("#percentage" + id).html("<div contenteditable></div>");
    $("#discount" + id).html("<div contenteditable></div>");
}

function addSubProject() {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/new-subproject",
        success: function (response) {
            var content = JSON.parse(response);

            $("#new-subproject-company").html(content.company);
            $("#new-subproject-type").html(content.type);
            $("#new-subproject-vessel").html(content.vessel);
            $("#new-subproject-customer").html(content.customer);
            $("#new-subproject-contact").html(content.contact);
            dlgNewSubProject();
        },
        error: function (e) {
        }
    });
}

function dlgNewSubProject() {
    $("#new-subproject-expired").datepicker();

    $("#new-subproject").dialog({
        autoOpen: true,
        modal: true,
        buttons: {
            "submit": function () {
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