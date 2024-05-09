package cn.whlit.framework.validate;

import cn.whlit.framework.ResultCode;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @author WangHaiLong 2024/5/3 16:15
 */
public class CollectionValidator<T, E> extends AbstractValidator<CollectionValidator<T, E>> {

    private final Collection<E> val;
    private final String path;
    private final BiFunction<E, String, T> createSubValidator;

    public CollectionValidator(Collection<E> val, String path, BiConsumer<ResultCode, Validate> handler, BiFunction<E, String, T> createSubValidator) {
        super(handler);
        this.val = val;
        this.path = path;
        this.createSubValidator = createSubValidator;
    }

    public CollectionValidator(E[] val, String path, BiConsumer<ResultCode, Validate> handler, BiFunction<E, String, T> createSubValidator) {
        super(handler);
        this.val = val == null ? null : Arrays.asList(val);
        this.path = path;
        this.createSubValidator = createSubValidator;
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

    public CollectionValidator<T, E> forEach(Consumer<T> consumer) {
        if (!isPresent()) {
            return this;
        }
        AtomicInteger i = new AtomicInteger();
        val.forEach(item -> consumer.accept(createSubValidator.apply(item, String.format("%s[%d]", path, i.getAndIncrement()))));
        return this;
    }

    @Override
    public CollectionValidator<T, E> notNull() {
        super.notNull();
        return this;
    }

    @Override
    public CollectionValidator<T, E> isNull() {
        super.isNull();
        return this;
    }
}
