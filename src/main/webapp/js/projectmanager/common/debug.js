/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getDBFiles() {
    $.ajax({
        type: "POST",
        url: "db-files",
        success: function (response) {
            $("#db-files").html(response);
        },
        error: function (e) {
        }
    });
}

function transferDBFile() {
    $.ajax({
        type: "POST",
        url: "db-files/transfer",
        data: "value=" + $('input[name = "radio-db-file"]:checked').val(),
        success: function (response) {
            $("#db-transfer").html("DB Transfer[" + response + "]");
        },
        error: function (e) {
        }
    });
    
    $("#dlg-transfer").dialog("close");
}

function dlgTransfer() {
    getDBFiles();

    $("#dlg-transfer").dialog({
        autoOpen: true,
        modal: true,
        width: 300,
        buttons: {
            "submit": function () {
                transferDBFile()
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