/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    var data = "pdId=" + $("#subproject option:selected").val() +
            "&supplierName=" + $("#supplier option:selected").val() +
            "&currency=" + $("#currency option:selected").val() +
            "&note=" + $("#note").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/send-email",
        data: data,
        success: function (response) {
//            location.reload();
            window.location = response;
        },
        error: function (e) {
        }
    });
}

function sendEmailSupplier() {
    var data = "pdId=" + $("#subproject option:selected").val() +
            "&supplierName=" + $("#supplier option:selected").val() +
            "&currency=" + $("#currency option:selected").val() +
            "&note=" + $("#note").val();

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

function refresh(response) {
    var data = "";

    if (response !== null) {
        var content = JSON.parse(response);

        if (content !== null) {
            if (content.billMaterialService !== null) {
                var bms = content.billMaterialService;
                
                data = "bms=" + bms + "&delivery=" + $("#delivery" + bms).text() + "&expenses=" + $("#expenses" + bms).text();

                if (content.billMaterialServiceItems !== null) {
                    var items = JSON.parse(content.billMaterialServiceItems);

                    if (items !== null) {
                        data += "&prices=";
                        items.forEach(function (item) {
                            data += $("#price" + bms + item).text() + ",";
                        });
                        data += "&discounts=";
                        items.forEach(function (item) {
                            data += $("#discount" + bms + item).text() + ",";
                        });
                        data += "&availabilities=";
                        items.forEach(function (item) {
                            data += $("#availability" + bms + item).text() + ",";
                        });
                    }
                }
            }
        }
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