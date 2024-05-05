package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

import java.util.function.BiConsumer;

/**
 * @author WangHaiLong 2024/5/3 13:39
 */
public abstract class AbstractValidator<T> implements Validate {

    public static final String DEFAULT_PATH = "";
    protected final BiConsumer<ResultCode, Validate> handler;
    private boolean preChecked = false;
    private boolean isNull = false;

    protected AbstractValidator(BiConsumer<ResultCode, Validate> handler) {
        this.handler = handler;
    }

    protected abstract T getSelf();

    protected String splicingPath(String path) {
        return getPath() == null || getPath().isBlank() ? path : String.format("%s.%s", getPath(), path);
    }

    protected void invalid(ResultCode resultCode) {
        handler.accept(resultCode, this);
    }

    public T isNull() {
        if (!preChecked) {
            preCheck();
        }
        if (!isNull) {
            invalid(ResultCode.ARG_MUST_NULL);
        }
        return getSelf();
    }

    public T notNull() {
        if (!preChecked) {
            preCheck();
        }
        if (isNull) {
            invalid(ResultCode.ARG_NOT_NULL);
        }
        return getSelf();
    }

    protected boolean isValid() {
        if (!preChecked){
            notNull();
        }
        return !isNull;
    }

    private void preCheck() {
        isNull = getVal() == null;
        preChecked = true;
    }

}
