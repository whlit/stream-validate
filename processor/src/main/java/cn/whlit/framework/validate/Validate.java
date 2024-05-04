package cn.whlit.framework.validate;

/**
 * @author WangHaiLong 2024/5/3 17:06
 */
public interface Validate {

    /**
     * 获取验证对象
     *
     * @return 验证对象
     */
    Object getVal();

    /**
     * 获取验证对象路径
     *
     * @return 验证对象路径
     */
    String getPath();

}
