package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

import java.util.function.BiConsumer;

/**
 * @author WangHaiLong 2024/5/4 19:33
 */
public class IntegerValidator extends NumberValidator<IntegerValidator, Integer> {

    private final Integer val;
    private final String path;

    public IntegerValidator(Integer val, String path, BiConsumer<ResultCode, Validate> handler) {
        super(handler);
        this.val = val;
        this.path = path;
    }

    @Override
    public IntegerValidator getSelf() {
        return this;
    }

    @Override
    int compareTo(Integer val) {
        return this.val.compareTo(val);
    }

    @Override
    public Integer getVal() {
        return val;
    }

    @Override
    public String getPath() {
        return path;
    }

}
