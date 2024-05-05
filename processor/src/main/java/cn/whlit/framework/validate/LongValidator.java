package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

import java.util.function.BiConsumer;

/**
 * @author WangHaiLong 2024/5/4 19:33
 */
public class LongValidator extends NumberValidator<LongValidator, Long> {

    private final Long val;
    private final String path;

    public LongValidator(Long val, String path, BiConsumer<ResultCode, Validate> handler) {
        super(handler);
        this.val = val;
        this.path = path;
    }

    @Override
    protected LongValidator getSelf() {
        return this;
    }

    @Override
    int compareTo(Long val) {
        return this.val.compareTo(val);
    }

    @Override
    public Long getVal() {
        return val;
    }

    @Override
    public String getPath() {
        return path;
    }
}
