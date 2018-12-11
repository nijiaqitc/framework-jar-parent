package com.njq.common.util.string;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import com.njq.common.util.date.DateUtil;

public class StringUtil2 extends StringUtil {
    public static final String STRING_LIST_SPLIT_CHARS = ",，";
    private static final Set<String> BLANK_STRINGS = new HashSet(2);
    private static final String EMOJI_RANGE_REGEX = "[\ud83c\udf00-\ud83d\uddff]|[\ud83d\ude00-\ud83d\ude4f]|[\ud83d\ude80-\ud83d\udeff]|[☀-⛿]|[\u2700-➿]";
    private static final Pattern PATTERN = Pattern.compile("[\ud83c\udf00-\ud83d\uddff]|[\ud83d\ude00-\ud83d\ude4f]|[\ud83d\ude80-\ud83d\udeff]|[☀-⛿]|[\u2700-➿]");
    private static final String EMOJI_RANGE_REGEX_EX = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";
    private static final Pattern PATTERN_EX = Pattern.compile("([\\x{10000}-\\x{10ffff}\ud800-\udfff])", 66);
    private static final String[] CHINESE_NUMBER_UNITS = new String[]{"", "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿", "百亿", "千亿", "万亿"};
    private static final char[] CHINESE_NUMBERS = new char[]{'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};

    public StringUtil2() {
    }

    public static String format(String pattern, Object... arguments) {
        List<Object> args = new ArrayList(arguments.length);
        Object[] var3 = arguments;
        int var4 = arguments.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Object argument = var3[var5];
            if (argument instanceof Number) {
                argument = String.valueOf(argument);
            } else if (argument instanceof Date) {
                argument = DateUtil.toDateString4((Date)argument);
            }  else if (argument instanceof DateTime) {
                argument = ((DateTime)argument).toString(DateUtil.DEFAULT_DATETIME_FORMATTER);
            } else if (argument instanceof LocalTime) {
                argument = ((LocalTime)argument).toString(DateUtil.DEFAULT_HOUR_MINUTE_FORMATTER);
            }

            args.add(argument);
        }

        return MessageFormat.format(pattern, args.toArray());
    }

    public static boolean isNotBlankAndUndefined(String input) {
        input = StringUtils.trim(input);
        if (StringUtils.isNotBlank(input)) {
            return true;
        } else {
            return !BLANK_STRINGS.contains(input);
        }
    }

    public static List<String> getInputParamStringList(String input) {
        if (StringUtils.isBlank(input)) {
            throw new IllegalArgumentException("输入为空");
        } else {
            List<String> list = new ArrayList();
            String[] codeStrings = StringUtils.split(input, ",，");
            String[] var3 = codeStrings;
            int var4 = codeStrings.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String codeString = var3[var5];
                if (!StringUtils.isBlank(codeString)) {
                    codeString = StringUtils.trim(codeString);
                    list.add(codeString);
                }
            }

            if (list.size() == 0) {
                return list;
            } else {
                return list;
            }
        }
    }

    public static String eraseEmojis(String input) {
        if (StringUtils.isBlank(input)) {
            return input;
        } else {
            Matcher matcher = PATTERN.matcher(input);
            StringBuffer sb = new StringBuffer();

            while(matcher.find()) {
                matcher.appendReplacement(sb, "");
            }

            matcher.appendTail(sb);
            return sb.toString();
        }
    }

    public static String eraseEmojisEx(String input) {
        if (StringUtils.isBlank(input)) {
            return input;
        } else {
            Matcher matcher = PATTERN_EX.matcher(input);
            StringBuffer sb = new StringBuffer();

            while(matcher.find()) {
                matcher.appendReplacement(sb, "");
            }

            matcher.appendTail(sb);
            return sb.toString();
        }
    }

    public static String subZeroAndDot(String numberString) {
        if (StringUtils.isBlank(numberString)) {
            return numberString;
        } else {
            if (StringUtils.contains(numberString, 46)) {
                numberString = numberString.replaceAll("0+?$", "");
                numberString = numberString.replaceAll("[.]$", "");
            }

            return numberString;
        }
    }

    public static String formatIntToChineseNumber(int number) {
        char[] val = String.valueOf(number).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < len; ++i) {
            String m = val[i] + "";
            int n = Integer.valueOf(m);
            boolean isZero = n == 0;
            String unit = CHINESE_NUMBER_UNITS[len - 1 - i];
            if (isZero) {
                if ('0' != val[i - 1]) {
                    sb.append(CHINESE_NUMBERS[n]);
                }
            } else {
                sb.append(CHINESE_NUMBERS[n]);
                sb.append(unit);
            }
        }

        return sb.toString();
    }

    public static String formatDecimalTochineseNumber(double decimal) {
        String decimals = String.valueOf(decimal);
        int decIndex = decimals.indexOf(".");
        int integ = Integer.valueOf(decimals.substring(0, decIndex));
        int dec = Integer.valueOf(decimals.substring(decIndex + 1));
        return formatIntToChineseNumber(integ) + "." + formatFractionalPart(dec);
    }

    private static String formatFractionalPart(int decimal) {
        char[] val = String.valueOf(decimal).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        char[] var4 = val;
        int var5 = val.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            char v = var4[var6];
            int n = Integer.valueOf(v + "");
            sb.append(CHINESE_NUMBERS[n]);
        }

        return sb.toString();
    }

    static {
        BLANK_STRINGS.add("null");
        BLANK_STRINGS.add("undefined");
    }
}