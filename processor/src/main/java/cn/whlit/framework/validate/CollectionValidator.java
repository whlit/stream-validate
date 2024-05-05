package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * @author WangHaiLong 2024/5/3 16:15
 */
public class CollectionValidator<T, E> extends AbstractValidator<CollectionValidator<T, E>> {

    private final Collection<E> val;
    private final String path;

    public CollectionValidator(Collection<E> val, String path, BiConsumer<ResultCode, Validate> handler) {
        super(handler);
        this.val = val;
        this.path = path;
    }

    @Override
    protected CollectionValidator<T, E> getSelf() {
        return this;
    }

    @Override
    public Collection<E> getVal() {
        return val;
    }

    @Override
    public String getPath() {
        return path;
    }


}
