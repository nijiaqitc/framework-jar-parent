package com.njq.common.enumreg;


public interface ValueDescription extends Description {
    /**
     * 获取值
     *
     * @return 获取枚举的值，不同于name()
     */
    String getValue();
}