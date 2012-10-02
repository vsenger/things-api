$(function(){
	var act;
	var fl=true;
	function all_null(){
		$('#content > ul > li').css({visibility:'hidden', right:'-9999px'});
		$('#page_0').css({visibility:'visible', right:'0'});
		$('.box_img').css({width:'0', height:'0', left:'337px', top:'337px;'});
		$('.text').css({opacity:'0', visibility:'visible'});
		$('.but_close img').css({top:'29px',left:'29px', width:'0', height:'0', zIndex:'1'});
		close_anim();
	}
	function null_content(){
		
	}
	all_null();
	function open_page(page){
		nav=parseInt(page.substr(6));
		$('#footer_menu li').removeClass('active');
		$('#footer_menu li').eq(nav).addClass('active');
		$('#menu li a img').css({opacity:'0'});
		if (page=='#page_0') {
			$('.trigger').click();
			$('#menu li a img').stop().animate({opacity:'1'},400);
			$('.menu_box .images span').stop().animate({rotate:'0deg'},0);
			$('.menu_box .images span').css({opacity:'0',visibility:'hidden'});
			$('.menu_box .images .nav1').css({opacity:'1',visibility:'visible'});
			menu_hover();
			$('#page_0').stop().animate({width:'940px'});
			$('#page_0 .inner').stop().animate({marginLeft:'387', marginTop:'255'});
			fl=true;
			$('#menu #nav1 a').mouseenter();
				$('.menu_box .images span').stop().animate({rotate:'-=360deg'},800,function(){
					$('.menu_box .images span').stop().animate({rotate:'0deg'},0);
				});
			act=page;
			location.hash=page+'/'
		} else {
			if (fl) {
				$('#page_0').stop().animate({width:'270px'});
				$('#page_0 .inner').stop().animate({marginLeft:'23', marginTop:'243'});
				fl=false
			}
			$('.images span').animate({rotate:51.4+360*k+'deg'}, function(){
				$('.images span').animate({rotate:51.4+360*k+'deg'},0)													  
				$('.menu_box .images span').stop().animate({opacity:'0'},400, function(){$(this).css({visibility:'hidden'})})
				$('.menu_box .images .nav1').css({visibility:'hidden', opacity:'0'});
				$('.menu_box .images .nav'+(nav+1)).css({visibility:'visible'}).stop().animate({opacity:'1'},400);
				$('.tittles > div').stop().animate({opacity:'0'},400, function(){$(this).css({visibility:'hidden'})})
				$('.tittles > .nav'+(nav+1)).css({visibility:'visible'}).stop().animate({opacity:'1'},400);
				$(page).css({visibility:'visible', right:'0'});
				close_anim();
				$(page).find('.box_img').stop().animate({width:'100%', height:'100%',left:'0', top:'0'},400, function(){
					$(page).find('.text').css({visibility:'visible'}).stop().animate({opacity:'1'},400, function(){
						$(this).css({opacity:'none'})
						$(page).find('.but_close').css({display:'block'}).find('.img').stop().animate({top:'0', left:'0', width:'100%', height:'100%'},400)
						act=page;
						location.hash=page+'/'	
					})
				})
			})
		}
	}
	var page=location.hash.slice(0,-1);
	if ((page=='')||(page=='#')) {	
		page='#page_0';
	}
	open_page(page);
	var nav=parseInt(page.substr(6));
	$('#footer_menu li').eq(nav).addClass('active');	
	$('a').click(function(){
		if ($(this).attr('href').substr($(this).attr('href').indexOf('#'),6)=='#page_') {
			page=$(this).attr('href');
			if (act=='#page_0') {
				if (page=='#page_0') {
					$('.menu').removeClass('close')
					open_page(page);
				} else{
					$('.trigger').click();
					$('#menu li a').unbind('mouseenter')
					$('#menu li a img').stop().animate({opacity:'0'},200);	
					open_page(page);
				}
			} else{
				$(act).find('.but_close').unbind('mouseenter, mouseleave').find('img').stop().animate({top:'29', left:'29', width:'0', height:'0'},400, function(){$(act).find('.but_close').css({display:'none'})});
				$(act).find('.text').stop().animate({opacity:'0'},400, function(){
					$(act).find('.text').css({visibility:'hidden'});
					$(act).find('.box_img').stop().animate({top:'337px',left:'337px', width:'0', height:'0'},400, function(){
						$(act).css({visibility:'hidden',right:'-9999px'})
						open_page(page);																								  
					})
				})	
			}
			return false;
		} 
	})
})

function close_anim(){
	$('.but_close').mouseenter(function(){
		$(this).find('.img_act').css({zIndex:'2'}).stop().animate({top:'0',left:'0', width:'100%', height:'100%'},400)
		$(this).find('.img').css({zIndex:'1'}).stop().animate({ top:'29',left:'29', width:'0', height:'0'})
	}).mouseleave(function(){
		$(this).find('.img').css({zIndex:'2'}).stop().animate({ top:'0',left:'0', width:'100%', height:'100%'})
		$(this).find('.img_act').css({zIndex:'1'}).stop().animate({ top:'29',left:'29', width:'0', height:'0'})
	})	
}