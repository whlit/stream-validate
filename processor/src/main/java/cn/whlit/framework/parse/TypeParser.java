package cn.whlit.framework.parse;

import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.TypeMessage;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangHaiLong 2024/5/4 15:27
 */
public class TypeParser {

    public static TypeMessage parse(ProcessContext context, TypeMirror typeMirror) {
        if (typeMirror == null) {
            return null;
        }
        TypeMessage typeMessage = new TypeMessage();
        Element element = context.typeUtils.asElement(typeMirror);
        typeMessage.setClassName(element.getSimpleName());
        typeMessage.setPackageName(context.elementUtils.getPackageOf(element).getQualifiedName());
        typeMessage.setKind(element.getKind());
        typeMessage.setTypeKind(typeMirror.getKind());

        List<? extends Element> enclosedElements = element.getEnclosedElements();

        if (enclosedElements == null) {
            return typeMessage;
        }

        typeMessage.setFields(new ArrayList<>());
        for (Element enclosedElement : enclosedElements) {
            if (enclosedElement.getKind().isField()) {
                typeMessage.getFields().add(FieldParser.parse(context, enclosedElement));
            }
        }
        for (Element enclosedElement : enclosedElements) {
            if (enclosedElement.getKind() == ElementKind.METHOD) {
                typeMessage.getFields().stream()
                        .filter(fieldMessage -> String.format("get%s", fieldMessage.getFieldName().toString().toLowerCase())
                                .equals(enclosedElement.getSimpleName().toString().toLowerCase()))
                        .forEach(fieldMessage -> fieldMessage.setGetterName(enclosedElement.getSimpleName()));
            }
        }

        return typeMessage;
    }

}
