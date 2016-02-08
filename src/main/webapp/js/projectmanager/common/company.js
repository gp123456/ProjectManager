/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function commitCompany(type) {
    var name = $("#company-name").val();
    var eMail = $("#company-email").val();

    var data = "name=" + name + "&email1=" + eMail + "&type" + type;

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/ProjectManager/company/add",
        data: data,
        success: function (response) {
            var content = JSON.parse(response)

            alert(content.company);

            $("#company").html(content.company);
        },
        error: function (e) {
        }
    });
    $("#add-company").dialog("close");
}

function addCompany(type) {
    $("#add-company").dialog({
        autoOpen: true,
        modal: true,
        width: 400,
        buttons: {
            "submit": commitCompany(type)
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