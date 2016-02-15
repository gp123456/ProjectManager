/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function editRequestQuotationValues(pbId, pbiId) {
    var data = "pbId=" + pbId + "&projectBillItem=" + pbiId +
            "&classRefresh=button alarm&classSave=button alarm";

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/item/edit",
        data: data,
        success: function (response) {
            alert(response);
            
            $("#request-quotation-items").html(response);

            $("#quantity" + pbId + pbiId).html("<div contenteditable></div>");
            $("#price" + pbId + pbiId).html("<div contenteditable></div>");
            $("#discount" + pbId + pbiId).html("<div contenteditable></div>");
        },
        error: function (e) {
        }
    });
}

function refreshValues(pbId, pbiId) {

}

function removeValues(pbId, pbiId) {

}

function saveValues(pbId, pbiId) {

}