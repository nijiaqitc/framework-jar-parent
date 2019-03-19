package com.njq.common.enumreg.title;

import com.njq.common.enumreg.EnumHelper;
import com.njq.common.enumreg.ValueDescription;
import com.njq.common.enumreg.channel.ChannelType;
import org.apache.commons.lang3.EnumUtils;

import java.util.ArrayList;
import java.util.List;

public enum TitleType implements ValueDescription {
	BASE_TITLE("base", "基础标题"),
	YXL_TITLE("yxl","一系列"),
	GRAB_TITLE("grab", "抓捕标题");
	private String value;
	private String description;
	private static List<ChannelType> VALUES = EnumUtils.getEnumList(ChannelType.class);

	TitleType(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public static ChannelType getChannelType(String value) {
		return EnumHelper.getEnum(VALUES, ChannelType.class, value);
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	/**
	 * 获取值列表
	 * @return
	 */
	public static List<String> getChannelValueList(){
		List<String> valueList = new ArrayList<>();
		VALUES.forEach(n->{
			valueList.add(n.getValue());
		});
		return valueList;
	}
}
