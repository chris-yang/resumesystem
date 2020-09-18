package com.beyondsoft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.beyondsoft.entity.SysRole;
import com.beyondsoft.entity.SysUser;
import com.beyondsoft.entity.SysUserRole;
import com.beyondsoft.service.LoginService;
import com.beyondsoft.service.SysRoleService;
import com.beyondsoft.service.SysUserRoleService;
import com.beyondsoft.service.SysUserService;
import com.beyondsoft.utils.HttpResultEnum;
import com.beyondsoft.utils.Result;
import com.beyondsoft.vo.SysUserRoleVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description 登陆验证
 *
 * @author admin
 * @date 2019/11/08.
 */
@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public Result login(String userName, String password) {
        Result r = null;
        if (StringUtils.isBlank(userName)) {
            r = new Result(HttpResultEnum.LOGIN_CODE_500.getCode(), HttpResultEnum.LOGIN_CODE_500.getMessage());
            return r;
        }
        //密码加密
        String password2=new Md5Hash(password,userName,3).toString();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);// 传到MyAuthorizingRealm类中的方法进行认证
            Session session = currentUser.getSession();
            SysUser sysUser = sysUserService.findUserByEmail(userName);
            session.setAttribute("user", sysUser);
            r = new Result(HttpResultEnum.CODE_200.getCode(), HttpResultEnum.CODE_200.getMessage());
            List<SysUserRoleVo> sysUserRoleVos = sysUserRoleService.getRolesByUserId(sysUser.getId());
            r.setData(sysUserRoleVos);
        } catch (UnknownAccountException e) {
            // e.printStackTrace();
            r = new Result(HttpResultEnum.LOGIN_CODE_501.getCode(), HttpResultEnum.LOGIN_CODE_501.getMessage());
        }  catch (IncorrectCredentialsException e) {
            r = new Result(HttpResultEnum.LOGIN_CODE_502.getCode(), HttpResultEnum.LOGIN_CODE_502.getMessage());
        }   catch (AuthenticationException e) {
            r= new Result(HttpResultEnum.LOGIN_CODE_500.getCode(), HttpResultEnum.LOGIN_CODE_500.getMessage());
        }
        // Session session = currentUser.getSession();
            // session.setAttribute("userName", userName);
       /* }catch (Exception e) {
            e.printStackTrace();
            r = new Result(HttpStateEnum.UNAUTHORIZED.getIndex(),HttpStateEnum.UNAUTHORIZED.getName());
        }*/
        return r;
    }

    /**
     * 登录成功之后选择角色 进行登录
     * @param userName
     * @param password
     * @param roleId
     * @return
     */
    @Override
    public Result login(String userName, String password, String roleId) {
        Result r = new Result(HttpResultEnum.LOGIN_CODE_500.getCode(),HttpResultEnum.LOGIN_CODE_500.getMessage());
        Result result = this.login(userName,password);

        if(result!= null && result.getCode().equals(HttpResultEnum.CODE_200.getCode())) {
            //验证当前用户是否有当前选择的角色
            SysUser currentUser = (SysUser)SecurityUtils.getSubject().getPrincipal();
            QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",currentUser.getId());
            queryWrapper.eq("role_id",roleId);
            SysUserRole sysUserRole = sysUserRoleService.getOne(queryWrapper);
            if(sysUserRole!=null){
                r.setCode(HttpResultEnum.CODE_200.getCode());
                r.setMsg(HttpResultEnum.CODE_200.getMessage());
                SysRole sysRole = sysRoleService.getById(roleId);
                SecurityUtils.getSubject().getSession().setAttribute("role",sysRole);
            }
        }
        return r;
    }
}
