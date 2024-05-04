package cn.whlit.framework.processor;

import javax.annotation.processing.Messager;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @author WangHaiLong 2024/5/4 15:29
 */
public class ProcessContext {

    public final Elements elementUtils;
    public final Types typeUtils;
    public final Messager messager;
    public final String packageName;
    public final String className;

    public ProcessContext(Elements elementUtils, Types typeUtils, Messager messager, String packageName, String className) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
        this.messager = messager;
        this.packageName = packageName;
        this.className = className;
    }


}
