package com.mingsoft.basic.servlet;


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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.mingsoft.util.FileUtil;
import com.mingsoft.util.StringUtil;

import net.mingsoft.basic.util.BasicUtil;

/**
 * 文件上传通用servlet
 * 
 * @author 王天培QQ:78750478
 * @version 版本号：100-000-000<br/>
 *          创建日期：2012-03-15<br/>
 *          历史修订：<br/>
 */
@WebServlet(urlPatterns = "/upload")
public class UploadServlet extends BaseServlet {

	private static final int BUFFER_SIZE = 100 * 1024;
	private static final String TEMP = "/temp";

	/**
	 * 处理post请求上传文件
	 * 
	 * @param req
	 *            HttpServletRequest对象
	 * @param res
	 *            HttpServletResponse 对象
	 * @throws ServletException
	 *             异常处理
	 * @throws IOException
	 *             异常处理
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html;charset=utf-8");
		Integer chunk = 0, chunks = 0; // 分块上传
		PrintWriter out = res.getWriter();
		String uploadFolder = this.getServletContext().getRealPath("/"); // 上传的文件路径
		String isRename = req.getParameter("isRename");// 是否重命名 true:重命名
		String _tempPath = req.getServletContext().getRealPath(TEMP);// 存放文件的临时目录路径
		FileUtil.createFolder(_tempPath);
		File tempPath = new File(_tempPath); // 用于存放临时文件的目录

		int maxSize = 1000000; // 允许上传文件大小,最大上传文件，单位：字节 1000000/1024=0.9M
		// String allowedFile = ".jpg,.gif,.png,.zip"; // 允许上传文件
		String deniedFile = ".exe,.com,.cgi,.asp", allowedFile; // 不允许上传文件

		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		// 允许设置内存中存储数据的门限，单位：字节
		factory.setSizeThreshold(4096);
		// the location for saving data that is larger than getSizeThreshold()
		// 如果文件大小大于SizeThreshold，则保存到临时目录
		factory.setRepository(tempPath);

		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum size before a FileUploadException will be thrown
		String uploadPath = "";
		try {
			List fileItems = upload.parseRequest(req);

			Iterator iter = fileItems.iterator();

			// 正则匹配，过滤路径取文件名
			String regExp = ".+\\\\(.+)$";

			// 过滤掉的文件类型
			String[] errorType = deniedFile.split(",");
			String fileName = "";
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				InputStream input = item.getInputStream();
				String fieldName = item.getFieldName();
				if (fieldName.equals("uploadPath")) {
					uploadPath = Streams.asString(input)+File.separator;
					uploadFolder += uploadPath;
					logger.info("uploadPath:" + uploadFolder);
				} else if (fieldName.equals("isRename")) {
					isRename =Streams.asString(input);
					logger.info("isRename:" + isRename);
				} else if (fieldName.equals("maxSize")) {
					maxSize = Integer.parseInt(item.getString()) * 1048576;
					logger.info("maxSize:" + maxSize);
				} else if (fieldName.equals("allowedFile")) {
					allowedFile = item.getString();
					logger.info("allowedFile:" + allowedFile);

				} else if (fieldName.equals("deniedFile")) {
					logger.info("deniedFile:" + deniedFile);
					deniedFile = item.getString();
				} else if ("chunk".equals(fieldName)) {
					chunk = Integer.valueOf(Streams.asString(input));
					logger.info("chunk:" + chunk);
				} else if ("chunks".equals(fieldName)) {
					chunks = Integer.valueOf(Streams.asString(input));
					logger.info("chunks:" + chunks);
				} else if ("name".equals(fieldName)) {
					fileName = new String(item.get(),"UTF-8");
					logger.info("name:" + fileName);
				} else if (!item.isFormField()) { // 忽略其他不是文件域的所有表单信息
					if(StringUtil.isBlank(fileName)) {
						fileName = item.getName();
					}
					long size = item.getSize();
					if ((fileName == null || fileName.equals("")) && size == 0)
						continue;
					try {
						// 最大上传文件，单位：字节 1000000/1024=0.9M
						upload.setSizeMax(maxSize);

						// 保存上传的文件到指定的目录
						// 在下文中上传文件至数据库时，将对这里改写
						String folder = uploadFolder + File.separator + TEMP;
						FileUtil.createFolder(folder);

						File destFile = new File(folder, fileName);
						// //文件已存在删除旧文件（上传了同名的文件）
						if (chunk == 0 && destFile.exists()) {
							destFile.delete();
							destFile = new File(folder, fileName);
						}
						// 合成文件
						appendFile(input, destFile);
						if (chunk == chunks - 1) {
							String _fileName = fileName;
							// // 重命名
							if (StringUtil.isBlank(isRename) || Boolean.parseBoolean(isRename)) {
								_fileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("."));
								destFile.renameTo(new File(uploadFolder,_fileName));
							} else {
								// savePath += fileName;
								// outPath += fileName;
								destFile.renameTo(new File(uploadFolder, _fileName));
							}
							logger.info("上传完成");
							out.print(uploadPath+File.separator+_fileName);
							new File(folder).delete();
						} else if (chunks == 0) {
							String _fileName = fileName;
							if (StringUtil.isBlank(isRename) || Boolean.parseBoolean(isRename)) {
								_fileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("."));
							}		
							destFile.renameTo(new File(uploadFolder, _fileName));
							new File(folder).delete();
							out.print(uploadPath+File.separator+_fileName);
							logger.info("上传完成");
						} else {
							logger.info("还剩[" + (chunks - 1 - chunk) + "]个块文件");

						}

						logger.debug("upload file ok return path " + uploadFolder + fileName);
						
						try {
							if (null != input) {
								input.close();
							}
							if (null != out) {
								out.flush();
								out.close();
							}
						} catch (IOException e) {
							logger.error(e.getMessage());
						}
					} catch (Exception e) {
						this.logger.debug(e);
					}

				}
			}
		} catch (FileUploadException e) {
			this.logger.debug(e);
		}
	}

	private void appendFile(InputStream in, File destFile) {
		OutputStream out = null;
		try {
			// plupload 配置了chunk的时候新上传的文件append到文件末尾
			if (destFile.exists()) {
				out = new BufferedOutputStream(new FileOutputStream(destFile, true), BUFFER_SIZE);
			} else {
				out = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE);
			}
			in = new BufferedInputStream(in, BUFFER_SIZE);

			int len = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	// /**
	// * 处理get请求上传文件
	// * @param request HttpServletRequest对象
	// * @param response HttpServletResponse 对象
	// * @throws ServletException Servlet异常处理
	// * @throws IOException IO异常处理
	// */
	// @Override
	// protected void doGet(HttpServletRequest request, HttpServletResponse
	// response)
	// throws ServletException, IOException {
	// String uploadPath = request.getParameter("uploadPath"); // 上传的文件路径
	// String fileSize = request.getParameter("fileSize"); // 上传的文件大小
	// String fileType = request.getParameter("fileType"); // 上传的文件类型
	// String deniedFileType = request.getParameter("deniedFileType"); //
	// 不允许上传的文件类型，
	// }
}