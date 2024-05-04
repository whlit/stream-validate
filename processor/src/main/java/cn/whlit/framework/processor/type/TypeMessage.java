package cn.whlit.framework.processor.type;

import javax.lang.model.element.ElementKind;
import java.util.List;

/**
 * @author WangHaiLong 2024/5/4 11:39
 */
public class TypeMessage {
    private String packageName;
    private String className;
    private ElementKind kind;
    private List<FieldMessage> fields;
    private boolean isPrimitive;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
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

    public boolean isPrimitive() {
        return isPrimitive;
    }

    public void setPrimitive(boolean primitive) {
        isPrimitive = primitive;
    }
}
