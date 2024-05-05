package cn.whlit.framework.generate;

import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import cn.whlit.framework.validate.CollectionValidator;
import cn.whlit.framework.validate.ObjectValidator;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import java.util.function.Consumer;

/**
 * @author WangHaiLong 2024/5/4 20:15
 */
public class ArrayMethodGenerator implements MethodGenerator {

    private static final ClassName VALIDATOR = ClassName.get(CollectionValidator.class);
    private static final ParameterizedTypeName CONSUMER = ParameterizedTypeName.get(ClassName.get(Consumer.class), ClassName.get(CollectionValidator.class));

    @Override
    public boolean canGenerate(FieldMessage fieldMessage) {
        if (fieldMessage.getFieldName() == null || fieldMessage.getGetter() == null || fieldMessage.getFieldType() == null) {
            return false;
        }
        return fieldMessage.getFieldType().getClass() == ArrayTypeName.class &&
                // 不是基本类型的数组，基本类型的数组，只做非空校验
                !((ArrayTypeName) fieldMessage.getFieldType()).componentType.isPrimitive();
    }

    @Override
    public MethodSpec generate(FieldMessage fieldMessage, ClassName validatorClass, ProcessContext context) {
        ArrayTypeName fieldType = (ArrayTypeName) fieldMessage.getFieldType();
        return MethodSpec.methodBuilder(fieldMessage.getFieldName())
                .returns(validatorClass)
                .addParameter(CONSUMER, "consumer")
                .beginControlFlow("if (!isValid())")
                .addStatement("return getSelf()")
                .endControlFlow()
                .addStatement("consumer.accept(new $T<$T, $T>(val.$N(), splicingPath($S), handler, (e, p) -> new $T(e, p, handler)))",
                        VALIDATOR,
                        ClassName.get(ObjectValidator.class),
                        fieldType.componentType.isPrimitive() ? fieldType.componentType.box() : fieldType.componentType,
                        fieldMessage.getGetter().getMethodName(),
                        fieldMessage.getFieldName(),
                        ClassName.get(ObjectValidator.class))
                .addStatement("return this")
                .build();
    }

}
