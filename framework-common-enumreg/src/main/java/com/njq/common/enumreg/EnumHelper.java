package com.njq.common.enumreg;

import com.njq.common.exception.BaseKnownException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public final class EnumHelper {

    private EnumHelper() {
    }

    /**
     * 根据枚举的value获得枚举
     *
     * @param enumList 枚举全部list
     * @param eClass   枚举类
     * @param value    值
     * @param <E>      继承ValueDescription的枚举
     * @return 枚举值
     * @throws
     */
    public static <E extends Enum<E> & ValueDescription> E getEnum(Collection<E> enumList, Class<E> eClass,
                                                                   String value) {
        E e = getEnumWithoutException(enumList, value);
        if (e == null) {
            throw new BaseKnownException(eClass.getSimpleName(), value + "不存在！");
        }
        return e;
    }

    /**
     * 根据枚举的value获得枚举
     *
     * @param enumList 枚举全部list
     * @param value    值
     * @param <E>      继承ValueDescription的枚举
     * @return 枚举值
     * @throws
     */
    public static <E extends Enum<E> & ValueDescription> E getEnumWithoutException(Collection<E> enumList,
                                                                                   String value) {
        return enumList.stream().filter(n -> StringUtils.equals(n.getValue(), value)).findAny().orElse(null);
    }

    /**
     * 根据枚举的value获得枚举
     *
     * @param enumList 枚举全部list
     * @param eClass   枚举类
     * @param value    值
     * @param <E>      继承ValueDescription的枚举
     * @return 枚举值
     * @throws
     */
    public static <E extends Enum<E> & ValueDescription2> E getEnumFromInt(Collection<E> enumList, Class<E> eClass,
                                                                           int value) {
        E t = getEnumFromIntWithoutException(enumList, value);
        if (t == null) {
            throw new BaseKnownException(eClass.getSimpleName(), String.valueOf(value));
        }
        return t;
    }

    /**
     * 根据枚举的value获得枚举
     *
     * @param enumList 枚举全部list
     * @param value    值
     * @param <E>      继承ValueDescription的枚举
     * @return 枚举值 或 null
     */
    public static <E extends Enum<E> & ValueDescription2> E getEnumFromIntWithoutException(Collection<E> enumList,
                                                                                           int value) {
        return enumList.stream().filter(n -> n.getValue() == value).findAny().orElse(null);
    }

    /**
     * 根据枚举的value获得枚举
     *
     * @param enumList 枚举全部list
     * @param eClass   枚举类
     * @param value    值
     * @param <E>      继承ValueDescription的枚举
     * @return 枚举值
     * @throws
     */
    public static <E extends Enum<E> & ValueDescription3> E getEnumFromLong(Collection<E> enumList, Class<E> eClass,
                                                                            long value) {
        E t = getEnumFromLongWithoutException(enumList, value);
        if (t == null) {
            throw new BaseKnownException(eClass.getSimpleName(), String.valueOf(value));
        }
        return t;
    }

    /**
     * 根据枚举的value获得枚举
     *
     * @param enumList 枚举全部list
     * @param value    值
     * @param <E>      继承ValueDescription的枚举
     * @return 枚举值 或 null
     */
    public static <E extends Enum<E> & ValueDescription3> E getEnumFromLongWithoutException(Collection<E> enumList,
                                                                                            long value) {
        return enumList.stream().filter(n -> n.getValue() == value).findAny().orElse(null);
    }
}