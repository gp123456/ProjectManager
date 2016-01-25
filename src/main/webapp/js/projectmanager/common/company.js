/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function commitCustomer() {
    var name = $("#customer-name").val();
    var eMail = $("#customer-email").val();

    var data = "name=" + name + "&email1=" + eMail;

    $.ajax({
        type: "GET",
        url: "http://192.168.178.29:8080/ProjectManager/company/add",
        data: data,
        success: function (response) {
            var content = JSON.parse(response)

            alert(content.customer);

            $("#new-project-customer").html(content.customer);
        },
        error: function (e) {
        }
    });
    $("#add-customer").dialog("close");
}

//function commitVesselCustomer() {
//    var name = $("#customer-name").val();
//    var referenceNumber = $("#customer-reference-number").val();
//
//    var data = "name=" + name + "&reference-number=" + referenceNumber;
//
//    $.ajax({
//        type: "GET",
//        url: "http://localhost:8080/ProjectManager/company/add",
//        data: data,
//        success: function (response) {
//            var content = JSON.parse(response)
//
//            $("#new-vessel-customer").html(content.customer);
//        },
//        error: function (e) {
//        }
//    });
//    $("#add-customer").dialog("close");
//}

function addCustomer() {
    $("#add-customer").dialog({
        autoOpen: true,
        modal: true,
        width: 400,
        buttons: {
            "submit": commitCustomer
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

//function addVesselCustomer() {
//    $("#add-customer").dialog({
//        autoOpen: true,
//        modal: true,
//        width: 400,
//        buttons: {
//            "submit": commitVesselCustomer
//        },
//        show: {
//            effect: "blind",
//            duration: 1000
//        },
//        hide: {
//            effect: "explode",
//            duration: 1000
//        }
//    });
//}
