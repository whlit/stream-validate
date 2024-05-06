package cn.whlit.framework.processor.type;

import com.squareup.javapoet.TypeName;

import java.util.List;

/**
 * @author WangHaiLong 2024/5/4 11:39
 */
public class TypeMessage {

    private List<FieldMessage> fields;
    private TypeName type;
    private List<MethodMessage> methods;

    public List<FieldMessage> getFields() {
        return fields;
    }

    public void setFields(List<FieldMessage> fields) {
        this.fields = fields;
    }

    public TypeName getType() {
        return type;
    }

    public void setType(TypeName type) {
        this.type = type;
    }

    public List<MethodMessage> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodMessage> methods) {
        this.methods = methods;
    }
}
