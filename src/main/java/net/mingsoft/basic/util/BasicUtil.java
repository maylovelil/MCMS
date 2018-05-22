
package net.mingsoft.basic.util;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mingsoft.base.constant.e.BaseCookieEnum;
import com.mingsoft.base.constant.e.BaseEnum;
import com.mingsoft.base.constant.e.BaseSessionEnum;
import com.mingsoft.basic.biz.IAppBiz;
import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.basic.entity.ModelEntity;
import com.mingsoft.basic.interceptor.BaseInterceptor;
import com.mingsoft.util.StringUtil;

import net.mingsoft.base.util.BaseUtil;

public class BasicUtil extends BaseUtil{
	/** Wap网关Via头信息中特有的描述信息 */
	private static String mobileGateWayHeaders[] = new String[] { "ZXWAP", // 中兴提供的wap网关的via信息，例如：Via=ZXWAP
																			// GateWayZTE
																			// Technologies，
			"chinamobile.com", // 中国移动的诺基亚wap网关，例如：Via=WTP/1.1
								// GDSZ-PB-GW003-WAP07.gd.chinamobile.com (Nokia
								// WAP Gateway 4.1 CD1/ECD13_D/4.1.04)
			"monternet.com", // 移动梦网的网关，例如：Via=WTP/1.1
								// BJBJ-PS-WAP1-GW08.bj1.monternet.com. (Nokia
								// WAP
								// Gateway 4.1 CD1/ECD13_E/4.1.05)
			"infoX", // 华为提供的wap网关，例如：Via=HTTP/1.1 GDGZ-PS-GW011-WAP2
						// (infoX-WISG
						// Huawei Technologies)，或Via=infoX WAP Gateway V300R001
						// Huawei Technologies
			"XMS 724Solutions HTG", // 国外电信运营商的wap网关，不知道是哪一家
			"wap.lizongbo.com", // 自己测试时模拟的头信息
			"Bytemobile",// 貌似是一个给移动互联网提供解决方案提高网络运行效率的，例如：Via=1.1 Bytemobile OSN
							// WebProxy/5.1
	};
	/** 电脑上的IE或Firefox浏览器等的User-Agent关键词 */
	private static String[] pcHeaders = new String[] { "Windows 98", "Windows ME", "Windows 2000", "Windows XP",
			"Windows NT", "Ubuntu" };
	/** 手机浏览器的User-Agent里的关键词 */
	private static String[] mobileUserAgents = new String[] { "Nokia", // 诺基亚，有山寨机也写这个的，总还算是手机，Mozilla/5.0
																		// (Nokia5800
																		// XpressMusic)UC
																		// AppleWebkit(like
																		// Gecko)
																		// Safari/530
			"SAMSUNG", // 三星手机
						// SAMSUNG-GT-B7722/1.0+SHP/VPP/R5+Dolfin/1.5+Nextreaming+SMM-MMS/1.2.0+profile/MIDP-2.1+configuration/CLDC-1.1
			"MIDP-2", // j2me2.0，Mozilla/5.0 (SymbianOS/9.3; U; Series60/3.2
						// NokiaE75-1 /110.48.125 Profile/MIDP-2.1
						// Configuration/CLDC-1.1 ) AppleWebKit/413 (KHTML like
						// Gecko) Safari/413
			"CLDC1.1", // M600/MIDP2.0/CLDC1.1/Screen-240X320
			"SymbianOS", // 塞班系统的，
			"MAUI", // MTK山寨机默认ua
			"UNTRUSTED/1.0", // 疑似山寨机的ua，基本可以确定还是手机
			"Windows CE", // Windows CE，Mozilla/4.0 (compatible; MSIE 6.0;
							// Windows CE; IEMobile 7.11)
			"iPhone", // iPhone是否也转wap？不管它，先区分出来再说。Mozilla/5.0 (iPhone; U; CPU
						// iPhone OS 4_1 like Mac OS X; zh-cn) AppleWebKit/532.9
						// (KHTML like Gecko) Mobile/8B117
			"iPad", // iPad的ua，Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X;
					// zh-cn) AppleWebKit/531.21.10 (KHTML like Gecko)
					// Version/4.0.4 Mobile/7B367 Safari/531.21.10
			"Android", // Android是否也转wap？Mozilla/5.0 (Linux; U; Android
						// 2.1-update1; zh-cn; XT800 Build/TITA_M2_16.22.7)
						// AppleWebKit/530.17 (KHTML like Gecko) Version/4.0
						// Mobile Safari/530.17
			"BlackBerry", // BlackBerry8310/2.7.0.106-4.5.0.182
			"UCWEB", // ucweb是否只给wap页面？ Nokia5800
						// XpressMusic/UCWEB7.5.0.66/50/999
			"ucweb", // 小写的ucweb貌似是uc的代理服务器Mozilla/6.0 (compatible; MSIE 6.0;)
						// Opera ucweb-squid
			"BREW", // 很奇怪的ua，例如：REW-Applet/0x20068888 (BREW/3.1.5.20; DeviceId:
					// 40105; Lang: zhcn) ucweb-squid
			"J2ME", // 很奇怪的ua，只有J2ME四个字母
			"YULONG", // 宇龙手机，YULONG-CoolpadN68/10.14 IPANEL/2.0 CTC/1.0
			"YuLong", // 还是宇龙
			"COOLPAD", // 宇龙酷派YL-COOLPADS100/08.10.S100 POLARIS/2.9 CTC/1.0
			"TIANYU", // 天语手机TIANYU-KTOUCH/V209/MIDP2.0/CLDC1.1/Screen-240X320
			"TY-", // 天语，TY-F6229/701116_6215_V0230 JUPITOR/2.2 CTC/1.0
			"K-Touch", // 还是天语K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223
						// Release/30.07.2008 Browser/WAP2.0
			"Haier", // 海尔手机，Haier-HG-M217_CMCC/3.0 Release/12.1.2007
						// Browser/WAP2.0
			"DOPOD", // 多普达手机
			"Lenovo", // 联想手机，Lenovo-P650WG/S100 LMP/LML Release/2010.02.22
						// Profile/MIDP2.0 Configuration/CLDC1.1
			"LENOVO", // 联想手机，比如：LENOVO-P780/176A
			"HUAQIN", // 华勤手机
			"AIGO-", // 爱国者居然也出过手机，AIGO-800C/2.04 TMSS-BROWSER/1.0.0 CTC/1.0
			"CTC/1.0", // 含义不明
			"CTC/2.0", // 含义不明
			"CMCC", // 移动定制手机，K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223
					// Release/30.07.2008 Browser/WAP2.0
			"DAXIAN", // 大显手机DAXIAN X180 UP.Browser/6.2.3.2(GUI) MMP/2.0
			"MOT-", // 摩托罗拉，MOT-MOTOROKRE6/1.0 LinuxOS/2.4.20 Release/8.4.2006
					// Browser/Opera8.00 Profile/MIDP2.0 Configuration/CLDC1.1
					// Software/R533_G_11.10.54R
			"SonyEricsson", // 索爱手机，SonyEricssonP990i/R100 Mozilla/4.0
							// (compatible; MSIE 6.0; Symbian OS; 405) Opera
							// 8.65 [zh-CN]
			"GIONEE", // 金立手机
			"HTC", // HTC手机
			"ZTE", // 中兴手机，ZTE-A211/P109A2V1.0.0/WAP2.0 Profile
			"HUAWEI", // 华为手机，
			"webOS", // palm手机，Mozilla/5.0 (webOS/1.4.5; U; zh-CN)
						// AppleWebKit/532.2 (KHTML like Gecko) Version/1.0
						// Safari/532.2 Pre/1.0
			"GoBrowser", // 3g GoBrowser.User-Agent=Nokia5230/GoBrowser/2.0.290
							// Safari
			"IEMobile", // Windows CE手机自带浏览器，
			"WAP2.0"// 支持wap 2.0的
	};

	/**
	 * 根据模块编码获得模块编号
	 * 
	 * @param code
	 *            编码
	 * @return 返回模块编号，如果没有返回0
	 */
	public static int getModelCodeId(BaseEnum code) {
		com.mingsoft.basic.biz.IModelBiz modelBiz = (com.mingsoft.basic.biz.IModelBiz) SpringUtil
				.getBean(SpringUtil.getRequest().getServletContext(), "modelBiz");
		ModelEntity model = modelBiz.getEntityByModelCode(code);
		if (model != null) {
			return model.getModelId();
		}
		return 0;
	}
	
	/**
	 * 根据模块编码获得模块编号
	 * 
	 * @param code
	 *            编码
	 * @return 返回模块编号，如果没有返回0
	 */
	public static int getModelCodeId(String code) {
		com.mingsoft.basic.biz.IModelBiz modelBiz = (com.mingsoft.basic.biz.IModelBiz) SpringUtil
				.getBean(SpringUtil.getRequest().getServletContext(), "modelBiz");
		ModelEntity model = modelBiz.getEntityByModelCode(code);
		if (model != null) {
			return model.getModelId();
		}
		return 0;
	}

	
	/**
	 * 获取当前请求的mcode编码 ，根据后台的菜单设置决定
	 * @return 不存在返回0
	 */
	public static int getModelCodeId() {
		String modelCode = BasicUtil.getString("modelCode","");
		com.mingsoft.basic.biz.IModelBiz modelBiz = (com.mingsoft.basic.biz.IModelBiz) SpringUtil
				.getBean(SpringUtil.getRequest().getServletContext(), "modelBiz");
		ModelEntity model = modelBiz.getEntityByModelCode(modelCode);
		if (model != null) {
			return model.getModelId();
		}
		return 0;
	}
	
	/**
	 * 获取当前请求的mcode编码 ，根据后台的菜单设置决定
	 * @return 不存在返回0
	 */
	public static int getModeld() {
		return BasicUtil.getInt("modelId", 0);
	}

	/**
	 * 获取当前模块对应的appid , appid主要根据用户的请求地址获得
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return 返回appId，找不到对应app,返回0
	 */
	public static int getAppId() {
		AppEntity app = BasicUtil.getApp();
		if (app != null) {
			return app.getAppId();
		}
		return 0;
	}

	/**
	 * 获取当前模块对应的appid , appid主要根据用户的请求地址获得
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return 返回appId，找不到对应app,返回0
	 */
	public static AppEntity getApp() {

		// 获取用户所请求的域名地址
		IAppBiz websiteBiz = (IAppBiz) SpringUtil.getBean(IAppBiz.class);
		AppEntity website = websiteBiz.getByUrl(BasicUtil.getDomain());
		return website;
	}

	/**
	 * 获取项目路径
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return 返回项目路径，例如：http://www.ip.com/projectName 后面没有反斜杠，后面接地址或参数必须加/
	 */
	public static String getUrl() {
		HttpServletRequest request = SpringUtil.getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName();
		if (request.getServerPort() == 80) {
			basePath += path;
		} else {
			basePath += ":" + request.getServerPort() + path;
		}
		return basePath;
	}
	
	/**
	 * 获取请求客户端ip
	 * 
	 * @return ip地址
	 */
	public static String getIp() {
		HttpServletRequest request = SpringUtil.getRequest();
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * 获取请求域名，域名不包括http请求协议头
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return 返回域名地址
	 */
	public static String getDomain() {
		String path = SpringUtil.getRequest().getContextPath();
		String domain = SpringUtil.getRequest().getServerName();
		if (SpringUtil.getRequest().getServerPort() == 80) {
			domain += path;
		} else {
			domain += ":" + SpringUtil.getRequest().getServerPort() + path;
		}
		return domain;
	}

	/**
	 * 默认页码参数
	 */
	@Deprecated
	private final static String PAGE_NO = "pageNo";
	
	/**
	 * 
	 */
	private final static String PAGE_NUMBER = "pageNumber";
	


	/**
	 * 默认一页显示数量
	 */
	private final static String PAGE_SIZE = "pageSize";
	private final static String PAGE = "page";

	/**
	 * 分页开始方法，必须配合BasicUtil.endPage()一起使用
	 */
	public static void startPage() {
		int pageNo = BasicUtil.getInt(PAGE_NO, 1);
		int pageNumber = BasicUtil.getInt(PAGE_NUMBER, 1);
		int _pageNo = pageNo;
		if(pageNumber>pageNo) {
			_pageNo = pageNumber;
		}
		PageHelper.startPage(_pageNo, BasicUtil.getInt(PAGE_SIZE, 10));
	}
	
	/**
	 * 每页显示数量
	 * @return 默认每页10条
	 */
	public static int getPageSzie() {
		return BasicUtil.getInt(PAGE_SIZE, 10);
	}
	/**
	 * 当前页码
	 * @return 默认1
	 */
	public static int getPageNo() {
		return BasicUtil.getInt(PAGE_NO, 1);
	}
	/**
	 * 分页开始方法，必须配合BasicUtil.endPage()一起使用
	 * @param count  是否统计总数 如果不需要分页使用false
	 */
	public static void startPage(boolean count) {
		BasicUtil.startPage(BasicUtil.getInt(PAGE_NO, 1), BasicUtil.getInt(PAGE_SIZE, 10),count);
	}
	
	/**
	 * 分页开始方法，必须配合BasicUtil.endPage()一起使用
	 * @param count  是否统计总数 如果不需要分页使用false
	 * @param pageNo  当前页码
	 * @param pageSize 一页显示数量
	 */
	public static void startPage(int pageNo,int pageSize,boolean count) {
		PageHelper.startPage(BasicUtil.getInt(PAGE_NO, pageNo), BasicUtil.getInt(PAGE_SIZE, pageSize),count);
	}
	
	/**
	 * 列表排序
	 * @param orderBy 排序字段，多个之间用,隔开
	 * @param order 默认DESC
	 */
	public static void orderBy(String orderBy,String order) {
		if (!order.equalsIgnoreCase("DESC") && !order.equalsIgnoreCase("ASC")) {
			order = "DESC";
		}
		PageHelper.orderBy(orderBy+" " + order);
	}

	@SuppressWarnings("rawtypes")
	public static PageInfo endPage(List list, String name) {
		@SuppressWarnings("unchecked")
		PageInfo page = new PageInfo(list);
		SpringUtil.getRequest().setAttribute(BaseInterceptor.PARAMS,
				BasicUtil.assemblyRequestUrlParams(new String[] { PAGE_NO }));
		SpringUtil.getRequest().setAttribute(name, page);
		return page;
	}

	@SuppressWarnings("rawtypes")
	public static PageInfo endPage(List list) {
		return BasicUtil.endPage(list, PAGE);
	}

	/**
	 * 获取整型值
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param param
	 *            参数名称
	 * @param def
	 *            默认值，如果参数不存在或不符合规则就用默认值替代
	 * @return 返回整型值
	 */
	public static Integer getInt(String param, int def) {
		String value = SpringUtil.getRequest().getParameter(param);
		if (StringUtil.isInteger(value)) {
			return Integer.parseInt(value);
		} else {
			return def;
		}
	}

	public static Integer getInt(String param) {
		return BasicUtil.getInt(param, 0);
	}

	/**
	 * 获取字符串值
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param param
	 *            参数名称
	 * @param def
	 *            默认值，如果参数不存在或不符合规则就用默认值替代
	 * @return 返回整型值
	 */
	public static String getString(String param, String def) {
		String value = SpringUtil.getRequest().getParameter(param);
		if (StringUtil.isBlank(value)) {
			value = def;
		}
		return value;
	}

	/**
	 * 获取字符串值
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param param
	 *            参数名称
	 * @return 返回整型值
	 */
	public static String getString(String param) {
		return BasicUtil.getString(param, "");
	}

	/**
	 * 获取整型值数组
	 * 
	 * @param param
	 *            参数名称
	 * @return 不存在返回null
	 */
	public static int[] getInts(String param) {
		String[] value = SpringUtil.getRequest().getParameterValues(param);
		if (StringUtil.isIntegers(value)) {
			return StringUtil.stringsToInts(value);
		} else {
			return null;
		}
	}
	
	/**
	 * 获取整型值数组
	 * 
	 * @param param
	 *            参数名称，如果param参数的值本身就是数组，将会忽略split参数
	 * @param split 分割符号
	 * @return 不存在返回null
	 */
	public static int[] getInts(String param,String split) {
		if (BasicUtil.getInts(param)!=null) {
			return BasicUtil.getInts(param);
		}
		String value = SpringUtil.getRequest().getParameter(param);
		if (StringUtil.isBlank(value)) {
			return null;
		}
 		if (StringUtil.isIntegers(value.split(split))) {
			return StringUtil.stringsToInts(value.split(split));
		} else {
			return null;
		}
	}

	private final static String IDS = "ids";

	public static int[] getIds() {
		return BasicUtil.getInts(IDS);
	}

	/**
	 * 将请求的request的参数重新组装。主要是将空值的替换成null,因为requestMap空值是"",这样处理有利于外部判断,
	 * 同时将获取到的值映射到页面上
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return 返回处理过后的数据
	 */
	public static Map<String, Object> assemblyRequestMap() {
		HttpServletRequest request = SpringUtil.getRequest();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String[]> map = request.getParameterMap();
		Iterator<String> key = map.keySet().iterator();
		while (key.hasNext()) {
			String k = (String) key.next().replace(".", "_");
			String[] value = map.get(k);

			if (value == null) {
				params.put(k, null);
				request.setAttribute(k, null);
			} else if (value.length == 1) {
				String temp = null;
				if (!StringUtil.isBlank(value[0])) {
					temp = value[0];
				}
				params.put(k, temp);
				request.setAttribute(k, temp);
			} else if (value.length == 0) {
				params.put(k, null);
				request.setAttribute(k, null);
			} else if (value.length > 1) {
				params.put(k, value);
				request.setAttribute(k, value);
			}
		}
		return params;
	}

	/**
	 * 将请求的request的参数重新组装。主要是将空值的替换成null,因为requestMap空值是"",这样处理有利于外部判断,
	 * 同时将获取到的值映射到页面上
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return 返回处理过后的数据
	 */
	public static String assemblyRequestUrlParams() {
		return assemblyRequestUrlParams(null);
	}

	/**
	 * 将请求的request的参数重新组装。主要是将空值的替换成null,因为requestMap空值是"",这样处理有利于外部判断,
	 * 同时将获取到的值映射到页面上
	 * 
	 * @param filter
	 *            需要过滤的字段
	 * @return 返回处理过后的数据
	 */

	public static String assemblyRequestUrlParams(String[] filter) {
		Map<String, String[]> map = SpringUtil.getRequest().getParameterMap();
		Iterator<String> key = map.keySet().iterator();
		StringBuffer sb = new StringBuffer();

		while (key.hasNext()) {

			String k = (String) key.next();

			if (filter != null && Arrays.asList(filter).contains(k)) {
				continue;
			}

			String[] value = map.get(k);
			if (value.length == 1) {
				String temp = "";
				if (!StringUtil.isBlank(value[0])) {
					temp = value[0];
				}
				sb.append(k).append("=").append(temp).append("&");
			} else if (value.length > 1) {
				sb.append(k).append("=").append(value).append("&");
			}
		}
		if (sb.length() > 0) {

			return sb.substring(0, sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 移除url参数
	 * 
	 * @param fitlers
	 *            需要移除的字段名称
	 */
	public static void removeUrlParams(String[] fitlers) {
		SpringUtil.getRequest().setAttribute(BaseInterceptor.PARAMS, BasicUtil.assemblyRequestUrlParams(fitlers));
	}

	/**
	 * 获取session的值
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param key
	 *            枚举类中的值
	 * @return session中获取的对象
	 */
	public static Object getSession(BaseSessionEnum key) {
		return SpringUtil.getRequest().getSession().getAttribute(key.toString());
	}

	/**
	 * 设置session的值
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param key
	 *            枚举类中的值
	 */
	public static void setSession(BaseSessionEnum key, Object value) {
		SpringUtil.getRequest().getSession().setAttribute(key.toString(), value);
	}

	/**
	 * 移除session的值
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param key
	 *            枚举类中的值
	 */
	public static void removeSession(BaseSessionEnum key) {
		SpringUtil.getRequest().getSession().removeAttribute(key.toString());
	}

	
	/**
	 * 获取filePath的物理路径
	 * @param filePath 文件
	 * @return 物理路径
	 */
	public static String getRealPath(String filePath) {
		HttpServletRequest request = SpringUtil.getRequest();
		
		String path = request.getServletContext().getRealPath("/");//该方法存在平台因素，存在有时后面有斜杠有时会没有
		String last = path.charAt(path.length()-1)+""; //取最后一个字符串，判断是否存在斜杠
		String frist = filePath.charAt(0)+""; //取第一个字符串，判断用户传递过来的参数是否有斜杠
		if(last.equals(File.separator)) { 
			if(frist.equals("\\") || frist.equals("/")) {//当前平台可以获取到最后的斜杠，那么就判断一下用户传递的参数是否有斜杠，有就截取掉
				path=path+filePath.substring(1);
			} else {
				path=path+filePath; //当前平台可以获取到最后的斜杠，用户传递的参数没有斜杠，就直接返回
			}
		} else {
			if(frist.equals("\\") || frist.equals("/")) {//当前平台没有斜杠，用户传递的参数有斜杠，有就放回
				path=path+filePath;
			} else {
				path=path+File.separator+filePath; //平台也米有斜杠，参数也没有斜杠，增加拼接放回
			}
		}
		return path;
	}

	/**
	 * 枚举类型转map类型
	 * 
	 * @param baseEnum 枚举类class.getEnumConstants()
	 * @return map集合，根据id,value
	 */
	public static Map enumToMap(BaseEnum[] baseEnum) {
		return BasicUtil.enumToMap(baseEnum, true);
	}

	/**
	 * 枚举类型转map类型
	 * 
	 * @param baseEnum 枚举类class.getEnumConstants()
	 * @param idKey true的id为主键，false的name为主键
	 * @return map集合
	 */
	public static Map enumToMap(BaseEnum[] baseEnum, boolean idKey) {
		Map map = new HashMap();
		for (BaseEnum be : baseEnum) {
			if (idKey) {
				map.put(be.toInt() + "", be.toString());
			} else {
				Enum e = (Enum) be;
				map.put(e.name(), be.toString());
			}
		}
		return map;
	}
	
	/**
	 * 资源文件转map类型
	 * @param rb资源文件包路径
	 * @return map集合
	 */
	public static Map resToMap(String resPath) {
		return BasicUtil.resToMap(ResourceBundle.getBundle(resPath));
	}
	
	/**
	 * 资源文件转map类型
	 * @param rb资源文件
	 * @return map集合
	 */
	@SuppressWarnings("rawtypes")
	public static Map resToMap(ResourceBundle rb) {
		Map map = new HashMap();
		Enumeration e = rb.getKeys();
		while(e.hasMoreElements()) {
			String key = e.nextElement().toString();
			map.put(key, rb.getString(key));
		}
		return map;
	}
	
	/**
	 * 获取Cookie的值
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param key
	 *            枚举类中的值
	 * @return Cookie中获取的对象
	 */
	public static String getCookie(BaseCookieEnum key) {
		HttpServletRequest request = SpringUtil.getRequest();
		if (request.getCookies() != null) {
			for (Cookie c : request.getCookies()) {
				if (c.getName().equals(key.toString())) {
					return c.getValue();
				}
			}
		}
		return null;
	}
	

	/**
	 * 设置Cookie值，浏览器关闭cookie会被删除
	 * 
	 * @param response
	 *            HttpServletResponse对象
	 * @param key
	 *            枚举类中的值
	 * @param value
	 *            存储对象
	 */
	public static void setCookie(HttpServletResponse response, BaseCookieEnum key,
			Object value) {
		BasicUtil.setCookie(response, key, value, -1);
	}
	

	/**
	 * 设置Cookie值
	 * 
	 * @param response
	 *            HttpServletResponse对象
	 * @param key
	 *            枚举类中的值
	 * @param value
	 *            存储对象
	 * @param expiry
	 *            过期时间，单位秒
	 */
	public static void setCookie(HttpServletResponse response, BaseCookieEnum key,
			Object value,int expiry) {
		Cookie cookie = new Cookie(key.toString(), (String) value);
		cookie.setPath("/");
		cookie.setValue((String) value);
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}
	
	/**
	 * 根据当前请求的特征，判断该请求是否来自手机终端，主要检测特殊的头信息，以及user-Agent这个header
	 * 
	 * @param request
	 *            HttpServletRequest对象,http请求
	 * @return 如果命中手机特征规则，则返回对应的特征字符串
	 */
	public static boolean isMobileDevice() {
		HttpServletRequest request = SpringUtil.getRequest();
		boolean b = false;
		boolean pcFlag = false;
		boolean mobileFlag = false;
		String via = request.getHeader("Via");
		String userAgent = request.getHeader("user-agent");
		for (int i = 0; via != null && !via.trim().equals("") && i < mobileGateWayHeaders.length; i++) {
			if (via.contains(mobileGateWayHeaders[i])) {
				mobileFlag = true;
				break;
			}
		}
		for (int i = 0; !mobileFlag && userAgent != null && !userAgent.trim().equals("")
				&& i < mobileUserAgents.length; i++) {
			if (userAgent.contains(mobileUserAgents[i])) {
				mobileFlag = true;
				break;
			}
		}
		for (int i = 0; userAgent != null && !userAgent.trim().equals("") && i < pcHeaders.length; i++) {
			if (userAgent.contains(pcHeaders[i])) {
				pcFlag = true;
				break;
			}
		}
		if (mobileFlag == true && pcFlag == false) {
			b = true;
		}
		return b;// false pc true shouji

	}

}
