package com.beyondsoft.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 下拉框
 *
 * @author  admin
 * @date 2019/11/13.
 */
@Data
public class SelectVo<T, Object> {

    private Integer value;

    private String name;
    private List<T> children = new ArrayList<>();

    public SelectVo() {}

    public SelectVo(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
