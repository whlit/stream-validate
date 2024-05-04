package cn.whlit.framework.validate;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author WangHaiLong 2024/5/3 16:15
 */
public class CollectionValidate<T, E> extends AbstractValidate<CollectionValidate<T, E>, Collection<E>> {

    private final Collection<E> val;

    private final String path;

    private final Function<E, AbstractValidate<T, E>> elementValidate;

    public CollectionValidate(E[] val, String path, Function<E, AbstractValidate<T, E>> elementValidate) {
        this.val = List.of(val);
        this.path = path;
        this.elementValidate = elementValidate;
    }

    public CollectionValidate(Collection<E> val, String path, Function<E, AbstractValidate<T, E>> elementValidate) {
        this.val = val;
        this.path = path;
        this.elementValidate = elementValidate;
    }


    @Override
    public CollectionValidate<T, E> getSelf() {
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

    public CollectionValidate<T, E> forEach(Consumer<AbstractValidate<T, E>> consumer) {
        notNull();
        val.forEach(e -> consumer.accept(elementValidate.apply(e)));
        return getSelf();
    }

}
