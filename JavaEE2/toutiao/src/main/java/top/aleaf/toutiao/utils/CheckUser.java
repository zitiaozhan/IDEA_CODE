package top.aleaf.toutiao.utils;

public class CheckUser {
    /**
     * 密码强度
     * <p>
     * 一、假定密码字符数范围6-16，除英文数字和字母外的字符都视为特殊字符：
     * 弱：^[0-9A-Za-z]{6,16}$
     * 中：^(?=.{6,16})[0-9A-Za-z]*[^0-9A-Za-z][0-9A-Za-z]*$
     * 强：^(?=.{6,16})([0-9A-Za-z]*[^0-9A-Za-z][0-9A-Za-z]*){2,}$
     * 二、假定密码字符数范围6-16，密码字符允许范围为ASCII码表字符：
     * 弱：^[0-9A-Za-z]{6,16}$
     * 中：^(?=.{6,16})[0-9A-Za-z]*[\x00-\x2f\x3A-\x40\x5B-\xFF][0-9A-Za-z]*$
     * 强：^(?=.{6,16})([0-9A-Za-z]*[\x00-\x2F\x3A-\x40\x5B-\xFF][0-9A-Za-z]*){2,}$
     *
     * @return Z = 字母 S = 数字 T = 特殊字符
     */
    public static int checkPassword(String passwordStr) {
        String regexZ = "[A-Za-z]{6,16}";
        String regexS = "[0-9]{6,16}";
        String regexT = "[-`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]{6,16}";
        String regexZT = "[-a-zA-Z`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]*[a-zA-Z]+[-`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]+[-a-zA-Z`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]{6,16}";
        String regexST = "[-\\d`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]*\\d+[-`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]+[-\\d`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]{6,16}";
        String regexZS = "[\\da-zA-Z]*\\d+[a-zA-Z]+[\\da-zA-Z]{6,16}";
        String regexZST = "(?=.*[a-z])(?=.*\\d)(?=.*[#@!~%^&*])[a-z\\d#@!~%^&*]{6,16}";
        int res = 0;

        if (passwordStr.matches(regexZ)) {
            res = 1;
        }
        if (passwordStr.matches(regexS)) {
            res = 1;
        }
        if (passwordStr.matches(regexT)) {
            res = 1;
        }
        if (passwordStr.matches(regexZT)) {
            res = 2;
        }
        if (passwordStr.matches(regexST)) {
            res = 2;
        }
        if (passwordStr.matches(regexZS)) {
            res = 2;
        }
        if (passwordStr.matches(regexZST)) {
            res = 3;
        }
        return res;
    }

    /**
     * 用户名合法性检验
     *
     * 由字母a～z(不区分大小写)、数字0～9、点、减号或下划线组成
     * 只能以字母开头，包含字符 数字 下划线，例如：beijing.2008
     * 用户名长度为4～18个字符
     * @param nameStr
     * @return
     */
    public static boolean checkUserName(String nameStr) {
        String patten = "[a-zA-Z]\\w{3,15}";
        boolean res = false;

        if (nameStr.matches(patten)) {
            res = true;
        }
        return res;
    }

}