package cn.whlit.framework;

import cn.whlit.framework.domain.Item;

/**
 * @author WangHaiLong 2024/5/4 18:37
 */
public class Main {

    public static void main(String[] args) {
        Item item = new Item();
        item.setId(1);
        ItemValidators.of(item, (resultCode, validate) -> System.out.println(validate.getPath() + resultCode.getMsg()))
                .id(integerValidator -> integerValidator.notNull())
                .name(stringValidator -> stringValidator.isNull())
                .description(stringValidator -> stringValidator.isNull())
                .status(objectValidator -> objectValidator.isNull())
                .type(objectValidator -> objectValidator.isNull())
                .size(objectValidator -> objectValidator.isNull())
                .p(objectValidator -> objectValidator.isNull())
                .number(objectValidator -> objectValidator.isNull())
        ;
    }
}
