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
 */package com.mingsoft.people.action.people;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.people.action.BaseAction;
import com.mingsoft.people.biz.IPeopleAddressBiz;
import com.mingsoft.people.constant.ModelCode;
import com.mingsoft.people.constant.e.PeopleAddressEnum;
import com.mingsoft.people.entity.PeopleAddressEntity;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.util.StringUtil;

import net.mingsoft.basic.util.BasicUtil;

/**
 * 
 * 普通用户收货地址信息控制层(外部请求接口)
 * @author MY开发团队
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-23 10:10:22<br/>
 * 历史修订：<br/>
 */
@Controller("peopleAddress")
@RequestMapping("/people/address")
public class PeopleAddressAction extends BaseAction {
	/**
	 * 注入用户收货地址业务层
	 */
	@Autowired
	private IPeopleAddressBiz peopleAddressBiz;

	/**
	 * 用户收货地址列表
	 * <dt><span class="strong">返回</span></dt><br/>
	 * [{<br/>
	 * peopleAddressId:"自增长编号"<br/>
	 * "peopleAddressConsigneeName": "收货人姓名"<br/>
	 * "peopleAddressAddress": "地址"<br/>
	 * "peopleAddressPhone": "手机号"<br/>
	 * "peopleAddressMail": "邮箱"<br/>
	 * "peopleAddressProvince": "省"<br/>
	 * "peopleAddressCity": "城市"<br/>
	 * "peopleAddressDistrict": "县／区"<br/>
	 * "peopleAddressStreet": "街道" <br/>
	 * "peopleAddressDefault": 1默认 0非默认 <br/>
	 * }]<br/>
	 */
	@RequestMapping("/list")
	public void list(@ModelAttribute PeopleAddressEntity peopleAddress, HttpServletRequest request,
			HttpServletResponse response) {
		// 通过session得到用户实体
		PeopleEntity people = this.getPeopleBySession(request);
		// 通过用户id和站点id查询用户收货地址列表
		peopleAddress.setPeopleAddressAppId(BasicUtil.getAppId());
		peopleAddress.setPeopleAddressPeopleId(people.getPeopleId());
		List list = peopleAddressBiz.query(peopleAddress);
		this.outJson(response, JSONArray.toJSONString(list));
	}

	/**
	 * 保存用户收货地址
	 * 
	 * @param peopleAddress
	 *            地址信息<br/>
	 *            <i>peopleAddress参数包含字段信息参考：</i><br/>
	 *            "peopleAddressConsigneeName": "收货人姓名"<br/>
	 *            "peopleAddressAddress": "地址"<br/>
	 *            "peopleAddressPhone": "手机号"<br/>
	 *            "peopleAddressMail": "邮箱"<br/>
	 *            "peopleAddressProvince": "省"<br/>
	 *            "peopleAddressCity": "城市"<br/>
	 *            "peopleAddressDistrict": "县／区"<br/>
	 *            "peopleAddressStreet": "街道" <br/>
	 *            "peopleAddressDefault": 1默认 0非默认 <br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public void save(@ModelAttribute PeopleAddressEntity peopleAddress, HttpServletRequest request,
			HttpServletResponse response) {

		// 通过session得到用户实体
		PeopleEntity peopleEntity = this.getPeopleBySession(request);
		// 判断用户信息是否为空
		if (peopleAddress == null) {
			this.outJson(response, ModelCode.PEOPLE, false, this.getResString("people.msg.null.error"),
					this.getResString("people.msg.null.error"));
			return;
		}
		// 验证手机号
		if (StringUtil.isBlank(peopleAddress.getPeopleAddressPhone())) {
			this.outJson(response, ModelCode.PEOPLE, false,
					this.getResString("people.msg.phone.error", com.mingsoft.people.constant.Const.RESOURCES));
			return;
		}
		// 验证邮箱
		if (!StringUtil.isBlank(peopleAddress.getPeopleAddressMail())) {
			if (!StringUtil.checkEmail(peopleAddress.getPeopleAddressMail())) {
				this.outJson(response, ModelCode.PEOPLE, false,
						this.getResString("people.msg.mail.error", com.mingsoft.people.constant.Const.RESOURCES));
				return;
			}
		}
		// 判断省是否为空
		if (StringUtil.isBlank(peopleAddress.getPeopleAddressProvince())) {
			this.outJson(response, ModelCode.PEOPLE, false, this.getResString("people.user.msg.null.error"));
			return;
		}
		// 注入用户id
		peopleAddress.setPeopleAddressPeopleId(peopleEntity.getPeopleId());
		// 注入站点id
		peopleAddress.setPeopleAddressAppId(BasicUtil.getAppId());
		// 进行保存
		peopleAddressBiz.saveEntity(peopleAddress);
		this.outJson(response, null, true, JSONObject.toJSONString(peopleAddress));
	}

	/**
	 * 更新用户收货地址
	 * 
	 * @param peopleAddress
	 *            地址信息<br/>
	 *            <i>peopleAddress参数包含字段信息参考：</i><br/>
	 *            "peopleAddressConsigneeName": "收货人姓名"<br/>
	 *            peopleAddressId 自增长编号<br/>
	 *            "peopleAddressAddress": "地址"<br/>
	 *            "peopleAddressPhone": "手机号"<br/>
	 *            "peopleAddressMail": "邮箱"<br/>
	 *            "peopleAddressProvince": "省"<br/>
	 *            "peopleAddressCity": "城市"<br/>
	 *            "peopleAddressDistrict": "县／区"<br/>
	 *            "peopleAddressStreet": "街道" <br/>
	 *            "peopleAddressDefault": 1默认 0非默认 <br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public void update(@ModelAttribute PeopleAddressEntity peopleAddress, HttpServletRequest request,
			HttpServletResponse response) {
		// 通过session得到用户实体
		PeopleEntity people = this.getPeopleBySession();
		peopleAddress.setPeopleAddressPeopleId(people.getPeopleId());
		PeopleAddressEntity address = (PeopleAddressEntity) peopleAddressBiz.getEntity(peopleAddress);
		if (people.getPeopleId() != address.getPeopleAddressPeopleId()) {
			this.outJson(response, false);
			return;
		}
		// 判断用户信息是否为空
		if (StringUtil.isBlank(peopleAddress.getPeopleAddressProvince())
				|| StringUtil.isBlank(peopleAddress.getPeopleAddressAddress())) {
			this.outJson(response, ModelCode.PEOPLE, false,
					this.getResString("people.address", com.mingsoft.people.constant.Const.RESOURCES));
			return;
		}
		// 验证手机号
		if (StringUtil.isBlank(peopleAddress.getPeopleAddressPhone())) {
			this.outJson(response, ModelCode.PEOPLE, false,
					this.getResString("people.msg.phone.error", com.mingsoft.people.constant.Const.RESOURCES));
			return;
		}
		peopleAddress.setPeopleAddressPeopleId(people.getPeopleId());
		peopleAddress.setPeopleAddressAppId(BasicUtil.getAppId());
		// 更新用户地址
		peopleAddressBiz.updateEntity(peopleAddress);
		this.outJson(response, null, true);
	}

	/**
	 * 设置默认地址
	 * 
	 * @param peopleAddress
	 *            地址信息<br/>
	 *            <i>peopleAddress参数包含字段信息参考：</i><br/>
	 *            peopleAddressId 自增长编号<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }
	 */
	@RequestMapping("/setDefault")
	public void setDefault(@ModelAttribute PeopleAddressEntity peopleAddress, HttpServletRequest request,
			HttpServletResponse response) {
		// 通过session得到用户实体
		PeopleEntity people = this.getPeopleBySession();
		// 将获取用户 PeopleAddressDefault 值还原为1，更新设为用户默认地址
		peopleAddress.setPeopleAddressPeopleId(people.getPeopleId());
		peopleAddress.setPeopleAddressAppId(BasicUtil.getAppId());
		// 更新用户地址
		peopleAddressBiz.setDefault(peopleAddress);
		this.outJson(response, null, true);
	}

	/**
	 * 根据收货地址id删除收货信息
	 * 
	 * @param peopleAddress
	 *            地址信息<br/>
	 *            <i>peopleAddress参数包含字段信息参考：</i><br/>
	 *            peopleAddressId 自增长编号<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }
	 */
	@RequestMapping("/delete")
	public void delete(@ModelAttribute PeopleAddressEntity peopleAddress, HttpServletRequest request,
			HttpServletResponse response) {
		// 根据收货地址id删除收货信息
		peopleAddress.setPeopleAddressPeopleId(this.getPeopleBySession().getPeopleId());
		peopleAddress.setPeopleAddressAppId(BasicUtil.getAppId());
		peopleAddressBiz.deleteEntity(peopleAddress);
		this.outJson(response, null, true);
	}

	/**
	 * 通过peopleAddressId查询用户收货地址实体
	 * 
	 * @param peopleAddress
	 *            地址信息<br/>
	 *            <i>peopleAddress参数包含字段信息参考：</i><br/>
	 *            peopleAddressId 自增长编号<br/>
	 *            peopleAddressDefault 大于0获取默认地址<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 * 
	 *            "{peopleAddressConsigneeName": "收货人姓名"<br/>
	 *            peopleAddressId 自增长编号<br/>
	 *            "peopleAddressAddress": "地址"<br/>
	 *            "peopleAddressPhone": "手机号"<br/>
	 *            "peopleAddressMail": "邮箱"<br/>
	 *            "peopleAddressProvince": "省"<br/>
	 *            "peopleAddressCity": "城市"<br/>
	 *            "peopleAddressDistrict": "县／区"<br/>
	 *            "peopleAddressStreet": "街道" <br/>
	 *            "peopleAddressDefault": 1默认 0非默认 }<br/>
	 */
	@RequestMapping("/get")
	public void get(@ModelAttribute PeopleAddressEntity peopleAddress, HttpServletRequest request,
			HttpServletResponse response) {
		// 通过用户地址id查询用户地址实体
		peopleAddress.setPeopleAddressPeopleId(this.getPeopleBySession().getPeopleId());
		PeopleAddressEntity address = (PeopleAddressEntity) peopleAddressBiz.getEntity(peopleAddress);
		if (this.getPeopleBySession(request).getPeopleId() != address.getPeopleAddressPeopleId()) {
			this.outJson(response, false);
			return;
		}
		this.outJson(response, JSONObject.toJSONString(address));
	}
}
