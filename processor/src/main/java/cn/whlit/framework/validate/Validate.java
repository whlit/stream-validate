package cn.whlit.framework.validate;

/**
 * @author WangHaiLong 2024/5/3 17:06
 */
public interface Validate<T> {

    /**
     * 获取验证对象
     *
     * @return 验证对象
     */
    T getVal();

    /**
     * 获取验证对象路径
     *
     * @return 验证对象路径
     */
    String getPath();

}
