package cn.whlit.framework.validate;

import cn.whlit.framework.ArgInvalidException;
import cn.whlit.framework.ResultCode;

/**
 * @author WangHaiLong 2024/5/3 17:02
 */
public class StringValidate extends AbstractValidate<StringValidate, String> {

    private final String val;

    private final String path;

    public StringValidate(String val, String path) {
        this.val = val;
        this.path = path;
    }

    public static StringValidate of(String val) {
        return new StringValidate(val, "");
    }

    @Override
    public StringValidate getSelf() {
        return this;
    }

    @Override
    public String getVal() {
        return val;
    }

    @Override
    public String getPath() {
        return path;
    }

    public StringValidate notEmpty() {
        notNull();
        if (val.isEmpty()) {
            throw new ArgInvalidException(ResultCode.ARG_NOT_EMPTY, splicingPath(path));
        }
        return this;
    }

    public StringValidate isEmpty() {
        notNull();
        if (val.isEmpty()) {
            return this;
        }
        throw new ArgInvalidException(ResultCode.ARG_MUST_EMPTY, splicingPath(path));
    }

    public StringValidate notBlank() {
        notNull();
        if (val.isBlank()) {
            throw new ArgInvalidException(ResultCode.ARG_NOT_EMPTY, splicingPath(path));
        }
        return this;
    }

    public StringValidate isBlank() {
        notNull();
        if (val.isBlank()) {
            return this;
        }
        throw new ArgInvalidException(ResultCode.ARG_MUST_EMPTY, splicingPath(path));
    }
}
