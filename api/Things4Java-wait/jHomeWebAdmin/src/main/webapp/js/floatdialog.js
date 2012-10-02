///////////////////////////////////////////////////////\\
///////////////////////////////////////////////////////\\
//***************************************************\\\\
//              JQuery Float Dialog v2.0             \\\\
//                    Easy PopUp                     \\\\
//                    ExpBuilder                     \\\\
//                elkadrey@gmail.com                 \\\\
//              Auther: Ahmed Elkadrey               \\\\
//***************************************************\\\\
///////////////////////////////////////////////////////\\
///////////////////////////////////////////////////////\\
jQuery.fn.floatdialog = function(id, options)
{
   var Config = {
                     backgroundcolor: "#000000",
                     speed          : 'slow',
                     event          : 'click',
                     effect         : true,
                     move           : 'default',
                     closeClass     : '.closebutton',
                     sound          : false,
                     soundsrc       : 'audio/sound01.mp3'
                };
    if(options)
    {
		jQuery.extend(Config, options);
	};

    //functions
    //{
    function brwstester()
    {
        return (document.compatMode && document.compatMode!="BackCompat")? document.documentElement : document.body;
    }

    function resizeheights(me)
    {

        $("#" + me).css({'height' : $(window).height() + brwstester().scrollTop  + "px"});
    }
    function display_mask(id)
    {
        if(Config.effect)
        {
            $("#floatdialog_mask_" + id).show().fadeTo(Config.speed, 0.33);
        }
        else
        {
            $("#floatdialog_mask_" + id).css({opacity: '0.33'}).show();
        }
    }

    function center_form(id)
    {
          var scwidth  = screen.width;
          var scheight = screen.height;
          var tblwidth = $("#" + id).width();
          var tblheight = $("#" + id).height();

          var D_width = (scwidth - tblwidth);
          if(D_width > 0)
          {
              D_width /= 2;
          }
          else if(D_width < 0)
          {
              D_width *= -1;
              D_width /= 2;
          }
          $("#" + id).css('left', D_width  + 'px');

          var D_height = (scheight - tblheight);
          if(D_height > 0)
          {
              D_height /= 2;
          }
          else if(D_height < 0)
          {
              D_height *= -1;
              D_height /= 2;
          }
          $("#" + id).css('top', brwstester().scrollTop + D_height /2 + 'px');
    }

    function display_form(id)
    {
        display_mask(id);
        center_form(id);
        $(window).scroll(function()
        {
            center_form(id);
        });

        if(Config.sound == true && Config.soundsrc)
        {
             $('#expbuilder_flash_floatdialog_' + id).remove();
             $("body").append('<div style="width: 0px;height: 0px;display: inline;" id="expbuilder_flash_floatdialog_' + id + '"><object id="expbuilder_flash_floatdialog_' + id + '_flash" height="0" width="0" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000">  <param value="vid" name="id"/><param value="middle" name="align"/>  <param value="sameDomain" name="allowScriptAccess"/>  <param value="true" name="allowFullScreen"/>  <param value="high" name="quality"/>  <param value="expbuilder-soundplayer.swf?' + Config.soundsrc + '" name="src"/>  <embed src="expbuilder-soundplayer.swf?' + Config.soundsrc + '" height="0" width="0" allowscriptaccess="sameDomain" allowfullscreen="true" quality="high" bgcolor="#000000" type="application/x-shockwave-flash" id="vid"/></object></div>');

             //document.getElementById('expbuilder_flash_floatdialog_' + id).setVariable( "soundurl", Config.soundsrc);

        }

        if(Config.move == 'down')
        {
            //move from up
            var top = document.getElementById(id).style.top;
            $("#" + id).css({'top': (brwstester().scrollTop - 100) + 'px'}).show().animate({"top":  top}, Config.speed);
        }
        else if(Config.move == 'up')
        {
            //move from down
            var top = document.getElementById(id).style.top;
            $("#" + id).css({'top': (brwstester().scrollTop +  (screen.height)) + 'px'}).show().animate({"top": top}, Config.speed);
        }
        else if(Config.move == 'right')
        {
            //move from left
            var left = document.getElementById(id).style.left;
            $("#" + id).css({'width': $("#" + id).width(), 'left': screen.width + 'px'}).show().animate({"left": left}, Config.speed);
        }
        else if(Config.move == 'left')
        {
            //move from right
            var left = document.getElementById(id).style.left;
            $("#" + id).css({'width': $("#" + id).width(), 'left': '-' + $("#" + id).width() + 'px'}).show().animate({"left": left}, Config.speed);
        }
        else if(Config.move == 'slidedown')
        {

            $("#" + id).slideDown(Config.speed);
        }
        else
        {
            //default
            if(Config.effect)
            {
                $("#" + id).fadeIn(Config.speed);
            }
            else
            {
                $("#" + id).show();
            }
        }
    }
    function disable_mask(id)
    {
        $(".disable_masking").hide();
        if(Config.effect)
        {
            $("#floatdialog_mask_" + id).fadeOut(Config.speed).fadeTo("", 100);
        }
        else
        {
            $("#floatdialog_mask_" + id).hide();
        }
    }
    //}
    $(document).ready(function()
    {
        if(!$("#floatdialog_mask_" + id).html())
        {
             $("body").append('<div id="floatdialog_mask_' + id + '" style="display: none;left: 0px;top: 0px;z-index: 6000;position: absolute;background-color:' + Config.backgroundcolor + '">&nbsp;</div>');
             $("#floatdialog_mask_" + id).css({'width' : 100+ "%"}).bind('click', function()
             {
                 disable_mask(id);
             });
             resizeheights('floatdialog_mask_' + id);
             if(Config.closeClass)
             {
                 $(Config.closeClass).bind('click', function()
                 {
                     disable_mask(id);
                 });
             }
        }

    });
    $("#" + id).hide().addClass('disable_masking');
    $(window).scroll(function()
     {
         resizeheights('floatdialog_mask_' + id);
     });


     
     if(Config.event == "load")
     {
         $(document).ready(function()
         {
            display_form(id);
         });
     }
     else
     {
        $(this).bind(Config.event, function()
        {
             display_form(id);
        });
     }

}