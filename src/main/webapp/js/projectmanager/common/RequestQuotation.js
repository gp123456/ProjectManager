/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function clearValue(response) {
    $("#delivery").html("<div contenteditable></div>");
    $("#expenses").html("<div contenteditable></div>");
    $("#material").html("");
    $("#grand").html("");

    if (response) {
        var content = JSON.parse(response);

        if (content.requestQuotationItem) {
            var items = JSON.parse(content.requestQuotationItem);

            items.forEach(function (item) {
                $("#price" + item.id).html("<div contenteditable></div>");
                $("#discount" + item.id).html("<div contenteditable></div>");
                $("#availability" + item.id).html("<div contenteditable></div>");
                $("#net" + item.id).html("");
            });
        }
        $("#refresh").removeAttr('disabled');
        $("#refresh").removeClass('button alarm');
        $("#refresh").addClass('button');
        $("#email").attr('disabled', 'disabled');
        $("#email").removeClass('button');
        $("#email").addClass('button alarm');
    }
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

function saveRFQ(url, pdId, rqId, supplier, currency) {
    if (supplier === 'none') {
        alert("You must select supplier first")
        return;
    }

    var data = "pdId=" + pdId +
            "&supplier=" + supplier +
            "&currency=" + currency +
            "&note=" + $("#note").val();

    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function (response) {
            var content = JSON.parse(response);

            $("#header").html(content.header);
            $("#email-address").val(content.email);
            $("#location").val(content.location);
            $("#email-rq-id").val(content.requestQuotationId);
        },
        error: function (e) {
        }
    });
}

function sendEmail() {
    saveRFQ("/ProjectManager/project/request-quotation/send-email",
            $("#subproject option:selected").val(),
            null,
            $("#supplier option:selected").val(),
            $("#currency option:selected").val());

    $('#dlg-email').dialog({
        autoOpen: true,
        modal: true,
        width: 670,
        buttons: {
            "submit": function () {
                var data = "email=" + $('#email-address').val() + "&id=" + $('#email-rq-id').val();

                $.ajax({
                    type: "POST",
                    url: "/ProjectManager/project/request-quotation/send-email-submit",
                    data: data,
                    success: function (response) {
                        location.href = response;

                        $("#dlg-email").dialog("close");

//                        setTimeout(function () {
//                            var l = $('#location').val();
//
//                            if (l) {
//                                location.href = l;
//                            }
//                        }, 5000);
                    }
                });
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

function sendEmailNCRFQ() {
    saveRFQ("/ProjectManager/project/request-quotation/send-email",
            $("#project_detail-id").val(),
            $("#request-quotation-id").val(),
            $("#supplier").val(),
            $("#currency-id").val());

    setTimeout(function () {
        var l = $('#location').val();

        if (l) {
            location.href = l;
        }
    }, 5000);

}

function sendEmailSupplier() {
    var data = "id=" + $('#request-quotation-id').val() + "&supplierNote=" + $("#supplier-note").val() + "&emailSender=" + $("#email-sender").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/send-email-supplier",
        data: data,
        success: function (response) {
            $("#header").html(response);
            setTimeout(function () {
                window.close();
            }, 5000);
        },
        error: function (e) {
        }
    });
}

function getRequestQuotation(mode) {
    var data = "rqId=" + $('#request-quotation-id').val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/get-request-quotation",
        data: data,
        success: function (response) {
            if (mode === "refresh") {
                refresh(response);
            } else if (mode === "clear") {
                clearValue(response);
            }
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
            if (content.requestQuotation !== null) {
                var id = content.requestQuotation;
                var currency = ( $("#lst-currency").css('display') !== 'none' ) ? $("#lst-currency option:selected").attr("value") : -1;

                data = "id=" + id +
                        "&delivery=" + $("#delivery").text() +
                        "&expenses=" + $("#expenses").text() +
                        "&currency=" + currency;

                if (content.requestQuotationItem !== null) {
                    var items = JSON.parse(content.requestQuotationItem);

                    data += "&itemInfo=";
                    if (items !== null) {
                        var itemInfo = [];

                        items.forEach(function (item) {
                            var price = $("#price" + item.id).text();
                            var availability = $("#availability" + item.id).text();

                            if (price === '0') {
                                alert("The price with zero value is invalid");
                                data = "";
                            }
                            if (availability === '0') {
                                alert("The availability with zero value is invalid");
                                data = "";
                            }

                            itemInfo.push({
                                id: item.id,
                                qty: item.qty,
                                price: price,
                                discount: $("#discount" + item.id).text(),
                                availability: availability,
                            });
                        });

                        data += JSON.stringify(itemInfo);
                    }
                }
            }
        }
    }

    return data;
}

function refresh(response) {
    var data = getValues(response);

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
            $("#refresh").attr('disabled', 'disabled');
            $("#refresh").removeClass('button');
            $("#refresh").addClass('button alarm');
            $("#email").removeAttr('disabled');
            $("#email").removeClass('button alarm');
            $("#email").addClass('button');
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
            $("#existing-rfq").val("ExistingRFQ" + content.countRFQ);
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

function discard() {
    var data = "rqId=" + $("#request-quotation-id").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/discard",
        data: data,
        success: function (response) {
            if (response) {
                var content = JSON.parse(response);

                if (content.header) {
                    $("#header").html(content.header);
                } else {
                    $("#header").html(response);
                }

                setTimeout(function () {
                    if (content.location) {
                        location.href = content.location;
                    } else {
                        location.reload();
                    }
                }, 5000);
            }
        },
        error: function (e) {
        }
    });
}

function completeRFQ() {
    var data = "?rqId=" + $("#request-quotations option:selected").val();

    location.href = "/ProjectManager/project/request-quotation/complete" + data;
}

function exitRFQ() {
    var data = "rqId=" + $("#request-quotations option:selected").val();
    
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/exit",
        data: data,
        success: function () {
            location.href = "http://localhost:8081/ProjectManager/project/history-new-project";
        },
        error: function (e) {
        }
    });
}

function save() {
    var supplier = $("#supplier option:selected").val();
    
    if (supplier === 'none') {
        alert("You must select supplier first")
        return;
    }

    var data = "?pdId=" + $("#subproject option:selected").val() +
            "&supplier=" + supplier +
            "&currency=" + $("#currency option:selected").val() +
            "&note=" + $("#note").val();
            
    location.href = "/ProjectManager/project/request-quotation/save" + data;

    setTimeout(function () {
        location.href = "http://localhost:8081/ProjectManager/project/history-new-project";
    }, 5000);
}

function changeRequestQuotalion(hasList, isEdit) {
    var pdId = $("#subproject option:selected").val();
    var rqId = $("#request-quotations option:selected").val();
    var data = "rqId=" + rqId + "&hasList=" + hasList + "&isEdit=" + isEdit;
    setRequestQuotation(pdId, rqId);

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/change-rfq",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);

            $("#request-quotation").html(content.requestQuotation);
            $("#request-quotation-items").html(content.itemRequestQuotation);
            if (hasList === true) {
                $("#supplier").html(content.suppliers);
                $("#currency").html(content.currency);
            } else {
                $("#supplier").val(content.suppliers);
                $("#currency").val(content.currency);
            }
            $("#note").html(content.notes);
            $("#supplier-note").html(content.notesSupplier);
        },
        error: function (e) {
        }
    });
}

function existingRequestQuotations(path) {
    var url = path + "/project/request-quotation?id=" + $("#subproject option:selected").val() + "&mode=ERQ";
    var win = window.open(url, '_blank');

    win.focus();
}

function setupEmailSender() {
    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/setup-email-sender",
        success: function (response) {
            $("#email-sender").html(response);
            dlgNewItem();
        },
        error: function (e) {
        }
    });
}

function dlgSetupEmailSender() {
    $("#dlg-email").dialog({
        autoOpen: true,
        modal: true,
        width: 365,
        buttons: {
            "submit": function () {
                sendEmail();
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

function submitQuotation() {
    var id = $("#subproject option:selected").val();
    
    window.location.href = "/ProjectManager/project/quotation" + "?id=" + id + "&mode=NEW";
}
