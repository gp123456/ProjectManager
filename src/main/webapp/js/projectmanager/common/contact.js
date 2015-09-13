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

    var data = "name=" + name + "&surname=" + surname + "&phone=" + phone +
            "&email=" + email;

    alert(data);

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/ProjectManager/contact/add",
        data: data,
        success: function (response) {
            var content = JSON.parse(response)
            
            alert(content.contact);

            $("#new-project-contact").html(content.contact);
        },
        error: function (e) {
        }
    });
    $("#add-contact").dialog("close");
}

function addContact() {
    var dialog = $("#add-contact").dialog({
        autoOpen: true,
        modal: true,
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

    dialog.find("form").on("submit", function (event) {
        event.preventDefault();
        commitContact();
    });
}