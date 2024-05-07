package cn.whlit.framework.processor.type;

import com.squareup.javapoet.TypeName;

/**
 * @author WangHaiLong 2024/5/7 16:29
 */
public class ParamMessage {
    private String paramName;
    private TypeName paramType;
    private Integer paramIndex;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public TypeName getParamType() {
        return paramType;
    }

    public void setParamType(TypeName paramType) {
        this.paramType = paramType;
    }

    public Integer getParamIndex() {
        return paramIndex;
    }

    public void setParamIndex(Integer paramIndex) {
        this.paramIndex = paramIndex;
    }
}
