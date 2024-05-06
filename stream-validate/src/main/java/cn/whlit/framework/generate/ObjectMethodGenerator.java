package cn.whlit.framework.generate;

import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import cn.whlit.framework.validate.ObjectValidator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import javax.lang.model.element.Modifier;
import java.util.function.Consumer;

/**
 * @author WangHaiLong 2024/5/4 20:15
 */
public class ObjectMethodGenerator implements MethodGenerator {

    public static final ClassName VALIDATOR = ClassName.get(ObjectValidator.class);
    public static final ParameterizedTypeName CONSUMER = ParameterizedTypeName.get(ClassName.get(Consumer.class), ClassName.get(ObjectValidator.class));

    @Override
    public boolean canGenerate(FieldMessage fieldMessage) {
        return fieldMessage.getFieldName() != null && fieldMessage.getGetter() != null;
    }

    @Override
    public MethodSpec generate(FieldMessage fieldMessage, ClassName validatorClass, ProcessContext context) {
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
