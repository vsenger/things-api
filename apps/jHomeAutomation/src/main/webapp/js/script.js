if ( $.browser.webkit) {
	$(window).load(function() {	
		// vertical scroll	
		$('.scroll-pane').jScrollPane({
			showArrows:false,
			scrollbarWidth:12,
			dragMaxHeight:100
		});					   
	})
} else{
	$(document).ready(function() {	
		// vertical scroll	
		$('.scroll-pane').jScrollPane({
			showArrows:false,
			scrollbarWidth:12,
			dragMaxHeight:100
		});					   
	})
}

$(document).ready(function() {
	// menu
	$('.menu').mobilyblocks({
		trigger: 'click',
		direction: 'counter',
		duration:800,
		zIndex:50,
		widthMultiplier:-0.95
	});
	$('#menu li .img_act').css({opacity:'0',visibility:'hidden'})
	$('#menu li:eq(0) .img_act').css({opacity:'1',visibility:'visible'});
	$('.menu_box .tittles > div').css({opacity:'0', visibility:'hidden'});
	$('.menu_box .nav1').css({opacity:'1', visibility:'visible'});
	$('.menu_box .images img').css({opacity:'0',visibility:'hidden'});
	$('.menu_box .images .nav1').css({opacity:'1',visibility:'visible'});
	
	
	menu_hover();
	$('.addit_menu').superfish({
      delay:       400,
      animation:   {opacity:'show', height:'show'},
      speed:       400,
      autoArrows:  false,
      dropShadows: false
    });
	$('.with_ul').hover(function(){
			$(this).find('> a').css({background:'#0d0c0c'})
		}, function(){
			$(this).find('> a').css({background:'none'})
		}
	)
	//  tool tip
	$('.normaltip').aToolTip({
    	toolTipClass: 'aToolTip'});
	// list
	$('.list1 li a').hover(function(){
			$(this).stop().animate({paddingLeft:'45'},400)						
		}, function(){
			$(this).stop().animate({paddingLeft:'37'},400)
		}
	)
	// map
	// for lightbox 
	if ($("a[rel^='prettyPhoto']").length) {
			$(document).ready(function() {
				// prettyPhoto
				$("a[rel^='prettyPhoto']").prettyPhoto({theme:'facebook'});
			});
	}

	// content set height
	var h;
	function setHeight(){
		new_h=$(window).height();
		if ((h!=new_h)) {
			h=new_h;
			if (h-275>729) {$('#content > ul').css({height:(h-275)});} else{$('#content > ul').css({height:729});}
		}
	}
	setInterval(setHeight,10);
	
 });
var last=0, k=0;

function menu_hover(){
		$('#menu li a').mouseenter(function(){
			nav=$(this).parent().attr('id');
			nav= nav.substr(3);
			deg=(parseInt(nav)-1)*51.4+360*k;
			if (last-deg>180) {deg=deg+360; k=k+1} else {
			if (last-deg<-180) {deg=deg-360; k=k-1};}
			last=deg;
			$('#menu li').find('.img_act').stop().animate({opacity:'0'},400, function(){$(this).css({visibility:'hidden'})});
			$(this).find('.img_act').css({visibility:'visible'}).stop().animate({opacity:'1'},400);
			$('.menu_box .tittles > div').stop().animate({opacity:'0'},400, function(){$(this).css({visibility:'hidden'})});
			$('.menu_box .tittles .nav'+nav).css({visibility:'visible'}).stop().animate({opacity:'1'});
				$('.menu_box .images span').stop().animate({rotate:deg+'deg', opacity:'0'},400, function(){$(this).css({visibility:'hidden'})});
				$('.menu_box .images .nav'+nav).css({visibility:'visible'}).stop().animate({rotate:deg+'deg', opacity:'1'},400);
			
		}
		)
}
$(window).load(function() {	
	$('.spinner').fadeOut();
	$('body').css({overflow:'inherit'});
})