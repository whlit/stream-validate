package cn.whlit.framework;

/**
 * @author WangHaiLong 2024/5/3 17:11
 */
public enum ResultCode {
    ARG_NOT_NULL("EA001", "参数不能为NULL"),
    ARG_MUST_NULL("EA002", "参数必须为NULL"),
    ARG_NOT_EMPTY("EA003", "参数不能为空"),
    ARG_MUST_EMPTY("EA004", "参数必须为空"),
    ;

    private final String code;
    private final String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
