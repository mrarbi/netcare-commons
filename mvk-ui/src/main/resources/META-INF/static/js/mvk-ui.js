var MVK = {
	
	MENU : (function() {
		
		var my = {};
		
		my.initMenuState = function() {
			if ($(window).width() < 980) {
				my.hideMenu();
			} else {
				my.showMenu();
			}
		};
		
		my.initMenuToggler = function() {
			$('a.toggleMenuButton').live('click', function() {
				$('#leftMenu div.wrapper').slideToggle('fast');
			});
		};
		
		my.showMenu = function() {
			$('#leftMenu div.wrapper').show();
		}
		
		my.hideMenu = function() {
			$('#leftMenu div.wrapper').hide();
		}
		
		my.initResizer = function() {
			$(window).resize(function() {
				if ($(window).width() < 980) {
					my.hideMenu();
				} else {
					my.showMenu();
				}
			});
		};
		
		my.init = function() {
			my.initMenuState();
			my.initMenuToggler();
			my.initResizer();
		};
		
		return my;
		
	})()
		
};