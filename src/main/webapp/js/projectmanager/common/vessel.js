/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function commitVessel() {
    var name = $("#vessel-name").val();
    var imo = $("#vessel-imo").val();
    var company = $("#customer option:selected").val();

    var data = "name=" + name + "&flag=" + imo + "&company=" + company;

    $.ajax({
        type: "POST",
        url: "/ProjectManager/vessel/add",
        data: data,
        success: function (response) {
            $("#vessel").html(response);
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
        width: 400,
        buttons: {
            "submit": function () {
                commitVessel();
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
