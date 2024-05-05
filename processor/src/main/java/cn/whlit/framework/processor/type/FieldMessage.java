package cn.whlit.framework.processor.type;

import com.squareup.javapoet.TypeName;

/**
 * @author WangHaiLong 2024/5/4 11:40
 */
public class FieldMessage {

    private String fieldName;
    private TypeName fieldType;
    private MethodMessage getter;
    private MethodMessage setter;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public TypeName getFieldType() {
        return fieldType;
    }

    public void setFieldType(TypeName fieldType) {
        this.fieldType = fieldType;
    }

    public MethodMessage getGetter() {
        return getter;
    }

    public void setGetter(MethodMessage getter) {
        this.getter = getter;
    }

    public MethodMessage getSetter() {
        return setter;
    }

    public void setSetter(MethodMessage setter) {
        this.setter = setter;
    }
}
