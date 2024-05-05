package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

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
    public StringValidator getSelf() {
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
        if (!isValid()){
            notNull();
            return this;
        }
        if (val.isEmpty()) {
            invalid(ResultCode.ARG_NOT_EMPTY);
        }
        return this;
    }

    public StringValidator isEmpty() {
        if (!isValid()){
            notNull();
            return this;
        }
        if (val.isEmpty()) {
            return this;
        }
        invalid(ResultCode.ARG_MUST_EMPTY);
        return this;
    }

    public StringValidator notBlank() {
        if (!isValid()){
            notNull();
            return this;
        }
        if (val.isBlank()) {
            invalid(ResultCode.ARG_NOT_EMPTY);
        }
        return this;
    }

    public StringValidator isBlank() {
        if (!isValid()){
            notNull();
            return this;
        }
        if (val.isBlank()) {
            return this;
        }
        invalid(ResultCode.ARG_MUST_EMPTY);
        return this;
    }
}
