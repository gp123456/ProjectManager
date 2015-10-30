/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//insert new item in Project Bill table
function insertItem(sel) {
    var data = "pdId=" + $("#bill-subproject option:selected").val() + "&id=" + sel.value;

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/new",
        data: data,
        success: function (response) {
            $("#project-bill-items").html(response);
        },
        error: function (e) {
        }
    });
}

function saveValues(pdid, id) {
    var available = Number($("#available" + pdid + id).text());
    var quantity = Number($("#quantity" + pdid + id).text());

    if (available < quantity) {
        alert("The quantity must is less or equal than quantity of item [" + available + "," + quantity + "]");
    } else {
        var data = "pdId=" + pdid + "&item=" + id + "&quantity=" + $("#quantity" + pdid + id).text() + "&cost=" + $("#cost" + pdid + id).text() +
                "&totalCost=" + $("#total_cost" + pdid + id).text() + "&percentage=" + $("#percentage" + pdid + id).text() + "&discount=" +
                $("#discount" + pdid + id).text() + "&salePrice=" + $("#sale_price" + pdid + id).text() + "&totalSalePrice=" + $("#total_sale_price" +
                pdid + id).text() + "&totalNetPrice=" + $("#total_net_price" + pdid + id).text();

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

function removeValues(pdid, id) {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/remove",
        data: "pdId=" + pdid + "&item=" + id,
        success: function (response) {
            var content = JSON.parse(response);

            $("#project-bill").html(content.project_bill);
            $("#project-bill-items").html(content.project_bill_items);
        },
        error: function (e) {
        }
    });
}

function refreshValues(pdid, id) {
    var data = "pdId=" + pdid + "&item=" + id + "&available=" + $("#available" + pdid + id).text() + "&price=" + $("#price" + pdid + id).text() +
            "&quantity=" + $("#quantity" + pdid + id).text() + "&cost=" + $("#cost" + pdid + id).text() + "&percentage=" + $("#percentage" +
            pdid + id).text() + "&discount=" + $("#discount" + pdid + id).text() + "&currency=" + $("#currency" + pdid + id + " option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/refresh",
        data: data,
        success: function (response) {
            $("#project-bill-items").html(response);
        },
        error: function (e) {
        }
    });
}

function editValues(pdid, id) {
    $("#quantity" + pdid + id).html("<div contenteditable></div>");
    $("#cost" + pdid + id).html("<div contenteditable></div>");
    $("#percentage" + pdid + id).html("<div contenteditable></div>");
    $("#discount" + pdid + id).html("<div contenteditable></div>");
}

function saveProjectBill() {
    var data = "project=" + $("#bill-projectdetail-id").val() + "&total_cost=" + $("#m_total_cost").text() + "&averange_discount=" +
            $("#m_average_discount").text() + "&total_sale_price=" + $("#m_sale_price").text() + "&total_net_price=" + $("#m_net_sale_price").text() +
            "&note=" + $("#notes").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/save",
        data: data,
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

function stockNewItem() {
    alert("pressed stock");

    var data = "pdId=" + $("#bill-subproject option:selected").val() + "&imno=" + $("#item-imno").val() + "description=" + $("#item-desc").val() +
            "location=" + $("#item-location option:selected").val() + "quantity=" + $("#item-quantity").val() + "price=" + $("#item-price").val() +
            "company=" + $("#item-location option:selected").val();
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/insert-new-item",
        data: data,
        success: function (response) {
            $("#project-bill-items").html(response);
        },
        error: function (e) {
        }
    });

    $("#add-item").dialog("close");
}

function nostockNewItem() {
    alert("pressed no stock");

    $("#add-item").dialog("close");
}

function dlgNewItem() {
    $("#add-item").dialog({
        autoOpen: true,
        modal: true,
        widht: 365,
        buttons: {
            "stock": function () {
                stockNewItem();
            },
            "no stock": function () {
                nostockNewItem();
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

function addSubProject() {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/new-subproject",
        success: function (response) {
            var content = JSON.parse(response);

            $("#new-project-company").html(content.company);
            $("#new-project-type").html(content.type);
            $("#new-project-vessel").html(content.vessel);
            $("#new-project-customer").html(content.customer);
            $("#new-project-contact").html(content.contact);
//            $("#new-project-expired").attr('value', content.expired);
            dlgNewSubProject();
        },
        error: function (e) {
        }
    });
}

function dlgNewSubProject() {
    $("#new-subproject").dialog({
        autoOpen: true,
        modal: true,
        width: 450,
        buttons: {
            "submit": function () {
                saveSubProject();
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

function saveSubProject() {
    var type = $("#new-project-type option:selected").attr("value");
    var expired = $("#new-project-expired").val();
    var expired_year = expired.split("-")[0];
    var expired_month = expired.split("-")[1];
    var expired_day = expired.split("-")[2];
    var customer = $("#new-project-customer option:selected").attr("value");
    var vessel = $("#new-project-vessel option:selected").attr("value");
    var company = $("#new-project-company option:selected").attr("value");
    var contact = $("#new-project-contact option:selected").attr("value");
    var project = $("#bill-project-id").val();
    var id = $("#bill-projectdetail-id").val();

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
    if (customer == "none") {
        alert("you must select a customer or add one");
        return;
    }
    if (contact == -1) {
        alert("you must select a contact or add one");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/subproject-save",
        data: "id=" + id + "&project=" + project + "&type=" + type + "&expired=" +
                expired_month + "/" + expired_day + "/" + expired_year + "&customer=" +
                customer + "&vessel=" + vessel + "&company=" + company + "&contact=" +
                contact,
        success: function (response) {
            $("#bill-subproject").html(response);
        },
        error: function (e) {
        }
    });

    $("#new-subproject").dialog("close");
}