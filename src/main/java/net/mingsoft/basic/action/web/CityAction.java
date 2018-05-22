package net.mingsoft.basic.action.web;

import java.util.List;

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

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.mingsoft.basic.bean.CityBean;
import net.mingsoft.basic.biz.ICityBiz;
import net.mingsoft.basic.entity.CityEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import net.mingsoft.basic.util.BasicUtil;
	
/**
 * 省市县镇村数据管理控制层
 * @author 伍晶晶
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-7-27 14:47:29<br/>
 * 历史修订：<br/>
 */
@Controller("webCityAction")
@RequestMapping("/basic/city")
public class CityAction extends com.mingsoft.basic.action.BaseAction{
	
	/**
	 * 注入省市县镇村数据业务层
	 */	
	@Autowired
	private ICityBiz cityBiz;
	
	
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
		BasicUtil.endPage(cityList);
		this.outJson(response, JSONArray.toJSONStringWithDateFormat(cityList, "yyyy-MM-dd"));
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
	 * 更新省市县镇村数据信息省市县镇村数据
	 * @param tier 输入需要的层级。省／市／县／镇／村 
	 * level：省市层级、整型 默认3级<br/>
	 * type:数据格式，tree为树形存在childs集合属性，否则为行关系数据，即有父级id，默认为树形结构
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * id: 城市编号<br/>
	 * parentId: 父级城市编号<br/>
	 * name: 城市名称<br/>
	 * childrensList: 子城市数据 <br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/query")
	@ResponseBody	 
	public void query(HttpServletResponse response,HttpServletRequest request) {
		int level = BasicUtil.getInt("level",3);//默认3级
		String type = BasicUtil.getString("type","tree"); //默认为树形结构
		List<CityBean> cityList = (List<CityBean>) cityBiz.queryForTree(level,type);
		this.outJson(response, JSONArray.toJSONString(cityList));
	}
	
		
}