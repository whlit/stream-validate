package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

import java.util.function.BiConsumer;

/**
 * @author WangHaiLong 2024/5/3 14:51
 */
public class ObjectValidator extends AbstractValidator<ObjectValidator> {

    private final Object val;
    private final String path;

    public ObjectValidator(Object val, String path, BiConsumer<ResultCode, Validate> handler) {
        super(handler);
        this.val = val;
        this.path = path;
    }

    public static ObjectValidator of(Object val, BiConsumer<ResultCode, Validate> handler) {
        return new ObjectValidator(val, DEFAULT_PATH, handler);
    }

    @Override
    public Object getVal() {
        return val;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public ObjectValidator getSelf() {
        return this;
    }

}
