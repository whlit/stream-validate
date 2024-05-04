package cn.whlit.framework.processor.type;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeKind;
import java.util.List;

/**
 * @author WangHaiLong 2024/5/4 11:39
 */
public class TypeMessage {
    private Name packageName;
    private Name className;
    private TypeKind typeKind;
    private ElementKind kind;
    private List<FieldMessage> fields;

    public Name getPackageName() {
        return packageName;
    }

    public void setPackageName(Name packageName) {
        this.packageName = packageName;
    }

    public Name getClassName() {
        return className;
    }

    public void setClassName(Name className) {
        this.className = className;
    }

    public ElementKind getKind() {
        return kind;
    }

    public void setKind(ElementKind kind) {
        this.kind = kind;
    }

    public List<FieldMessage> getFields() {
        return fields;
    }

    public void setFields(List<FieldMessage> fields) {
        this.fields = fields;
    }

    public TypeKind getTypeKind() {
        return typeKind;
    }

    public void setTypeKind(TypeKind typeKind) {
        this.typeKind = typeKind;
    }
}
