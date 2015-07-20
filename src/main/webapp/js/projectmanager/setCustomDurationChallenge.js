/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function seCustomDurationChallenge(){
    $('#customDuration').click(function(){
        if($('#customDuration').prop("checked")){
            $('#customDuration').prop("checked", false);
        }
        else{
            $('#customDuration').prop("checked", true);
            document.getElementById("autoDuration").value = -1;
            document.getElementById("startDuration").value = "";
            document.getElementById("endDuration").value = "";
            document.getElementById("matchMin").value = "";
        }
    });
}