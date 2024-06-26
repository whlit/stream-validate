package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

import java.util.function.BiConsumer;

/**
 * @author WangHaiLong 2024/5/5 13:02
 */
public abstract class NumberValidator<T, N extends Number> extends AbstractValidator<T> {

    protected NumberValidator(BiConsumer<ResultCode, Validate> handler) {
        super(handler);
    }

    protected abstract T getSelf();

    abstract int compareTo(N val);

    public T biggerThan(N val) {
        if (isPresent() && compareTo(val) <= 0) {
            invalid(ResultCode.ARG_NOT_BIGGER_THAN);
        }
        return getSelf();
    }

    public T smallerThan(N val) {
        if (isPresent() && compareTo(val) >= 0) {
            invalid(ResultCode.ARG_NOT_BIGGER_THAN);
        }
        return getSelf();
    }

    public T between(N start, N end) {
        if (isPresent() && (compareTo(start) < 0 || compareTo(end) > 0)) {
            invalid(ResultCode.ARG_NOT_BETWEEN);
        }
        return getSelf();
    }

}
