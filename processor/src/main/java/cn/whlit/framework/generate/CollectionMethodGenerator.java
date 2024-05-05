package cn.whlit.framework.generate;

import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import cn.whlit.framework.validate.CollectionValidator;
import cn.whlit.framework.validate.ObjectValidator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * @author WangHaiLong 2024/5/4 20:15
 */
public class CollectionMethodGenerator implements MethodGenerator {

    private static final ClassName VALIDATOR = ClassName.get(CollectionValidator.class);
    private static final ParameterizedTypeName CONSUMER = ParameterizedTypeName.get(ClassName.get(Consumer.class), ClassName.get(CollectionValidator.class));
    private static final List<ClassName> SUPPORT_TYPE = List.of(
            ClassName.get(Collection.class),
            ClassName.get(List.class),
            ClassName.get(ArrayList.class),
            ClassName.get(LinkedList.class),
            ClassName.get(Vector.class),
            ClassName.get(Stack.class),
            ClassName.get(CopyOnWriteArrayList.class),
            ClassName.get(Set.class),
            ClassName.get(HashSet.class),
            ClassName.get(LinkedHashSet.class),
            ClassName.get(TreeSet.class),
            ClassName.get(SortedSet.class),
            ClassName.get(EnumSet.class)
    );

    @Override
    public boolean canGenerate(FieldMessage fieldMessage) {
        if (fieldMessage.getFieldName() == null || fieldMessage.getGetter() == null || fieldMessage.getFieldType() == null) {
            return false;
        }
        if (fieldMessage.getFieldType().getClass() == ParameterizedTypeName.class) {
            ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) fieldMessage.getFieldType();
            return SUPPORT_TYPE.contains(parameterizedTypeName.rawType);
        }
        return false;
    }

    @Override
    public MethodSpec generate(FieldMessage fieldMessage, ClassName validatorClass, ProcessContext context) {
        ParameterizedTypeName fieldType = (ParameterizedTypeName) fieldMessage.getFieldType();
        return MethodSpec.methodBuilder(fieldMessage.getFieldName())
                .returns(validatorClass)
                .addParameter(CONSUMER, "consumer")
                .beginControlFlow("if (!isValid())")
                .addStatement("return getSelf()")
                .endControlFlow()
                .addStatement("consumer.accept(new $T<$T, $T>(val.$N(), splicingPath($S), handler, (e, p) -> new $T(e, p, handler)))",
                        VALIDATOR,
                        ClassName.get(ObjectValidator.class),
                        fieldType.typeArguments.get(0),
                        fieldMessage.getGetter().getMethodName(),
                        fieldMessage.getFieldName(),
                        ClassName.get(ObjectValidator.class))
                .addStatement("return this")
                .build();
    }

}
