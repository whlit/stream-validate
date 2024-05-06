package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

import java.util.function.BiConsumer;

/**
 * @author WangHaiShort 2024/5/4 19:33
 */
public class ShortValidator extends AbstractValidator<ShortValidator> {

    private final Short val;
    private final String path;

    public ShortValidator(Short val, String path, BiConsumer<ResultCode, Validate> handler) {
        super(handler);
        this.val = val;
        this.path = path;
    }

    @Override
    protected ShortValidator getSelf() {
        return this;
    }

    @Override
    public Short getVal() {
        return val;
    }

    @Override
    public String getPath() {
        return path;
    }
}
