package cn.whlit.framework;

import cn.whlit.framework.domain.Item;
import cn.whlit.framework.validate.AbstractValidator;
import cn.whlit.framework.validate.StringValidator;

/**
 * @author WangHaiLong 2024/5/4 18:37
 */
public class Main {

    public static void main(String[] args) {
        Item item = new Item();
        item.setId(1);
        item.setName("item");
        item.setChildren(new Item[]{new Item()});

        ItemValidators.of(item, (resultCode, validate) -> {
                    // 处理结果，只要是不符合的情况都会调用这个处理逻辑
                    System.out.printf("result: %s, path: [%s], value: %s%n",
                            resultCode.getMsg(), validate.getPath(), validate.getVal());
                })
                // 校验item本身的属性
                .id(AbstractValidator::notNull)
                .name(name -> name.notEmpty().notEmpty())
                // 校验集合类型的属性
                .children(children ->
                        // 集合类型这个属性本身的校验
                        children.notNull()
                                // 使用forEach遍历集合的每一个元素，消费的元素为集合元素对应的校验器
                                .forEach(childItem ->
                                        childItem.notNull()
                                                .id(AbstractValidator::notNull)
                                                .name(StringValidator::notEmpty)
                                )
                ).tags(tags ->
                        tags.notNull()
                                .forEach(StringValidator::notEmpty
                                )
                ).number(number ->
                        number.notNull()
                                .biggerThan(0L)
                );
    }
}
