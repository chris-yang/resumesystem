package com.beyondsoft.service;

import com.beyondsoft.utils.Result;

/**
 * Created by Administrator on 2019/11/08.
 */
public interface LoginService {
    /**
     * 验证用户 选择角色信息
     * @param userName
     * @param password
     * @return
     */
    public Result login(String userName, String password);

    /**
     * 登录成功之后选择角色 进行登录
     * @param userName
     * @param password
     * @param roleId
     * @return
     */
    public Result login(String userName, String password,String roleId);

}
