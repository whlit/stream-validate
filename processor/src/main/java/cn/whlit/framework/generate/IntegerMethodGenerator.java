package cn.whlit.framework.generate;

import cn.whlit.framework.ResultCode;
import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import cn.whlit.framework.validate.IntegerValidator;
import cn.whlit.framework.validate.Validate;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import javax.lang.model.type.TypeKind;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author WangHaiLong 2024/5/4 20:15
 */
public class IntegerMethodGenerator implements MethodGenerator {

    public static final ClassName INTEGER_VALIDATOR = ClassName.get(IntegerValidator.class);
    public static final ParameterizedTypeName CONSUMER = ParameterizedTypeName.get(ClassName.get(Consumer.class), ClassName.get(IntegerValidator.class));
    public static final ParameterizedTypeName BI_CONSUMER = ParameterizedTypeName.get(ClassName.get(BiConsumer.class), ClassName.get(ResultCode.class), ParameterizedTypeName.get(ClassName.get(Validate.class), ClassName.get(Integer.class)));

    @Override
    public boolean canGenerate(FieldMessage fieldMessage) {
        TypeKind typeKind = fieldMessage.getTypeKind();
        if (typeKind == TypeKind.INT) {
            return true;
        }
        if (typeKind == TypeKind.DECLARED &&
                fieldMessage.getFieldType().getClassName().toString().equals(Integer.class.getSimpleName())) {
            return true;
        }
        return false;
    }

    @Override
    public MethodSpec generate(FieldMessage fieldMessage, ClassName validatorClass, ProcessContext context) {
        return MethodSpec.methodBuilder(fieldMessage.getFieldName().toString())
                .returns(validatorClass)
                .addParameter(CONSUMER, "consumer")
                .addStatement("consumer.accept(new $T(val.$N(), splicingPath($S), handler))",
                        INTEGER_VALIDATOR,
                        fieldMessage.getGetterName().toString(),
                        fieldMessage.getFieldName().toString())
                .addStatement("return this")
                .build();
    }

}
