package utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author old
 * 常用的正则表达式
 * Created by haoshuai on 2017-8-16.
 */
public class RegexUtils {

    /**
     * 判断是否是正确的IP地址
     *
     * @param ip
     * @return boolean true,通过，false，没通过
     */
    public static boolean isIp(String ip) {
        if (null == ip || "".equals(ip)) {
            return false;
        }
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        return ip.matches(regex);
    }

    /**
     * 判断是否是正确的邮箱地址
     *
     * @param email
     * @return boolean true,通过，false，没通过
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) {
            return false;
        }
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return email.matches(regex);
    }

    /**
     * 判断是否含有中文，仅适合中国汉字，不包括标点
     * @param text
     * @return boolean true,通过，false，没通过
     */
    public static boolean isChinese(String text) {
        if (null == text || "".equals(text)) {
            return false;
        }
        String rgx = "[\u4e00-\u9fa5]";
        Pattern p = Pattern.compile(rgx);
        Matcher m = p.matcher(text);
        return m.find();
    }

    /**
     * 判断是否正整数
     *
     * @param number
     *            数字
     * @return boolean true,通过，false，没通过
     */
    public static boolean isNumber(String number) {
        if (null == number || "".equals(number)) {
            return false;
        }
        String regex = "[0-9]*";
        return number.matches(regex);
    }

    /**
     * 判读一个字符串中是否包含数字
     *
     * @param str 待检string
     * @return 包含 or 不包含
     */
    public static boolean containNum(String str) {

        if (StringUtils.isEmpty(str)) {
            return false;
        }
        String rgx = ".*\\d+.*";
        Pattern pattern = Pattern.compile(rgx);

        return pattern.matcher(str).matches();
    }

    /**
     * 判断几位小数(正数)
     *
     * @param decimal
     *            数字
     * @param count
     *            小数位数
     * @return boolean true,通过，false，没通过
     */
    public static boolean isDecimal(String decimal, int count) {
        if (null == decimal || "".equals(decimal)) {
            return false;
        }
        String regex = "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){" + count
                + "})?$";
        return decimal.matches(regex);
    }

    /**
     * 判断是否是身份证号码
     *
     * @param cardNumber
     *            身份证号码
     * @return boolean true,通过，false，没通过
     */
    public static boolean isIDCard(String cardNumber) {
        if (null == cardNumber || "".equals(cardNumber)) {
            return false;
        }
        String regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
        return cardNumber.matches(regex);
    }

    /**
     * 判断是否是手机号码
     *
     * @param phoneNumber
     *            手机号码
     * @return boolean true,通过，false，没通过
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        if (null == phoneNumber || "".equals(phoneNumber)) {
            return false;
        }
//        String regex = "^1[3|4|5|8][0-9]\\d{8}$";
        String regex = "^1\\d{10}$";
        return phoneNumber.matches(regex);
    }

    /**
     * 判断是否是固定电话
     *
     * @param telNumber
     *            固话号码
     * @return boolean true,通过，false，没通过
     */
    public static boolean isTelNumber(String telNumber) {
        if (null == telNumber || "".equals(telNumber)) {
            return false;
        }
        String regex = "^\\d{4}[-]{0,1}\\d{8}$";
        return telNumber.matches(regex);
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param text
     * @return boolean true,通过，false，没通过
     */
    public static boolean hasSpecialChar(String text) {
        if (null == text || "".equals(text)) {
            return false;
        }
        String rgx="[a-z]*[A-Z]*\\d*-*_*\\s*";
        if (text.replaceAll(rgx, "").length() == 0) {
            // 如果不包含特殊字符
            return true;
        }
        return false;
    }

    /**
     * 适应CJK（中日韩）字符集，部分中日韩的字是一样的
     */
    public static boolean isChinese2(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDigit(String digit) {
        String regex = "[+-]?[0-9]*";
        return Pattern.matches(regex, digit);
    }

    /**
     * 是否中文
     * @param c
     * @return
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    public static String getNumeric(String str) {
        String pattern = "\\d+";
        StringBuilder sb = new StringBuilder();
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        while (m.find()) {
            sb.append(m.group(0));
        }
        return sb.toString();
    }

}
