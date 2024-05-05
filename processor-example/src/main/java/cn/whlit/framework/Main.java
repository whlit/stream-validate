package cn.whlit.framework;

import cn.whlit.framework.domain.Item;

import java.util.List;

/**
 * @author WangHaiLong 2024/5/4 18:37
 */
public class Main {

    public static void main(String[] args) {
        Item item = new Item();
        item.setId(1);
        item.setItems(List.of(new Item()));
        ItemValidators.of(item, (resultCode, validate) -> System.out.println(validate.getPath() + resultCode.getMsg()))
                .id(integerValidator -> integerValidator.notNull().smallerThan(10))
                .name(stringValidator -> stringValidator.notNull().isBlank().isBlank())
                .description(stringValidator -> stringValidator.isNull().isBlank())
                .status(objectValidator -> objectValidator.isNull())
                .type(objectValidator -> objectValidator.isNull())
                .size(objectValidator -> objectValidator.isNull())
                .p(objectValidator -> objectValidator.isNull())
                .number(objectValidator -> objectValidator.isNull())
                .children(collectionValidator -> collectionValidator.notNull())
                .items(collectionValidator -> {
                    collectionValidator.notNull().forEach(item2 -> item2.notNull());
                })
        ;
    }
}
