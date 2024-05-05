package cn.whlit.framework.domain;

import java.util.Date;
import java.util.List;

/**
 * @author WangHaiLong 2024/5/3 17:43
 */
public class Item {

    private Integer id;

    private Long number;

    private String name;

    private List<String> tags;

    private Item[] children;

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

    public Item[] getChildren() {
        return children;
    }

    public void setChildren(Item[] children) {
        this.children = children;
    }
}
