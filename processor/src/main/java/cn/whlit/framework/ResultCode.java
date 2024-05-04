package cn.whlit.framework;

/**
 * @author WangHaiLong 2024/5/3 17:11
 */
public enum ResultCode {
    ARG_NOT_NULL("EA001", "参数不能为NULL: '%s'"),
    ARG_MUST_NULL("EA002", "参数必须为NULL: '%s"),
    ARG_NOT_EMPTY("EA003", "参数不能为空: '%s'"),
    ARG_MUST_EMPTY("EA004", "参数必须为空: '%s'"),
    ;

    private final String code;
    private final String format;

    ResultCode(String code, String format) {
        this.code = code;
        this.format = format;
    }

    public String getCode() {
        return code;
    }

    public String getFormat() {
        return format;
    }

    public String format(Object... args) {
        return String.format(format, args);
    }
}
