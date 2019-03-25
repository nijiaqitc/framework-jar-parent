package com.njq.common.util.string;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class StringUtil {
    public static final String DEFAULT_ENCODE = "UTF-8";
    public static final String QQ_NO_REGEX = "[1-9][0-9]{3,12}";
    public static final String PHONE_NO_REGEX = "1[0-9]{10}";
    public static final String OX0_OXFF_REGEX = "([0-9]{1,2}|[0-1][0-9]{2}|2[0-4][0-9]|25[0-5])";
    public static final String IP_REGEX = "([0-9]{1,2}|[0-1][0-9]{2}|2[0-4][0-9]|25[0-5])(?:\\.([0-9]{1,2}|[0-1][0-9]{2}|2[0-4][0-9]|25[0-5])){3}";
    public static final int MYSQL = 0;
    public static final int ORACLE = 1;
    public static final int DEFAULT_TYPE = 0;
    private static final long MAX_IP = 4294967295L;
    private static final String[] ENCODES = new String[]{"GBK", "ISO8859_1", "UTF-8", "GB2312", "BIG5", "Unicode", "EUC_KR", "SJIS", "EUC_JP", "ASCII"};
    private static final char DOT = '.';
    private static final long FIRST = 4278190080L;
    private static final long SECOND = 16711680L;
    private static final long THIRD = 65280L;
    private static final long FOURTH = 255L;
    private static final String REG_STR_CHECK_PHONE = "^1\\d{10}$";
    private static char[] CODE = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static char[] NEW_CODE = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static Pattern patternCheckPhone = Pattern.compile("^1\\d{10}$");

    public StringUtil() {
    }

    public static int hashCode(String str) {
        int h = 0;
        char[] val = str.toCharArray();
        char[] var3 = val;
        int var4 = val.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            char aVal = var3[var5];
            h = 31 * h + aVal;
        }

        return h;
    }

    public static String getChar(int value) {
        StringBuilder sb;
        for (sb = new StringBuilder(4); value > 0; value /= 26) {
            --value;
            sb.append((char) (value % 26 + 65));
        }

        return sb.reverse().toString();
    }

    public static String getEncoding(String str) {
        if (str == null) {
            throw new NullPointerException();
        } else {
            String[] var1 = ENCODES;
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                String encode = var1[var3];

                try {
                    if (str.equals(new String(str.getBytes(encode), encode))) {
                        return encode;
                    }
                } catch (UnsupportedEncodingException var6) {
                    ;
                }
            }

            return "";
        }
    }

    public static String changeCode(String text) {
        return changeCode(text, "UTF-8");
    }

    public static String changeCode(String text, String destCode) {
        if (isEmpty(text)) {
            return text;
        } else {
            String encode = getEncoding(text);
            return destCode.equals(encode) ? text : changeCode(text, getEncoding(text), destCode);
        }
    }

    public static String changeCode(String text, String srcCode, String destCode) {
        if (isEmpty(text)) {
            return text;
        } else {
            try {
                return new String(text.getBytes(srcCode), destCode);
            } catch (UnsupportedEncodingException var4) {
                return text;
            }
        }
    }

    public static long changeTime(String timeText, String timePattern) {
        SimpleDateFormat df = new SimpleDateFormat(timePattern);

        Date date;
        try {
            date = df.parse(timeText);
        } catch (ParseException var5) {
            throw new IllegalArgumentException("错误的参数格式");
        }

        return date.getTime();
    }

    public static boolean isEmpty(Object val) {
        if (val == null) {
            return true;
        } else if (val instanceof String) {
            String text = (String) val;
            if (text.length() == 0) {
                return true;
            } else {
                int len = text.length();

                for (int i = 0; i < len; ++i) {
                    if (!Character.isWhitespace(text.charAt(i))) {
                        return false;
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean notEmpty(Object text) {
        return !isEmpty(text);
    }

    public static boolean isNumber(String text) {
        return text != null && text.matches("\\s*-?\\d+\\s*");
    }

    public static boolean compare(Object src, Object dest) {
        if (src == null) {
            return dest == null;
        } else {
            return src == dest || src.equals(dest);
        }
    }

    public static long changeIp(String ipv4) {
        if (ipv4 == null) {
            throw new NullPointerException("参数不能为空");
        } else {
            ipv4 = ipv4.trim();
            if (!ipv4.matches("([0-9]{1,2}|[0-1][0-9]{2}|2[0-4][0-9]|25[0-5])(?:\\.([0-9]{1,2}|[0-1][0-9]{2}|2[0-4][0-9]|25[0-5])){3}")) {
                throw new IllegalArgumentException("IPv4地址格式错误");
            } else {
                long lip = 0L;
                int begin = 0;

                String num;
                for (int end = ipv4.indexOf(46); end > 0; end = ipv4.indexOf(46, begin)) {
                    num = ipv4.substring(begin, end);
                    lip = lip << 8 | Long.parseLong(num);
                    begin = end + 1;
                }

                num = ipv4.substring(begin);
                lip = lip << 8 | Long.parseLong(num);
                return lip;
            }
        }
    }

    public static String changeIp(long ip) {
        if (ip >= 0L && ip <= 4294967295L) {
            StringBuilder sb = new StringBuilder();
            sb.append((ip & 4278190080L) >> 24).append('.');
            sb.append((ip & 16711680L) >> 16).append('.');
            sb.append((ip & 65280L) >> 8).append('.');
            sb.append(ip & 255L);
            return sb.toString();
        } else {
            throw new IllegalArgumentException("IPv4地址数值超出范围");
        }
    }

    public static String getNetAddress(String ip, String mask) {
        return changeIp(getNetLongAddr(ip, mask));
    }

    public static long getNetLongAddr(String ip, String mask) {
        long ipLong = changeIp(ip);
        long maskLong = changeIp(mask);
        return ipLong & maskLong;
    }

    public static synchronized String getTimeString() {
        StringBuilder sb = new StringBuilder();

        for (long current = System.nanoTime(); current > 0L; current >>= 5) {
            sb.append(CODE[(int) (current & 31L)]);
        }

        return sb.toString();
    }

    public static synchronized String getNanoString() {
        StringBuilder sb = new StringBuilder();

        for (long current = System.nanoTime(); current > 0L; current >>= 4) {
            sb.append(NEW_CODE[(int) (current & 15L)]);
        }

        return sb.toString();
    }

    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String htmlEncode(String target) {
        if (target == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            int len = target.length();

            for (int i = 0; i < len; ++i) {
                char c = target.charAt(i);
                switch (c) {
                    case '\n':
                        sb.append("<br>");
                        break;
                    case '\r':
                        if (i < len - 1 && target.charAt(i + 1) == '\n') {
                            sb.append("<br>");
                            ++i;
                            break;
                        }

                        sb.append("<br>");
                        break;
                    case ' ':
                        if (i < len - 1 && target.charAt(i + 1) == ' ') {
                            sb.append(" &nbsp;");
                            ++i;
                            break;
                        }
                    default:
                        sb.append(c);
                        break;
                    case '"':
                        sb.append("&quot;");
                        break;
                    case '&':
                        sb.append("&amp;");
                        break;
                    case '<':
                        sb.append("&lt;");
                        break;
                    case '>':
                        sb.append("&gt;");
                        break;
                    case '¥':
                        sb.append("&yen;");
                        break;
                    case '©':
                        sb.append("&copy;");
                        break;
                    case '®':
                        sb.append("&reg;");
                        break;
                    case '€':
                        sb.append("&euro;");
                        break;
                    case '™':
                        sb.append("&#153;");
                }
            }

            return sb.toString();
        }
    }

    public static String realSql(String sql, Object... params) {
        return realSql(sql, 0, params);
    }

    public static String realSql(String sql, int type, Object... params) {
        if (sql == null) {
            return null;
        } else if (params == null) {
            return sql;
        } else {
            StringBuilder sb = new StringBuilder(sql);
            int index = 0;
            Object[] var5 = params;
            int var6 = params.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Object obj = var5[var7];
                index = sb.indexOf("?", index);
                if (index < 0) {
                    return sb.toString();
                }

                String value = getSqlTypeValue(obj, type);
                sb.replace(index, index + 1, value);
                index += value.length();
            }

            return sb.toString();
        }
    }

    private static String getSqlTypeValue(Object obj, int type) {
        if (obj instanceof String) {
            return "'" + obj.toString() + "'";
        } else if (obj instanceof java.sql.Date) {
            return getDate((java.sql.Date) obj, type);
        } else if (obj instanceof Timestamp) {
            return getTimestamp((Timestamp) obj, type);
        } else {
            return obj == null ? "" : obj.toString();
        }
    }

    private static String getTimestamp(Timestamp time, int type) {
        switch (type) {
            case 0:
                if (time == null) {
                    return "now()";
                }

                return "'" + getStampFormat().format(time) + "'";
            case 1:
                if (time == null) {
                    return "SYSTIMESTMAP";
                }

                return "TO_TIMESTAMP('" + getStampFormat().format(time) + "','yyyymmdd hh24:mi:ssxff')";
            default:
                return getTimestamp(time, 0);
        }
    }

    private static String getDate(java.sql.Date date, int type) {
        switch (type) {
            case 0:
                if (date == null) {
                    return "now()";
                }

                return "'" + getDateFormat().format(date) + "'";
            case 1:
                if (date == null) {
                    return "SYSDATE";
                }

                return "TO_DATE('" + getDateFormat().format(date) + "','yyyymmdd hh24:mi:ss')";
            default:
                return getDate(date, 0);
        }
    }

    public static String changeSQLExpression(Collection<String> data) {
        if (data != null && !data.isEmpty()) {
            StringBuilder sb = new StringBuilder("('");
            Iterator<String> itr = data.iterator();
            sb.append((String) itr.next());

            while (itr.hasNext()) {
                sb.append("','").append((String) itr.next());
            }

            sb.append("')");
            return sb.toString();
        } else {
            return "('')";
        }
    }

    public static String changeSQLExpression(String[] data) {
        int len = data.length;
        if (len == 0) {
            return "('')";
        } else {
            StringBuilder sb = new StringBuilder("('");
            sb.append(data[0]);

            for (int i = 1; i < len; ++i) {
                sb.append("','").append(data[i]);
            }

            sb.append("')");
            return sb.toString();
        }
    }

    public static String replaceText(String text, Map<String, Object> value) {
        return replaceText(text, new char[]{'$', '{'}, new char[]{'}'}, value);
    }

    public static String replaceText(String text, String start, String end, Map<String, Object> value) {
        return start != null && end != null ? replaceText(text, start.toCharArray(), end.toCharArray(), value) : text;
    }

    public static String replaceText(String text, char[] start, char[] end, Map<String, Object> value) {
        if (value != null && !value.isEmpty()) {
            int slen = start.length;
            int elen = end.length;
            if (text != null && text.length() >= slen + elen) {
                StringBuilder sb = new StringBuilder(text);
                int i = 0;
                int j = 0;
                int k = 0;
                int offset = 0;
                int len = text.length();

                for (int mark = -1; i < len; ++i) {
                    char c = text.charAt(i);
                    if (mark > -1) {
                        if (end[k] == c) {
                            ++k;
                            if (k == elen) {
                                String param = text.substring(mark + slen, i - elen + 1);
                                Object val = value.get(param);
                                if (val != null) {
                                    String v = val.toString();
                                    sb.replace(mark + offset, i + 1 + offset, v);
                                    int dis = i - mark + 1;
                                    offset += v.length() - dis;
                                }

                                mark = -1;
                                k = 0;
                            }
                        } else {
                            k = end[0] == c ? 1 : 0;
                            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && c != '_' && (c < '0' || c > '9')) {
                                mark = -1;
                                j = start[0] == c ? 1 : 0;
                            }
                        }
                    } else if (start[j] == c) {
                        ++j;
                        if (j == slen) {
                            mark = i - j + 1;
                            j = 0;
                        }
                    } else if (j > 0) {
                        j = start[0] == c ? 1 : 0;
                    }
                }

                return sb.toString();
            } else {
                return text;
            }
        } else {
            return text;
        }
    }

    public static String toFirstUpperCase(String text) {
        if (text != null && text.length() != 0) {
            char c = text.charAt(0);
            if (c < 'a' && c > 'z') {
                return text;
            } else {
                c = (char) (c & 223);
                char[] chr = text.toCharArray();
                chr[0] = c;
                return new String(chr);
            }
        } else {
            return text;
        }
    }

    public static String toFirstLowerCase(String text) {
        if (text != null && text.length() != 0) {
            char c = text.charAt(0);
            if (c < 'A' && c > 'Z') {
                return text;
            } else {
                c = (char) (c | 32);
                char[] chr = text.toCharArray();
                chr[0] = c;
                return new String(chr);
            }
        } else {
            return text;
        }
    }

    public static String[] getIpInfo(String ipAddrWithMask) {
        if (ipAddrWithMask == null) {
            throw new NullPointerException("address is empty");
        } else if (!ipAddrWithMask.matches("\\d+\\.\\d+\\.\\d+\\.\\d+/\\d+")) {
            throw new IllegalArgumentException(ipAddrWithMask + " is not match!");
        } else {
            int index = ipAddrWithMask.indexOf(47);
            String ipAddr = ipAddrWithMask.substring(0, index);
            String ipMaskNum = ipAddrWithMask.substring(index + 1);
            int count = 0;
            int num = Integer.parseInt(ipMaskNum);
            StringBuilder sb = new StringBuilder();

            while (true) {
                ++count;
                if (count >= 5) {
                    sb.deleteCharAt(sb.length() - 1);
                    return new String[]{ipAddr, sb.toString(), ipMaskNum};
                }

                if (num < 1) {
                    sb.append("0.");
                } else if (num >= 8) {
                    sb.append("255.");
                } else {
                    int c = 0;

                    int d;
                    for (d = 0; c < num; d += 1 << 8 - c) {
                        ++c;
                    }

                    sb.append(d).append('.');
                }

                num -= 8;
            }
        }
    }

    public static String urlEncode(String str, String code) {
        try {
            return code == null ? URLEncoder.encode(str, "UTF-8") : URLEncoder.encode(str, code);
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            return str;
        }
    }

    public static String urlDecode(String str, String code) {
        try {
            return code == null ? URLDecoder.decode(str, "UTF-8") : URLDecoder.decode(str, code);
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            return str;
        }
    }

    public static String[] urlsplit(String url) {
    	if(isEmpty(url)) {
    		return null;
    	}else {
    		String[] sa = url.split("\\?")[0].split("\\/");
    		return sa;
    	}
    }

    public static String urlPostfix(String url){
        String[] strArray=urlsplit(url);
        if(strArray != null){
            String[] st =strArray[strArray.length-1].split(".");
            if(st.length>1) {
                return st[1];
            }
        }
        return null;
    }

    public static String urlEncode(String str) {
        return urlEncode(str, (String) null);
    }

    public static String urlDecode(String str) {
        return urlDecode(str, (String) null);
    }

    public static String byteSubstring(String src, int byteBegin, int len) {
        if (src == null) {
            return null;
        } else {
            int slen = src.length();
            if (byteBegin >= slen) {
                throw new ArrayIndexOutOfBoundsException(byteBegin);
            } else {
                if (byteBegin < 0) {
                    byteBegin = 0;
                }

                if (len >= slen && byteBegin == 0) {
                    return src;
                } else {
                    slen -= byteBegin;
                    len = slen < len ? slen : len;
                    return new String(src.getBytes(), byteBegin, len);
                }
            }
        }
    }

    public static String traslateTime(long timeGap) {
        if (timeGap < 0L) {
            throw new IllegalArgumentException("The time gap is negative");
        } else {
            long day = timeGap / 86400000L;
            timeGap %= 86400000L;
            long hour = timeGap / 3600000L;
            timeGap %= 3600000L;
            long minute = timeGap / 60000L;
            timeGap %= 60000L;
            long second = timeGap / 1000L;
            timeGap %= 1000L;
            long[] num = new long[]{day, hour, minute, second, timeGap};
            String[] numName = new String[]{"天", "小时", "分", "秒", "毫秒"};
            StringBuilder sb = new StringBuilder();
            int len = num.length;

            for (int i = 0; i < len; ++i) {
                if (num[i] > 0L) {
                    sb.append(num[i]).append(numName[i]);
                }
            }

            return sb.toString();
        }
    }

    public static String getBandwidth(long bandwidth) {
        String[] units = new String[]{"b", "K", "M", "G", "T"};
        int unit = 0;
        long remainder = 0L;
        long divisor = 1000L;

        while (remainder == 0L && bandwidth >= divisor) {
            remainder = bandwidth % divisor;
            if (remainder == 0L) {
                ++unit;
                bandwidth /= divisor;
            } else {
                divisor = 1024L;
            }
        }

        if (bandwidth < divisor) {
            return bandwidth + units[unit];
        } else {
            double bw;
            for (bw = (double) bandwidth; bw > (double) divisor; ++unit) {
                bw /= (double) divisor;
            }

            return (new DecimalFormat("#.##")).format(bw) + units[unit];
        }
    }

    public static boolean isPhoneNO(String number) {
        return number == null ? false : number.matches("1[0-9]{10}");
    }

    public static boolean isQQNO(String number) {
        return number == null ? false : number.matches("[1-9][0-9]{3,12}");
    }

    public static boolean isIpAddr(String ip) {
        return ip != null && ip.matches("([0-9]{1,2}|[0-1][0-9]{2}|2[0-4][0-9]|25[0-5])(?:\\.([0-9]{1,2}|[0-1][0-9]{2}|2[0-4][0-9]|25[0-5])){3}");
    }

    public static String getUppers(String text) {
        if (text == null) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            int len = text.length();
            boolean first = true;

            for (int i = 0; i < len; ++i) {
                char c = text.charAt(i);
                if (first && c >= 'a' && c <= 'z') {
                    sb.append((char) (c & 223));
                    first = false;
                } else if (c >= 'A' && c <= 'Z') {
                    first = false;
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

    public static String toString(Collection<String> values, char gap) {
        if (values != null && !values.isEmpty()) {
            Iterator<String> itr = values.iterator();
            if (values.size() == 1) {
                return (String) itr.next();
            } else {
                StringBuilder sb = new StringBuilder((String) itr.next());

                while (itr.hasNext()) {
                    sb.append(gap).append((String) itr.next());
                }

                return sb.toString();
            }
        } else {
            return "";
        }
    }

    public static String getSafeString(String str) {
        return str == null ? "" : str;
    }

    public static boolean isEmpty(String val) {
        return val == null || "".equals(val.trim());
    }

    public static boolean notEmpty(String val) {
        return !isEmpty(val);
    }

    public static int checkPhone(String phone, String whilePhones, boolean openWhileCheck) {
        int result = 0;
        if (notEmpty(phone) && patternCheckPhone.matcher(phone).matches()) {
            if (openWhileCheck && !whilePhones.contains(phone)) {
                result = 1;
            }
        } else {
            result = 2;
        }

        return result;
    }

    public static String listToString(List<String> phones) {
        String target = "";
        if (null != phones && phones.size() != 0) {
            StringBuilder sBuffer = new StringBuilder(phones.size() * 11);
            Iterator var3 = phones.iterator();

            while (var3.hasNext()) {
                String string = (String) var3.next();
                sBuffer.append(",").append(string);
            }

            target = sBuffer.substring(1);
        }

        return target;
    }

    public static List<String> stringTolist(String string) {
        List<String> target = new ArrayList();
        if (notEmpty(string)) {
            target = Arrays.asList(string.split(","));
        }

        return (List) target;
    }

    private static DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    }

    private static DateFormat getStampFormat() {
        return new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
    }

    public static String converStringTime(Integer num) {
        if (num == null) {
            return null;
        } else {
            String time = num.toString();
            StringBuffer stb = new StringBuffer();
            int len = 6 - time.length();

            for (int i = 0; i < len; ++i) {
                stb.append("0");
            }

            return stb.append(time).toString();
        }
    }

    /**
     * 将Long数组转换成字符串
     *
     * @param ids
     * @return 2016-1-5 author njq
     */
    public static String LongToString(Long[] ids) {
        if (ids.length <= 0) {
            return "";
        }
        StringBuffer stb = new StringBuffer();
        for (long l : ids) {
            stb.append(l + ",");
        }
        return stb.toString().substring(0, stb.toString().length() - 1);
    }

    /**
     * 将String数组转换成字符串
     *
     * @param strs
     * @return 2016年6月30日 author njq
     */
    public static String StringsToString(String[] strs) {
        if (strs.length <= 0) {
            return "";
        }
        StringBuffer stb = new StringBuffer();
        for (String l : strs) {
            stb.append(l + ",");
        }
        return stb.toString().substring(0, stb.toString().length() - 1);
    }

    /**
     * 判断对象是否不为空
     *
     * @param obj
     * @return 2016年6月27日 author njq
     */
    public static boolean IsNotEmpty(Object obj) {
        return obj != null && obj != "";
    }

    /**
     * 判断对象是否为空
     *
     * @param value
     * @return
     */
    public static boolean IsEmpty(String value) {
        return value == null || value == "";
    }
}