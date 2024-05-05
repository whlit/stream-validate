package cn.whlit.framework.generate;

import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import cn.whlit.framework.validate.CollectionValidator;
import cn.whlit.framework.validate.ObjectValidator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * @author WangHaiLong 2024/5/4 20:15
 */
public class CollectionMethodGenerator implements MethodGenerator {

    private static final ClassName VALIDATOR = ClassName.get(CollectionValidator.class);
    //    private static final ParameterizedTypeName CONSUMER = ParameterizedTypeName.get(ClassName.get(Consumer.class), ParameterizedTypeName.get(ClassName.get(CollectionValidator.class), ClassName.get(ObjectValidator.class), ClassName.get(Object.class)));
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

    /**
     * 生成集合校验方法
     * <br>
     * 示例结构如下
     * <br>
     * <pre>
     * public ItemValidators items(Consumer<CollectionValidator<ItemValidators, Item>> consumer) {
     *   // 前置空校验
     *   if (!isValid()) {
     *     return getSelf();
     *   }
     *   // 创建属性对应的校验器，也就是校验集合本身
     *   consumer.accept(new CollectionValidator<ItemValidators, Item>(
     *     val.getItems(),
     *     splicingPath("items"),
     *     handler,
     *     // 创建子元素的校验器 e: 子元素，p: 子元素的路径
     *     (e, p) -> new ObjectValidator(e, p, handler)
     *   ));
     *   return this;
     * }
     * </pre>
     *
     * @param fieldMessage   字段信息
     * @param validatorClass 校验器类
     * @param context        上下文
     * @return 校验方法
     */
    @Override
    public MethodSpec generate(FieldMessage fieldMessage, ClassName validatorClass, ProcessContext context) {
        // TODO 将子元素校验器类型改为正确的校验器
        ParameterizedTypeName fieldType = (ParameterizedTypeName) fieldMessage.getFieldType();
        TypeName subType = fieldType.typeArguments.get(0);
        ParameterizedTypeName CONSUMER = ParameterizedTypeName.get(
                ClassName.get(Consumer.class),
                ParameterizedTypeName.get(
                        ClassName.get(CollectionValidator.class),
                        ClassName.get(ObjectValidator.class),
                        subType
                )
        );
        return MethodSpec.methodBuilder(fieldMessage.getFieldName())
                .addModifiers(Modifier.PUBLIC)
                .returns(validatorClass)
                .addParameter(CONSUMER, "consumer")
                .beginControlFlow("if (!isValid())")
                .addStatement("return getSelf()")
                .endControlFlow()
                .addStatement("consumer.accept(new $T<$T, $T>(val.$N(), splicingPath($S), handler, (e, p) -> new $T(e, p, handler)))",
                        VALIDATOR,
                        ClassName.get(ObjectValidator.class),
                        subType,
                        fieldMessage.getGetter().getMethodName(),
                        fieldMessage.getFieldName(),
                        ClassName.get(ObjectValidator.class))
                .addStatement("return this")
                .build();
    }

}
