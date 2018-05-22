package net.mingsoft.mdiy.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.bind.annotation.RequestMethod;

import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.basic.parser.BasicParser;
import com.mingsoft.basic.parser.IGeneralParser;
import com.mingsoft.mdiy.entity.PageEntity;
import com.mingsoft.parser.IParserRegexConstant;
import com.mingsoft.util.FileUtil;
import com.mingsoft.util.StringUtil;

import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.basic.util.SpringUtil;

public class MdiyUtil {


	/**
	 * 解析页面，只能解析简单标签
	 * @param key 关键字
	 * @param param 需要替换的值
	 * @return 返回解析好的界面
	 */
	public static String parserDiyPage(String key,Map param) {
		com.mingsoft.mdiy.biz.IPageBiz pageBiz = (com.mingsoft.mdiy.biz.IPageBiz) SpringUtil
				.getBean(SpringUtil.getRequest().getServletContext(), "pageBiz");
		PageEntity page = new PageEntity();
		page.setPageAppId(BasicUtil.getAppId());
		page.setPageKey(key);
		page = (PageEntity)pageBiz.getEntity(page);
		if(StringUtil.isBlank(page.getPagePath())) {
			return "";
		}
		
		AppEntity app = BasicUtil.getApp();
		String templatePath = page.getPagePath();
		String path = BasicUtil.getRealPath(IParserRegexConstant.REGEX_SAVE_TEMPLATE) + File.separator + app.getAppId()
				+ File.separator + app.getAppStyle() + File.separator;
		String content = "";
		if (BasicUtil.isMobileDevice() && !StringUtil.isBlank(app.getAppMobileStyle())) { // 移动端
			String htmlContent = FileUtil.readFile(path + app.getAppMobileStyle() + File.separator + templatePath); // 读取模版文件内容
			Map map = new HashMap();
			map.put(IGeneralParser.MOBILE, app.getAppMobileStyle());
			content = BasicParser.getInstance().parse(htmlContent, app, map);
		} else {
			String htmlContent = FileUtil.readFile(path + templatePath);
			content = BasicParser.getInstance().parse(htmlContent, app);
		}
		
		Iterator keys = param.keySet().iterator();
		// 将get或post提交过来的参数映射到界面上去
		while (keys.hasNext()) {
			String _key = keys.next().toString();
			if (!StringUtil.isBlank(param.get(_key))){
				content = content.replace("{" + _key + "/}", "");
				continue;
			}
			content = content.replace("{" + _key + "/}", param.get(_key)+"");
		}
		return content;
		
	}
}
