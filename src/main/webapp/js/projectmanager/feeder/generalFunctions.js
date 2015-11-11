function checkNumbering(obj) {
        obj.keydown(function(event) {
        // Allow: backspace, delete, tab, escape, and enter
        if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 || 
             // Allow: Ctrl+A
            (event.keyCode == 65 && event.ctrlKey === true) || 
             // Allow: home, end, left, right
            (event.keyCode >= 35 && event.keyCode <= 39)) {
                 // let it happen, don't do anything
                 return;
        }
        else {
            // Ensure that it is a number and stop the keypress
            if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
                event.preventDefault(); 
            }   
        }
    });
};
/* ==================================================== */
function checkIfEmpty(obj)
{
   
        if(!$.trim(this.value).length) { // zero-length string AFTER a trim
            //$(this).parents('p').addClass('warning');
            //alert('This element is mandatory');
        }
        else
        {
            //$(this).parents('p').addClass('allok');
        }

};
/* ==================================================== */
function Gritter(titleMessage, textMessage, stickyFlag, timeFader, classname)
{
    $.gritter.add({
          // (string | mandatory) the heading of the notification
          title: titleMessage,
          // (string | mandatory) the text inside the notification
          text: textMessage,
          // (string | optional) the image to display on the left
          //image: 'http://a0.twimg.com/profile_images/59268975/jquery_avatar_bigger.png',
          // (bool | optional) if you want it to fade out on its own or just sit there
          sticky: stickyFlag,
          // (int | optional) the time you want it to be alive for before fading out
          time: timeFader,
          class_name: classname
    });
};