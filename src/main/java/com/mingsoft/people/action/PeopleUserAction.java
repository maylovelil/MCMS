package com.mingsoft.people.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.ModelMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.mingsoft.people.bean.PeopleBean;
import com.mingsoft.people.biz.IPeopleBiz;
import com.mingsoft.people.biz.IPeopleUserBiz;
import com.mingsoft.people.constant.ModelCode;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.people.entity.PeopleUserEntity;
import net.mingsoft.base.util.JSONObject;
import com.mingsoft.util.StringUtil;
import com.mingsoft.base.entity.BaseEntity;
import net.mingsoft.basic.util.BasicUtil;
import com.mingsoft.base.filter.DateValueFilter;
import com.mingsoft.base.filter.DoubleValueFilter;
import net.mingsoft.basic.bean.EUListBean;
	
/**
 * 用户基础信息表管理控制层
 * @author 伍晶晶
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-23 10:10:22<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/people/peopleUser")
public class PeopleUserAction extends com.mingsoft.people.action.BaseAction{
	
	/**
	 * 注入用户基础信息表业务层
	 */	
	@Autowired
	private IPeopleUserBiz peopleUserBiz;
	
	/**
	 * 注入用户基础信息控制层
	 */
	@Autowired
	private IPeopleBiz peopleBiz;
	
	/**
	 * 返回主界面index
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response,HttpServletRequest request){
		return view ("/people/user/index");
	}
	
	/**
	 * 查询用户基础信息表列表
	 * @param peopleUser 用户基础信息表实体
	 * <i>peopleUser参数包含字段信息参考：</i><br/>
	 * puPeopleId 用户ID关联people表的（people_id）<br/>
	 * puRealName 用户真实名称<br/>
	 * puAddress 用户地址<br/>
	 * puIcon 用户头像图标地址<br/>
	 * puNickname 用户昵称<br/>
	 * puSex 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday 用户出生年月日<br/>
	 * puCard 身份证<br/>
	 * puAppId 用户所属应用ID<br/>
	 * puProvince 省<br/>
	 * puCity 城市<br/>
	 * puDistrict 区<br/>
	 * puStreet 街道<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * puPeopleId: 用户ID关联people表的（people_id）<br/>
	 * puRealName: 用户真实名称<br/>
	 * puAddress: 用户地址<br/>
	 * puIcon: 用户头像图标地址<br/>
	 * puNickname: 用户昵称<br/>
	 * puSex: 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday: 用户出生年月日<br/>
	 * puCard: 身份证<br/>
	 * puAppId: 用户所属应用ID<br/>
	 * puProvince: 省<br/>
	 * puCity: 城市<br/>
	 * puDistrict: 区<br/>
	 * puStreet: 街道<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EUListBean list(@ModelAttribute PeopleBean peopleUser,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		if(peopleUser == null){
			peopleUser = new PeopleBean();
		}
		peopleUser.setPeopleAppId(BasicUtil.getAppId());
		BasicUtil.startPage();
		List<PeopleBean> peopleUserList = peopleUserBiz.query(peopleUser);
		EUListBean list = new EUListBean(peopleUserList,(int)BasicUtil.endPage(peopleUserList).getTotal());
		return list;
	}
	
	/**
	 * 返回编辑界面peopleUser_form
	 */
	@RequestMapping("/form")
	public String form(@ModelAttribute PeopleUserEntity peopleUser,HttpServletResponse response,HttpServletRequest request,ModelMap model){
		if(peopleUser.getPuPeopleId() != null){
			BaseEntity peopleUserEntity = peopleUserBiz.getEntity(peopleUser.getPuPeopleId());			
			model.addAttribute("peopleUserEntity",peopleUserEntity);
		}
		return view ("/people/user/form");
	}
	
	/**
	 * 获取用户基础信息表
	 * @param peopleUser 用户基础信息表实体
	 * <i>peopleUser参数包含字段信息参考：</i><br/>
	 * puPeopleId 用户ID关联people表的（people_id）<br/>
	 * puRealName 用户真实名称<br/>
	 * puAddress 用户地址<br/>
	 * puIcon 用户头像图标地址<br/>
	 * puNickname 用户昵称<br/>
	 * puSex 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday 用户出生年月日<br/>
	 * puCard 身份证<br/>
	 * puAppId 用户所属应用ID<br/>
	 * puProvince 省<br/>
	 * puCity 城市<br/>
	 * puDistrict 区<br/>
	 * puStreet 街道<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * puPeopleId: 用户ID关联people表的（people_id）<br/>
	 * puRealName: 用户真实名称<br/>
	 * puAddress: 用户地址<br/>
	 * puIcon: 用户头像图标地址<br/>
	 * puNickname: 用户昵称<br/>
	 * puSex: 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday: 用户出生年月日<br/>
	 * puCard: 身份证<br/>
	 * puAppId: 用户所属应用ID<br/>
	 * puProvince: 省<br/>
	 * puCity: 城市<br/>
	 * puDistrict: 区<br/>
	 * puStreet: 街道<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute PeopleUserEntity peopleUser,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(peopleUser.getPuPeopleId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("pu.people.id")));
			return;
		}
		PeopleUserEntity _peopleUser = (PeopleUserEntity)peopleUserBiz.getEntity(peopleUser.getPuPeopleId());
		this.outJson(response, _peopleUser,"peopleOldPassword","peoplePassword");
	}
	
	/**
	 * 保存用户基础信息表实体
	 * @param peopleUser 用户基础信息表实体
	 * <i>peopleUser参数包含字段信息参考：</i><br/>
	 * puPeopleId 用户ID关联people表的（people_id）<br/>
	 * puRealName 用户真实名称<br/>
	 * puAddress 用户地址<br/>
	 * puIcon 用户头像图标地址<br/>
	 * puNickname 用户昵称<br/>
	 * puSex 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday 用户出生年月日<br/>
	 * puCard 身份证<br/>
	 * puAppId 用户所属应用ID<br/>
	 * puProvince 省<br/>
	 * puCity 城市<br/>
	 * puDistrict 区<br/>
	 * puStreet 街道<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * puPeopleId: 用户ID关联people表的（people_id）<br/>
	 * puRealName: 用户真实名称<br/>
	 * puAddress: 用户地址<br/>
	 * puIcon: 用户头像图标地址<br/>
	 * puNickname: 用户昵称<br/>
	 * puSex: 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday: 用户出生年月日<br/>
	 * puCard: 身份证<br/>
	 * puAppId: 用户所属应用ID<br/>
	 * puProvince: 省<br/>
	 * puCity: 城市<br/>
	 * puDistrict: 区<br/>
	 * puStreet: 街道<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	@RequiresPermissions("people:save")
	public void save(@ModelAttribute PeopleUserEntity peopleUser, HttpServletResponse response, HttpServletRequest request) {
		//验证用户真实名称的值是否合法			
		if(!StringUtil.checkLength(peopleUser.getPuRealName()+"", 0, 50)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.real.name"), "0", "50"));
			return;			
		}
		if(!StringUtil.checkLength(peopleUser.getPuAddress()+"", 0, 200)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.address"), "0", "200"));
			return;			
		}
		if(!StringUtil.checkLength(peopleUser.getPuIcon()+"", 0, 200)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.icon"), "0", "200"));
			return;			
		}
		if(!StringUtil.checkLength(peopleUser.getPuNickname()+"", 0, 50)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.nickname"), "0", "50"));
			return;			
		}
		//验证用户性别(0.未知、1.男、2.女)的值是否合法			
		if(!StringUtil.checkLength(peopleUser.getPuSex()+"", 0, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.sex"), "0", "10"));
			return;			
		}
		//验证身份证的值是否合法			
		if(!StringUtil.checkLength(peopleUser.getPuCard()+"", 0, 255)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.card"), "0", "255"));
			return;			
		}
		if(!StringUtil.isBlank(StringUtil.Md5(peopleUser.getPeoplePassword()))){
			//设置用户密码
			peopleUser.setPeoplePassword(StringUtil.Md5(peopleUser.getPeoplePassword()));
		}
		//验证用户输入的信息是否合法
		if(!this.checkPeople(peopleUser, request, response)){
			return;
		}
		peopleUser.setPeopleDateTime(new Date());
		peopleUser.setPeopleAppId(BasicUtil.getAppId());
		peopleUserBiz.savePeople(peopleUser);
		this.outJson(response, peopleUser,"peopleOldPassword","peoplePassword");
	}
	
	/**
	 * @param peopleUser 用户基础信息表实体
	 * <i>peopleUser参数包含字段信息参考：</i><br/>
	 * puPeopleId:多个puPeopleId直接用逗号隔开,例如puPeopleId=1,2,3,4
	 * 批量删除用户基础信息表
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("people:del")
	public void delete(@RequestBody List<PeopleUserEntity> peopleUsers,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = new int[peopleUsers.size()];
		for(int i = 0;i<peopleUsers.size();i++){
			ids[i] = peopleUsers.get(i).getPuPeopleId();
		}
		peopleUserBiz.deletePeople(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新用户基础信息表信息用户基础信息表
	 * @param peopleUser 用户基础信息表实体
	 * <i>peopleUser参数包含字段信息参考：</i><br/>
	 * puPeopleId 用户ID关联people表的（people_id）<br/>
	 * puRealName 用户真实名称<br/>
	 * puAddress 用户地址<br/>
	 * puIcon 用户头像图标地址<br/>
	 * puNickname 用户昵称<br/>
	 * puSex 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday 用户出生年月日<br/>
	 * puCard 身份证<br/>
	 * puAppId 用户所属应用ID<br/>
	 * puProvince 省<br/>
	 * puCity 城市<br/>
	 * puDistrict 区<br/>
	 * puStreet 街道<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * puPeopleId: 用户ID关联people表的（people_id）<br/>
	 * puRealName: 用户真实名称<br/>
	 * puAddress: 用户地址<br/>
	 * puIcon: 用户头像图标地址<br/>
	 * puNickname: 用户昵称<br/>
	 * puSex: 用户性别(0.未知、1.男、2.女)<br/>
	 * puBirthday: 用户出生年月日<br/>
	 * puCard: 身份证<br/>
	 * puAppId: 用户所属应用ID<br/>
	 * puProvince: 省<br/>
	 * puCity: 城市<br/>
	 * puDistrict: 区<br/>
	 * puStreet: 街道<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@ResponseBody	 
	@RequiresPermissions("people:update")
	public void update(@ModelAttribute PeopleUserEntity peopleUser, HttpServletResponse response,
			HttpServletRequest request) {
		if(!StringUtil.checkLength(peopleUser.getPuRealName()+"", 0, 50)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.real.name"), "0", "50"));
			return;			
		}
		if(!StringUtil.checkLength(peopleUser.getPuAddress()+"", 0, 200)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.address"), "0", "200"));
			return;			
		}
		if(!StringUtil.checkLength(peopleUser.getPuIcon()+"", 0, 200)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.icon"), "0", "200"));
			return;			
		}
		if(!StringUtil.checkLength(peopleUser.getPuNickname()+"", 0, 50)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.nickname"), "0", "50"));
			return;			
		}
		//验证用户性别(0.未知、1.男、2.女)的值是否合法			
		if(!StringUtil.checkLength(peopleUser.getPuSex()+"", 0, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.sex"), "0", "10"));
			return;			
		}
		//验证身份证的值是否合法			
		if(!StringUtil.checkLength(peopleUser.getPuCard()+"", 0, 255)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pu.card"), "0", "255"));
			return;			
		}
		if(!this.checkUpdatePeople(peopleUser, request, response)){
			return;
		}
		//判断用户密码是否为空，如果不为空则进行密码的更新
		if(!StringUtil.isBlank(peopleUser.getPeoplePassword())){
			//设置用户密码
			peopleUser.setPeoplePassword(StringUtil.Md5(peopleUser.getPeoplePassword()));
		}
		peopleUser.setPeopleId(peopleUser.getPuPeopleId());
		peopleUserBiz.updatePeople(peopleUser);
		this.outJson(response, peopleUser,"peopleOldPassword","peoplePassword");
	}
	
	/**
	 * 验证更新用户信息是判断用户输入的信息是否合法
	 * @param peopleUser  用户实体
	 * @param request http请求对象
	 * @param response http响应对象
	 */
	public boolean checkUpdatePeople(PeopleUserEntity peopleUser,HttpServletRequest request,HttpServletResponse response){
		
		//获取更改前的用户
		PeopleUserEntity oldPeopleUser = (PeopleUserEntity) peopleUserBiz.getEntity(peopleUser.getPuPeopleId());
		//获取应用id
		int appId = this.getAppId(request);
		//如果填写了邮箱，则验证邮箱格式是否正确
		if (!StringUtil.isBlank(peopleUser.getPeopleMail()) && !StringUtil.isEmail(peopleUser.getPeopleMail())) {
			this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.msg.mail.format.error", com.mingsoft.people.constant.Const.RESOURCES));
			return false;
		}
		//验证用户名不能为空
		if(StringUtil.isBlank(peopleUser.getPeopleName())){
			this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.msg.name.error", com.mingsoft.people.constant.Const.RESOURCES));
			return false;
		}
				
		//如果填写了手机号码，则验证手机号码填写是否正确
		if (!StringUtil.isBlank(peopleUser.getPeoplePhone()) && !StringUtil.isMobile(peopleUser.getPeoplePhone())) {
			this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.msg.phone.format.error", com.mingsoft.people.constant.Const.RESOURCES));
			return false;
		}
				
				
		//当用户名进行修改时验证用户名是否是唯一的
		if (!StringUtil.isBlank(peopleUser.getPeopleName())) {
			// 验证手机号是否唯一
			PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(peopleUser.getPeopleName(), appId);
			//判断之前是否已经存在用户名，如果不存在，则判断是否存在重名，如果存在,判断用户是否更改用户名如果更改则判断新更改的用户名是否已经存在
			//判断填写的用户名和之前用户名是否相同，如果不相同
			if(StringUtil.isBlank(oldPeopleUser.getPeopleName())){
				if (peoplePhone != null) {
					this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.name.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
					return false;
				}
			}else{
				if(!oldPeopleUser.getPeopleName().equals(peopleUser.getPeopleName())){
					if (peoplePhone != null) {
							this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.name.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
							return false;
					}
				}
			}
					
		}
		if(!StringUtil.isBlank(peopleUser.getPeoplePhone())){
			PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(peopleUser.getPeoplePhone(), appId);
			//判断之前是否已经存在手机号，如果不存在，则判断是否存在重名，如果存在,判断用户是否更改手机号如果更改则判断新更改的手机号是否已经存在
			//判断填写的手机号和之前手机号是否相同，如果不相同
			if(StringUtil.isBlank(oldPeopleUser.getPeoplePhone())){
				if (peoplePhone != null) {
					this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.name.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
					return false;
				}
			}else{
				if(!oldPeopleUser.getPeoplePhone().equals(peopleUser.getPeoplePhone())){
					if (peoplePhone != null) {
						this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.name.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
						return false;
					}
				}
			}
		}
		//验证邮箱的唯一性
		if(!StringUtil.isBlank(peopleUser.getPeopleMail())){
			PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(peopleUser.getPeopleMail(), appId);
			//判断之前是否已经存在手机号，如果不存在，则判断是否存在重名，如果存在,判断用户是否更改手机号如果更改则判断新更改的手机号是否已经存在
			//判断填写的手机号和之前手机号是否相同，如果不相同
			if(StringUtil.isBlank(oldPeopleUser.getPeopleMail())){
				if (peoplePhone != null) {
					this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.name.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
					return false;
				}
			}else{
				if(!oldPeopleUser.getPeopleMail().equals(peopleUser.getPeopleMail())){
					if (peoplePhone != null) {
						this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.name.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
						return false;
					}
				}
			}
		}
		//验证用户身份证号码
		return true;
	}
	
	/**
	 * 验证保存用户时输入的信息是否合法
	 * @param peopleUser  用户实体
	 * @param request http请求对象
	 * @param response http响应对象
	 */
	public boolean checkPeople(PeopleUserEntity peopleUser,HttpServletRequest request,HttpServletResponse response){
		//获取应用id
		int appId = this.getAppId(request);
		//如果填写了邮箱，则验证邮箱格式是否正确
		if (!StringUtil.isBlank(peopleUser.getPeopleMail()) && !StringUtil.isEmail(peopleUser.getPeopleMail())) {
			this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.msg.mail.format.error", com.mingsoft.people.constant.Const.RESOURCES));
			return false;
		}
		//验证用户名不能为空
		if(StringUtil.isBlank(peopleUser.getPeopleName())){
			this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.msg.name.error", com.mingsoft.people.constant.Const.RESOURCES));
			return false;
		}
		//如果填写了手机号码，则验证手机号码填写是否正确
		if (!StringUtil.isBlank(peopleUser.getPeoplePhone()) && !StringUtil.isMobile(peopleUser.getPeoplePhone())) {
			this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.msg.phone.format.error", com.mingsoft.people.constant.Const.RESOURCES));
			return false;
		}
		
		
		//验证用户名是否是唯一的
		if (!StringUtil.isBlank(peopleUser.getPeopleName())) {
			// 验证手机号是否唯一
			PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(peopleUser.getPeopleName(), appId);
			if (peoplePhone != null) {
				this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.name.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
				return false;
			}
		}
		if (!StringUtil.isBlank(peopleUser.getPeoplePhone())) {
			// 验证手机号是否唯一
			PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(peopleUser.getPeoplePhone(), appId);
			if (peoplePhone != null) {
				this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.phone.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
				return false;
			}
		}
		if (!StringUtil.isBlank(peopleUser.getPeopleMail())) {
			// 验证邮箱是否唯一
			PeopleEntity peopleMail = this.peopleBiz.getEntityByUserName(peopleUser.getPeopleMail(), appId);
			if (peopleMail != null) {
				this.outJson(response, ModelCode.PEOPLE_USER, false, this.getResString("people.register.msg.mail.repeat.error", com.mingsoft.people.constant.Const.RESOURCES));
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 获取用户详细信息
	 * @param peopleId 用户ID
	 * @param request
	 * @param response
	 */
	@Deprecated
	@RequestMapping("/getEntity")
	public void getEntity(String peopleId,HttpServletRequest request,HttpServletResponse response){
		if(StringUtil.isBlank(peopleId) || !StringUtil.isInteger(peopleId)){
			this.outJson(response, ModelCode.PEOPLE_USER,false);
			return ;
		}
		PeopleUserEntity peopleUser = (PeopleUserEntity) this.peopleUserBiz.getEntity(Integer.parseInt(peopleId));
		if(peopleUser == null){
			this.outJson(response, ModelCode.PEOPLE_USER,false);
			return ;
		}
		this.outJson(response, ModelCode.PEOPLE_USER,true,null,JSONObject.toJSONString(peopleUser));
	}
}