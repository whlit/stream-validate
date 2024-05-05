package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

import java.util.Date;
import java.util.function.BiConsumer;

/**
 * @author WangHaiDate 2024/5/4 19:33
 */
public class DateValidator extends AbstractValidator<DateValidator> {

    private final Date val;
    private final String path;

    public DateValidator(Date val, String path, BiConsumer<ResultCode, Validate> handler) {
        super(handler);
        this.val = val;
        this.path = path;
    }

    @Override
    public DateValidator getSelf() {
        return this;
    }

    @Override
    public Date getVal() {
        return val;
    }

    @Override
    public String getPath() {
        return path;
    }
}
