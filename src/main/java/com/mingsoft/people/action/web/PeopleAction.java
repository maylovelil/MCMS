/**
The MIT License (MIT) * Copyright (c) 2016 MY科技

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
package com.mingsoft.people.action.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.json.JsonObjectConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.base.constant.Const;
import com.mingsoft.base.entity.ResultJson;
import com.mingsoft.people.action.BaseAction;
import com.mingsoft.people.bean.PeopleLoginBean;
import com.mingsoft.people.biz.IPeopleBiz;
import com.mingsoft.people.biz.IPeopleUserBiz;
import com.mingsoft.people.constant.ModelCode;
import com.mingsoft.people.constant.e.CookieConstEnum;
import com.mingsoft.people.constant.e.PeopleEnum;
import com.mingsoft.people.constant.e.SessionConstEnum;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.people.entity.PeopleUserEntity;
import com.mingsoft.util.DateUtil;
import com.mingsoft.util.MD5Util;
import com.mingsoft.util.StringUtil;

import cn.hutool.http.HttpUtil;
import cn.hutool.log.Log;
import net.mingsoft.basic.util.BasicUtil;

/**
 * 
 * MY会员模块,前端调用（不需要用户登录进行的操作）
 * 
 * @author MY开发团队
 * @version 版本号：0.0<br/>
 *          创建日期：2017-8-23 10:10:22<br/>
 *          历史修订：<br/>
 */
@Controller("webPeople")
@RequestMapping("/")
public class PeopleAction extends BaseAction {

	/**
	 * 注入用户基础业务层
	 */
	@Autowired
	private IPeopleBiz peopleBiz;

	/**
	 * 注入用户基础业务层
	 */
	@Autowired
	private IPeopleUserBiz peopleUserBiz;

	/**
	 * 验证码验证<br/>
	 * 例如流程需要短信验证或邮箱验证，为有效防止恶意发送验证码。提供给ajax异步请求使用
	 * 注意：页面提交对验证码表单属性名称必须是rand_code，否则无效
	 * 
	 * @param rand_code
	 *            验证码
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息", <br/>
	 *            }
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.POST)
	@ResponseBody
	public void checkCode(HttpServletRequest request, HttpServletResponse response) {
		// 验证码验证 验证码不为null 或 验证码不相等
		if (!checkRandCode(request)) {
			this.outJson(response, ModelCode.PEOPLE, false,
					this.getResString("err.error", this.getResString("rand.code")));
			return;
		}
		this.outJson(response, ModelCode.PEOPLE, true);
	}

	/**
	 * 验证用户名、手机号、邮箱是否可用，同一时间只能判断一种，优先用户名称 ,只验证已绑定的用户,建议独立使用<br/>
	 * 
	 * @param people
	 *            用户信息<br/>
	 *            <i>people参数包含字段信息参考：</i><br/>
	 *            peopleMail 邮箱<br/>
	 *            peoplePhone 手机号<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true存在｜false不存在",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public void check(@ModelAttribute PeopleEntity people, HttpServletRequest request, HttpServletResponse response) {
		PeopleEntity _people = peopleBiz.getByPeople(people, this.getAppId(request));
		if (_people != null) {
			this.outJson(response, true);
		} else {
			this.outJson(response, false);
		}
	}

	/**
	 * 登录验证,登录必须存在验证码 <br/>
	 * 
	 * 
	 * @param rand_code
	 *            验证码
	 * 
	 * @param people
	 *            用户信息<br/>
	 *            <i>people参数包含字段信息参考：</i><br/>
	 *            peopleName 用名称<br/>
	 *            peoplePassword 密码 <br/>
	 *            peopleAutoLogin 自动登录 如果大于0表示开启自动登录，1表示自动登录保留1天<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息", <br/>
	 *            resultData:
	 *            "{"peopleAutoLogin":自动登录多少天,"peopleName":用户,"peopleId":用户编号,"peopleMail
	 *            ":用户邮箱} "<br/>
	 *            }
	 */
	@RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
	@ResponseBody
	public void checkLogin(@ModelAttribute PeopleEntity people, HttpServletRequest request,
			HttpServletResponse response) {
		// 验证码验证 验证码不为null 或 验证码不相等
		if (!this.checkRandCode(request)) {
			this.outJson(response, null, false, this.getResString("err.error", this.getResString("rand.code")));
			return;
		}
		// 用户名和密码不能为空
		if (StringUtil.isBlank(people.getPeopleName()) || StringUtil.isBlank(people.getPeoplePassword())) {
			this.outJson(response, ModelCode.PEOPLE_LOGIN, false,
					this.getResString("err.error", this.getResString("people")));
			return;
		}

		// 根据应用ID和用户名查询用户密码
		int appId = this.getAppId(request);
		PeopleEntity peopleEntity = this.peopleBiz.getEntityByUserName(people.getPeopleName(), appId);
		if (peopleEntity == null) {
			// 用户名或密码错误
			this.outJson(response, ModelCode.PEOPLE_LOGIN, false,
					this.getResString("err.error", this.getResString("pepple.no.exist")));
			return;
		}

		// 将用户输入的密码用MD5加密再和数据库中的进行比对
		String peoplePassWord = MD5Util.MD5Encode(people.getPeoplePassword(), Const.UTF8);
		if (peoplePassWord.equals(peopleEntity.getPeoplePassword())) {
			// 登录成功,压入用户session
			this.setPeopleBySession(request, peopleEntity);
			PeopleLoginBean tempPeople = new PeopleLoginBean();
			tempPeople.setPeopleId(peopleEntity.getPeopleId());
			tempPeople.setPeopleName(peopleEntity.getPeopleName());
			tempPeople.setPeopleMail(peopleEntity.getPeopleMail());
			tempPeople.setPeopleState(peopleEntity.getPeopleState());
			tempPeople.setCookie(request.getHeader("cookie"));
			// 判断用户是否点击了自动登录
			if (people.getPeopleAutoLogin() > 0) {
				tempPeople.setPeoplePassword(people.getPeoplePassword());
				this.setCookie(request, response, CookieConstEnum.PEOPLE_COOKIE, JSONObject.toJSONString(tempPeople),
						60 * 60 * 24 * people.getPeopleAutoLogin());
			}

			this.outJson(response, ModelCode.PEOPLE_LOGIN, true, JSONObject.toJSONString(tempPeople));

		} else {
			// 用户名或密码错误
			this.outJson(response, ModelCode.PEOPLE_LOGIN, false,
					this.getResString("err.error", this.getResString("pepple.no.exist")));
		}

	}

	/**
	 * 验证用户是否登录
	 * 
	 * <dt><span class="strong">返回</span></dt><br/>
	 * { result:"true｜false" }
	 */
	@RequestMapping(value = "/checkLoginStatus", method = RequestMethod.POST)
	@ResponseBody
	public void checkLoginStatus(HttpServletRequest request, HttpServletResponse response) {
		PeopleEntity people = this.getPeopleBySession(request);
		this.outJson(response, people == null ? false : true);
	}

	/**
	 * 验证重置密码过程中收到的验证码是否正确
	 * 
	 * @param rand_code
	 *            验证码，可能会传递经过多个流程，具体根据业务确定
	 * 
	 * @param people
	 *            用户信息<br/>
	 *            <i>people参数包含字段信息参考：</i><br/>
	 *            peopleCode 短信、邮箱验证码 <br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }
	 */
	@RequestMapping(value = "/checkResetPasswordCode", method = RequestMethod.POST)
	@ResponseBody
	public void checkResetPasswordCode(@ModelAttribute PeopleEntity people, HttpServletRequest request,
			HttpServletResponse response) {
		// 验证码验证 验证码不为null 或 验证码不相等
		if (StringUtil.isBlank(this.getRandCode(request)) || !this.checkRandCode(request)) {
			this.outJson(response, null, false, this.getResString("err.error", this.getResString("rand.code")));
			return;
		}

		PeopleEntity _people = (PeopleEntity) this.getSession(request, SessionConstEnum.PEOPLE_EXISTS_SESSION);
		if (_people == null) {
			// 用户不存在
			this.outJson(response, ModelCode.PEOPLE, false,
					this.getResString("err.not.exist", this.getResString("people")));
			return;

		}

		LOG.debug(_people.getPeoplePhoneCheck() + ":" + PeopleEnum.PHONE_CHECK.toInt());
		LOG.debug(_people.getPeopleCode() + ":" + people.getPeopleCode());
		// 判断用户验证是否通过\判断用户输入对邮箱验证码是否与系统发送对一致\判断验证码对有效时间
		if (_people.getPeoplePhoneCheck() == PeopleEnum.PHONE_CHECK.toInt()
				&& _people.getPeopleCode().equals(people.getPeopleCode())) {
			this.setSession(request, SessionConstEnum.PEOPLE_RESET_PASSWORD_SESSION, _people);
			this.outJson(response, ModelCode.PEOPLE, true,
					this.getResString("success", this.getResString("people.get.password")));
		} else if (_people.getPeopleMailCheck() == PeopleEnum.MAIL_CHECK.toInt()
				&& _people.getPeopleCode().equals(people.getPeopleCode())) {
			this.setSession(request, SessionConstEnum.PEOPLE_RESET_PASSWORD_SESSION, _people);
			this.outJson(response, ModelCode.PEOPLE, true,
					this.getResString("success", this.getResString("people.get.password")));
		} else {
			this.outJson(response, ModelCode.PEOPLE, false,
					this.getResString("fail", this.getResString("people.get.password")));
		}
	}

	/**
	 * 用户名、邮箱、手机号验证 ,用户重置密码必须使用该接口<br/>
	 * 适用场景:?<br/>
	 * 1、用户注册是对用户名、邮箱或手机号唯一性判断 2、用户取回密码是判断账号是否存在
	 * 
	 * @param people
	 *            用户信息<br/>
	 *            <i>people参数包含字段信息参考：</i><br/>
	 *            peopleName 用户名称验证<br/>
	 *            peopleMail 用户邮箱验证，注意：只验证绑定成功的邮箱 <br/>
	 *            peoplePhone 用户手机验证，注意：只验证绑定成功的手机 <br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"模块编码",<br/>
	 *            result:"true存在｜false不存在或错误",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }
	 * 
	 */
	@RequestMapping(value = "/isExists", method = RequestMethod.POST)
	@ResponseBody
	public void isExists(@ModelAttribute PeopleEntity people, HttpServletRequest request,
			HttpServletResponse response) {
		LOG.debug(JSONObject.toJSONString(people));
		if (StringUtil.isBlank(people.getPeopleName()) && StringUtil.isBlank(people.getPeoplePhone())
				&& StringUtil.isBlank(people.getPeopleMail())) {
			this.outJson(response, ModelCode.PEOPLE, false,
					this.getResString("err.empty", this.getResString("people.name")));
			return;
		}

		// 获取应用ID
		int appId = this.getAppId(request);
		people.setPeopleAppId(appId);
		// 如果接收到mail值，就给mail_check赋值1
		if (!StringUtil.isBlank(people.getPeopleMail())) {
			people.setPeopleMailCheck(PeopleEnum.MAIL_CHECK);
		}
		// 如果接收到phone值，就给phone_check赋值1
		if (!StringUtil.isBlank(people.getPeoplePhone())) {
			people.setPeoplePhoneCheck(PeopleEnum.PHONE_CHECK);
		}
		PeopleEntity _people = (PeopleEntity) this.peopleBiz.getEntity(people);
		if (_people != null) {
			this.setSession(request, SessionConstEnum.PEOPLE_EXISTS_SESSION, _people);
			this.outJson(response, ModelCode.PEOPLE, true);
			return;
		}
		this.outJson(response, ModelCode.PEOPLE, false);
	}

	/**
	 * 用户注册<br/>
	 * 用户可以更具用名称、手机号、邮箱进行注册<br/>
	 * 几种注册流程的形式：<br/>
	 * 1、普通用户名称、登录密码，优先用户名注册,登录密码最长度范围6～12个字符；<br/>
	 * 2、邮箱、邮箱验证码、登录密码；<br/>
	 * 3、手机号、短信验证码、登录密码；<br/>
	 * 注意： 1、注册页面必须存在验证码<br/>
	 * 2、如果需要接收验证码操作，需要使用发送验证码配合使用才能完成注册流程<br/>
	 * 
	 * @param rand_code
	 *            验证码
	 * 
	 * @param people
	 *            用户信息<br/>
	 *            <i>people参数包含字段信息参考：</i><br/>
	 *            peoplePhone 手机号<br/>
	 *            peopleName 用名称 用户名长度在3～12个字符之间，只能是字母数字混合<br/>
	 *            peopleMail 邮箱<br/>
	 *            peoplePassword 注册密码 <br/>
	 *            peopleCode 如果注册流程存在用户验证码，那么必须传递该参数 <br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",result:"true成功、false失败",resultMsg: "提示信息"}
	 * @see PeopleEntity
	 * @see PeopleEnum
	 * 
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public void register(@ModelAttribute PeopleUserEntity people, HttpServletRequest request,
			HttpServletResponse response) {
		LOG.debug("people register");
		// 验证码验证 验证码不为null 或 验证码不相等
		if (!checkRandCode(request)) {
			this.outJson(response, null, false, this.getResString("err.error", this.getResString("rand.code")));
			return;
		}

		// 判断用户信息是否为空
		if (people == null) {
			this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
					this.getResString("err.empty", this.getResString("people")));
			return;
		}

		int appId = this.getAppId(request);
		// 如果用户名不为空表示使用的是账号注册方式
		if (!StringUtil.isBlank(people.getPeopleName())) {
			// 验证用户名
			if (StringUtil.isBlank(people.getPeopleName())) {
				this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
						this.getResString("err.empty", this.getResString("people.name")));
				return;
			}

			if (people.getPeopleName().contains(" ")) {
				this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
						this.getResString("people.name") + this.getResString("people.space"));
				return;
			}

			if (!StringUtil.checkLength(people.getPeopleName(), 3, 30)) {
				this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
						this.getResString("err.length", this.getResString("people.name"), "3", "30"));
				return;
			}

			// 判断用户名是否已经被注册
			PeopleEntity peopleName = this.peopleBiz.getEntityByUserName(people.getPeopleName(), appId);
			if (peopleName != null) {
				this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
						this.getResString("err.exist", this.getResString("people.name") + peopleName.getPeopleName()));
				return;
			}
		}

		if (!StringUtil.isBlank(people.getPeoplePhone())) {// 验证手机号
			PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(people.getPeoplePhone(), appId);
			if (peoplePhone != null && peoplePhone.getPeoplePhoneCheck() == PeopleEnum.PHONE_CHECK.toInt()) { // 已存在
				this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
						this.getResString("err.exist", this.getResString("people.phone")));
				return;
			} else {
				Object obj = this.getSession(request, SessionConstEnum.SEND_CODE_SESSION);
				if (obj != null) {
					PeopleEntity _people = (PeopleEntity) obj;
					if (_people.getPeoplePhone().equals(people.getPeoplePhone())) {
						if (_people.getPeopleCode().equals(people.getPeopleCode())) {
							people.setPeoplePhoneCheck(PeopleEnum.PHONE_CHECK);
						} else {
							this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
									this.getResString("err.error", this.getResString("people.code")));
							return;
						}
					}
				}
			}
		}

		if (!StringUtil.isBlank(people.getPeopleMail())) {// 验证邮箱
			// 检查邮箱格式是否含有空格
			if (people.getPeopleMail().contains(" ")) {
				this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
						this.getResString("people.mail") + this.getResString("people.space"));
				return;
			}
			PeopleEntity peopleMail = this.peopleBiz.getEntityByUserName(people.getPeopleMail(), appId);
			if (peopleMail != null && peopleMail.getPeopleMailCheck() == PeopleEnum.MAIL_CHECK.toInt()) {
				this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
						this.getResString("err.exist", this.getResString("people.mail")));
				return;
			} else {
				Object obj = this.getSession(request, SessionConstEnum.SEND_CODE_SESSION);
				if (obj != null) {
					PeopleEntity _people = (PeopleEntity) obj;
					if (_people.getPeopleMail().equals(people.getPeopleMail())) {
						if (_people.getPeopleCode().equals(people.getPeopleCode())) {
							people.setPeopleMailCheck(PeopleEnum.MAIL_CHECK);
						} else {
							this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
									this.getResString("err.error", this.getResString("people.mail")));
							return;
						}
					}
				}
			}
		}

		// 密码
		if (StringUtil.isBlank(people.getPeoplePassword())) {
			this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
					this.getResString("err.empty", this.getResString("people.password")));
			return;
		}

		if (people.getPeoplePassword().contains(" ")) {
			this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
					this.getResString("people.password") + this.getResString("people.space"));
			return;
		}

		if (people.getPeoplePassword().length() < 6 || people.getPeoplePassword().length() > 30) {
			this.outJson(response, ModelCode.PEOPLE_REGISTER, false,
					this.getResString("err.length", this.getResString("people.password"), "6", "30"));
			return;
		}

		// 将密码使用MD5加密
		people.setPeoplePassword(MD5Util.MD5Encode(people.getPeoplePassword(), Const.UTF8));
		people.setPeopleAppId(appId);
		people.setPeopleDateTime(new Date());
		peopleUserBiz.savePeople(people);
		this.outJson(response, ModelCode.PEOPLE_REGISTER, true,
				this.getResString("success", this.getResString("people.register")));
		LOG.debug("people register ok");
	}

	/**
	 * 用户重置密码<br/>
	 * 当用户忘记登录密码时可以通过注册绑定的邮箱或绑定的手机号进行取回，操作过程中需要通过邮件模块与短信模块发送验证码给用户。<br/>
	 * 业务场景：用户输入手机号（邮箱），点击发送验证码,发送间隔时间为60秒,用户将接收到的验证码输入提交，
	 * 
	 * @param rand_code
	 *            验证码
	 * 
	 * @param people
	 *            用户信息<br/>
	 *            <i>people参数包含字段信息参考：</i><br/>
	 *            peoplePassword 用户新密码<br/>
	 *            peopleCode 验证码<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public void resetPassword(@ModelAttribute PeopleEntity people, HttpServletRequest request,
			HttpServletResponse response) {
		// 验证码验证 验证码不为null 或 验证码不相等
		if (StringUtil.isBlank(this.getRandCode(request)) || !this.checkRandCode(request)) {
			this.outJson(response, null, false, this.getResString("err.error", this.getResString("rand.code")));
			return;
		}

		// 验证新密码的长度
		if (!StringUtil.checkLength(people.getPeoplePassword(), 3, 12)) {
			this.outJson(response, ModelCode.PEOPLE, false,
					this.getResString("err.error", this.getResString("people.password")));
			return;
		}

		PeopleEntity _people = (PeopleEntity) this.getSession(request, SessionConstEnum.PEOPLE_RESET_PASSWORD_SESSION);

		if (_people == null) {
			// 用户不存在
			this.outJson(response, ModelCode.PEOPLE, false,
					this.getResString("err.not.exist", this.getResString("people")));
			return;

		}

		// 判断用户验证是否通过\判断用户输入对邮箱验证码是否与系统发送对一致\判断验证码对有效时间
		if (_people.getPeoplePhoneCheck() == PeopleEnum.PHONE_CHECK.toInt()
				&& _people.getPeopleCode().equals(people.getPeopleCode())) {
			_people.setPeoplePassword(MD5Util.MD5Encode(people.getPeoplePassword(), Const.UTF8));
			peopleBiz.updateEntity(_people);
			LOG.debug("更新密码成功");
			this.outJson(response, ModelCode.PEOPLE, true,
					this.getResString("success", this.getResString("people.get.password")));
		} else if (_people.getPeopleMailCheck() == PeopleEnum.MAIL_CHECK.toInt()
				&& _people.getPeopleCode().equals(people.getPeopleCode())) {
			_people.setPeoplePassword(MD5Util.MD5Encode(people.getPeoplePassword(), Const.UTF8));
			peopleBiz.updateEntity(_people);
			LOG.debug("更新密码成功");
			this.outJson(response, ModelCode.PEOPLE, true,
					this.getResString("success", this.getResString("people.get.password")));
		} else {
			LOG.debug("更新密码失败");
			this.outJson(response, ModelCode.PEOPLE, false,
					this.getResString("fail", this.getResString("people.get.password")));
		}
	}

	/**
	 * 自动登录
	 * 
	 * <dt><span class="strong">返回</span></dt><br/>
	 * {result:"true｜false"}<br/>
	 */
	@RequestMapping(value = "/autoLogin", method = RequestMethod.POST)
	@ResponseBody
	public void autoLogin(HttpServletRequest request, HttpServletResponse response) {

		// 获取页面上标记为PEOPLE_COOKIE的cookies值
		String cookie = this.getCookie(request, CookieConstEnum.PEOPLE_COOKIE);
		if (StringUtil.isBlank(cookie)) {
			this.outJson(response, false);
			return;
		}

		PeopleEntity people = JSONObject.parseObject(cookie, PeopleEntity.class);
		// 查找到cookies里用户名对应的用户实体
		PeopleEntity peopleEntity = this.peopleBiz.getByPeople(people, this.getAppId(request));
		if (peopleEntity != null) {
			// 登录成功,压入用户session
			setPeopleBySession(request, peopleEntity);
			this.outJson(response, true);
		} else {
			this.outJson(response, false);
		}

	}

	/**
	 * 用户发送验证码，可以通过邮箱或手机发送
	 * 
	 * @param receive
	 *            接收地址，只能是邮箱或手机号，邮箱需要使用邮箱插件，手机号需要短信插件
	 * @param modelCode
	 *            对应邮件插件的模块编号
	 * @param thrid
	 *            默认sendcloud
	 * @param rand_code
	 *            图片验证码 如果isSession=true rand_code为必填项
	 * @param isSession
	 *            true启用session保存code,false 关联用户信息，true一般是当用户手机还不存在系统中时使用，
	 * @param people
	 *            用户信息<br/>
	 *            <i>people参数包含字段信息参考：</i><br/>
	 *            peopleName 用户名<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {result:"true｜false"}<br/>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/sendCode")
	public void sendCode(@ModelAttribute PeopleEntity people, HttpServletRequest request,
			HttpServletResponse response) {
		String receive = request.getParameter("receive");
		String modelCode = request.getParameter("modelCode");
		String thrid = request.getParameter("thrid");
		String type = request.getParameter("type");
		boolean isSession = this.getBoolean(request, "isSession");

		if (StringUtil.isBlank(receive)) {
			this.outJson(response, ModelCode.PEOPLE, false, this.getResString("err.empty", this.getResString("receive")));
			return;
		}
		if (StringUtil.isBlank(modelCode)) {
			this.outJson(response, ModelCode.PEOPLE, false, this.getResString("err.empty", "modelCode"));
			return;
		}
		if (StringUtil.isBlank(type)) {
			this.outJson(response, ModelCode.PEOPLE, false, this.getResString("err.empty", this.getResString("type")));
			return;
		}
		String peopleCode = StringUtil.randomNumber(6);
		// 解密得到的模块编码
		String _modelCode = this.encryptByAES(request, modelCode);
		this.LOG.debug("前端传的"+modelCode);
		this.LOG.debug("解密"+_modelCode);
		Map params = new HashMap();
		params.put("modelCode", _modelCode);
		params.put("receive", receive);
		params.put("thrid", thrid); // 使用第三方平台发送，确保用户能收到
		params.put("content", "{code:'" + peopleCode + "'}");
		params.put("type",type);
		if (isSession) { // 启用session
			if (!this.checkRandCode(request)) {
				this.outJson(response, ModelCode.PEOPLE, false,
						this.getResString("err.error", this.getResString("rand.code")));
				return;
			}

			Object obj = this.getSession(request, SessionConstEnum.SEND_CODE_SESSION);
			if (obj != null) {
				PeopleEntity p = (PeopleEntity) obj;
				if (DateUtil.diffSec(new DateUtil(), new DateUtil(p.getPeopleCodeSendDate().getTime())) == 60) {
					this.outJson(response, ModelCode.PEOPLE, false, this.getResString("people.code.time.error"));
					return;

				}
			}

			PeopleEntity _people = new PeopleEntity();
			_people.setPeopleCode(peopleCode);
			_people.setPeopleCodeSendDate(DateUtil.dateToTimestamp(new Date()));
			this.setSession(request, SessionConstEnum.SEND_CODE_SESSION, _people);
			String contentt = HttpUtil.post(this.getUrl(request) + "/msend/send.do", params);
			ResultJson rs = JSONObject.parseObject(contentt, ResultJson.class);
			this.outJson(response, rs.getResultMsg());
			LOG.debug("send " + receive + ":content " + peopleCode);
			return;
		}
		// 给people赋值（邮箱或电话）
		if (StringUtil.isMobile(receive)) {
			people.setPeoplePhone(receive);
		} else {
			people.setPeopleMail(receive);
		}
		// 判断是否接到用户名，应用于找回密码发送验证码
		if (StringUtil.isBlank(people.getPeopleName()) && this.getPeopleBySession() == null) {
			// 如果接收到mail值，就给mail_check赋值1
			if (!StringUtil.isBlank(people.getPeopleMail())) {
				people.setPeopleMailCheck(PeopleEnum.MAIL_CHECK);
			}
			// 如果接收到phone值，就给phone_check赋值1
			if (!StringUtil.isBlank(people.getPeoplePhone())) {
				people.setPeoplePhoneCheck(PeopleEnum.PHONE_CHECK);
			}
		}

		// 获取应用ID
		int appId = this.getAppId(request);
		people.setPeopleAppId(appId);
		// 通过用户名地址和应用id得到用户实体
		PeopleEntity peopleEntity = (PeopleEntity) this.peopleBiz.getEntity(people);
		if (peopleEntity == null) {
			this.outJson(response, ModelCode.PEOPLE, false,
					this.getResString("err.not.exist", this.getResString("people")));
			return;
		}
		if (peopleEntity.getPeopleUser() != null) {
			CodeBean code = new CodeBean();
			code.setCode(peopleCode);
			code.setUserName(peopleEntity.getPeopleUser().getPuNickname());
			params.put("content", JSONObject.toJSONString(code));
		}

		// 将生成的验证码加入用户实体
		peopleEntity.setPeopleCode(peopleCode);

		// 将当前时间转换为时间戳格式保存进people表
		peopleEntity.setPeopleCodeSendDate(DateUtil.dateToTimestamp(new Date()));
		// 更新该实体
		this.peopleBiz.updateEntity(peopleEntity);

		PeopleEntity _people = (PeopleEntity) this.getSession(request, SessionConstEnum.PEOPLE_EXISTS_SESSION);
		if (_people != null) {
			this.setSession(request, SessionConstEnum.PEOPLE_EXISTS_SESSION, peopleEntity);
		}
		String content = HttpUtil.post(this.getUrl(request) + "/msend/send.do", params);
		LOG.debug("content :" + content);
		ResultJson rs = JSONObject.parseObject(content, ResultJson.class);
		if(rs != null) {
			this.outJson(response, true);
		} 
		
		LOG.debug("send " + receive + ":content " + peopleCode);
	}

	/**
	 * 验证用户输入的接收验证码
	 * 
	 * @param code
	 *            接收到的验证码
	 * @param receive
	 *            接收地址，只能是邮箱或手机号
	 * @param people
	 *            用户信息<br/>
	 *            <i>people参数包含字段信息参考：</i><br/>
	 *            peopleName 用户名<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {result:"true｜false"}<br/>
	 */
	@RequestMapping(value = "/checkSendCode", method = RequestMethod.POST)
	public void checkSendCode(@ModelAttribute PeopleEntity people, HttpServletRequest request,
			HttpServletResponse response) {
		String code = request.getParameter("code");
		String receive = request.getParameter("receive");
		// 验证码
		if (StringUtil.isBlank(code)) {
			this.outJson(response, ModelCode.PEOPLE, false,
					this.getResString("err.error", this.getResString("people.code")));
			return;
		}
		if (StringUtil.isMobile(receive)) {
			people.setPeoplePhone(receive);
		}
		if (StringUtil.isEmail(receive)) {
			people.setPeopleMail(receive);
		}
		// 获取应用ID
		int appId = this.getAppId(request);
		people.setPeopleAppId(appId);
		// 根据邮箱地址查找用户实体
		PeopleEntity peopleEntity = (PeopleEntity) this.peopleBiz.getEntity(people);

		// 在注册流程，在发送验证码的时数据库可能还不存在用户信息
		if (BasicUtil.getSession(SessionConstEnum.SEND_CODE_SESSION) != null) {
			peopleEntity = (PeopleEntity) BasicUtil.getSession(SessionConstEnum.SEND_CODE_SESSION);
			// 判断用户输入的随机码是否正确
			if (!peopleEntity.getPeopleCode().equals(code)) {
				this.outJson(response, ModelCode.PEOPLE, false,
						this.getResString("err.error", this.getResString("people.code")));
				return;
			} else {
				this.outJson(response, ModelCode.PEOPLE, true);
			}
		} else {
			if (StringUtil.isMobile(receive)) {
				// 如果用户已经绑定过手机直接返回错误
				if (peopleEntity.getPeoplePhoneCheck() == PeopleEnum.PHONE_CHECK.toInt()) {
					this.outJson(response, ModelCode.PEOPLE, false);
					return;
				}
			} else {
				// 如果用户已经绑定过邮箱直接返回错误
				if (peopleEntity.getPeopleMailCheck() == PeopleEnum.MAIL_CHECK.toInt()) {
					this.outJson(response, ModelCode.PEOPLE, false);
					return;
				}
			}

			// 得到发送验证码时间，并转换为String类型
			String date = peopleEntity.getPeopleCodeSendDate().toString();

			// 如果发送时间和当前时间只差大于30分钟，则返回false
			if (DateUtil.secondBetween(date) > 60 * 60 * 24) {
				this.outJson(response, ModelCode.PEOPLE, false, this.getResString("people.msg.code.error"));
				return;
			}

			// 判断用户输入的随机码是否正确
			if (!peopleEntity.getPeopleCode().equals(code)) {
				this.outJson(response, ModelCode.PEOPLE, false,
						this.getResString("err.error", this.getResString("people.code")));
				return;
			}

			// 将随机码在数据库中清空
			peopleEntity.setPeopleCode("");
			if (StringUtil.isMobile(receive)) {
				peopleEntity.setPeoplePhoneCheck(PeopleEnum.PHONE_CHECK);
			} else {
				peopleEntity.setPeopleMailCheck(PeopleEnum.MAIL_CHECK);
			}
			peopleBiz.updateEntity(peopleEntity);
			this.outJson(response, ModelCode.PEOPLE, true);

		}

	}

	/**
	 * 解绑邮箱-> 验证用户输入的接收验证码
	 * 
	 * @param code
	 *            接收到的验证码
	 * @param receive
	 *            接收地址，只能是邮箱或手机号
	 * @param people
	 *            用户信息<br/>
	 *            <i>people参数包含字段信息参考：</i><br/>
	 *            peopleName 用户名<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {result:"true｜false"}<br/>
	 */
	@RequestMapping(value = "/cancelBind", method = RequestMethod.POST)
	public void cancelBind(@ModelAttribute PeopleEntity people, HttpServletRequest request,
			HttpServletResponse response) {
		String code = request.getParameter("code");
		String receive = request.getParameter("receive");
		// 验证码
		if (StringUtil.isBlank(code)) {
			this.outJson(response, ModelCode.PEOPLE, false,
					this.getResString("err.error", this.getResString("people.code")));
			return;
		}
		if (StringUtil.isMobile(receive)) {
			people.setPeoplePhone(receive);
		}
		if (StringUtil.isEmail(receive)) {
			people.setPeopleMail(receive);
		}
		// 获取应用ID
		int appId = this.getAppId(request);
		people.setPeopleAppId(appId);
		// 根据用户名和邮箱或手机号查找用户实体
		PeopleEntity peopleEntity = (PeopleEntity) this.peopleBiz.getEntity(people);

		// 在注册流程，在发送验证码的时数据库可能还不存在用户信息
		if (BasicUtil.getSession(SessionConstEnum.SEND_CODE_SESSION) != null) {
			peopleEntity = (PeopleEntity) BasicUtil.getSession(SessionConstEnum.SEND_CODE_SESSION);
			// 判断用户输入的随机码是否正确
			if (!peopleEntity.getPeopleCode().equals(code)) {
				this.outJson(response, ModelCode.PEOPLE, false,
						this.getResString("err.error", this.getResString("people.code")));
				return;
			} else {
				this.outJson(response, ModelCode.PEOPLE, true);
			}
		} else {
			if (StringUtil.isMobile(receive)) {
				// 如果用户未绑定过手机直接返回错误
				if (peopleEntity.getPeoplePhoneCheck() == PeopleEnum.PHONE_NO_CHECK.toInt()) {
					this.outJson(response, ModelCode.PEOPLE, false);
					return;
				}
			} else {
				// 如果用户未绑定过邮箱直接返回错误
				if (peopleEntity.getPeopleMailCheck() == PeopleEnum.MAIL_NO_CHECK.toInt()) {
					this.outJson(response, ModelCode.PEOPLE, false);
					return;
				}
			}

			// 得到发送验证码时间，并转换为String类型
			String date = peopleEntity.getPeopleCodeSendDate().toString();

			// 如果发送时间和当前时间只差大于30分钟，则返回false
			if (DateUtil.secondBetween(date) > 60 * 60 * 24) {
				this.outJson(response, ModelCode.PEOPLE, false, this.getResString("people.msg.code.error"));
				return;
			}

			// 判断用户输入的随机码是否正确
			if (!peopleEntity.getPeopleCode().equals(code)) {
				this.outJson(response, ModelCode.PEOPLE, false,
						this.getResString("err.error", this.getResString("people.code")));
				return;
			}

			// 将随机码在数据库中清空
			peopleEntity.setPeopleCode("");
			if (StringUtil.isMobile(receive)) {
				peopleEntity.setPeoplePhoneCheck(PeopleEnum.PHONE_NO_CHECK);
			} else {
				peopleEntity.setPeopleMailCheck(PeopleEnum.MAIL_NO_CHECK);
			}
			peopleBiz.updateEntity(peopleEntity);
			this.outJson(response, ModelCode.PEOPLE, true);

		}

	}

}

// 创建一个bean方便邮件发送，避免userName特色字符导致json格式转换失败
class CodeBean {
	String code;
	String userName;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
