package pqt.tmall.pojo;

import java.util.List;

public class Property {
    private Integer id;

    private Integer cid;

    private String name;

    private List<PropertyValue> propertyValues  ;

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setPropertyValues(List<PropertyValue> propertyValues) {
    }
}