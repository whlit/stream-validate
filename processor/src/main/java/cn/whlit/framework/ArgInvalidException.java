package cn.whlit.framework;

/**
 * @author WangHaiLong 2024/5/3 17:15
 */
public class ArgInvalidException extends RuntimeException {

    private final ResultCode resultCode;

    public ArgInvalidException(ResultCode resultCode, Object... args) {
        super(resultCode.format(args));
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
