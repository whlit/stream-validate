package cn.whlit.framework.processor.type;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeKind;

/**
 * @author WangHaiLong 2024/5/4 11:40
 */
public class FieldMessage {

    private Name fieldName;
    private TypeMessage fieldType;
    private Modifier modifier;
    private TypeKind typeKind;


    public Name getFieldName() {
        return fieldName;
    }

    public void setFieldName(Name fieldName) {
        this.fieldName = fieldName;
    }

    public TypeMessage getFieldType() {
        return fieldType;
    }

    public void setFieldType(TypeMessage fieldType) {
        this.fieldType = fieldType;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public void setModifier(Modifier modifier) {
        this.modifier = modifier;
    }

    public TypeKind getTypeKind() {
        return typeKind;
    }

    public void setTypeKind(TypeKind typeKind) {
        this.typeKind = typeKind;
    }
}
