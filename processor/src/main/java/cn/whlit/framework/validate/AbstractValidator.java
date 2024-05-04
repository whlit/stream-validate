package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

import java.util.function.BiConsumer;

/**
 * @author WangHaiLong 2024/5/3 13:39
 */
public abstract class AbstractValidator<T> implements Validate {

    public static final String DEFAULT_PATH = "";
    protected final BiConsumer<ResultCode, Validate> handler;

    protected AbstractValidator(BiConsumer<ResultCode, Validate> handler) {
        this.handler = handler;
    }

    public abstract T getSelf();

    protected String splicingPath(String path) {
        return getPath() == null || getPath().isBlank() ? path : String.format("%s.%s", getPath(), path);
    }

    void invalid(ResultCode resultCode) {
        handler.accept(resultCode, this);
    }

    public T isNull() {
        if (getVal() == null) {
            return getSelf();
        }
        invalid(ResultCode.ARG_MUST_NULL);
        return getSelf();
    }

    public T notNull() {
        if (getVal() != null) {
            return getSelf();
        }
        invalid(ResultCode.ARG_NOT_NULL);
        return getSelf();
    }


}
