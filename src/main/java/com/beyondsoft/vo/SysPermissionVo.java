package com.beyondsoft.vo;

import com.beyondsoft.entity.SysPermission;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 菜单Vo
 *
 * @author admin
 * @date 2019/11/11.
 */
@Data
public class SysPermissionVo extends SysPermission {

    private String parentContent;
    private String checkArr;
    private String title;
    private List<SysPermissionVo> childrens ;
    private boolean open;
    private boolean checked;
    public List<SysPermissionVo> getChildrens() {
        if(childrens==null) {
            childrens = new ArrayList<>();
        }
        return childrens;
    }

    public void setChildrens(List<SysPermissionVo> childrens) {
        this.childrens = childrens;
    }



}
