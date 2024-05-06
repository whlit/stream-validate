package cn.whlit.framework.generate;

import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

/**
 * @author WangHaiLong 2024/5/4 20:07
 */
public interface MethodGenerator {

    boolean canGenerate(FieldMessage fieldMessage);

    MethodSpec generate(FieldMessage fieldMessage, ClassName validatorClass, ProcessContext context);
}
