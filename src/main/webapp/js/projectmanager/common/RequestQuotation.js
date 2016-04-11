/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function changeRequestQuotationSubproject() {
    var data = "pdId=" + $("#subproject option:selected").val();
    
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/change-subproject",
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

function changeSupplier() {
    var data = "pdId=" + $("#subproject option:selected").val() +
            "&location=" + $("#location option:selected").val() +
            "&supplier=" + $("#supplier option:selected").val();
    
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/supplier",
        data: data,
        success: function (response) {
            $("#bill-material-service").html(response);
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

function createRequetQuotation() {
    
}