package com.mingsoft.cms.util;

import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @项目名称: CRM
 * @作者: MaYong
 * @描述: Config文件读取
 * @创建时间: 2017年02月28日 11:40
 * @版本 : V1.0
 */
public class ConfigUtils {

	// 并发访问锁
	private static final ReentrantLock lock = new ReentrantLock();
	private static FileConfiguration instance = null;

	// 静态初始化
	static{
		if (instance == null){
			init();
		}
	}

	private ConfigUtils(){}

	/**
	 * @author MaYong
	 */
	private static void init(){
		lock.lock();
		try {
			if (instance == null) {
				instance = new PropertiesConfiguration();
				instance.setEncoding("utf-8");
				instance.setURL(ConfigUtils.class.getResource("/ms.properties"));
				// DEFAULT_REFRESH_DELAY = 5000
				instance.setReloadingStrategy(new FileChangedReloadingStrategy());//使用文件内容发送变化重新加载策略

				try {
					instance.load();
					ConfigurationUtils.dump(instance, System.out);
				} catch (Exception e) {
					e.printStackTrace(System.out);
				}
			}
		} finally {
			lock.unlock();
		}
	}


	/**
	 * 取得配置文件实例
	 * @return 配置文件实例
	 */
	public static FileConfiguration getInstance() {
	    if (instance == null) {
            init();
        }
        return instance;
    }

	/**
	 * 获得指定路径键名配置值
	 * @param key 路径键名
	 * @return 值
	 */
	public static String getStringValue(String key) {
		return instance.getString(key);
	}
	
	/**
	 * 获得指定路径键名配置值
	 * @param key 路径键名
	 * @return 值
	 */
	public static int getIntValue(String key) {
		return instance.getInt(key);
	}
}
