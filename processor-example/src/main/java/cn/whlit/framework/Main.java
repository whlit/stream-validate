package cn.whlit.framework;

import cn.whlit.framework.domain.Item;

/**
 * @author WangHaiLong 2024/5/4 18:37
 */
public class Main {

    public static void main(String[] args) {
        Item item = new Item();
        item.setId(1);
        ItemValidators.of(item, (resultCode, validate) -> System.out.println("验证不通过")).id(integerValidator -> integerValidator.isNull());
    }
}
