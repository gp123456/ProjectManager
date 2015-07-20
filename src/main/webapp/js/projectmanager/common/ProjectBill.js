/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//insert new item in Project Bill table
function insertItem(sel) {
    $.ajax({
        type: "POST",
        url: "new",
        data: "id=" + sel.value,
        success: function (response) {
            $("#project-bill-items").html(response);
        },
        error: function (e) {
        }
    });
}

function newProjectBillInfo(id) {
    var available = Number($("#available" + id).text());
    var quantity = Number($("#quantity" + id).text());

    if (available < quantity) {
        alert("The quantity must is less or equal than quantity of item [" + available + "," + quantity + "]");
    } else {
        var data = "id=" + id + "&quantity=" + $("#quantity" + id).text() + "&cost=" + $("#cost" + id).text() +
                "&totalCost=" + $("#total_cost" + id).text() + "&percentage=" + $("#percentage" + id).text() +
                "&discount=" + $("#discount" + id).text() + "&salePrice=" + $("#sale_price" + id).text() +
                "&totalSalePrice=" + $("#total_sale_price" + id).text() + "&totalNetPrice=" +
                $("#total_net_price" + id).text();
        
        $.ajax({
            type: "POST",
            url: "new/info",
            data: data,
            success: function (response) {
                var content = JSON.parse(response);
                
                $("#project-bill").html(content.project_bill);
                $("#project-bill-items").html(content.project_bill_items);
            },
            error: function (e) {
            }
        });
    }
}

function removeProjectBillInfo(id) {
    $.ajax({
        type: "POST",
        url: "remove/info",
        data: "id=" + id,
        success: function (response) {
            var content = JSON.parse(response);
            
            $("#project-bill").html(content.project_bill);
            $("#project-bill-items").html(content.project_bill_items);
        },
        error: function (e) {
        }
    });
}

function saveProjectBill() {
    $.ajax({
        type: "POST",
        url: "save",
        data: "express=" + $("#express").text() + "&notes=" + $("#notes").text(),
        success: function (response) {
            location.reload();
        },
        error: function (e) {
        }
    });
}

function createProjectBillPDF() {
    $.ajax({
        type: "POST",
        url: "createpdf",
        success: function () {
        },
        error: function (e) {
        }
    });
}

function changeValues(id) {
    var data = "id=" + id + "&code=" + $("#code" + id).text() + "&description=" + $("#description" + id).text() +
            "&available=" + $("#available" + id).text() + "&price=" + $("#price" + id).text() +
            "&quantity=" + $("#quantity" + id).text() + "&cost=" + $("#cost" + id).text() +
            "&percentage=" + $("#percentage" + id).text() + "&discount=" + $("#discount" + id).text();
    
    $.ajax({
        type: "POST",
        url: "changevalues",
        data: data,
        success: function (response) {
            $("#project-bill-items").html(response);
        },
        error: function (e) {
        }
    });
}