/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

var requestQuotationIds = [];

function setRequestQuotation(pdId, rqId) {
    if (requestQuotationIds.length > 0) {
        var diff = true;

        requestQuotationIds.forEach(function (info) {
            if (info.pdId === pdId) {
                info.rqId = rqId;
                diff = false;
            }
        });

        if (diff === true) {
            requestQuotationIds.push({pd: pdId, rq: rqId});
        }
    } else {
        requestQuotationIds.push({pd: pdId, rq: rqId});
    }
}