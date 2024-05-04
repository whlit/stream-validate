package cn.whlit.framework.processor;

import cn.whlit.framework.processor.type.FieldMessage;
import cn.whlit.framework.processor.type.TypeMessage;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangHaiLong 2024/5/4 11:05
 */
public class TypeHandle {

    private final Elements elementUtils;
    private final Types typeUtils;

    public TypeHandle(Elements elementUtils, Types typeUtils) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
    }

    public TypeMessage getTypeMessage(TypeMirror typeMirror) {
        if (typeMirror == null) {
            return null;
        }
        TypeMessage typeMessage = new TypeMessage();
        Element element = typeUtils.asElement(typeMirror);
        typeMessage.setClassName(element.getSimpleName());
        typeMessage.setPackageName(elementUtils.getPackageOf(element).getQualifiedName());
        typeMessage.setKind(element.getKind());
        typeMessage.setTypeKind(typeMirror.getKind());

        List<? extends Element> enclosedElements = element.getEnclosedElements();

        if (enclosedElements == null) {
            return typeMessage;
        }
        typeMessage.setFields(new ArrayList<>());
        for (Element enclosedElement : enclosedElements) {
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                FieldMessage fieldMessage = new FieldMessage();

                fieldMessage.setFieldName(enclosedElement.getSimpleName());
                fieldMessage.setModifiers(enclosedElement.getModifiers());

                TypeMirror fieldTypeMirror = enclosedElement.asType();
                fieldMessage.setTypeKind(fieldTypeMirror.getKind());

                if (!fieldMessage.getTypeKind().isPrimitive()) {
                    TypeMessage fieldTypeMessage = new TypeMessage();
                    if (fieldMessage.getTypeKind() == TypeKind.ARRAY) {
                        fieldTypeMirror = ((ArrayType) fieldTypeMirror).getComponentType();
                    }
                    fieldTypeMessage.setTypeKind(fieldTypeMirror.getKind());
                    if (!fieldTypeMessage.getTypeKind().isPrimitive()){
                        Element fieldTypeElement = typeUtils.asElement(fieldTypeMirror);
                        fieldTypeMessage.setClassName(fieldTypeElement.getSimpleName());
                        fieldTypeMessage.setKind(fieldTypeElement.getKind());
                        fieldTypeMessage.setPackageName(elementUtils.getPackageOf(fieldTypeElement).getQualifiedName());
                    }

                    fieldMessage.setFieldType(fieldTypeMessage);
                }
                typeMessage.getFields().add(fieldMessage);
            }
        }

        return typeMessage;
    }


}
