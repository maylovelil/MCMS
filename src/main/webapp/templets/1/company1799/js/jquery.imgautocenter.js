// jquery.ImgAutoCenter - 2012-03-14 - Hogen Wang
// hack by jquery.autoIMG.js - 2010-04-02 - Tang Bin
(function ($) {
		// 检测是否支持css2.1 max-width属性
	var isMaxWidth = 'maxWidth' in document.documentElement.style,
		// 检测是否IE7浏览器
		isIE7 = !-[1,] && !('prototype' in Image) && isMaxWidth;

	$.fn.autoIMG = function () {
		var maxWidth = this.width();

		return this.find('img').each(function (i, img) {
			// 如果支持max-width属性则使用此，否则使用下面方式
			if (isMaxWidth) return img.style.maxWidth = maxWidth + 'px';
			var src = img.src;

			// 隐藏原图
			img.style.display = 'none';
			img.removeAttribute('src');

			// 获取图片头尺寸数据后立即调整图片
			imgReady(src, function (width, height) {
				// 等比例缩小
				if (width > maxWidth) {
					height = maxWidth / width * height,
					width = maxWidth;
					img.style.width = width + 'px';
					img.style.height = height + 'px';
				};
				// 显示原图
				img.style.display = '';
				img.setAttribute('src', src);
			});

		});
	};

	$.fn.ImgAutoCenter = function (settings) {
		
		settings=jQuery.extend({
			imgMaxWidth:0,
			imgMaxHeight:0
			},settings);
		
		var maxWidth = this.width();
		var maxHeight = this.height();
		var panel= this;
		//alert(panel.html());

		this.find('img').each(function (i, img) {
			// 如果支持max-width属性则使用此，否则使用下面方式
			if (isMaxWidth && maxWidth > 0){ 
				
				img.style.maxWidth = maxWidth + 'px';
				//img.style.marginTop = 
				//alert(img.height);
				//return;
			}
			var src = img.src;
			
			var imgItem =$(this);
		
			if(settings.imgMaxWidth == 0) settings.imgMaxWidth = maxWidth;
			if(settings.imgMaxHeight == 0) settings.imgMaxHeight = maxHeight;

			// 隐藏原图
			//img.style.display = 'none';
			//img.removeAttribute('src');

			// 获取图片头尺寸数据后立即调整图片
			imgReady(src, function (imgw, imgh) {
				//alert(imgh);
/*				if(imgw<settings.imgMaxWidth && imgh <settings.imgMaxHeight){
					//alert("111");
					var margintop = (settings.imgMaxHeight - imgh) / 2;
					imgItem.css("margin-top", margintop + "px");
					return;
				}*/
				// 等比例缩小
				//alert((settings.imgMaxWidth / settings.imgMaxHeight) > (imgw / imgh));
				//如果比例大于设置的，说明图片比较"长"imgMaxWidth/imgMaxHeight)>(imgw/imgh)
				if ((settings.imgMaxWidth / settings.imgMaxHeight) > (imgw / imgh)) {
					
					if (settings.imgMaxHeight > imgh) {
						//图片比例小于最大设定，则居中即可
						var margintop = (settings.imgMaxHeight - imgItem.height()) / 2;
						if (margintop < 0) {
							margintop = 0;
						}
						//img.style.marginTop = margintop + "px";
						imgItem.css("margin-top", margintop + "px");
						panel.css("text-align", "center");
					} else {
						imgItem.css("height", settings.imgMaxHeight + "px");
						panel.css("text-align", "center");
					}
				} else {
					//alert(imgh);
					//图片比较窄,需要缩放到最大宽度
					if (settings.imgMaxWidth > imgw) {
						var margintop = (settings.imgMaxHeight - imgh) / 2;
						if (margintop < 0) {
							margintop = 0;
						}
						imgItem.css("margin-top", margintop + "px");
						panel.css("text-align", "center");
					} else {
						imgItem.css("width", settings.imgMaxWidth + "px");
						var margintop = (settings.imgMaxHeight - (imgh*settings.imgMaxWidth/imgw)) / 2;
						imgItem.css("margin-top", margintop + "px");
					}
				}	
				
				// 显示原图
				//img.style.display = '';
				//img.setAttribute('src', src);
			});

		});
	};

	// IE7缩放图片会失真，采用私有属性通过三次插值解决
	isIE7 && (function (c,d,s) {s=d.createElement('style');d.getElementsByTagName('head')[0].appendChild(s);s.styleSheet&&(s.styleSheet.cssText+=c)||s.appendChild(d.createTextNode(c))})('img {-ms-interpolation-mode:bicubic}',document);

	/**
	 * 图片头数据加载就绪事件
	 * @see		http://www.planeart.cn/?p=1121
	 * @param	{String}	图片路径
	 * @param	{Function}	尺寸就绪 (参数1接收width; 参数2接收height)
	 * @param	{Function}	加载完毕 (可选. 参数1接收width; 参数2接收height)
	 * @param	{Function}	加载错误 (可选)
	 */
	var imgReady = (function () {
		var list = [], intervalId = null,

		// 用来执行队列
		tick = function () {
			var i = 0;
			for (; i < list.length; i++) {
				list[i].end ? list.splice(i--, 1) : list[i]();
			};
			!list.length && stop();
		},

		// 停止所有定时器队列
		stop = function () {
			clearInterval(intervalId);
			intervalId = null;
		};

		return function (url, ready, load, error) {
			var check, width, height, newWidth, newHeight,
				img = new Image();

			img.src = url;

			// 如果图片被缓存，则直接返回缓存数据
			if (img.complete) {
				ready(img.width, img.height);
				load && load(img.width, img.height);
				return;
			};

			// 检测图片大小的改变
			width = img.width;
			height = img.height;
			check = function () {
				newWidth = img.width;
				newHeight = img.height;
				if (newWidth !== width || newHeight !== height ||
					// 如果图片已经在其他地方加载可使用面积检测
					newWidth * newHeight > 1024
				) {
					ready(newWidth, newHeight);
					check.end = true;
				};
			};
			check();

			// 加载错误后的事件
			img.onerror = function () {
				error && error();
				check.end = true;
				img = img.onload = img.onerror = null;
			};

			// 完全加载完毕的事件
			img.onload = function () {
				load && load(img.width, img.height);
				!check.end && check();
				// IE gif动画会循环执行onload，置空onload即可
				img = img.onload = img.onerror = null;
			};

			// 加入队列中定期执行
			if (!check.end) {
				list.push(check);
				// 无论何时只允许出现一个定时器，减少浏览器性能损耗
				if (intervalId === null) intervalId = setInterval(tick, 40);
			};
		};
	})();

})(jQuery);