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

}
