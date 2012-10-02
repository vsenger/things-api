$(function(){
	$('.error').hide();
	
	// reset form and hide all errors
	$("a#clear").click(function(){
		$('.error').hide();
		$('form#contact-form').clearForm();
	});
	
	// show message error if after editing
	// the name field contains improper value
	$("input#name").blur(function(){
		if(validateInput('name')){
			if(!validateName()){
				$("label#name_error").hide();
				$("label#name_error2").show();
			}
		}else{
			$("label#name_error2").hide();
		}
	});
	
	// show message error if after editing
	// the email field contains improper value
	$("input#email").blur(function(){
		if(validateInput('email')){
			if(!validateEmail()){
				$("label#email_error").hide();
				$("label#email_error2").show();
			}
		}else{
			$("label#email_error2").hide();
		}
	});
	
	// show message error if after editing
	// the phone field contains improper value
	if($("input#phone").length){
	$("input#phone").blur(function(){
		if(validateInput('phone')){
			if(!validatePhone()){
				$("label#phone_error").hide();
				$("label#phone_error2").show();
			}
		}else{
			$("label#phone_error2").hide();
		}
	})};
	
	// show message error if after editing
	// the message field contains improper value
	$("textarea#message").blur(function(){
		if(validateTextArea('message')){
			if(!validateMessage()){
				$("label#message_error").hide();
				$("label#message_error2").show();
			}
		}else{
			$("label#message_error2").hide();
		}
	});
	
	$("input#name").keydown(function(){
		if(validateInput('name')){
			$("label#name_error").hide();
		}
		if(validateName()){
			$("label#name_error2").hide();
		}
	});
	
	$("input#email").keydown(function(){
		if(validateInput('email')){
			$("label#email_error").hide();
		}
		if(validateEmail()){
			$("label#email_error2").hide();
		}
	});
	if($("input#phone").length){
	$("input#phone").keydown(function(){
		if(validateInput('phone')){
			$("label#phone_error").hide();
		}
		if(validatePhone()){
			$("label#phone_error2").hide();
		}
	})};
	
	$("textarea#message").keydown(function(){
		if(validateTextArea('message')){
			$("label#message_error").hide();
		}
		if(validateMessage()){
			$("label#message_error2").hide();
		}
	});
	
	var owner_email = $("input#owner_email").val();
	if(!isValidEmailAddress(owner_email)){
		$('#contact_form').html("<label class='error'>*Owner email is not valid</label>")
	}
		
	$("a#submit").click(function(){
		// validate and process form
		var quit = false;
		if(validateName()){
			name = validateName();
			$("label#name_error").hide();
			$("label#name_error2").hide();
		}else if(validateInput('name')){
			$("label#name_error").hide();
			$("label#name_error2").show();
		}else{
			$("label#name_error").show();
			$("label#name_error2").hide();
			quit = true;
		}
		if(validateEmail()){
			email = validateEmail();
			$("label#email_error").hide();
			$("label#email_error2").hide();
		}else if(validateInput('email')){
			$("label#email_error").hide();
			$("label#email_error2").show();
		}else{
			$("label#email_error").show();
			$("label#email_error2").hide();
			quit = true;
		}
		if(validatePhone()){
			phone = validatePhone();
			$("label#phone_error").hide();
			$("label#phone_error2").hide();
		}else if(validateInput('phone')){
			$("label#phone_error").hide();
			$("label#phone_error2").show();
		}else{
			$("label#phone_error").show();
			$("label#phone_error2").hide();
			quit = true;
		}
		if(validateMessage()){
			message = validateMessage();
			$("label#message_error").hide();
			$("label#message_error2").hide();
		}else if(validateTextArea('message')){
			$("label#message_error").hide();
			$("label#message_error2").show();
		}else{
			$("label#message_error").show();
			$("label#message_error2").hide();
			quit = true;
		}
		if(quit){
			return false;
		}
		
		var stripHTML = $("input#stripHTML").val();
		var smtpMailServer = $("input#smtpMailServer").val();
		
		var dataString = 'name=' + name + '&email=' + email + '&phone=' + phone + '&message=' + message + '&owner_email=' + owner_email + '&stripHTML=' + stripHTML + '&smtpMailServer=' + smtpMailServer;
		
		var serverProcessorType = $("input#serverProcessorType").val();
		if(serverProcessorType == 'asp'){
			fileExtension = 'ashx';
		}else{
			fileExtension = serverProcessorType;
		}
		var mailHandlerURL = "bin/MailHandler." + fileExtension;
		$.ajax({
			type: "POST",
			url: mailHandlerURL,
			data: dataString,
			success: function(){
				$('.error').hide();
				$('form#contact-form').clearForm();
				$('#contact_form').html("<div class='download-box'>Contact form submitted!</div>")
					.append("<br><label for='message'><b>We will be in touch soon.</b></label>")
					.hide()
					.fadeIn(1500, function(){
						$('#contact_form').append("<br><br><a id='back' onclick='window.location.reload(); return false;' class='button'>back</a>");
					});
			}
		});
				
		return false;
	});
});
$.fn.clearForm = function(){
	return this.each(function(){
		var type = this.type, tag = this.tagName.toLowerCase();
		if (tag == 'form'){
			return $(':input',this).clearForm();
		}
		if (type == 'text' || type == 'password' || tag == 'textarea'){
			this.value = '';
		}else if (type == 'checkbox' || type == 'radio'){
			this.checked = false;
		}else if (tag == 'select'){
			this.selectedIndex = -1;
		}
	});
};
function isValidName(name){
	var pattern = new RegExp(/^[a-zA-Z'][a-zA-Z-' ]+[a-zA-Z']?$/);
	
	return pattern.test(name);
}
function isValidEmailAddress(emailAddress){
	var pattern = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);
	
	return pattern.test(emailAddress);
}
function isValidPhoneNumber(phoneNumber){
	var pattern = new RegExp(/^\+?(\d[\d\-\+\(\) ]{5,}\d$)/);
	
	return pattern.test(phoneNumber);
}

function validateName(){
	var name = $("input#name").val();
	if(isValidName(name)){
		if(name=='Name'){return false;}
		else{return name;}
	}else{
		return false;
	}
	
}

function validateEmail(){
	var email = $("input#email").val();
	if(!isValidEmailAddress(email)){
		return false;
	}else{
		return email;
	}
}
function validatePhone(){
	var phone = $("input#phone").val();
	if(!isValidPhoneNumber(phone)){
		return false;
	}else{
		return phone;
	}
}

function validateMessage(){
	var message = $("textarea#message").val();
	if(message.length < 10){
		return false;
	}else{
		return message;
	}
}

// make sure visitor does not input a blank field
function validateInput(field){
	var fieldObject = $("input#" + field + "").val();
	if(fieldObject.length < 1){
		return false;
	}else{
		return true;
	}
}

function validateTextArea(field){
	var fieldObject = $("textarea#" + field + "").val();
	if(fieldObject.length < 1){
		return false;
	}else{
		return true;
	}
}