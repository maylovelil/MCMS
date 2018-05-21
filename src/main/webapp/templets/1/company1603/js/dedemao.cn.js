//Menu
$(function() {
	if ($(window).width() >= 768) {
		$(".navi li").hover(function() {
			$(this).find('ul:first').slideDown("fast").css({
				display: "block"
			});
		}, function() {
			$(this).find('ul:first').slideUp("fast").css({
				display: "none"
			});
		});
	}
});
$(function() {
	if ($(window).width() > 768) {
		$(window).scroll(function() {
			if ($(window).scrollTop() >= 90) {
				$(".header").addClass("head-pinned");
			} else {
				$(".header").removeClass("head-pinned");
			}
		});
	}
});
// MobileMenu
$(function() {
	$('#mobile-menu').click(function() {
		$(".main-menu").slideToggle("fast");
		$(this).toggleClass('opacity');
		$("#search-box").css({
			display: "none"
		});
		$("#mobile-so").removeClass('opacity');
	});
});
// SearchForm
$(function() {
	$("#btn-so").click(function() {
		$("#search-box").slideToggle("fast");
		$(this).toggleClass('opacity');
	});
	$("#mobile-so").click(function() {
		$("#search-box").slideToggle("fast");
		$(this).toggleClass('opacity');
		$(".main-menu").css({
			display: "none"
		});
		$('#mobile-menu').removeClass('opacity');
	});
});
// Menu First
$(function() {
	$(".postlist li:last").addClass("nb");
	$(".foot-rt img").addClass("fadeInLeft wow animated");
});
// Slideshow
$(function() {
	$("#sliderbox").hover(function() {
		$("#sliderbox .bx-prev").fadeIn();
		$("#sliderbox .bx-next").fadeIn();
	}, function() {
		$("#sliderbox .bx-prev").fadeOut();
		$("#sliderbox .bx-next").fadeOut();
	});
	$('#slideshow').bxSlider({
		mode: 'fade',
		/*'horizontal', 'vertical'*/
		controls: true,
		nextSelector: '#slider-next',
		prevSelector: '#slider-prev',
		prevText: '上一张',
		nextText: '下一张',
		auto: true,
		speed: 500,
		pause: 5000,
		pager: true
	});
});
// AnnounceSlider
$(function() {
	$('#newsbar').bxSlider({
		wrapperClass: 'announce-wrapper',
		mode: 'vertical',
		controls: false,
		auto: true,
		speed: 600,
		pause: 4000,
		pager: false
	});
});
// TickerSlider
$(function() {
	var ratio = 0.70;
	var liWidth = $('.pic-scroll-list .slide').width();
	//var liHeight = liWidth * ratio;
	// $('.pic-scroll-list .slide img').width( liWidth );
	
	// $('.pic-scroll-list .slide img').height( liHeight );
	if ($(window).width() > 960) {
		$('#row-scroll #ticker').bxSlider({
			wrapperClass: 'ticker-wrapper',
			slideWidth: 5000,
			pager: false,
			auto: true,
			minSlides: 4,
			maxSlides: 4,
			pause: 4000,
			speed: 800,
			slideMargin: 15
		});
	} else if ($(window).width() <= 960 && $(window).width() >= 768) {
		$('#row-scroll #ticker').bxSlider({
			wrapperClass: 'ticker-wrapper',
			slideWidth: 5000,
			pager: false,
			auto: true,
			minSlides: 2,
			maxSlides: 2,
			pause: 4000,
			speed: 800,
			slideMargin: 15
		});
	} else if ($(window).width() < 768) {
		$('#row-scroll #ticker').bxSlider({
			wrapperClass: 'ticker-wrapper',
			slideWidth: 5000,
			pager: false,
			auto: true,
			minSlides: 1,
			maxSlides: 1,
			pause: 4000,
			speed: 800,
			slideMargin: 0
		});
	}
	$('#home-row-scroll #ticker').bxSlider({
		wrapperClass: 'ticker-wrapper',
		slideWidth: 5000,
		pager: false,
		auto: true,
		minSlides: 1,
		maxSlides: 1,
		pause: 4000,
		speed: 800,
		slideMargin: 0
	});
});
//Piclist-ImageResponsive
$(function() {
	var ratio = 0.70;
	var liWidth = $('.piclist li').width();
	//var liHeight = liWidth * ratio;
	var liHeight = liWidth;
	$('.piclist li img').width(liWidth);
	// $('.piclist li img').height( liHeight );
	$('.piclist li img').height(liHeight);

});
//Weixin
$(function() {
	$("#i_weixin").hover(function() {
		$("#weixin").slideDown("fast").css({
			display: "block"
		});
	}, function() {

		$("#weixin").slideUp("fast").css({
			display: "none"
		});
	});
});
//BackTop
$(function() {
	var $backToTopTxt = "",
		$backToTopEle = $('<a class="backToTop" title="返回顶部"></a>').appendTo($("body")).text($backToTopTxt).attr("title", $backToTopTxt).click(function() {
			$("html, body").animate({
				scrollTop: 0
			}, 120);
		}),
		$backToTopFun = function() {
			var st = $(document).scrollTop(),
				winh = $(window).height();
			(st > 0) ? $backToTopEle.fadeIn() : $backToTopEle.fadeOut();
			if (!window.XMLHttpRequest) {
				$backToTopEle.css("top", st + winh - 166);
			}
		};
	$(window).bind("scroll", $backToTopFun);
	$(function() {
		$backToTopFun();
	});
});