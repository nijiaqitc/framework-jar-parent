package com.njq.common.enumreg.cachepre;


import com.njq.common.enumreg.ValueDescription;

/**
 * redis緩存前綴設置
 * @author Administrator
 *
 */
public enum CacheNamePrefixEnum implements ValueDescription {
	DOCLIST_FOR_QUERY("yxl", "docList", "系列文章"),
	INDEX_BANNER_INFO("index","banner","首页banner信息"),
	GRAB_MENU("grab","menu","菜单目录"),
	GRAB_LOGIN_TOKEN("grab", "loginToken", "登录cookie");
	private String cacheGroup;
	private String cacheName;
	private String description;

	CacheNamePrefixEnum(String cacheGroup, String cacheName, String description) {
		this.cacheGroup = cacheGroup;
		this.cacheName = cacheName;
		this.description = description;
	}

	@Override
	public String getValue() {
		return cacheGroup + ":" + cacheName + ":";
	}

	@Override
	public String getDescription() {
		return description;
	}
}