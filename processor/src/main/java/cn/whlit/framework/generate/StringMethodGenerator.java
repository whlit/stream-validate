package cn.whlit.framework.generate;

import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import cn.whlit.framework.validate.StringValidator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import javax.lang.model.type.TypeKind;
import java.util.function.Consumer;

/**
 * @author WangHaiLong 2024/5/4 20:15
 */
public class StringMethodGenerator implements MethodGenerator {

    public static final ClassName VALIDATOR = ClassName.get(StringValidator.class);
    public static final ParameterizedTypeName CONSUMER = ParameterizedTypeName.get(ClassName.get(Consumer.class), ClassName.get(StringValidator.class));

    @Override
    public boolean canGenerate(FieldMessage fieldMessage) {
        TypeKind typeKind = fieldMessage.getTypeKind();
        return typeKind == TypeKind.DECLARED &&
                fieldMessage.getFieldType().getClassName().toString().equals(String.class.getSimpleName());
    }

    @Override
    public MethodSpec generate(FieldMessage fieldMessage, ClassName validatorClass, ProcessContext context) {
        return MethodSpec.methodBuilder(fieldMessage.getFieldName().toString())
                .returns(validatorClass)
                .addParameter(CONSUMER, "consumer")
                .beginControlFlow("if (!isValid())")
                .addStatement("return getSelf()")
                .endControlFlow()
                .addStatement("consumer.accept(new $T(val.$N(), splicingPath($S), handler))",
                        VALIDATOR,
                        fieldMessage.getGetterName().toString(),
                        fieldMessage.getFieldName().toString())
                .addStatement("return this")
                .build();
    }

}
