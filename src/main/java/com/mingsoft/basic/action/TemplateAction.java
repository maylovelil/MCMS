/**
The MIT License (MIT) * Copyright (c) 2016 MY科技(mingsoft.net)

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mingsoft.basic.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingsoft.basic.action.BaseAction;
import com.mingsoft.basic.biz.IAppBiz;
import com.mingsoft.basic.constant.Const;
import com.mingsoft.basic.constant.ModelCode;
import com.mingsoft.basic.constant.e.CookieConstEnum;
import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.basic.entity.ManagerSessionEntity;
import com.mingsoft.parser.IParserRegexConstant;
import com.mingsoft.util.FileUtil;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;

import net.mingsoft.basic.util.BasicUtil;

/**
 * 
 * 
 * <p>
 * <b>MYCMS-MY内容管理系统</b>
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇MY科技有限公司
 * </p>
 * 
 * @author 姓名：张敏
 * 
 * @version 300-001-001
 * 
 *          <p>
 *          版权所有 MY科技
 *          </p>
 * 
 *          <p>
 *          Comments:获取有关模版文件夹或模版文件信息
 *          </p>
 * 
 *          <p>
 *          Create Date:2014-8-12
 *          </p>
 * 
 *          <p>
 *          Modification history:
 *          </p>
 */
@Controller("/a")
@RequestMapping("/${managerPath}/template")
public class TemplateAction extends BaseAction {

	/**
	 * 站点业务层
	 */
	@Autowired
	private IAppBiz appBiz;

	/**
	 * 模版列表分页路径
	 */
	private final static String SKINLIST_PAGE_URL = "/manager/template/queryTemplateSkin.do";

	/**
	 * 查询模版风格供站点选择
	 * 
	 * @param model
	 * @param request
	 *            请求
	 * @return 模版文件集合
	 */
	@RequestMapping("/queryAppTemplateSkin")
	@ResponseBody
	public Map queryAppTemplateSkin(HttpServletRequest request) {
		List<String> folderNameList = (List<String>) this.queryTemplateFile(request);
		Map map = new HashMap();
		if (!StringUtil.isBlank(folderNameList)) {
			map.put("fileName", folderNameList);
		}
		return map;
	}

	/**
	 * 查询模版文件供栏目选择
	 * 
	 * @param request
	 *            请求
	 * @return 返回列表集合
	 */
	@RequestMapping("/queryTemplateFileForColumn")
	@ResponseBody
	public List<String> queryTemplateFileForColumn(HttpServletRequest request) {
		ManagerSessionEntity managerSession = getManagerBySession(request);
		String path = this.getRealPath(request, IParserRegexConstant.REGEX_SAVE_TEMPLATE) + File.separator
				+ managerSession.getBasicId();
		int websiteId = managerSession.getBasicId();
		AppEntity website = (AppEntity) appBiz.getEntity(websiteId);
		path = path + File.separator + website.getAppStyle();
		LOG.debug("tempPath:" + path);
		List<String> listName = new ArrayList<String>();
		files(listName, new File(path), website.getAppStyle());
		return listName;
	}

	private void files(List list, File fileDir, String style) {
		if (fileDir.isDirectory()) {
			File files[] = fileDir.listFiles();
			for (int i = 0; i < files.length; i++) {
				File currFile = files[i];
				if (currFile.isFile()) {
					String ex = currFile.getName();
					if (ex.endsWith("htm") || ex.endsWith("html")) {
						String _pathName = new String();
						_pathName = files(currFile, style, _pathName);
						list.add(_pathName + currFile.getName());
					}
				} else if (currFile.isDirectory()
						&& !currFile.getName().equalsIgnoreCase(IParserRegexConstant.MOBILE)) {
					files(list, currFile, style);
				}
			}
		}
	}

	private String files(File file, String style, String pathName) {
		if (!file.getParentFile().getName().equals(style)) {
			pathName = file.getParentFile().getName() + "/" + pathName;
			pathName = files(file.getParentFile(), style, pathName);
		}
		return pathName;
	}

	/**
	 * 点击模版管理，获取所有的模版文件名
	 * 
	 * @param response
	 *            响应
	 * @param model
	 * @param request
	 *            请求
	 * @return 返回模版文件名集合
	 */
	@RequestMapping("/queryTemplateSkin")
	protected String queryTemplateSkin(HttpServletResponse response, ModelMap model, HttpServletRequest request) {
		String pageNo = request.getParameter("pageNo");
		if (!StringUtil.isInteger(pageNo)) {
			pageNo = "1";
		}
		ManagerSessionEntity managerSession = getManagerBySession(request);
		List<String> folderNameList = this.queryTemplateFile(request);
		model.addAttribute("folderNameList", folderNameList);
		model.addAttribute("websiteId", managerSession.getBasicId());
		int recordCount = 0;
		if (!StringUtil.isBlank(folderNameList)) {
			recordCount = folderNameList.size();
		}
		PageUtil page = new PageUtil(StringUtil.string2Int(pageNo), recordCount, getUrl(request) + SKINLIST_PAGE_URL);
		this.setCookie(request, response, CookieConstEnum.PAGENO_COOKIE, pageNo);
		model.addAttribute("page", page);
		return view("/template/template_list");
	}

	/**
	 * 解压zip模版文件
	 * 
	 * @param fileUrl
	 *            文件路径
	 * @throws ZipException
	 * @throws IOException
	 */
	@RequestMapping("/unZip")
	@ResponseBody
	@RequiresPermissions("template:upload")
	public String unZip(ModelMap model, HttpServletRequest request) throws  IOException {
		boolean hasDic = false;
		String entryName = "";
		String fileUrl = request.getParameter("fileUrl");
		// 创建文件对象
		File file = new File(this.getRealPath(request, fileUrl));
		// 创建zip文件对象
		ZipFile zipFile = new ZipFile(file);
		// 创建本zip文件解压目录
		File unzipFile = new File(
				this.getRealPath(request, fileUrl.substring(0, fileUrl.length() - file.getName().length())));
		// 得到zip文件条目枚举对象
		Enumeration<? extends ZipEntry> zipEnum = zipFile.getEntries();
		// 定义输入输出流对象
		// 循环读取条目
		while (zipEnum.hasMoreElements()) {
			// 得到当前条目
			ZipEntry entry = (ZipEntry) zipEnum.nextElement();
			entryName = new String(entry.getName().getBytes("utf-8"));
			File f = new File(unzipFile.getAbsolutePath() + File.separator + entryName);
			if(f.getName().charAt(0)=='.') {
				continue;
			}
			if(!hasDic) {
				new File(unzipFile.getAbsolutePath() + File.separator + entryName).getParentFile().mkdirs();
				hasDic = true;
			}
			// 若当前条目为目录则创建
			if (entry.isDirectory()) {
				new File(unzipFile.getAbsolutePath() + File.separator + entryName).mkdirs();
			} else {
				// 若当前条目为文件则解压到相应目录
				InputStream input = zipFile.getInputStream(entry);
				OutputStream output = new FileOutputStream(
						new File(unzipFile.getAbsolutePath() + File.separator + entryName));
				byte[] buffer = new byte[1024 * 8];
				int readLen = 0;
				while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
					output.write(buffer, 0, readLen);
				}
				output.flush();
				output.close();
				input.close();
				input = null;
				output = null;
			}
		}
		zipFile.close();
		return entryName;
	}

	/**
	 * 删除模版
	 * 
	 * @param name
	 *            模版名称
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("template:del")
	public boolean delete(HttpServletRequest request) {
		String fileName = request.getParameter("fileName");
		String path = this.getRealPath(request, IParserRegexConstant.REGEX_SAVE_TEMPLATE + File.separator
				+ this.getAppId(request) + File.separator + fileName);
		try {
			FileUtil.delFolders(path);
			FileUtil.delFile(path);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	/**
	 * 获取模版路径
	 * 
	 * @param request
	 *            请求
	 * @param fileNameUrl
	 *            文件路径
	 * @return 返回路径
	 */
	private String getTemplateUrl(HttpServletRequest request, String fileNameUrl) {
		ManagerSessionEntity managerSession = getManagerBySession(request);
		String templetsUrl = this.getRealPath(request, IParserRegexConstant.REGEX_SAVE_TEMPLATE + File.separator
				+ managerSession.getBasicId() + File.separator + fileNameUrl);
		return templetsUrl;
	}

	/**
	 * 显示子文件和子文件夹
	 * 
	 * @param response
	 *            响应
	 * @param model
	 * @param request
	 *            请求
	 * @return 返回文件名集合
	 */
	@RequestMapping("/showChildFileAndFolder")
	public String showChildFileAndFolder(HttpServletResponse response, ModelMap model, HttpServletRequest request) {
		ManagerSessionEntity managerSession = getManagerBySession(request);
		List<String> folderNameList = null;
		String skinFolderName = request.getParameter("skinFolderName");
		File files[] = new File(this.getRealPath(request, skinFolderName)).listFiles();
		if (!StringUtil.isBlank(files)) {
			folderNameList = new ArrayList<String>();
			List<String> fileNameList = new ArrayList<String>();
			for (int i = 0; i < files.length; i++) {
				File currFile = files[i];
				String filter = BasicUtil.getRealPath(
						IParserRegexConstant.REGEX_SAVE_TEMPLATE + File.separator + this.getAppId(request));
				LOG.debug("过滤路径" + filter);
				String temp = currFile.getPath().replace(filter, "");
				if (currFile.isDirectory()) {
					folderNameList.add(temp);
				} else {
					fileNameList.add(temp);
				}
			}
			folderNameList.addAll(fileNameList);
			model.addAttribute("fileNameList", folderNameList);
		}
		String uploadFileUrl = skinFolderName;
		model.addAttribute("uploadFileUrl", uploadFileUrl);
		model.addAttribute("websiteId", managerSession.getBasicId());
		return view("/template/template_file_list");
	}

	/**
	 * 读取模版文件内容
	 * 
	 * @param model
	 * @param request
	 *            请求
	 * @return 返回文件内容
	 */
	@RequestMapping("/readFileContent")
	@RequiresPermissions("template:update")
	public String readFileContent(ModelMap model, HttpServletRequest request) {
		String fileName = request.getParameter("fileName");
		if (!StringUtil.isBlank(fileName)) {

			model.addAttribute("fileContent", FileUtil.readFile(this.getRealPath(request, fileName)));
		}
		model.addAttribute("name", new File(this.getRealPath(request, fileName)).getName());
		model.addAttribute("fileName", fileName);
		model.addAttribute("fileNamePrefix", fileName.substring(0, fileName.lastIndexOf(File.separator) + 1));
		return view("/template/template_edit_file");
	}

	/**
	 * 删除模版文件
	 * 
	 * @param styleName
	 *            文件名称
	 * @param request
	 *            请求
	 */
	@RequestMapping("/deleteTemplateFile")
	@ResponseBody
	public int deleteTemplateFile(HttpServletRequest request) {
		int pageNo = 1;
		ManagerSessionEntity managerSession = getManagerBySession(request);
		String fileName = request.getParameter("fileName");
		FileUtil.delFile(this.getRealPath(request, IParserRegexConstant.REGEX_SAVE_TEMPLATE + File.separator
				+ managerSession.getBasicId() + File.separator + fileName));
		// 判断当前页码
		this.getHistoryPageNoByCookie(request);
		return pageNo;
	}

	/**
	 * 写入模版文件内容
	 * 
	 * @param model
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @throws IOException
	 */
	@RequestMapping("/writeFileContent")
	public void writeFileContent(ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String fileName = request.getParameter("fileName");
		String oldFileName = request.getParameter("oldFileName");
		String fileContent = request.getParameter("fileContent");
		ManagerSessionEntity managerSession = getManagerBySession(request);
		if (!StringUtil.isBlank(fileName)) {
			// 文件路径
			String templets = this.getRealPath(request, fileName);
			LOG.debug(templets);
			FileUtil.writeFile(fileContent, templets, "utf-8");
			if (!fileName.equals(oldFileName)) {
				// 得到一个待命名文件对象
				File newName = new File(templets);
				// 获取新名称文件的文件对象
				File oldName = new File(this.getRealPath(request, oldFileName));
				// 进行重命名
				oldName.renameTo(newName);
				FileUtil.delFile(this.getRealPath(request, oldFileName));
			}
			this.outJson(response, ModelCode.ROLE, true, null);
		}
	}

	/**
	 * 查询模版文件集合
	 * 
	 * @param request
	 *            请求
	 * @return 模版文件集合
	 */
	private List<String> queryTemplateFile(HttpServletRequest request) {
		ManagerSessionEntity managerSession = getManagerBySession(request);
		List<String> folderNameList = null;
		if (!isSystemManager(request)) {
			String templets = this.getRealPath(request, IParserRegexConstant.REGEX_SAVE_TEMPLATE + File.separator
					+ managerSession.getBasicId() + File.separator);
			File file = new File(templets);
			String[] str = file.list();
			if (!StringUtil.isBlank(str)) {
				folderNameList = new ArrayList<String>();
				for (int i = 0; i < str.length; i++) {
					// 避免不为文件夹的文件显示
					if (str[i].indexOf(".") < 0) {
						folderNameList.add(str[i]);
					}
				}
			}
		}
		return folderNameList;
	}

}