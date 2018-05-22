package net.mingsoft.basic.biz;

import com.mingsoft.base.biz.IBaseBiz;
import com.mingsoft.util.*;
import java.util.*;

import net.mingsoft.basic.bean.CityBean;
import net.mingsoft.basic.entity.CityEntity;

/**
 * 省市县镇村数据业务
 * @author 伍晶晶
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-7-27 14:47:29<br/>
 * 历史修订：<br/>
 */
public interface ICityBiz extends IBaseBiz {
	/**
	 * 查询省／直辖市／自治区
	 * @return 省／直辖市／自治区列表
	 * province_id 省／直辖市／自治区编号
	 * province_name 省／直辖市／自治区名称
	 */
	public List<CityEntity> queryProvince();
	/**
	 * 查询市
	 * @param cityEntity 城市实体
	 * @return 市级列表
	 * city_id 市编号
	 * city_name 市名称
	 */
	public List<CityEntity> queryCity(CityEntity cityEntity);
	/**
	 * 查询区／县
	 * @param cityEntity
	 * @return 区／县列表
	 * county_id 区／县编号
	 * county_name 区／县 名称
	 */
	public List<CityEntity> queryCounty(CityEntity cityEntity);
	/**
	 * 查询街道／镇
	 * @param cityEntity
	 * @return 街道／镇列表
	 * town_id 街道／镇编号
	 * town_name 街道／镇名称
	 */
	public List<CityEntity> queryTown(CityEntity cityEntity);
	/**
	 * 查询村委会
	 * @param cityEntity
	 * @return 村委会／社区列表
	 * village_id 村委会／社区编号
	 * village_name 村委会／社区名称
	 */
	public List<CityEntity> queryVillage(CityEntity cityEntity);
	/**
	 * 根据层级，和类型来确定返回多少级数
	 * @param tier 层级数，整型。
	 * @return
	 * id 城市编号
	 * name 城市名称
	 * parentId 父级城市编号		
	 * childrensList 子城市数据，包括id，name，parentId,childrensList
	 */
	public List<CityBean> queryForTree(int tier,String type);
	
	/**
	 * 通过层级过滤城市数据，提高性能
	 * @param level 默认3级获取到区数据
	 * @return 列表
	 */
	public List<CityEntity> queryByLevel(int level);
}