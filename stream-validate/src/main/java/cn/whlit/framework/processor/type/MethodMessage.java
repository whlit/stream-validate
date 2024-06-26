package cn.whlit.framework.processor.type;

import com.squareup.javapoet.TypeName;

import java.util.List;

/**
 * @author WangHaiLong 2024/5/5 17:03
 */
public class MethodMessage {

    private String methodName;
    private TypeName methodReturnType;
    private List<ParamMessage> methodParameters;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public TypeName getMethodReturnType() {
        return methodReturnType;
    }

    public void setMethodReturnType(TypeName methodReturnType) {
        this.methodReturnType = methodReturnType;
    }

    public List<ParamMessage> getMethodParameters() {
        return methodParameters;
    }

    public void setMethodParameters(List<ParamMessage> methodParameters) {
        this.methodParameters = methodParameters;
    }
}
