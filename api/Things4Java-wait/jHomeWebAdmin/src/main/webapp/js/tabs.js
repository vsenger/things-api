/* ------------------------------------------------------------------------
	Do it when you're ready dawg!
------------------------------------------------------------------------- */

	$(function(){
		tabs.init();
	});
	
	tabs = {
		init : function(){
			$('.tabs').each(function(){
				$(this).find('.tab-content:gt(0)').hide();

				$(this).find('ul.nav a').click(function(){
					$(this).parents('div.tabs').find('.tab-content').hide();
					$($(this).attr('href')).show();

					$(this).parent().addClass('selected').siblings().removeClass('selected');

					return false;
				});
			});
		}
	}