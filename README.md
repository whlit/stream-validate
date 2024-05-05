# Stream Validate

一个像Stream一样使用的数据校验工具

- 使用方式

新建一个类或者接口，在类或者接口上添加@Validator注解，注解中添加需要校验的类，编译后会自动生成以校验类为前缀，以被注解的类名为后缀的校验类，


``` java [配置类]
package cn.whlit.framework;

import cn.whlit.framework.domain.Item;
import cn.whlit.framework.processor.Validator;

@Validator(Item.class)
public interface Validators {

}
```


<details>

<summary>被校验类-Item</summary>

```java [被校验类]
package cn.whlit.framework.domain;

import java.util.List;

public class Item {

    private Integer id;

    private Long number;

    private String name;

    private List<String> tags;

    private List<Item> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Item> getChildren() {
        return children;
    }

    public void setChildren(List<Item> children) {
        this.children = children;
    }
}

```

</details>

生成的类是位于项目的target/generated-sources/annotations目录下，包名是被注解的类所在包名，可以添加多个被注解的类进行配置，需要校验的类只能在一个`@Validator`注解中配置，重复配置可能生成的类会出现不可预料的错误。

<details>

<summary>生成的校验类-ItemValidators</summary>

``` java [生成的校验类]
package cn.whlit.framework;

import cn.whlit.framework.domain.Item;
import cn.whlit.framework.validate.AbstractValidator;
import cn.whlit.framework.validate.CollectionValidator;
import cn.whlit.framework.validate.IntegerValidator;
import cn.whlit.framework.validate.LongValidator;
import cn.whlit.framework.validate.StringValidator;
import cn.whlit.framework.validate.Validate;
import java.lang.Override;
import java.lang.String;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ItemValidators extends AbstractValidator<ItemValidators> {
  private final Item val;

  private final String path;

  public ItemValidators(Item val, String path, BiConsumer<ResultCode, Validate> handler) {
    super(handler);
    this.val = val;
    this.path = path;
  }

  @Override
  public Item getVal() {
    return val;
  }

  @Override
  public String getPath() {
    return path;
  }

  @Override
  public ItemValidators getSelf() {
    return this;
  }

  public static ItemValidators of(Item val, BiConsumer<ResultCode, Validate> handler) {
    return new ItemValidators(val, "", handler);
  }

  ItemValidators id(Consumer<IntegerValidator> consumer) {
    if (!isValid()) {
      return getSelf();
    }
    consumer.accept(new IntegerValidator(val.getId(), splicingPath("id"), handler));
    return this;
  }

  ItemValidators number(Consumer<LongValidator> consumer) {
    if (!isValid()) {
      return getSelf();
    }
    consumer.accept(new LongValidator(val.getNumber(), splicingPath("number"), handler));
    return this;
  }

  ItemValidators name(Consumer<StringValidator> consumer) {
    if (!isValid()) {
      return getSelf();
    }
    consumer.accept(new StringValidator(val.getName(), splicingPath("name"), handler));
    return this;
  }

  public ItemValidators tags(Consumer<CollectionValidator<StringValidator, String>> consumer) {
    if (!isValid()) {
      return getSelf();
    }
    consumer.accept(new CollectionValidator<StringValidator, String>(val.getTags(), splicingPath("tags"), handler, (e, p) -> new StringValidator(e, p, handler)));
    return this;
  }

  public ItemValidators children(Consumer<CollectionValidator<ItemValidators, Item>> consumer) {
    if (!isValid()) {
      return getSelf();
    }
    consumer.accept(new CollectionValidator<ItemValidators, Item>(val.getChildren(), splicingPath("children"), handler, (e, p) -> new ItemValidators(e, p, handler)));
    return this;
  }
}


```

</details>


- 使用校验类

``` java [使用校验类]

public class Main {

    public static void main(String[] args) {
        Item item = new Item();
        item.setId(1);
        item.setName("item");
        item.setChildren(List.of(new Item()));

        ItemValidators.of(item, (resultCode, validate) -> {
                    // 处理结果，只要是不符合的情况都会调用这个处理逻辑
                    System.out.println(String.format("result: %s, path: [%s], value: %s",
                            resultCode.getMsg(), validate.getPath(), validate.getVal()));
                })
                // 校验item本身的属性
                .id(id -> id.notNull())
                .name(name -> name.notEmpty())
                // 校验集合类型的属性
                .children(children ->
                        // 集合类型这个属性本身的校验
                        children.notNull()
                                // 使用forEach遍历集合的每一个元素，消费的元素为集合元素对应的校验器
                                .forEach(childItem ->
                                        childItem.notNull()
                                                .id(id -> id.notNull())
                                                .name(name -> name.notEmpty())
                                )
                ).tags(tags ->
                        tags.notNull()
                                .forEach(tag ->
                                        tag.notEmpty()
                                )
                );
    }
}
```

<details>

<summary>校验结果</summary>

```
result: 参数不能为NULL, path: [children[0].id], value: null
result: 参数不能为NULL, path: [children[0].name], value: null
result: 参数不能为空, path: [children[0].name], value: null
result: 参数不能为NULL, path: [tags], value: null
```

</details>