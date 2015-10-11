/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function commitVessel() {
    var name = $("#vessel-name").val();
    var imo = $("#vessel-imo").val();

    var data = "name=" + name + "&flag=" + imo;

    alert(data);

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/ProjectManager/vessel/add",
        data: data,
        success: function (response) {
            var content = JSON.parse(response)

            alert(content.customer);

            $("#new-project-vessel").html(content.vessel);
        },
        error: function (e) {
        }
    });
    $("#add-vessel").dialog("close");
}

function addVessel() {
    $("#add-vessel").dialog({
        autoOpen: true,
        modal: true,
        width: 403,
        buttons: {
            "submit": commitVessel
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