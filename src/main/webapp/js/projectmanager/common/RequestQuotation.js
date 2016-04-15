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

function changeRQSupplier() {
    var data = "pdId=" + $("#subproject option:selected").val() +
            "&supplier=" + $("#supplier option:selected").val();

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

function savePDF(pId) {

}

function saveXLS(pId) {

}

function sendEmail(pId) {

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