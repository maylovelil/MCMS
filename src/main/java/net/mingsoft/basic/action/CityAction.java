package net.mingsoft.basic.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.mingsoft.basic.biz.ICityBiz;
import net.mingsoft.basic.entity.CityEntity;
import net.mingsoft.base.util.JSONObject;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.base.entity.BaseEntity;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.basic.bean.ListBean;
import com.mingsoft.base.filter.DateValueFilter;
import com.mingsoft.base.filter.DoubleValueFilter;

import net.mingsoft.basic.bean.CityBean;
import net.mingsoft.basic.bean.EUListBean;
	
/**
 * 省市县镇村数据管理控制层
 * @author 伍晶晶
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-7-27 14:47:29<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/basic/city")
public class CityAction extends com.mingsoft.basic.action.BaseAction{
	
	/**
	 * 注入省市县镇村数据业务层
	 */	
	@Autowired
	private ICityBiz cityBiz;
	
	/**
	 * 返回主界面index
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response,HttpServletRequest request){
		return view ("/basic/city/index");
	}
	
	/**
	 * 查询省市县镇村数据列表
	 * @param city 省市县镇村数据实体
	 * <i>city参数包含字段信息参考：</i><br/>
	 * id 主键编号<br/>
	 * provinceId 省／直辖市／自治区级id<br/>
	 * provinceName 省／直辖市／自治区级名称<br/>
	 * cityId 市级id <br/>
	 * cityName 市级名称<br/>
	 * countyId 县／区级id<br/>
	 * countyName 县／区级名称<br/>
	 * townId 街道／镇级id<br/>
	 * townName 街道／镇级名称<br/>
	 * villageId 村委会id<br/>
	 * villageName 村委会名称<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * id: 主键编号<br/>
	 * provinceId: 省／直辖市／自治区级id<br/>
	 * provinceName: 省／直辖市／自治区级名称<br/>
	 * cityId: 市级id <br/>
	 * cityName: 市级名称<br/>
	 * countyId: 县／区级id<br/>
	 * countyName: 县／区级名称<br/>
	 * townId: 街道／镇级id<br/>
	 * townName: 街道／镇级名称<br/>
	 * villageId: 村委会id<br/>
	 * villageName: 村委会名称<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute CityEntity city,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		BasicUtil.startPage();
		List cityList = cityBiz.query(city);
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(cityList,(int)BasicUtil.endPage(cityList).getTotal()),new DoubleValueFilter(),new DateValueFilter()));
	}
	
	/**
	 * 返回编辑界面city_form
	 */
	@RequestMapping("/form")
	public String form(@ModelAttribute CityEntity city,HttpServletResponse response,HttpServletRequest request,ModelMap model){
		if(StringUtil.isBlank(city.getId())){
			BaseEntity cityEntity = cityBiz.getEntity(Integer.parseInt(city.getId()));			
			model.addAttribute("cityEntity",cityEntity);
		}
		
		return view ("/basic/city/form");
	}
	
	/**
	 * 获取省市县镇村数据
	 * @param city 省市县镇村数据实体
	 * <i>city参数包含字段信息参考：</i><br/>
	 * id 主键编号<br/>
	 * provinceId 省／直辖市／自治区级id<br/>
	 * provinceName 省／直辖市／自治区级名称<br/>
	 * cityId 市级id <br/>
	 * cityName 市级名称<br/>
	 * countyId 县／区级id<br/>
	 * countyName 县／区级名称<br/>
	 * townId 街道／镇级id<br/>
	 * townName 街道／镇级名称<br/>
	 * villageId 村委会id<br/>
	 * villageName 村委会名称<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * id: 主键编号<br/>
	 * provinceId: 省／直辖市／自治区级id<br/>
	 * provinceName: 省／直辖市／自治区级名称<br/>
	 * cityId: 市级id <br/>
	 * cityName: 市级名称<br/>
	 * countyId: 县／区级id<br/>
	 * countyName: 县／区级名称<br/>
	 * townId: 街道／镇级id<br/>
	 * townName: 街道／镇级名称<br/>
	 * villageId: 村委会id<br/>
	 * villageName: 村委会名称<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute CityEntity city,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(StringUtil.isBlank(city.getId())) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("id")));
			return;
		}
		CityEntity _city = (CityEntity)cityBiz.getEntity(Integer.parseInt(city.getId()));
		this.outJson(response, _city);
	}
	
	/**
	 * 保存省市县镇村数据实体
	 * @param city 省市县镇村数据实体
	 * <i>city参数包含字段信息参考：</i><br/>
	 * id 主键编号<br/>
	 * provinceId 省／直辖市／自治区级id<br/>
	 * provinceName 省／直辖市／自治区级名称<br/>
	 * cityId 市级id <br/>
	 * cityName 市级名称<br/>
	 * countyId 县／区级id<br/>
	 * countyName 县／区级名称<br/>
	 * townId 街道／镇级id<br/>
	 * townName 街道／镇级名称<br/>
	 * villageId 村委会id<br/>
	 * villageName 村委会名称<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * id: 主键编号<br/>
	 * provinceId: 省／直辖市／自治区级id<br/>
	 * provinceName: 省／直辖市／自治区级名称<br/>
	 * cityId: 市级id <br/>
	 * cityName: 市级名称<br/>
	 * countyId: 县／区级id<br/>
	 * countyName: 县／区级名称<br/>
	 * townId: 街道／镇级id<br/>
	 * townName: 街道／镇级名称<br/>
	 * villageId: 村委会id<br/>
	 * villageName: 村委会名称<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute CityEntity city, HttpServletResponse response, HttpServletRequest request) {
		cityBiz.saveEntity(city);
		this.outJson(response, JSONObject.toJSONString(city));
	}
	
	/**
	 * @param city 省市县镇村数据实体
	 * <i>city参数包含字段信息参考：</i><br/>
	 * id:多个id直接用逗号隔开,例如id=1,2,3,4
	 * 批量删除省市县镇村数据
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(@RequestBody List<CityEntity> citys,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = new int[citys.size()];
		for(int i = 0;i<citys.size();i++){
			ids[i] = Integer.parseInt(citys.get(i).getId());
		}
		cityBiz.delete(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新省市县镇村数据信息省市县镇村数据
	 * @param city 省市县镇村数据实体
	 * <i>city参数包含字段信息参考：</i><br/>
	 * id 主键编号<br/>
	 * provinceId 省／直辖市／自治区级id<br/>
	 * provinceName 省／直辖市／自治区级名称<br/>
	 * cityId 市级id <br/>
	 * cityName 市级名称<br/>
	 * countyId 县／区级id<br/>
	 * countyName 县／区级名称<br/>
	 * townId 街道／镇级id<br/>
	 * townName 街道／镇级名称<br/>
	 * villageId 村委会id<br/>
	 * villageName 村委会名称<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * id: 主键编号<br/>
	 * provinceId: 省／直辖市／自治区级id<br/>
	 * provinceName: 省／直辖市／自治区级名称<br/>
	 * cityId: 市级id <br/>
	 * cityName: 市级名称<br/>
	 * countyId: 县／区级id<br/>
	 * countyName: 县／区级名称<br/>
	 * townId: 街道／镇级id<br/>
	 * townName: 街道／镇级名称<br/>
	 * villageId: 村委会id<br/>
	 * villageName: 村委会名称<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@ResponseBody	 
	public void update(@ModelAttribute CityEntity city, HttpServletResponse response,
			HttpServletRequest request) {
		cityBiz.updateEntity(city);
		this.outJson(response, JSONObject.toJSONString(city));
	}
	
	/**
	 * 查询省列表
	 * @param response
	 * @param request
	 */
	@RequestMapping("/province")
	@ResponseBody
	public void province(HttpServletResponse response, HttpServletRequest request) {
		List cityList = cityBiz.queryProvince();		
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(cityList));
	}
	
	/**
	 * 根据省id查询城市列表
	 * @param city
	 * @param response
	 * @param request
	 */
	@RequestMapping("/city")
	@ResponseBody
	public void city(@ModelAttribute CityEntity city,HttpServletResponse response, HttpServletRequest request) {
		List cityList = cityBiz.queryCity(city);		
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(cityList));
	}
	
	/**
	 * 根据城市id查询区域列表
	 * @param city 
	 * @param response
	 * @param request
	 */
	@RequestMapping("/county")
	@ResponseBody
	public void county(@ModelAttribute CityEntity city,HttpServletResponse response, HttpServletRequest request) {
		List cityList = cityBiz.queryCounty(city);		
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(cityList));
	}
}