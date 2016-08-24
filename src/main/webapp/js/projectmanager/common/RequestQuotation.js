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
        $("#email").attr('disabled', 'disabled');
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

function savePDF() {

}

function saveXLS() {

}

function saveRFQ(url) {
    var currency = $("#currency option:selected").val();

    if (currency === 'none') {
        alert("You must select currency first")
        return;
    }

    var data = "pdId=" + $("#subproject option:selected").val() +
            "&name=" + $("#name").text() +
            "&supplier=" + $("#supplier option:selected").val() +
            "&currency=" + $("#currency option:selected").val() +
            "&note=" + $("#note").val();

    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function (response) {
//            $("#header").html(response);
            window.location = response;

        },
        error: function (e) {
        }
    });
}

function sendEmail() {
    saveRFQ("/ProjectManager/project/request-quotation/send-email");
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

                data = "id=" + id +
                        "&delivery=" + $("#delivery").text() +
                        "&expenses=" + $("#expenses").text();

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
            $("#email").removeAttr('disabled');
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

//function changeRequestQuotationSupplier() {
//    var data = "pdId=" + $("#subproject option:selected").val() +
//            "&supplier=" + $("#supplier option:selected").val();
//
//    $.ajax({
//        type: "POST",
//        url: "/ProjectManager/project/request-quotation/change-supplier",
//        data: data,
//        success: function (response) {
////            $("#request-quotation-supplier").html(response);
//        },
//        error: function (e) {
//        }
//    });
//}

//function removeRequestQuotationSupplier(supplier) {
//    var data = "pdId=" + $("#subproject option:selected").val() + "&supplier=" + supplier;
//
//    $.ajax({
//        type: "POST",
//        url: "/ProjectManager/project/request-quotation/remove-supplier",
//        data: data,
//        success: function (response) {
//            $("#request-quotation-supplier").html(response);
//        },
//        error: function (e) {
//        }
//    });
//}

function completeRFQ() {
    var data = "pdId=" + $("#subproject option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/request-quotation/complete",
        data: data,
        success: function (response) {
            if (response) {
                var content = JSON.parse(response);

                if (content.header) {
                    $("#header").html(content.header);
                }

                setTimeout(function () {
                    if (content.location) {
                        location.href = content.location;
                    } else {
                        location.reload();
                    }
//                window.location = content.location;
                }, 5000);
            }
        },
        error: function (e) {
        }
    });
}

function save() {
    saveRFQ("/ProjectManager/project/request-quotation/save");
}

function changeRequestQuotalion(hasList) {
    var data = "rqId=" + $("#request-quotations option:selected").val() + "&hasList=" + hasList;

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