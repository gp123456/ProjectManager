
/*** template system ***/
$.fn.templatesys = function(load) {
    // var load = ['user', 'xxx']; //load nedded templates
    ich.clearAll()
    $.ajax({url: 'js/betiator/common/templates.json', dataType: 'json',
        async: false, success: function(objtemp) {

            //console.log(objtemp)
            $.each(load, function(key, value) {
                $.each(objtemp.templates, function(index, template) {
                    if (value == index) {
                        //console.log(value, index, template, 'loaded')
                        ich.addTemplate(index, template);
                    } else {
                        //  console.log(value, index, 'template not exist')
                    }
                })
            })
        }
    });

}



function getUrlVars(data) {
    var vars = {}, hash;
    var hashes = data.split('&');
    //var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for (var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        //vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}


$(function() {


    $("#Datefrom").datepicker({
        defaultDate: "+1w",
        dateFormat: 'dd-mm-yy',
        changeMonth: true,
        numberOfMonths: 1,
        onClose: function(selectedDate) {
            $("#Dateto").datepicker("option", "minDate", selectedDate);
        }
    });
    $("#Dateto").datepicker({
        defaultDate: "+1w",
        dateFormat: 'dd-mm-yy',
        changeMonth: true,
        numberOfMonths: 1,
        onClose: function(selectedDate) {
            $("#Datefrom").datepicker("option", "maxDate", selectedDate);
        }
    });

    /* fixing the scrolbar when on menu expand the height is change */
    $('.parent-menu-icon').on('click', function() {
        onresize();
    })



    /**
     * Starts chosen select.
     */
    $(".chosen-select").chosen();

    /**
     * Starts js tree.
     */
    $('#jstree').jstree();

    /**
     * Points the head menu which is showed.
     */
    $('.headmenu1').click(function() {
        $(".headmenu2").css("display", "none");
        $(this).next(".headmenu2").css("display", "block");
    });

    /**
     * Show head menu titles underline only on hover them.
     */
    $('.headmenu1').hover(function() {
        $(this).next('.headmenu2').toggle();
    });

    /**
     * Collapse search criteria div.
     */
    $('.inputArrow').click(function() {
        if ($(this).parent().next('.criteriacontent').is(":visible")) {
            $(this).parent().next('.criteriacontent').hide("slow");
            $(this).css("background", "url('images/betiator/common/arrow-up.png')");
        }
        else {
            $(this).parent().next('.criteriacontent').show("slow");
            $(this).css("background", "url('images/betiator/common/arrow-down.png')");
        }
    });

    /**
     * Hides the search results (jstree).
     */
    $('.hide').click(function() {
        if ($('#jstree').is(":visible")) {
            $('#jstree').hide("slow");
            $(this).css("background", "url('images/betiator/common/arrow-up.png') no-repeat center");
        }
        else {
            $('#jstree').show("slow");
            $(this).css("background", "url('images/betiator/common/arrow-down.png') no-repeat center");
        }
    });

    /**
     * Changes color of toggle inputs.
     */
    $('.toggle-bg').click(function() {
        $(this).toggleClass('toggle-bg1');
    });
    
});


/*
 This function changes the height  of the left menu
 */
function onresize() {
    // - 54 is the header height */
    var h = 0, w = 0;
    $('.menu-container.expanded').css("min-height", 0);
    $('.menu-container.collapsed').css("min-height", 0);

    h = $(document).height(), w = $(document).width();
    $('.menu-container.expanded').css("min-height", h - 153)
    $('.menu-container.collapsed').css("min-height", h)

}

$(window).resize(onresize);

$(window).bind("load", function() {
    onresize()
})

setInterval(function() {
    onresize()
}, 1000);



