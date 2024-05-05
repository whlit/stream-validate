package cn.whlit.framework.generate;

import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import cn.whlit.framework.validate.CollectionValidator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import javax.lang.model.type.TypeKind;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * @author WangHaiLong 2024/5/4 20:15
 */
public class CollectionMethodGenerator implements MethodGenerator {

    private static final ClassName VALIDATOR = ClassName.get(CollectionValidator.class);
    private static final ParameterizedTypeName CONSUMER = ParameterizedTypeName.get(ClassName.get(Consumer.class), ClassName.get(CollectionValidator.class));
    private static final List<String> SUPPORT_TYPE = List.of(
            Collection.class.getSimpleName(),
            List.class.getSimpleName(),
            ArrayList.class.getSimpleName(),
            LinkedList.class.getSimpleName(),
            Vector.class.getSimpleName(),
            Stack.class.getSimpleName(),
            CopyOnWriteArrayList.class.getSimpleName(),
            Set.class.getSimpleName(),
            HashSet.class.getSimpleName(),
            LinkedHashSet.class.getSimpleName(),
            TreeSet.class.getSimpleName(),
            SortedSet.class.getSimpleName(),
            EnumSet.class.getSimpleName()
    );

    @Override
    public boolean canGenerate(FieldMessage fieldMessage) {
        return fieldMessage.getTypeKind() == TypeKind.DECLARED
                && SUPPORT_TYPE.contains(fieldMessage.getFieldType().getClassName().toString());
    }

    @Override
    public MethodSpec generate(FieldMessage fieldMessage, ClassName validatorClass, ProcessContext context) {
        return MethodSpec.methodBuilder(fieldMessage.getFieldName().toString())
                .returns(validatorClass)
                .addParameter(CONSUMER, "consumer")
                .addStatement("consumer.accept(new $T(val.$N(), splicingPath($S), handler))",
                        VALIDATOR,
                        fieldMessage.getGetterName().toString(),
                        fieldMessage.getFieldName().toString())
                .addStatement("return this")
                .build();
    }

}
