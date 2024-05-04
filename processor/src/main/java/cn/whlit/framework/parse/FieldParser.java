package cn.whlit.framework.parse;

import cn.whlit.framework.processor.ProcessContext;
import cn.whlit.framework.processor.type.FieldMessage;
import cn.whlit.framework.processor.type.TypeMessage;

import javax.lang.model.element.Element;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * @author WangHaiLong 2024/5/4 15:37
 */
public class FieldParser {

    public static FieldMessage parse(ProcessContext context, Element element) {
        FieldMessage fieldMessage = new FieldMessage();

        fieldMessage.setFieldName(element.getSimpleName());
        fieldMessage.setModifiers(element.getModifiers());

        TypeMirror fieldTypeMirror = element.asType();

        fieldMessage.setTypeKind(fieldTypeMirror.getKind());

        if (!fieldMessage.getTypeKind().isPrimitive()) {
            TypeMessage fieldTypeMessage = new TypeMessage();
            if (fieldMessage.getTypeKind() == TypeKind.ARRAY) {
                fieldTypeMirror = ((ArrayType) fieldTypeMirror).getComponentType();
            }
            fieldTypeMessage.setTypeKind(fieldTypeMirror.getKind());
            if (!fieldTypeMessage.getTypeKind().isPrimitive()) {
                Element fieldTypeElement = context.typeUtils.asElement(fieldTypeMirror);
                fieldTypeMessage.setClassName(fieldTypeElement.getSimpleName());
                fieldTypeMessage.setKind(fieldTypeElement.getKind());
                fieldTypeMessage.setPackageName(context.elementUtils.getPackageOf(fieldTypeElement).getQualifiedName());
            }

            fieldMessage.setFieldType(fieldTypeMessage);
        }
        return fieldMessage;
    }

}
