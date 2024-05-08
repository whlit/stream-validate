package cn.whlit.framework.generate;

import cn.whlit.framework.ResultCode;
import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import cn.whlit.framework.processor.type.TypeMessage;
import cn.whlit.framework.validate.AbstractValidator;
import cn.whlit.framework.validate.Validate;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author WangHaiLong 2024/5/4 15:25
 */
public class ClassGenerator {

    public static final List<MethodGenerator> methodGenerators = new ArrayList<>();
    public static final ObjectMethodGenerator objectMethodGenerator = new ObjectMethodGenerator();

    static {
        methodGenerators.add(new PrimitiveTypeMethodGenerator());
        methodGenerators.add(new StringMethodGenerator());
        methodGenerators.add(new CollectionMethodGenerator());
        methodGenerators.add(new ArrayMethodGenerator());
        methodGenerators.add(new ObjectMethodGenerator());
    }

    public static TypeSpec generate(ProcessContext context, TypeMessage typeMessage) {

        ClassName validatorClass = ClassName.get("", String.format("%s%s", ((ClassName) typeMessage.getType()).simpleName(), context.getClassNameSuffix()));

        TypeSpec.Builder builder = TypeSpec.classBuilder(validatorClass);
        builder.addModifiers(Modifier.PUBLIC);

        //添加父类
        builder.superclass(ParameterizedTypeName.get(ClassName.get(AbstractValidator.class), validatorClass));

        // 添加val和path成员变量声明
        builder.addField(FieldSpec.builder(typeMessage.getType(), "val", Modifier.PRIVATE, Modifier.FINAL).build());
        builder.addField(FieldSpec.builder(TypeName.get(String.class), "path", Modifier.PRIVATE, Modifier.FINAL).build());

        // 通用方法
        constructor(typeMessage.getType(), builder);
        getVal(typeMessage.getType(), builder);
        getPath(builder);
        getSelf(validatorClass, builder);
        of(typeMessage.getType(), validatorClass, builder);

        // 添加校验对象的成员变量的校验方法
        fieldsMethod(context, typeMessage, validatorClass, builder);

        return builder.build();
    }

    private static void fieldsMethod(ProcessContext context, TypeMessage typeMessage, ClassName validatorClass, TypeSpec.Builder builder) {
        for (FieldMessage field : typeMessage.getFields()) {
            methodGenerators.stream()
                    .filter(methodGenerator -> methodGenerator.canGenerate(field))
                    .findFirst()
                    .ifPresent(methodGenerator -> {
                        MethodSpec methodSpec = methodGenerator.generate(field, validatorClass, context);
                        builder.addMethod(methodSpec);
                    });
        }
    }

    private static void of(TypeName valClass, ClassName validatorClass, TypeSpec.Builder builder) {
        MethodSpec of = MethodSpec.methodBuilder("of")
                .addParameter(valClass, "val")
                .addParameter(ParameterizedTypeName.get(ClassName.get(BiConsumer.class), ClassName.get(ResultCode.class), ClassName.get(Validate.class)), "handler")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(validatorClass)
                .addStatement("return new $T(val, \"\", handler)", validatorClass)
                .build();
        builder.addMethod(of);
    }

    private static void getSelf(TypeName validatorClass, TypeSpec.Builder builder) {
        MethodSpec getSelf = MethodSpec.methodBuilder("getSelf")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(validatorClass)
                .addStatement("return this")
                .build();
        builder.addMethod(getSelf);
    }

    private static void getPath(TypeSpec.Builder builder) {
        MethodSpec getPath = MethodSpec.methodBuilder("getPath")
                .addStatement("return path")
                .returns(TypeName.get(String.class))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .build();
        builder.addMethod(getPath);
    }

    private static void getVal(TypeName valClass, TypeSpec.Builder builder) {
        MethodSpec getVal = MethodSpec.methodBuilder("getVal")
                .addStatement("return val")
                .returns(valClass)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .build();
        builder.addMethod(getVal);
    }

    private static void constructor(TypeName valClass, TypeSpec.Builder builder) {
        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(valClass, "val")
                .addParameter(TypeName.get(String.class), "path")
                .addParameter(ParameterizedTypeName.get(ClassName.get(BiConsumer.class), ClassName.get(ResultCode.class), ClassName.get(Validate.class)), "handler")
                .addStatement("super(handler)")
                .addStatement("this.val = val")
                .addStatement("this.path = path")
                .build();
        builder.addMethod(constructor);
    }
}
