/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global auto */

function insertItem() {
    var data = "pdId=" + $("#subproject option:selected").val() +
            "&item=" + $("#item option:selected").val() +
            "&classSave=button alarm";

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/item/insert",
        data: data,
        success: function (response) {

            $("#bill-material-service-item").html(response);
        },
        error: function (e) {
        }
    });
}

function changeItem() {
    var data = "itemId=" + $("#item option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/item/change",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);

            $("#availability").val(content.availability);
            $("#price").val(content.price);
        },
        error: function (e) {
        }
    });
}

function saveItem(pdid, id) {
    var quantity = Number($("#quantity" + pdid + id).text());

    var data = "pdId=" + pdid +
            "&item=" + id +
            "&quantity=" + ((isNaN(quantity) || quantity === null) ? 0 : quantity) +
            "&classSave=button";

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/item/save",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);

            $("#bill-material-service").html(content.billMaterialService);
            $("#bill-material-service-item").html(content.billMaterialServiceItems);
            $("#note").val(content.note);
        },
        error: function (e) {
        }
    });
}

function removeItem(pdid, id) {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/item/remove",
        data: "pdId=" + pdid +
                "&item=" + id,
        success: function (response) {
            $("#bill-material-service-item").html(response);
        },
        error: function (e) {
        }
    });
}

function editItem(pdid, id) {
    var data = "pdId=" + pdid +
            "&item=" + id +
            "&classRefresh=button alarm&classSave=button alarm";

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/item/edit",
        data: data,
        success: function (response) {
            $("#bill-material-service-item").html(response);

            $("#quantity" + pdid + id).html("<div contenteditable></div>");
            $("#cost" + pdid + id).html("<div contenteditable></div>");
            $("#percentage" + pdid + id).html("<div contenteditable></div>");
            $("#discount" + pdid + id).html("<div contenteditable></div>");
        },
        error: function (e) {
        }
    });
}

function saveBillMaterialService() {
    var pdId = $("#subproject option:selected").val();
    var data = "project=" + pdId +
            "&name=" + $("#name" + pdId).text() +
            "&note=" + $("#note").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/save",
        data: data,
        success: function (response) {
            if (response === "index") {
                location.reload();
            } else {
                $("#bill-header").html(response);
            }
        },
        error: function (e) {
        }
    });
}

function savePDF(prjRef) {
    var data = "projectReference=" + prjRef;

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/savepdf",
        data: data,
        success: function () {
        },
        error: function () {
        }
    });
}

function saveXLS(prjRef) {
    var data = "projectReference=" + prjRef;

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/savexls",
        data: data,
        success: function (response) {
            $("#button-action-message").html(response);
        },
        error: function (e) {
        }
    });
}

function sendEmail(prjRef) {
    var data = "projectReference=" + prjRef;

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/sendemail",
        data: data,
        success: function (response) {
            $("#button-action-message").html(response);
        },
        error: function (e) {
        }
    });
}

function addItem() {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/add-item",
        success: function (response) {
            var content = JSON.parse(response);

            $("#item-currency").html(content.currency);
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
    var currency = $("#item-currency option:selected").val();

    if (imno === 0) {
        $("#validate-add-item").text("You must give a item reference");
    } else if (isNaN(quantity) || quantity === 0) {
        $("#validate-add-item").text("You must give a valid number quantity");
    } else if (isNaN(price) || price === 0.0) {
        $("#validate-add-item").text("You must give a valid number price");
    } else if (location === -1) {
        $("#validate-add-item").text("You must select a location");
    } else if (company === -1) {
        $("#validate-add-item").text("You must select a supplier");
    } else {
        var data = "pdId=" + $("#subproject option:selected").val() + "&location=1&imno=" +
                imno + "&description=" + $("#item-desc").val() + "&location=" + location +
                "&quantity=" + quantity + "&price=" + price + "&company=" + company + "&currency=" + currency;

        $.ajax({
            type: "POST",
            url: "/ProjectManager/project/bill-material-service/item/stock",
            data: data,
            success: function (response) {
                $("#bill-material-service-item").html(response);
            },
            error: function (xhr, status, error) {
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
        url: "/ProjectManager/project/bill-material-service/new-subproject",
        success: function (response) {
            var content = JSON.parse(response);

            $("#subproject-company").html(content.company);
            $("#type").html(content.type);
            $("#expired").val(content.expired);
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
    var type = $("#type option:selected").val();
    var expired = $("#expired").datepicker({dateFormat: 'yy-mm-dd'}).val();
    var company = $("#subproject-company option:selected").val();
    var project = $("#bill-project-id").val();

    if (company === "none") {
        alert("you must select company");
        return;
    }
    if (type === "none") {
        alert("you must select type");
        return;
    }

    var data = "project=" + project + "&type=" + type + "&expired=" + expired + "&company=" + company;

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/save-subproject",
        data: data,
        success: function (response) {
            location.reload();
        },
        error: function (e) {
        }
    });

    $("#new-subproject").dialog("close");
}

function changeSubproject() {
    var data = "id=" + $("#subproject option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/change-subproject",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);

            $("#info-type").val(content.type);
            $("#company").val(content.company);
            $("#customer").val(content.customer);
            $("#vessel").val(content.vessel);
            $("#contact").val(content.contact);
            $("#bill-material-service").html(content.billMaterialService);
            if (content.noItems === "false") {
                $('#select-item').show();
                $('#select-bill-material-service-item').show();
                $("#bill-material-service-item").html(content.billMaterialServiceItems);
            } else {
                $('#select-item').hide();
                $('#select-bill-material-service-item').hide();
            }
            
            $("#note").val(content.note);
        },
        error: function (e) {
        }
    });
}

function removeBillMaterialService() {
    var data = "pdId=" + $("#subproject option:selected").val();
    
    alert(data);
    
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/remove",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);
            
            alert(response);
            
            $("#bill-material-service").html(content.billMaterialService);
            if (content.noItems === "false") {
                $('#select-item').show();
                $('#select-bill-material-service-item').show();
                $("#bill-material-service-item").html(content.billMaterialServiceItems);
            } else {
                $('#select-item').hide();
                $('#select-bill-material-service-item').hide();
            }
        },
        error: function (e) {
        }
    });
}