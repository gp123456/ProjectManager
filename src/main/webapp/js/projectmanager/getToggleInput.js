/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function getToggleInput(){
    $('.toggle-bg').click(function(){
        var val = $(this).find("input:checked").val();
        alert(val);
        if($(this).hasClass('toggle-bg1')){
            return "on";
        }
        else{
            return "off";
        }
    });
}