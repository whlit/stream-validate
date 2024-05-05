package cn.whlit.framework.vali;

import cn.whlit.framework.domain.Product;
import cn.whlit.framework.domain.Prop;
import cn.whlit.framework.processor.Validator;

/**
 * @author WangHaiLong 2024/5/5 22:38
 */
@Validator({Prop.class, Product.class})
public interface Valid {
}
