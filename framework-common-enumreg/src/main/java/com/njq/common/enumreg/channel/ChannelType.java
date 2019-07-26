package com.njq.common.enumreg.channel;

import com.njq.common.enumreg.EnumHelper;
import com.njq.common.enumreg.ValueDescription;
import org.apache.commons.lang3.EnumUtils;

import java.util.ArrayList;
import java.util.List;

public enum ChannelType implements ValueDescription {
	CUSTOM("custom", "自定义"),
	CNBLOGS("cnblogs", "博客园"),
	CSDN("csdn","csdn"),
	YXL("yxl","系列文章"),
	SBANNER("banner","banner图"),
	YH_WIKI("yhwiki", "永辉的wiki文档"),
	QI_SHU("qishu", "qishu"),
	XIANGCUN("xiangcunxiaoshuo","xiangcunxiaoshuo");

	private String value;
	private String description;
	private static List<ChannelType> VALUES = EnumUtils.getEnumList(ChannelType.class);

	ChannelType(String value, String description) {
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
			valueList.add(n.value);
		});
		return valueList;
	}
	
}
