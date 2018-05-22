package com.mingsoft.mdiy.action.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mingsoft.basic.parser.BaseParser;
import com.mingsoft.mdiy.action.BaseAction;

@Controller("webDiyPath")
@RequestMapping(value={"/","/mdiy/page"})
public class PageAction extends BaseAction {

	@Autowired
	private BaseParser parser;

	/**
	 * 自定义页码
	 * 
	 * @param key
	 */
	@RequestMapping(value = "/{diy}")
	public void diy(@PathVariable(value = "diy") String diy, HttpServletRequest req, HttpServletResponse resp) {
		String content = this.generaterPage(diy, parser, req);
		this.outString(resp, content);
	}
}
