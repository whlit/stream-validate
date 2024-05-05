package cn.whlit.framework.generate;

import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import cn.whlit.framework.processor.type.TypeMessage;
import cn.whlit.framework.validate.*;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import javax.lang.model.type.TypeKind;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author WangHaiLong 2024/5/4 20:15
 */
public class PrimitiveTypeMethodGenerator implements MethodGenerator {

    public static final Map<String, ClassName> PACK_PRIMITIVE = Map.of(
            Integer.class.getSimpleName(), ClassName.get(IntegerValidator.class),
            Long.class.getSimpleName(), ClassName.get(LongValidator.class),
            Short.class.getSimpleName(), ClassName.get(ShortValidator.class),
            Byte.class.getSimpleName(), ClassName.get(ByteValidator.class),
            Float.class.getSimpleName(), ClassName.get(FloatValidator.class),
            Double.class.getSimpleName(), ClassName.get(DoubleValidator.class),
            Character.class.getSimpleName(), ClassName.get(CharacterValidator.class),
            Boolean.class.getSimpleName(), ClassName.get(BooleanValidator.class)
    );

    public static final Map<TypeKind, ClassName> PRIMITIVE = Map.of(
            TypeKind.INT, ClassName.get(IntegerValidator.class),
            TypeKind.LONG, ClassName.get(LongValidator.class),
            TypeKind.SHORT, ClassName.get(ShortValidator.class),
            TypeKind.BYTE, ClassName.get(ByteValidator.class),
            TypeKind.FLOAT, ClassName.get(FloatValidator.class),
            TypeKind.DOUBLE, ClassName.get(DoubleValidator.class),
            TypeKind.CHAR, ClassName.get(CharacterValidator.class),
            TypeKind.BOOLEAN, ClassName.get(BooleanValidator.class)
    );

    @Override
    public boolean canGenerate(FieldMessage fieldMessage) {
        return fieldMessage.getTypeKind().isPrimitive() || isPackPrimitive(fieldMessage.getFieldType());
    }

    @Override
    public MethodSpec generate(FieldMessage fieldMessage, ClassName validatorClass, ProcessContext context) {
        ClassName VALIDATOR = fieldMessage.getTypeKind().isPrimitive() ?
                PRIMITIVE.get(fieldMessage.getTypeKind()) :
                PACK_PRIMITIVE.get(fieldMessage.getFieldType().getClassName().toString());
        ParameterizedTypeName CONSUMER = ParameterizedTypeName.get(ClassName.get(Consumer.class), VALIDATOR);
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

    private boolean isPackPrimitive(TypeMessage typeMessage) {
        return typeMessage != null &&
                typeMessage.getClassName() != null &&
                PACK_PRIMITIVE.containsKey(typeMessage.getClassName().toString());
    }

}
