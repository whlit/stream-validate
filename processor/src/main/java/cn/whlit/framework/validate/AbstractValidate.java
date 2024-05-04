package cn.whlit.framework.validate;

import cn.whlit.framework.ArgInvalidException;
import cn.whlit.framework.ResultCode;

import java.util.function.Consumer;

/**
 * @author WangHaiLong 2024/5/3 13:39
 */
public abstract class AbstractValidate<T, E> implements Validate<E> {

    private Consumer<Validate<E>> handler;

    public abstract T getSelf();

    public T handle(Consumer<Validate<E>> handler){
        this.handler = handler;
        return getSelf();
    }

    public String splicingPath(String path){
        return getPath() == null || getPath().isBlank() ? path : String.format("%s.%s", getPath(), path);
    }

    public T isNull(){
        if (getVal() == null){
            return getSelf();
        }
        if (handler != null){
            handler.accept(this);
        }else {
            throw new ArgInvalidException(ResultCode.ARG_MUST_NULL, getPath());
        }
        return getSelf();
    }

    public T notNull(){
        if (getVal() == null){
            return getSelf();
        }
        if (handler != null){
            handler.accept(this);
        }else {
            throw new ArgInvalidException(ResultCode.ARG_NOT_NULL, getPath());
        }
        return getSelf();
    }




}
