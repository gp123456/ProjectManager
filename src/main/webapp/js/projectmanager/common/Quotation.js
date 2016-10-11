/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function changeLocationBitMaterialService() {
    var data = "pdId=" + $("#subproject option:selected").val() +
            "&location=" + $("#location option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/location",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);
            
            $("#bill-material-service").html(content.billMaterialService);
            $("#bill-material-service-item").html(content.billMaterialServiceItems);
        },
        error: function (e) {
        }
    });
}

function addBillMaterialService(location) {
    var items = [];
    $(':checkbox:checked').each(function (i) {
        items[i] = $(this).val();
    });
    var data = "id=" + $("#subproject option:selected").val() +
            "&srcLocation=" + location +
            "&newLocation=" + $("#location option:selected").val() +
            "&itemIds=" + items;

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/replace/add",
        data: data,
        success: function (response) {
            $("#bill-material-service-item").html(response);
        },
        error: function (e) {
        }
    });

    $("#replace-bill-material-service").dialog("close");
}

function dlgReplaceBillMaterialService(location) {
    $("#replace-bill-material-service").dialog({
        autoOpen: true,
        modal: true,
        width: 310,
        buttons: {
            "submit": function () {
                addBillMaterialService(location);
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

function replaceBillMaterialService() {
    var data = "pdid=" + $("#bill-project-id").val() +
            "&location=" + $("#location option:selected").val();

    $.ajax({
        type: "POST",
        url: "/ProjectManager/project/quotation/replace",
        data: data,
        success: function (response) {
            var content = JSON.parse(response);
            
            $("#lst-bill-material-service-item").html(content.items);
            dlgReplaceBillMaterialService(content.location);
        },
        error: function (e) {
        }
    });
}

function submit() {
    
}