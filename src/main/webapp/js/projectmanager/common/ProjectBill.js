/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//insert new item in Project Bill table
function insertItem(sel) {
    var data = "pdId=" + $("#bill-subproject option:selected").val() + "&location=1&item=" +
            sel.value + "&classRefresh=button alarm&classSave=button alarm";

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/item/insert",
        data: data,
        success: function (response) {
            $("#project-bill-items").html(response);
        },
        error: function (e) {
        }
    });
}

function saveValues(pdid, location, id) {
    var quantity = Number($("#quantity" + pdid + location + id).text());
    var cost = Number($("#cost" + pdid + location + id).text());
    var percentage = Number($("#percentage" + pdid + location + id).text());
    var discount = Number($("#discount" + pdid + location + id).text());
    var currency = $("#currency" + pdid + location + id + " option:selected").val();
    var total_cost = $("#total_cost" + pdid + location + id).text();
    var sale_price = $("#sale_price" + pdid + location + id).text();
    var total_sale_price = $("#total_sale_price" + pdid + location + id).text();
    var total_net_price = $("#total_net_price" + pdid + location + id).text();

    var data = "pdId=" + pdid + "&location=" + location + "&item=" + id + "&quantity=" +
            quantity + "&cost=" + cost + "&totalCost=" + total_cost + "&percentage=" +
            percentage + "&discount=" + discount + "&salePrice=" + sale_price +
            "&totalSalePrice=" + total_sale_price + "&totalNetPrice=" + total_net_price +
            "&currency=" + currency + "&classRefresh=button&classSave=button";

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/item/save",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);

            $("#project-bill").html(content.projectBill);
            $("#project-bill-items").html(content.projectBillItems);
        },
        error: function (e) {
        }
    });
}

function removeValues(pdid, location, id) {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/item/remove",
        data: "pdId=" + pdid + "&location=" + location + "&item=" + id,
        success: function (response) {
            var content = JSON.parse(response);

            $("#project-bill").html(content.projectBill);
            $("#project-bill-items").html(content.projectBillItems);
        },
        error: function (e) {
        }
    });
}

function refreshValues(pdid, location, id) {
    var available = Number($("#available" + pdid + location + id).text());
    var quantity = Number($("#quantity" + pdid + location + id).text());
    var cost = Number($("#cost" + pdid + location + id).text());
    var percentage = Number($("#percentage" + pdid + location + id).text());
    var discount = Number($("#discount" + pdid + location + id).text());

    if (isNaN(quantity) || quantity == 0) {
        alert("You must give a valid number quantity");
    } else if (isNaN(cost) || cost == 0) {
        alert("You must give a valid number cost");
    } else if (isNaN(percentage) || percentage == 0) {
        alert("You must give a valid number percentage");
    } else if (isNaN(discount) || discount == 0) {
        alert("You must give a valid number discount");
    } else {
        var conf = true;
        if (available < quantity) {
            conf = confirm("The quantity must is less or equal than quantity of item: " + available);
        }

        if (conf == true) {
            var data = "pdId=" + pdid + "&location=" + location + "&item=" + id + "&available=" +
                    available + "&price=" + $("#price" + pdid + id).text() + "&quantity=" +
                    quantity + "&cost=" + cost + "&percentage=" + percentage + "&discount=" + discount +
                    "&currency=" + $("#currency" + pdid + id + " option:selected").val() +
                    "&classRefresh=button&classSave=button alarm";

            $.ajax({
                type: "POST",
                url: "/ProjectManager/project/project-bill/item/refresh",
                data: data,
                success: function (response) {
                    $("#project-bill-items").html(response);
                },
                error: function (e) {
                }
            });
        }
    }
}

function editValues(pdid, location, id) {
    var data = "pdId=" + pdid + "&location=" + location + "&item=" + id +
            "&classRefresh=button alarm&classSave=button alarm";

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/item/edit",
        data: data,
        success: function (response) {
            $("#project-bill-items").html(response);

            $("#quantity" + pdid + id).html("<div contenteditable></div>");
            $("#cost" + pdid + id).html("<div contenteditable></div>");
            $("#percentage" + pdid + id).html("<div contenteditable></div>");
            $("#discount" + pdid + id).html("<div contenteditable></div>");
        },
        error: function (e) {
        }
    });
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
    var imno = $("#item-imno").val();
    var location = $("#item-location option:selected").val();
    var quantity = Number($("#item-quantity").val());
    var price = Number($("#item-price").val());
    var company = $("#item-supplier option:selected").val();

    if (imno == 0) {
        $("#validate-add-item").text("You must give a item reference");
    } else if (isNaN(quantity) || quantity == 0) {
        $("#validate-add-item").text("You must give a valid number quantity");
    } else if (isNaN(price) || price == 0) {
        $("#validate-add-item").text("You must give a valid number price");
    } else if (location == -1) {
        $("#validate-add-item").text("You must select a location");
    } else if (company == -1) {
        $("#validate-add-item").text("You must select a supplier");
    } else {
        var data = "pdId=" + $("#bill-subproject option:selected").val() + "&imno=" +
                imno + "&description=" + $("#item-desc").val() + "&location=" + location +
                "&quantity=" + quantity + "&price=" + price + "&company=" + company;

        $.ajax({
            type: "POST",
            url: "/ProjectManager/project/project-bill/item-stock/insert1",
            data: data,
            success: function (response) {
                $("#project-bill-items").html(response);
            },
            error: function (xhr, status, error) {
                alarm(error);
            }
        });

        $("#add-item").dialog("close");
    }
}

function nostockNewItem() {
    var imno = $("#item-imno").val();
    var quantity = Number($("#item-quantity").val());
    var price = Number($("#item-price").val());

    if (imno == 0) {
        $("#validate-add-item").text("You must give a item reference");
    } else if (isNaN(quantity) || quantity == 0) {
        $("#validate-add-item").text("You must give a valid number quantity");
    } else if (isNaN(price) || price == 0) {
        $("#validate-add-item").text("You must give a valid number price");
    } else {
        var data = "pdId=" + $("#bill-subproject option:selected").val() + "&location=1&imno=" +
                imno + "&quantity=" + quantity + "&price=" + price;
        $.ajax({
            type: "POST",
            url: "/ProjectManager/project/project-bill/item-nostock/insert",
            data: data,
            success: function (response) {
                $("#project-bill-items").html(response);
            },
            error: function (e) {
            }
        });

        $("#add-item").dialog("close");
    }
}

function dlgNewItem() {
    $("#add-item").dialog({
        autoOpen: true,
        modal: true,
        width: 365,
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
//            $("#new-project-vessel").html(content.vessel);
//            $("#new-project-customer").html(content.customer);
//            $("#new-project-contact").html(content.contact);
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
    var company = $("#new-project-company option:selected").attr("value");
    var project = $("#bill-project-id").val();
//    var customer = $("#new-project-customer option:selected").attr("value");
//    var vessel = $("#new-project-vessel option:selected").attr("value");
//    var contact = $("#new-project-contact option:selected").attr("value");

    if (company == "none") {
        alert("you must select company");
        return;
    }
    if (type == "none") {
        alert("you must select type");
        return;
    }
//    if (vessel == -1) {
//        alert("you must select a vessel or add one");
//        return;
//    }
//    if (customer == "none") {
//        alert("you must select a customer or add one");
//        return;
//    }
//    if (contact == -1) {
//        alert("you must select a contact or add one");
//        return;
//    }

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/subproject-save",
        data: "project=" + project + "&type=" + type + "&expired=" + expired_month +
                "/" + expired_day + "/" + expired_year, // + "&customer=" + customer +
//                "&vessel=" + vessel + "&company=" + company + "&contact=" + contact,
        success: function (response) {
            var content = JSON.parse(response);

            $("#bill-subproject").html(content.projectDetails);
            $("#bill-project-id").html(content.projectId);
        },
        error: function (e) {
        }
    });

    $("#new-subproject").dialog("close");
}

function addProjectBillItems() {
}

function dlgReplaceProjectBillItems() {
    $("#replace-project-bill-items").dialog({
        autoOpen: true,
        modal: true,
        width: 250,
        buttons: {
            "submit": function () {
                addProjectBillItems();
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

function replaceProjectBillItems(pdid) {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/project-bill/replace-project-bill-items",
        data: "pdid=" + pdid,
        success: function (response) {
            $("#lst-project-bill-items").html(response);
            dlgReplaceProjectBillItems();
        },
        error: function (e) {
        }
    });
}