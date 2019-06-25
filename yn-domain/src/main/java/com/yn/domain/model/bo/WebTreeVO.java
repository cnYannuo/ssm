package com.yn.domain.model.bo;

import java.io.Serializable;

public class WebTreeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String label;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
