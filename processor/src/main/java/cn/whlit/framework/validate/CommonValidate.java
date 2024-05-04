package cn.whlit.framework.validate;

/**
 * @author WangHaiLong 2024/5/3 14:51
 */
public class CommonValidate extends AbstractValidate<CommonValidate, Object>{

    private final Object val;

    private final String path;

    public CommonValidate(Object val, String path) {
        this.val = val;
        this.path = path;
    }

    public static CommonValidate of(Object val) {
        return new CommonValidate(val, "");
    }

    @Override
    public Object getVal() {
        return val;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public CommonValidate getSelf() {
        return this;
    }

}
