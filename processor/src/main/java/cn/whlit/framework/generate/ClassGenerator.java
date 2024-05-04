package cn.whlit.framework.generate;

import cn.whlit.framework.ResultCode;
import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import cn.whlit.framework.processor.type.TypeMessage;
import cn.whlit.framework.validate.AbstractValidator;
import cn.whlit.framework.validate.CollectionValidator;
import cn.whlit.framework.validate.ObjectValidator;
import cn.whlit.framework.validate.Validate;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author WangHaiLong 2024/5/4 15:25
 */
public class ClassGenerator {

    private static final ClassName commonValidator = ClassName.get(ObjectValidator.class);
    private static final ClassName collectionValidator = ClassName.get(CollectionValidator.class);
    private static final ClassName abstractValidator = ClassName.get(AbstractValidator.class);
    private static final ClassName stringValidate = ClassName.get(ObjectValidator.class);


    public static final List<MethodGenerator> methodGenerators = List.of(new IntegerMethodGenerator());

    public static TypeSpec generate(ProcessContext context, TypeMessage typeMessage) {

        ClassName valClass = ClassName.get(typeMessage.getPackageName().toString(), typeMessage.getClassName().toString());
        ClassName validatorClass = ClassName.get("", String.format("%s%s", typeMessage.getClassName().toString(), context.className));

        TypeSpec.Builder builder = TypeSpec.classBuilder(validatorClass);
        builder.addModifiers(Modifier.PUBLIC);
        builder.superclass(ParameterizedTypeName.get(ClassName.get(AbstractValidator.class), validatorClass));

        builder.addField(FieldSpec.builder(valClass, "val", Modifier.PRIVATE, Modifier.FINAL).build());
        builder.addField(FieldSpec.builder(TypeName.get(String.class), "path", Modifier.PRIVATE, Modifier.FINAL).build());


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

        MethodSpec getVal = MethodSpec.methodBuilder("getVal")
                .addStatement("return val")
                .returns(valClass)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .build();
        builder.addMethod(getVal);

        MethodSpec getPath = MethodSpec.methodBuilder("getPath")
                .addStatement("return path")
                .returns(TypeName.get(String.class))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .build();
        builder.addMethod(getPath);

        MethodSpec getSelf = MethodSpec.methodBuilder("getSelf")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(validatorClass)
                .addStatement("return this")
                .build();
        builder.addMethod(getSelf);

        MethodSpec of = MethodSpec.methodBuilder("of")
                .addParameter(valClass, "val")
                .addParameter(ParameterizedTypeName.get(ClassName.get(BiConsumer.class), ClassName.get(ResultCode.class), ClassName.get(Validate.class)), "handler")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(validatorClass)
                .addStatement("return new $T(val, \"\", handler)", validatorClass)
                .build();
        builder.addMethod(of);

        for (FieldMessage field : typeMessage.getFields()) {
            methodGenerators.forEach(methodGenerator -> {
                if (methodGenerator.canGenerate(field)) {
                    MethodSpec methodSpec = methodGenerator.generate(field, validatorClass, context);
                    builder.addMethod(methodSpec);
                }
            });
        }

        return builder.build();
    }
}
