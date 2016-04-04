/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function changeSubproject1() {
    var data = "pdId=" + $("#subproject option:selected").val() +
            "&location=" + $("#location option:selected").val();
    
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/change",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);
            
            $("#supplier").html(content.supplier);
            $("#currency").html(content.currency);
            $("#request-quotation-items").html(content.itemRequestQuotation);
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

function savePDF(pId) {

}

function saveXLS(pId) {

}

function sendEmail(pId) {

}