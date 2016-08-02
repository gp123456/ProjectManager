/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function clearValue(name, bms, bmsi) {
    var data = "name=" + name + "&bms=" + bms + "&bmsi=" + bmsi;

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/get-value",
        data: data,
        success: function (id) {
            $('#' + id).html("");
        },
        error: function (e) {
        }
    });
}

function handleAll(cb) {
    var data = "pdId=" + $("#subproject option:selected").val() +
            "&checked=" + cb.checked;

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/create-all",
        data: data,
        success: function (response) {
            $("#bill-material-service-item").html(response);
        },
        error: function (e) {
        }
    });
}

function handleClick(cb, bms, bmsi) {
    var data = "pdId=" + $("#subproject option:selected").val() +
            "&checked=" + cb.checked +
            "&bms=" + bms +
            "&bmsi=" + bmsi;

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/create",
        data: data,
        success: function (response) {
            $("#bill-material-service-item").html(response);
        },
        error: function (e) {
        }
    });
}

function changeBMSSubproject() {
    var data = "pdId=" + $("#subproject option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/change-subproject-bms",
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

function dlgRequestQuotation() {
    $('#dlg-request-quotation1').dialog({
        autoOpen: true,
        modal: true,
        width: 374,
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

function savePDF() {

}

function saveXLS() {

}

function sendEmail() {
    var currency = $("#currency option:selected").val();
    
    if (currency === 'none') {
        alert("You must select currency first")
        return;
    }
    
    var data = "pdId=" + $("#subproject option:selected").val() +
            "&supplier=" + $("#supplier option:selected").val() +
            "&currency=" + $("#currency option:selected").val() +
            "&note=" + $("#note").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/send-email",
        data: data,
        success: function (response) {
//            $("#header").html(response);
            window.location = response;
            
        },
        error: function (e) {
        }
    });
}

function sendEmailSupplier() {
    var data = "pdId=" + $("#request-quotation-id").val() +
            "&supplierName=" + $("#supplier option:selected").val() +
            "&supplierNote=" + $("#supplier-note").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/send-email-supplier",
        data: data,
        success: function () {
            location.reload();
        },
        error: function (e) {
        }
    });
}

function cancel() {
    var data = "pdId=" + $("#subproject option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/cancel",
        data: data,
        success: function () {
            location.reload();
        },
        error: function (e) {
        }
    });
}

function getVirtualRequestQuotation() {
    var data = "rqId=" + $('#request-quotation-id').val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/get-virtual-request-quotation",
        data: data,
        success: function (response) {
            refresh(response);
        },
        error: function (e) {
        }
    });
}

function getValues(response) {
    var data = "";

    if (response !== null) {
        var content = JSON.parse(response);

        if (content !== null) {
            if (content.billMaterialService !== null) {
                var bms = content.billMaterialService;

                data = "bms=" + bms + "&delivery=" + $("#delivery").text() + "&expenses=" + $("#expenses").text();

                if (content.billMaterialServiceItemIds !== null) {
                    var items = JSON.parse(content.billMaterialServiceItemIds);
                    var qties = JSON.parse(content.billMaterialServiceItemQuantities);

                    if (items !== null) {
                        data += "&itemIds=";
                        items.forEach(function (item) {
                            data += item + ",";
                        });
                        data += "&quantities=";
                        qties.forEach(function (qty) {
                            data += qty + ",";
                        });
                        data += "&discounts=";
                        items.forEach(function (item) {
                            data += $("#discount" + item).text() + ",";
                        });
                        data += "&prices=";
                        items.forEach(function (item) {
                            var value = $("#price" + item).text();
                            
                            if (value === '0') {
                                alert("The price with zero value is invalid");
                                data = "";
                            }
                            data += value + ",";
                        });
                        data += "&availabilities=";
                        items.forEach(function (item) {
                            var value = $("#availability" + item).text();
                            
                            if (value === '0') {
                                alert("The availability with zero value is invalid");
                                data = "";
                            }
                            data += value + ",";
                        });
                    }
                }
            }
        }
    }
    
    return data;
}

function refresh(response) {
    var data = getValues(response);
    
    console.log(data);
    
    if (data === '') {
        return;
    }
    
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/refresh",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);

            $("#request-quotation").html(content.requestQuotation);
            $("#request-quotation-items").html(content.itemRequestQuotation);
        },
        error: function (e) {
        }
    });
}

function submitRequestQuotation() {
    var data = "id=" + $("#project-id").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/submit",
        data: data,
        success: function (response) {
            location.href = response;
        },
        error: function (e) {
        }
    });
}

function changeSubproject() {
    var data = "pdId=" + $("#subproject option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/change-subproject",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);

            $("#request-quotation").html(content.requestQuotation);
            $("#request-quotation-items").html(content.itemRequestQuotation);
            $("#note").val(content.note);
        },
        error: function (e) {
        }
    });
}

function selectBMSI() {
    var data = "pId=" + $("#project-id").val() +
            "&pdId=" + $("#subproject option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/select-bmsi",
        data: data,
        success: function (response) {
            location.href = response;
        },
        error: function (e) {
        }
    });
}

function changeRequestQuotationSupplier() {
    var data = "pdId=" + $("#subproject option:selected").val() +
            "&supplier=" + $("#supplier option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/change-supplier",
        data: data,
        success: function (response) {
            $("#request-quotation-supplier").html(response);
        },
        error: function (e) {
        }
    });
}

function removeRequestQuotationSupplier(supplier) {
    var data = "pdId=" + $("#subproject option:selected").val() + "&supplier=" + supplier;

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/remove-supplier",
        data: data,
        success: function (response) {
            $("#request-quotation-supplier").html(response);
        },
        error: function (e) {
        }
    });
}