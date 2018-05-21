		;(function($) {
			$.scrollBtn = function(options) {
				var opts = $.extend({}, $.scrollBtn.defaults, options);

				var $scrollBtn = $('<div></div>').css({
									bottom: opts.bottom + 'px',
									right: opts.right + 'px'
								}).addClass('scroll-up')
								.attr('title', opts.title)
								.click(function() {
									$('html, body').animate({scrollTop: 0}, opts.duration);
								}).appendTo('body');
																					
				$(window).bind('scroll', function() {
					var scrollTop = $(document).scrollTop(),
						viewHeight = $(window).height();

					if(scrollTop <= opts.showScale) {
						if($scrollBtn.is(':visible'))
							$scrollBtn.fadeOut(500);
					} else {
						if($scrollBtn.is(':hidden')) 
							$scrollBtn.fadeIn(500);
					}

					if(isIE6()) {
						var top = viewHeight + scrollTop - $scrollBtn.outerHeight() - opts.bottom;
						$scrollBtn.css('top', top + 'px');
					}
				});

				function isIE6() {
					if($.browser.msie) {
						if($.browser.version == '6.0') return true;
					}
				}
			};

			/**
			 * -params 
			 *  -showScale: scroll down how much to show the scrollup button
			 *  -right: to right of scrollable container 
			 *  -bottom: to bottom of scrollable container 
			 */
			$.scrollBtn.defaults = {
				showScale: 100,  
				right:10,
				bottom:10,
				duration:200,
				title:'返回顶部'
			}
		})(jQuery);

		