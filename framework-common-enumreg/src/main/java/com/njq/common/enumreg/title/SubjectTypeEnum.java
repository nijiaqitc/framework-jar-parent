package com.njq.common.enumreg.title;

import com.njq.common.enumreg.ValueDescription;
import com.njq.common.enumreg.channel.ChannelType;
import org.apache.commons.lang3.EnumUtils;

import java.util.List;

/**
 * @author: nijiaqi
 * @date: 2019/4/26
 */
public enum SubjectTypeEnum implements ValueDescription {
    SELECTSUBJECT("2", "选择"),
    ANSWERSUBJECT("1", "简答"),
    WRITESUBJECT("3", "笔试");
    private String value;
    private String description;

    private static List<ChannelType> VALUES = EnumUtils.getEnumList(ChannelType.class);

    SubjectTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
