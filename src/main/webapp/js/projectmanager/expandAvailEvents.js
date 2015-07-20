function expandTable() {
    $('.inputArrowClosed').off("click");
    $('.inputArrowClosed').on("click", function() {
        var div = $(this).parents('tr').find('.CopenHideTableDiv');
        if (div.data("state") == "closed") {

            var tr = $(this).closest("tr");
            div.css("display", "block");
            div.data("state", "opened");

            $(this).css("background", "url(images/betiator/challenge/selectbox_open.png) no-repeat");
            tr.css("background-color", "rgb(255,255,230)");
            tr.removeClass('hoverenable');
            var imgPlus = $(this).closest("tr").find(".inputArrowClosed");
//            imgPlus.css("height", "19");
//            imgPlus.css("top", "-33px");
//            $(this).closest("tr").find(".updown").css("top", "-33px");
            $(this).closest("tr").find(".noBorder").closest('td').css("border-left", "none");
            $(this).closest("tr").find(".noBorder").closest('td').css("border-right", "none");
            $(this).closest("tr").find(".noBorder").closest('td').next().css("border-left", "none");
            $(this).closest("tr").find(".noBorder").closest('td').prev().css("border-right", "none");
            if ($('#TableWinners').height() > 198) {
                $('#TableWinners_wrapper .onlythis').css("width", "715px");
            }
            else {
                $('#TableWinners_wrapper .onlythis').css("width", "730px");
            }
        }
        else {
            var color1;
            var tr = $(this).closest("tr");
            if (tr.hasClass('odd')) {
                color1 = "rgb(255,255,255)";
            }
            else {
                color1 = "rgb(240,240,240)";
            }
            div.css("display", "none");
            div.data("state", "closed");
            $(this).css("background", "url(images/betiator/challenge/selectbox_closed.png) no-repeat");
            tr.css("background-color", color1);
            tr.css("background-color", '');
            tr.addClass('hoverenable');
            var imgPlus = $(this).closest("tr").find(".inputArrowClosed");
//            imgPlus.css("height", "19");
//            imgPlus.css("top", "0px");
//            $(this).closest("tr").find(".updown").css("top", "0px");
            $(this).closest("tr").find(".noBorder").closest('td').css("border", "1px solid #ccc");
            $(this).closest("tr").find(".noBorder").closest('td').next().css("border", "1px solid #ccc");
            $(this).closest("tr").find(".noBorder").closest('td').prev().css("border", "1px solid #ccc");
            if ($('#TableWinners').height() > 198) {
                $('#TableWinners_wrapper .onlythis').css("width", "715px");
            }
            else {
                $('#TableWinners_wrapper .onlythis').css("width", "730px");
            }
        }
    });
}