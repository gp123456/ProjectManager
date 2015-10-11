/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function commitContact() {
    var name = $("#contact-name").val();
    var surname = $("#contact-surname").val();
    var phone = $("#contact-phone").val();
    var email = $("#contact-email").val();
    var vessel = $("#new-project-vessel option:selected").attr("value");
    var customer = $("#new-project-customer option:selected").attr("value");

    var data = "name=" + name + "&surname=" + surname + "&phone=" + phone +
            "&email=" + email + "&vessel=" + vessel + "&company=" + customer;

    alert(data);

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/ProjectManager/contact/add",
        data: data,
        success: function (response) {
            var content = JSON.parse(response)

            $("#new-project-contact").html(content.contact);
        },
        error: function (e) {
        }
    });
    $("#add-contact").dialog("close");
}

function addContact() {
    $("#add-contact").dialog({
        autoOpen: true,
        modal: true,
        width: 400,
        buttons: {
            "submit": commitContact
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
