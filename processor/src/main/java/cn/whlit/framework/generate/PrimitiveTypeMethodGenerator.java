package cn.whlit.framework.generate;

import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import cn.whlit.framework.validate.*;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author WangHaiLong 2024/5/4 20:15
 */
public class PrimitiveTypeMethodGenerator implements MethodGenerator {

    public static final Map<TypeName, ClassName> VALIDATOR_MAP = new HashMap<>();

    static {
        VALIDATOR_MAP.put(TypeName.get(Integer.class), ClassName.get(IntegerValidator.class));
        VALIDATOR_MAP.put(TypeName.get(Long.class), ClassName.get(LongValidator.class));
        VALIDATOR_MAP.put(TypeName.get(Short.class), ClassName.get(ShortValidator.class));
        VALIDATOR_MAP.put(TypeName.get(Byte.class), ClassName.get(ByteValidator.class));
        VALIDATOR_MAP.put(TypeName.get(Float.class), ClassName.get(FloatValidator.class));
        VALIDATOR_MAP.put(TypeName.get(Double.class), ClassName.get(DoubleValidator.class));
        VALIDATOR_MAP.put(TypeName.get(Character.class), ClassName.get(CharacterValidator.class));
        VALIDATOR_MAP.put(TypeName.get(Boolean.class), ClassName.get(BooleanValidator.class));
        VALIDATOR_MAP.put(TypeName.INT, ClassName.get(IntegerValidator.class));
        VALIDATOR_MAP.put(TypeName.LONG, ClassName.get(LongValidator.class));
        VALIDATOR_MAP.put(TypeName.SHORT, ClassName.get(ShortValidator.class));
        VALIDATOR_MAP.put(TypeName.BYTE, ClassName.get(ByteValidator.class));
        VALIDATOR_MAP.put(TypeName.FLOAT, ClassName.get(FloatValidator.class));
        VALIDATOR_MAP.put(TypeName.DOUBLE, ClassName.get(DoubleValidator.class));
        VALIDATOR_MAP.put(TypeName.CHAR, ClassName.get(CharacterValidator.class));
        VALIDATOR_MAP.put(TypeName.BOOLEAN, ClassName.get(BooleanValidator.class));
    }

    @Override
    public boolean canGenerate(FieldMessage fieldMessage) {
        if (fieldMessage.getFieldName() == null || fieldMessage.getGetter() == null || fieldMessage.getFieldType() == null) {
            return false;
        }
        return fieldMessage.getFieldType().isPrimitive() || fieldMessage.getFieldType().isBoxedPrimitive();
    }

    @Override
    public MethodSpec generate(FieldMessage fieldMessage, ClassName validatorClass, ProcessContext context) {
        ClassName VALIDATOR = VALIDATOR_MAP.get(fieldMessage.getFieldType());
        ParameterizedTypeName CONSUMER = ParameterizedTypeName.get(ClassName.get(Consumer.class), VALIDATOR);
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
