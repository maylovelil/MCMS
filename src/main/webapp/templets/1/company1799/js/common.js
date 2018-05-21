function showNav(){
	$('.ulNav>li').hover(function(){
		$(this).addClass('hover');
		$(this).find('dl').slideDown("fast");
		
	},function(){
		$(this).removeClass('hover');
		$(this).find('dl').slideUp("fast");
	});
}

$(document).ready(function(e) {
    if($(".ulNav").size() >0){
		showNav();
	}
});
function collectionHover(){
	$(".collection-ul li a").hover(function(){
		$(".collection-ul li").removeClass("hover");
		$(this).parent("li").addClass("hover");
		$("#collection4").hasClass("hover")?$("#collection4 a img").prop("src","images/collection_4hover.png"):$("#collection4 a img").prop("src","images/collection_4.png");
	},function(){
		//$(this).parent("li").removeClass("hover");
	});
}
//刷新验证码
function ChangeValidateCode()
{
	$('#validatecode').attr( 'src','/images/common/validatecode.aspx?rnd=' + Math.random());
}

//加入收藏
function addFavorite(url, title) {
	try {
		window.external.addFavorite(url, title);
	} catch (e){
		try {
			window.sidebar.addPanel(title, url, '');
        	} catch (e) {
			alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请使用Ctrl+D进行添加");
		}
	}
}
//设为首页
function SetHome(obj,url){
	try{
		obj.style.behavior='url(#default#homepage)';
		obj.setHomePage(url);
	}catch(e){
		if(window.netscape){
			try{
				netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
			}catch(e){
				alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
			}
		}else{
			alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将【"+url+"】设置为首页。");
		}
	}
}

//头部搜索
function TopSearch(){
	var key = $("#skey").val();
	if(key == "" || key ==" GO SEARCH "){
		alert('Please enter a key!');
		$("#skey").focus();
	} else {
		window.location.href='/Search.aspx?key='+escape(key);
	}
}

//搜索
function GetSearch()
{
	var key=$("#key").val();
	if(key =="" || key =="站内搜索")
	{
		alert("请输入关键字！");
		$("#key").focus();
		return false;
	}
	else
	{
		window.location.href='/Search.aspx?key='+escape(key);
		return false;
	}
}
var lockSearch=false;
var loadinghtml='<div class="loadinghtml" style=" padding:20px 0px 10px 0px;"><p><img src="/Images/common/ajax-loader.gif" width="66" height="66" /><p><p>Loading...</p></div>';
//快速筛选商品
function QuickSeach(){
	var keyword =$("#key").val();
	var Page= $("#page").val();
	
	if(!lockSearch)
	{
		lockSearch=true;
		$("#commonList").html(loadinghtml);
		$.ajax({type:'POST',
			   url:'/ajax/server.aspx?action=searchatc&rnd='+Math.random(),
			   data:'key='+escape(keyword)+'&page='+escape(Page),
			   success:function(data){
				   $("#commonList").html(data);
				   lockSearch=false;
			   },
			   error:function(){
				   $("#commonList").html("搜索错误，请联系管理员！");
				   lockSearch=false;
			   }
			   })
	}
}
//跳转到页面
function GoToQuickSearchPage(page)
{
	$("#Page").val(page)
	QuickSeach();
}

function getNowStr(){
	var now =new Date();
	var m = now.getMonth()+1;
	var week=now.getDay();
	if (m < 10) {
		strm = "0" + m;
	} else {
		strm = m;
	}
	var date = now.getDate();
	if (date < 10) {
		strd = "0" + date;
	} else {
		strd = date;
	}
	switch(week){
		case 0:
			strweek = "日";
			break;
		case 1:
			strweek = "一";
			break;
		case 2:
			strweek = "二";
			break;
		case 3:
			strweek = "三";
			break;
		case 4:
			strweek = "四";
			break;
		case 5:
			strweek = "五";
			break;
		case 6:
			strweek = "六";
			break;
		case 7:
			strweek = "日";
			break;
	}
	h = now.getHours();

	tempMinutes = now.getMinutes();
	if (tempMinutes < 10) {
		minutes = "0" + tempMinutes;
	}
	else {
		minutes = tempMinutes;
	}
	
	strnow = now.getFullYear()+'年'+strm+'月'+strd+'日 星期'+strweek + ' '+h+':'+minutes;
	
	document.write(strnow);
	
}

/*********
验证是否是数字
**********/
function checkIsInteger(str) {
    //如果为空，则通过校验
    if (str == "")
        return true;
    if (/^(\-?)(\d+)$/.test(str))
        return true;
    else
        return false;
}
function Env(){
	var ua=navigator.userAgent.toLowerCase();
	function check(r){
		return r.test(ua);
	}
	return {
	  //判断环境，操作系统、浏览器、是否是https连接等
		DOC : document,
        isStrict : DOC.compatMode == "CSS1Compat",
        isOpera : check(/opera/),
        isChrome : check(/\bchrome\b/),
        isWebKit : check(/webkit/),
        isSafari : !isChrome && check(/safari/),
        isSafari2 : isSafari && check(/applewebkit\/4/), // unique to Safari 2
        isSafari3 : isSafari && check(/version\/3/),
        isSafari4 : isSafari && check(/version\/4/),
        isIE : !isOpera && check(/msie/),
        isIE7 : isIE && check(/msie 7/),
        isIE8 : isIE && check(/msie 8/),
        isIE6 : isIE && !isIE7 && !isIE8,
        isGecko : !isWebKit && check(/gecko/),
        isGecko2 : isGecko && check(/rv:1\.8/),
        isGecko3 : isGecko && check(/rv:1\.9/),
        isBorderBox : isIE && !isStrict,
        isWindows : check(/windows|win32/),
        isMac : check(/macintosh|mac os x/),
        isAir : check(/adobeair/),
        isLinux : check(/linux/),
        isSecure : /^https/i.test(window.location.protocol),
       /**
         * 是否为空，如果允许allowBlank=true，则当v=''时返回true
         */
        isEmpty : function(v, allowBlank){
            return v === null || v === undefined || ((this.isArray(v) && !v.length)) || (!allowBlank ? v === '' : false);
        },

        /**
         * 是否为数组类型
         */
        isArray : function(v){
            return toString.apply(v) === '[object Array]';
        },

        /**
         * 是否为日期类型
         */
        isDate : function(v){
            return toString.apply(v) === '[object Date]';
        },

        /**
         * 是否为Object类型
         */
        isObject : function(v){
            return !!v && Object.prototype.toString.call(v) === '[object Object]';
        },

        /**
         * 判断是否是函数
         */
        isFunction : function(v){
            return toString.apply(v) === '[object Function]';
        },

        /**
         * 判断是否为数字
         */
        isNumber : function(v){
            return typeof v === 'number' && isFinite(v);
        },

        /**
         * 判断字符串类型
         */
        isString : function(v){
            return typeof v === 'string';
        },

        /**
         * 判断布尔类型
         */
        isBoolean : function(v){
            return typeof v === 'boolean';
        },

        /**
         * 判断是否为dom元素
         */
        isElement : function(v) {
            return !!v && v.tagName;
        },

        /**
         * 判断是否已定义
         */
        isDefined : function(v){
            return typeof v !== 'undefined';
        }
	}
}

$(document).ready(function(){
  
  $('li.mainlevel').mousemove(function(){
  $(this).find('ul').slideDown();//you can give it a speed
  });
  $('li.mainlevel').mouseleave(function(){
  $(this).find('ul').slideUp("fast");
  });
  
});