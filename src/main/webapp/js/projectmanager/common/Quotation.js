/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function changeQuotaSubproject(mode) {
    var pdId = $("#subproject option:selected").val();
    var data = "id=" + pdId + "&mode=" + mode;

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/change-quota-suproject",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);

            if (mode === 'rq') {
                $("#request-quotations").html(content.requestQuotations);
                $("#supplier").val(content.supplier);
                $("#currency").val(content.currency);
                $("#request-quotation").html(content.requestQuotation);
                $("#request-quotation-items").html(content.itemRequestQuotation);
                $("#note").html(content.noteRequestQuotation);
                $("#supplier-note").html(content.noteSupplierRequestQuotation);
                setRequestQuotation(pdId, content.requestQuotationId);
            } else if (mode === 'q') {
                $("#type").val(content.type);
                $("#company").val(content.company);
                $("#customer").val(content.customer);
                $("#vessel").val(content.vessel);
                $("#contact").val(content.contact);
                $("#location").html(content.location);
                $("#currency").html(content.currency);
                $("#quotation").html(content.quotation);
                $("#quotation-item").html(content.quotationItem);
            }
        },
        error: function (xhr, status, error) {
            console.log(error);
        }
    });
}

function addItem() {
    var items = [];
    $('#lst-select-qitem :selected').each(function (i, selected) {
        items[i] = $(selected).val();
    });

    var data = "bmsId=" + $('#bill-material-service-id').val()
            + "&location=" + $("#locations option:selected").val()
            + "&currency=" + $("#currency option:selected").val()
            + "&items=" + items;

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/add-item",
        data: data,
        success: function (response) {
            if (response) {
                var content = JSON.parse(response);

                if (content) {
                    if (content.quotation) {
                        $("#quotation").html(content.quotation);
                    }
                    if (content.quotationItem) {
                        $("#quotation-item").html(content.quotationItem);
                    }
                    $("#availability").val("");
                    $("#delivery").val("");
                    $("#packing").val("");
                    $("#payment").val("");
                    $("#validity").val("");
                    $("#calculate").removeAttr('disabled');
                    $("#calculate").removeClass('button alarm');
                    $("#calculate").addClass('button');
                    $("#email").attr('disabled', 'disabled');
                    $("#email").removeClass('button');
                    $("#email").addClass('button alarm');
                    $("#save").attr('disabled', 'disabled');
                }
            }
        },
        error: function (xhr, status, error) {
            console.log(error);
        }
    });
}

function dlgSelectItem() {
    var data = "bmsId=" + $('#bill-material-service-id').val() + "&location=" + $("#locations option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/select-item",
        data: data,
        success: function (response) {
            if (response) {
                var content = JSON.parse(response);

                if (content) {
                    if (content.status === "exist") {
                        alert(content.message);
                    } else if (content.status === "selected") {
                        $("#lst-select-qitem").html(content.data);
                        $("#dlg-select-qitem").dialog({
                            autoOpen: true,
                            modal: true,
                            width: 500,
                            buttons: {
                                "submit": function () {
                                    addItem();
                                    $("#dlg-select-qitem").dialog("close");
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
                }
            }
        },
        error: function (xhr, status, error) {
            console.log(error);
        }
    });
}

function qSubmit() {
    if (!requestQuotationIds.isArray) {
        var pdId = $("#subproject option:selected").val();
        var bmsId = $("#bill-material-service option:selected").val();

        setRequestQuotation(pdId, bmsId)
    }

    var data = "infoRQ=" + JSON.stringify(requestQuotationIds);

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/rq-submit",
        data: data,
        success: function () {
            window.location.href = "/ProjectManager/project/quotation?mode=NEW_RQ";
        },
        error: function (xhr, status, error) {
            console.log(error);
        }
    });
}

function getQuotation(mode) {
    var data = "bmsId=" + $('#bill-material-service-id').val() + "&location=" + $("#locations option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/get-item",
        data: data,
        success: function (response) {
            if (mode === "calculate") {
                qCalculate(response);
            } else if (mode === "clear") {
                qClear(response);
            }
        },
        error: function (xhr, status, error) {
            console.log(error);
        }
    });
}

function getQValues(response) {
    var data = "";

    if (response !== null) {
        var content = JSON.parse(response);

        if (content !== null) {
            if (content.billMaterialService !== null) {
                data = "id=" + content.billMaterialService + "&location=" + content.location;

                if (content.billMaterialServiceItem !== null) {
                    var items = JSON.parse(content.billMaterialServiceItem);

                    data += "&itemInfo=";
                    if (items !== null) {
                        var itemInfo = [];

                        items.forEach(function (item) {
                            var price = $("#price" + item.id + content.location).text();

                            if (price === '') {
                                alert("The price with zero value is invalid");
                                data = "";
                                return data;
                            }

                            itemInfo.push({
                                id: item.id,
                                qty: item.qty,
                                price: price,
                                discount: $("#discount" + item.id + content.location).text()
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

function qClear(response) {
    if (response !== null) {
        var content = JSON.parse(response);

        if (content !== null && content.billMaterialService !== null && content.location !== null && content.billMaterialServiceItem !== null) {
            var data = "bmsId=" + content.billMaterialService + "&location=" + content.location;

            $.ajax({
                type: "POST",
                url: "/ProjectManager/project/quotation/clear",
                data: data,
                success: function () {
                    $("#grand" + content.location).html("0.00");
                    JSON.parse(content.billMaterialServiceItem).forEach(function (item) {
                        $("#price" + item.id + content.location).html("<div contenteditable></div>");
                        $("#price" + item.id + content.location).css('backgroundColor', 'rgb(247, 128, 128)');
                        $("#price" + item.id + content.location).css('color', 'rgba(29, 25, 10, 0.84)');
                        $("#discount" + item.id + content.location).html("<div contenteditable></div>");
                        $("#discount" + item.id + content.location).css('backgroundColor', 'rgb(247, 128, 128)');
                        $("#discount" + item.id + content.location).css('color', 'rgba(29, 25, 10, 0.84)');
                        $("#net" + item.id + content.location).html("0.00");
                    });
                    $("#calculate").removeAttr('disabled');
                    $("#calculate").removeClass('button alarm');
                    $("#calculate").addClass('button');
                    $("#email").attr('disabled', 'disabled');
                    $("#email").removeClass('button');
                    $("#email").addClass('button alarm');
                    $("#save").attr('disabled', 'disabled');
                },
                error: function (xhr, status, error) {
                    console.log(error);
                }
            });
        }
    }
}

function qCalculate(response) {
    var data = getQValues(response);

    if (data === '') {
        return;
    }

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/calculate",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);

            $("#quotation").html(content.quotation);
            $("#quotation-item").html(content.quotationItem);
            $("#calculate").attr('disabled', 'disabled');
            $("#calculate").removeClass('button');
            $("#calculate").addClass('button alarm');
            $("#email").removeAttr('disabled');
            $("#email").removeClass('button alarm');
            $("#email").addClass('button');
            $("#save").removeAttr('disabled');
        },
        error: function (xhr, status, error) {
            console.log(error);
        }
    });
}

function qSave() {
    var data = "?customerReference=" + $("#customer-reference").val()
            + "&billMaterialService=" + $('#bill-material-service-id').val()
            + "&location=" + $("#locations option:selected").val()
            + "&availability=" + $("#availability").val()
            + "&delivery=" + $("#delivery").val()
            + "&packing=" + $("#packing").val()
            + "&payment=" + $("#payment").val()
            + "&validity=" + $("#validity").val()
            + "&welcome=" + $("#welcome-note").val()
            + "&remark=" + $("#remarks-note").val()
            + "&note=" + $("#note").val()
            + "&dateExpired=" + $("#expired").datepicker({dateFormat: 'yyyy-mm-dd'}).val();

    location.href = "/ProjectManager/project/quotation/save" + data;

    setTimeout(function () {
        if (!confirm("Do you want create another location?")) {
            location.href = "http://localhost:8081/ProjectManager/project/history-new-project";
        }
    }, 5000);
}

function qRemove() {

}

function qEmail() {

}

function changeQCurrency() {
    var location = $("#locations option:selected").val();
    var data = "bmsId=" + $('#bill-material-service-id').val()
            + "&location=" + location
            + "&currency=" + $("#currency option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/change-currency",
        data: data,
        success: function (response) {
            $("#rq-currency" + location).html(response);
        },
        error: function (xhr, status, error) {
            console.log(error);
        }
    });
}

function completeQuotation() {
    var location = $("#locations option:selected").val();
    var data = "bmsId=" + $('#bill-material-service-id').val()
            + "&location=" + location;

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/complete",
        data: data,
        success: function (response) {
            $("#header").html(response);
            setTimeout(function () {
                location.href = "http://localhost:8081/ProjectManager/project/history-new-project";
            }, 5000);
        },
        error: function (xhr, status, error) {
            console.log(error);
        }
    });
}

function changeLocation() {
    var data = "bmsId=" + $('#bill-material-service-id').val()
            + "&location=" + $("#locations option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/change-location",
        data: data,
        success: function (response) {
            if (response) {
                var content = JSON.parse(response);
                
                if (content) {
                    if (content.quotationItem) {
                        $("#quotation-item").html(content.quotationItem);
                    }
                    $("#availability").val(content.availability);
                    $("#delivery").val(content.delivery);
                    $("#packing").val(content.packing);
                    $("#payment").val(content.payment);
                    $("#validity").val(content.validity);
                    $("#calculate").removeAttr('disabled');
                    $("#calculate").removeClass('button alarm');
                    $("#calculate").addClass('button');
                    $("#email").attr('disabled', 'disabled');
                    $("#email").removeClass('button');
                    $("#email").addClass('button alarm');
                    $("#save").attr('disabled', 'disabled');
                }
            }
        },
        error: function (xhr, status, error) {
            console.log(error);
        }
    });
}
