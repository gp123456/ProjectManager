 /*$( "body").on('click' , '.menu-loby' , function() { 
   
  $.ajax({
                url: 'lobbyConfiguration.html', 
                success: function(x) {                     
                    $(".admin-panel").html(x); 
                }
            }); 
  });
*/
 $(document).ready(function(){
 	$( "#tabs" ).tabs();
 	/* ---------------------- */
 	$(".collapsecriteria").on('click', function(){
 		if ($(this).next().height()==160)
 		{
 			$(this).next().stop().animate({'height': '0px'},300, function(){$(this).next().css('display', 'none');});
 		}
 		else
 		{
 			$(this).next().stop().animate({'height': '160px'},300, function(){$(this).next().css('display', 'block');});
 		}
 	});
 });