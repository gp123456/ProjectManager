/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function commitVessel() {
    var name = $("#vessel-name").val();
    var imo = $("#vessel-imo").val();
    var company = $("#new-project-customer option:selected").val();

    var data = "name=" + name + "&flag=" + imo + "&company=" + company;

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/ProjectManager/vessel/add",
        data: data,
        success: function (response) {
            var content = JSON.parse(response)

            $("#new-project-vessel").html(content.vessel);
//            $("#new-project-customer").html(content.customer);
//            $("#new-project-contact").html(content.contact);
        },
        error: function (e) {
        }
    });
    $("#add-vessel").dialog("close");
}

//function getCustomers() {
//    $.ajax({
//        type: "POST",
//        url: "lst-customer",
//        success: function (response) {
//            $("#new-vessel-customer").html(response);
//        },
//        error: function (e) {
//        }
//    });
//}

function addVessel() {
//    getCustomers();
    
    $("#add-vessel").dialog({
        autoOpen: true,
        modal: true,
        width: 400,
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
