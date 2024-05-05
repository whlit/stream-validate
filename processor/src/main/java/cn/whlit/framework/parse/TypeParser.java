package cn.whlit.framework.parse;

import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import cn.whlit.framework.processor.type.MethodMessage;
import cn.whlit.framework.processor.type.TypeMessage;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangHaiLong 2024/5/4 15:27
 */
public class TypeParser {

    private static final String GETTER_PREFIX = "get";
    private static final String SETTER_PREFIX = "set";

    public static TypeMessage parse(ProcessContext context, TypeMirror typeMirror) {
        if (typeMirror == null) {
            return null;
        }
        TypeMessage typeMessage = new TypeMessage();
        typeMessage.setType(TypeName.get(typeMirror));
        typeMessage.setFields(new ArrayList<>());
        typeMessage.setMethods(new ArrayList<>());

        Element element = context.typeUtils.asElement(typeMirror);
        List<? extends Element> enclosedElements = element.getEnclosedElements();

        if (enclosedElements == null) {
            return typeMessage;
        }

        for (Element enclosedElement : enclosedElements) {
            if (enclosedElement.getKind().isField()) {
                FieldMessage fieldMessage = parseField(enclosedElement);
                typeMessage.getFields().add(fieldMessage);
            } else if (enclosedElement.getKind() == ElementKind.METHOD) {
                MethodMessage methodMessage = parseMethod((ExecutableElement) enclosedElement);
                typeMessage.getMethods().add(methodMessage);
            }
        }

        filterGetterAndSetter(typeMessage);

        return typeMessage;
    }

    private static MethodMessage parseMethod(ExecutableElement method) {
        MethodMessage methodMessage = new MethodMessage();
        methodMessage.setMethodName(method.getSimpleName().toString());
        methodMessage.setMethodReturnType(TypeName.get(method.getReturnType()));
        return methodMessage;
    }

    private static FieldMessage parseField(Element element) {
        TypeMirror fieldTypeMirror = element.asType();

        FieldMessage fieldMessage = new FieldMessage();
        fieldMessage.setFieldType(TypeName.get(fieldTypeMirror));
        fieldMessage.setFieldName(element.getSimpleName().toString());
        return fieldMessage;
    }

    private static void filterGetterAndSetter(TypeMessage typeMessage) {
        typeMessage.getFields().forEach(fieldMessage -> {
            typeMessage.getMethods().forEach(methodMessage -> {
                if (methodMessage.getMethodName().toLowerCase().equals(String.format("%s%s", SETTER_PREFIX, fieldMessage.getFieldName().toLowerCase()))) {
                    fieldMessage.setSetter(methodMessage);
                }
                if (methodMessage.getMethodName().toLowerCase().equals(String.format("%s%s", GETTER_PREFIX, fieldMessage.getFieldName().toLowerCase()))) {
                    fieldMessage.setGetter(methodMessage);
                }
            });
        });
    }

}
