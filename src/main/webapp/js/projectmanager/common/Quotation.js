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
        error: function (e) {
        }
    });
}

function addItem() {
    var data = "rqId=" + $('#request-quotation-id').val()
            + "&location=" + $("#locations option:selected").val()
            + "&currency=" + $("#currency option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/add-item",
        data: data,
        success: function (response) {
            if (response) {
                var content = JSON.parse(response);

                if (content) {
                    if (content.status) {
                        if (content.status === "exist") {
                            alert(content.message);
                        } else if (content.status === "created") {
                            if (content.quotation) {
                                $("#quotation").html(content.quotation);
                            }
                            if (content.quotationItem) {
                                $("#quotation-item").html(content.quotationItem);
                            }
                            $("#calculate").removeAttr('disabled');
                            $("#calculate").removeClass('button alarm');
                            $("#calculate").addClass('button');
                            $("#email").attr('disabled', 'disabled');
                            $("#email").removeClass('button');
                            $("#email").addClass('button alarm');
                            $("#save").attr('disabled', 'disabled');
                        }
                    }
                }
            }
        },
        error: function (e) {
        }
    });
}

function qSubmit() {
    if (!requestQuotationIds.isArray) {
        var pdId = $("#subproject option:selected").val();
        var rqId = $("#request-quotations option:selected").val();

        setRequestQuotation(pdId, rqId)
    }

    var data = "infoRQ=" + JSON.stringify(requestQuotationIds);

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/rq-submit",
        data: data,
        success: function () {
            window.location.href = "/ProjectManager/project/quotation?mode=NEW_RQ";
        },
        error: function (e) {
        }
    });
}

function getQuotation(mode) {
    var data = "rqId=" + $('#request-quotation-id').val() + "&location=" + $("#locations option:selected").val();

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
                data = "id=" + content.requestQuotation + "&location=" + content.location;

                if (content.requestQuotationItem !== null) {
                    var items = JSON.parse(content.requestQuotationItem);

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

        if (content !== null && content.requestQuotation !== null && content.location !== null && content.requestQuotationItem !== null) {
            var data = "rqId=" + content.requestQuotation + "&location=" + content.location;

            $.ajax({
                type: "POST",
                url: "/ProjectManager/project/quotation/clear",
                data: data,
                success: function () {
                    $("#grand" + content.location).html("0.00");
                    JSON.parse(content.requestQuotationItem).forEach(function (item) {
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
                error: function (e) {
                }
            });
        }
    }
}

function qCalculate(response) {
    var data = getValues(response);

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
        error: function (e) {
        }
    });
}

function qSave() {
    var data = "customerReference=" + $("#customer-reference").val()
            + "&availability=" + $("#availability").val()
            + "&delivery=" + $("#delivery").val()
            + "&packing=" + $("#packing").val()
            + "&payment=" + $("#payment").val()
            + "&validity=" + $("#validity").val()
            + "&welcome=" + $("#welcome-note").val()
            + "&remark=" + $("#remarks-note").val()
            + "&note=" + $("#note").val();

    console.log(data);

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/save",
        data: data,
        success: function (response) {
            $("#header").html(response);
            setTimeout(function () {
                location.href = "http://localhost:8081/ProjectManager/project/history-new-project";
            }, 5000);
        },
        error: function (e) {
        }
    });
}

function qRemove() {

}

function qEmail() {

}

function changeQCurrency() {
    var location = $("#locations option:selected").val();
    var data = "rqId=" + $('#request-quotation-id').val()
            + "&location=" + location
            + "&currency=" + $("#currency option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/change-currency",
        data: data,
        success: function (response) {
            $("#rq-currency" + location).html(response);
        },
        error: function (e) {
        }
    });
}

function completeQuotation() {
    var location = $("#locations option:selected").val();
    var data = "rqId=" + $('#request-quotation-id').val()
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
        error: function (e) {
        }
    });
}