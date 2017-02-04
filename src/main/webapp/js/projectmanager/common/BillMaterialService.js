/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global auto */

function insertItem() {
    var data = "pdId=" + $("#subproject option:selected").val() +
            "&item=" + $("#item option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/item/insert",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);

            $("#bill-material-service").html(content.billMaterialService);
            $("#bill-material-service-item").html(content.billMaterialServiceItems);
        },
        error: function (e) {
        }
    });
}

function viewItem() {
    var data = "itemId=" + $("#item option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/item/view",
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

function editItem(id) {
    $("#quantity" + id).html("<div contenteditable></div>");
}

function getBillMaterialServiceItems() {
    var data = "pdId=" + $("#subproject option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/get-bill-material-items",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);
            var type = content.type;
            var items = content.items;

            if (type === "Service") {
                saveBillMaterialService(null);
            } else {
                if (type === "Sale" && items !== null) {
                    saveBillMaterialService(items);
                } else {
                    alert("You must submit one item at least");
                }
            }
        },
        error: function (e) {
        }
    });
}

function saveBillMaterialService(response) {
    var flagRFQ = $("#flag-rfq:checked").val();
    var items = (response) ? JSON.parse(response) : null;
    var data = "?project=" + $("#subproject option:selected").val() + "&note=" + encodeURIComponent($("#note").val());
    

    if (items !== null) {
        data += "&quantities=";

        items.forEach(function (item) {
            var value = $("#quantity" + item).text();

            if (value == '' || isNaN(value)) {
                alert("you must insert values in all quantities");
            } else {
                data += item + "-" + value + ",";
            }
        });
    } else {
        data += "&quantities=";
    }
    data += "&flagRFQ=";
    data += (typeof flagRFQ === "undefined") ? "off" : flagRFQ;
    
    console.log(data);
    
    location.href = "/ProjectManager/project/bill-material-service/save" + data;

    setTimeout(function () {
        location.href = "http://localhost:8081/ProjectManager/project/history-new-project";
    }, 5000);
}

function addBMSItem() {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/add-item",
        success: function (response) {
            var content = JSON.parse(response);

            $("#item-currency").html(content.currency);
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
        width: 400,
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
    var expired = $("#expired").datepicker({dateFormat: 'yyyy-mm-dd'}).val();
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

function viewSubproject() {
    var data = "id=" + $("#subproject option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/view-subproject",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);

            $("#info-type").val(content.type);
            $("#company").val(content.company);
            $("#customer").val(content.customer);
            $("#vessel").val(content.vessel);
            $("#contact").val(content.contact);
            $("#bill-material-service").html(content.billMaterialService);
            $("#bill-material-title").text(content.BillMaterialTitle);
            $("#bill-material-summary").text(content.BillMaterialSummary);
            $("#bill-material-detail").text(content.BillMaterialDetail);
            if (content.noItems === "false") {
                $('#select-item').show();
                $('#select-bill-material-service-item').show();
                $("#bill-material-service-item").html(content.billMaterialServiceItems);
            } else {
                $('#select-item').hide();
                $('#select-bill-material-service-item').hide();
            }

            $("#note").val(content.note);
            if (content.type === "Service") {
                $("#flag-rfq-view").hide();
            } else {
                $("#flag-rfq-view").show();
                if (content.flagRFQ === "true") {
                    $("#flag-rfq").attr('checked', content.flagRFQ);
                }
            }
        },
        error: function (e) {
        }
    });
}

function removeBillMaterialService() {
    var data = "pdId=" + $("#subproject option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/bill-material-service/remove",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);

            $("#header").html(content.header);

            setTimeout(function () {
                if (content.moreBillMaterialService === "true") {
                    location.reload();
                } else {
                    window.location = content.location;
                }
            }, 5000);
        },
        error: function (e) {
        }
    });
}

function editValue() {
    $('#name').html("<div contenteditable></div>");
}