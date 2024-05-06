package cn.whlit.framework.generate;

import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;
import java.util.function.Consumer;

/**
 * @author WangHaiLong 2024/5/4 20:15
 */
public class PrimitiveTypeMethodGenerator implements MethodGenerator {

    @Override
    public boolean canGenerate(FieldMessage fieldMessage) {
        if (fieldMessage.getFieldName() == null || fieldMessage.getGetter() == null || fieldMessage.getFieldType() == null) {
            return false;
        }
        return fieldMessage.getFieldType().isPrimitive() || fieldMessage.getFieldType().isBoxedPrimitive();
    }

    @Override
    public MethodSpec generate(FieldMessage fieldMessage, ClassName validatorClass, ProcessContext context) {
        TypeName VALIDATOR = context.getValidatorTypes().get(fieldMessage.getFieldType());
        ParameterizedTypeName CONSUMER = ParameterizedTypeName.get(ClassName.get(Consumer.class), VALIDATOR);
        return MethodSpec.methodBuilder(fieldMessage.getFieldName())
                .addModifiers(Modifier.PUBLIC)
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
