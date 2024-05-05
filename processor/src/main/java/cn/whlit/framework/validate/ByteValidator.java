package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

import java.util.function.BiConsumer;

/**
 * @author WangHaiByte 2024/5/4 19:33
 */
public class ByteValidator extends AbstractValidator<ByteValidator> {

    private final Byte val;
    private final String path;

    public ByteValidator(Byte val, String path, BiConsumer<ResultCode, Validate> handler) {
        super(handler);
        this.val = val;
        this.path = path;
    }

    @Override
    public ByteValidator getSelf() {
        return this;
    }

    @Override
    public Byte getVal() {
        return val;
    }

    @Override
    public String getPath() {
        return path;
    }
}
