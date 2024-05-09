package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;
import cn.whlit.framework.util.StringUtils;

import java.util.function.BiConsumer;

/**
 * @author WangHaiLong 2024/5/3 17:02
 */
public class StringValidator extends AbstractValidator<StringValidator> {

    private final String val;
    private final String path;

    public StringValidator(String val, String path, BiConsumer<ResultCode, Validate> handler) {
        super(handler);
        this.val = val;
        this.path = path;
    }

    public StringValidator of(String val, BiConsumer<ResultCode, Validate> handler) {
        return new StringValidator(val, path, handler);
    }

    @Override
    protected StringValidator getSelf() {
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

    public StringValidator notEmpty() {
        if (StringUtils.isEmpty(val)) {
            invalid(ResultCode.ARG_NOT_EMPTY);
        }
        return this;
    }

    public StringValidator isEmpty() {
        if (StringUtils.isNotEmpty(val)) {
            invalid(ResultCode.ARG_MUST_EMPTY);
        }
        return this;
    }

    public StringValidator notBlank() {
        if (StringUtils.isBlank(val)) {
            invalid(ResultCode.ARG_NOT_EMPTY);
        }
        return this;
    }

    public StringValidator isBlank() {
        if (StringUtils.isNotBlank(val)) {
            invalid(ResultCode.ARG_MUST_EMPTY);
        }
        return this;
    }
}
