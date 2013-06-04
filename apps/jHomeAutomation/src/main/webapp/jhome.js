$(document).ready(function(){ 
  var things="http://localhost:8080/things/thing/";
  var lampon=false;
  var aberto=false;
  var readingSensor = true;
  var identificador = self.setInterval(atualizar, 1000);
  function atualizar() {
    if(readingSensor) {
      
      $.ajax({  
        type: "POST",  
        dataType: "text",
        url: things + "temp" ,
        success: function(data) {  
          $('#sensorTemperature').html(data);
        }  
      }); 
      $.ajax({  
        type: "POST",  
        dataType: "text",
        url: things + "light" ,
        success: function(data) {  
          $('#sensorLight').html(data);
        }  
      }); 
      $.ajax({  
        type: "POST",  
        dataType: "text",
        url: things + "distance",
        success: function(data) {  
          $('#sensorDistance').html(data);
        }  
      });       
    }
  };
  var rgb1;
  $('#jhome1-color').ColorPicker({
    color: '#0000ff',
    onShow: function (colpkr) {
      $(colpkr).fadeIn(500);
      return false;
    },
    onHide: function (colpkr) {
      //alert(dataString);
      $(colpkr).fadeOut(500);
      $.ajax({  
        type: "POST",  
        url: things + "red?" + rgb1.r 
      });  
      $.ajax({  
        type: "POST",  
        url: things + "green?" + rgb1.g 
      });  
      $.ajax({  
        type: "POST",  
        url: things + "blue?" + rgb1.b 
      });  

return false;

    },
    onChange: function (hsb, hex, rgb) {
      $('#jhome1-color').css('background-color', '#' + hex);            
      rgb1 = rgb;
      return false;
    }
  });        


  $('#lampControl').click(function() {
    if(lampon) $('#lampControl').attr("src", "images/icons/lamp3-off.png");
    else  $('#lampControl').attr("src", "images/icons/lamp3.png");
    lampon=!lampon; 
    $.ajax({  
      type: "GET",  
      url: things + "relay2?" + (lampon ? 1 : 0),  
      success: function() {  
    
      }  
    }); 
              
  });
  $('#StopSensor').click(function() {
    $.ajax({  
      type: 'GET',  
      url: '../Startup?action=off',  
      data: '',  
      success: function() {  
      }  
    
    }); 
  });               
          
  $('#imageTemperature').click(function() {
    var requestUrl =
    '../resources/sensor';
    $.getJSON(requestUrl,
      function(data) {
        $('#sensorTemperature').html(data.temperatureCelsius);
        $('#sensorTemperatureBluetooth').html(data.temperatureCelsius1);
        $('#sensorLight').html(data.light);
        $('#sensorDistance').html(data.distance);
      });
    
  }); 
  $('form#coffeescheduler').submit(function() {         
    var when = $('input[name=when]');
    var howlong = $('input[name=howLong]');
    var dataString = 'when=' + when.val() 
    +  "&howLong=" + howlong.val()
    + "&relayName=relay2";
    $.ajax({  
      type: 'GET',  
      url: '../Agendador',  
      data: dataString,  
      success: function() {  
        $('#feedbackdiv').html('<p>Coffee Scheduled!</p>');
      }
    });
    return false;
    
  });        
}); 
