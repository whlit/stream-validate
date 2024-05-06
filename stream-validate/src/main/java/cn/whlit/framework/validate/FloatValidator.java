package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

import java.util.function.BiConsumer;

/**
 * @author WangHaiFloat 2024/5/4 19:33
 */
public class FloatValidator extends NumberValidator<FloatValidator, Float> {

    private final Float val;
    private final String path;

    public FloatValidator(Float val, String path, BiConsumer<ResultCode, Validate> handler) {
        super(handler);
        this.val = val;
        this.path = path;
    }

    @Override
    protected FloatValidator getSelf() {
        return this;
    }

    @Override
    public Float getVal() {
        return val;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    int compareTo(Float val) {
        return this.val.compareTo(val);
    }
}
