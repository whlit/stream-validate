package cn.whlit.framework.domain;

import java.util.Date;
import java.util.List;

/**
 * @author WangHaiLong 2024/5/3 17:43
 */
public class Item {

    private Integer id;

    private int age;

    private Long number;

    private String name;

    private String description;

    private Boolean status;

    private Byte type;

    private byte[] img;

    private Short size;

    private Double discount;

    private Float lastPrice;

    private Character p;

    private Date createTime;

    private List<String> tags;

    private List<Item> items;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public Short getSize() {
        return size;
    }

    public void setSize(Short size) {
        this.size = size;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Float getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Float lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Character getP() {
        return p;
    }

    public void setP(Character p) {
        this.p = p;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Item[] getChildren() {
        return children;
    }

    public void setChildren(Item[] children) {
        this.children = children;
    }
}
