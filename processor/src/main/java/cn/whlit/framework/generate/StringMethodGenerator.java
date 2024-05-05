package cn.whlit.framework.generate;

import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import cn.whlit.framework.validate.StringValidator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import java.util.function.Consumer;

/**
 * @author WangHaiLong 2024/5/4 20:15
 */
public class StringMethodGenerator implements MethodGenerator {

    public static final ClassName VALIDATOR = ClassName.get(StringValidator.class);
    public static final ParameterizedTypeName CONSUMER = ParameterizedTypeName.get(ClassName.get(Consumer.class), ClassName.get(StringValidator.class));

    @Override
    public boolean canGenerate(FieldMessage fieldMessage) {
        if (fieldMessage.getFieldName() == null || fieldMessage.getGetter() == null) {
            return false;
        }
        return fieldMessage.getFieldType().equals(ClassName.get(String.class));
    }

    @Override
    public MethodSpec generate(FieldMessage fieldMessage, ClassName validatorClass, ProcessContext context) {
        return MethodSpec.methodBuilder(fieldMessage.getFieldName())
                .returns(validatorClass)
                .addParameter(CONSUMER, "consumer")
                .beginControlFlow("if (!isValid())")
                .addStatement("return getSelf()")
                .endControlFlow()
                .addStatement("consumer.accept(new $T(val.$N(), splicingPath($S), handler))",
                        VALIDATOR,
                        fieldMessage.getGetter().getMethodName(),
                        fieldMessage.getFieldName())
                .addStatement("return this")
                .build();
    }

}
