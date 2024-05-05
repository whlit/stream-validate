package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

import java.util.function.BiConsumer;

/**
 * @author WangHaiBoolean 2024/5/4 19:33
 */
public class BooleanValidator extends AbstractValidator<BooleanValidator> {

    private final Boolean val;
    private final String path;

    public BooleanValidator(Boolean val, String path, BiConsumer<ResultCode, Validate> handler) {
        super(handler);
        this.val = val;
        this.path = path;
    }

    @Override
    public BooleanValidator getSelf() {
        return this;
    }

    @Override
    public Boolean getVal() {
        return val;
    }

    @Override
    public String getPath() {
        return path;
    }
}
