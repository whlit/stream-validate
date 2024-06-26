package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

import java.util.function.BiConsumer;

/**
 * @author WangHaiDouble 2024/5/4 19:33
 */
public class DoubleValidator extends NumberValidator<DoubleValidator, Double> {

    private final Double val;
    private final String path;

    public DoubleValidator(Double val, String path, BiConsumer<ResultCode, Validate> handler) {
        super(handler);
        this.val = val;
        this.path = path;
    }

    @Override
    protected DoubleValidator getSelf() {
        return this;
    }

    @Override
    public Double getVal() {
        return val;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    int compareTo(Double val) {
        return this.val.compareTo(val);
    }
}
