package cn.whlit.framework.processor;

import cn.whlit.framework.validate.*;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.Messager;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WangHaiLong 2024/5/4 15:29
 */
public class ProcessContext {

    public final Elements elementUtils;
    public final Types typeUtils;
    public final Messager messager;
    private String packageName;
    private String classNameSuffix;
    private final Map<TypeName, TypeName> validatorTypes = new HashMap<>();

    public ProcessContext(Elements elementUtils, Types typeUtils, Messager messager) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
        this.messager = messager;
        validatorTypes.put(TypeName.get(Integer.class), ClassName.get(IntegerValidator.class));
        validatorTypes.put(TypeName.get(Long.class), ClassName.get(LongValidator.class));
        validatorTypes.put(TypeName.get(Short.class), ClassName.get(ShortValidator.class));
        validatorTypes.put(TypeName.get(Byte.class), ClassName.get(ByteValidator.class));
        validatorTypes.put(TypeName.get(Float.class), ClassName.get(FloatValidator.class));
        validatorTypes.put(TypeName.get(Double.class), ClassName.get(DoubleValidator.class));
        validatorTypes.put(TypeName.get(Character.class), ClassName.get(CharacterValidator.class));
        validatorTypes.put(TypeName.get(Boolean.class), ClassName.get(BooleanValidator.class));
        validatorTypes.put(TypeName.INT, ClassName.get(IntegerValidator.class));
        validatorTypes.put(TypeName.LONG, ClassName.get(LongValidator.class));
        validatorTypes.put(TypeName.SHORT, ClassName.get(ShortValidator.class));
        validatorTypes.put(TypeName.BYTE, ClassName.get(ByteValidator.class));
        validatorTypes.put(TypeName.FLOAT, ClassName.get(FloatValidator.class));
        validatorTypes.put(TypeName.DOUBLE, ClassName.get(DoubleValidator.class));
        validatorTypes.put(TypeName.CHAR, ClassName.get(CharacterValidator.class));
        validatorTypes.put(TypeName.BOOLEAN, ClassName.get(BooleanValidator.class));
        validatorTypes.put(TypeName.get(String.class), ClassName.get(StringValidator.class));
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassNameSuffix() {
        return classNameSuffix;
    }

    public void setClassNameSuffix(String classNameSuffix) {
        this.classNameSuffix = classNameSuffix;
    }

    public Map<TypeName, TypeName> getValidatorTypes() {
        return validatorTypes;
    }
}
