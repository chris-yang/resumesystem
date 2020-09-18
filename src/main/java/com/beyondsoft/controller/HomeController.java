package com.beyondsoft.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.beyondsoft.entity.*;
import com.beyondsoft.service.*;
import com.beyondsoft.utils.Const;
import com.beyondsoft.utils.Result;
import com.beyondsoft.vo.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * @description Home控制器类
 *
 * @author admin
 * @date 2019/11/07.
 */
@Controller
public class HomeController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysDataDicService sysDataDicService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private BaCustomerService baCustomerService;
    @Autowired
    private BaCityService baCityService;


    @GetMapping({"/","/login"})
    public String toLogin() {
        return "login";
    }

    @GetMapping("/home")
    public String index() {
        return "home";
    }

    @GetMapping("/403")
    public String unauthorizedRole(){
        System.out.println("------没有权限-------");
        return "error/403";
    }

    /**
     * 登录验证成功之后 当用前用的拥有的角色
     * @param username
     * @param password
     * @return
     */
    @ResponseBody
    @PostMapping("/doLogin")
    public Result verifyLogin(String username,String password) {
        Result r = loginService.login(username, password);
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return r;
    }

    /**
     * 根据当前用户名\密码\角色进行登录验证
     * @param username
     * @param password
     * @param roleId
     * @return
     */
    @ResponseBody
    @PostMapping("/verify/login")
    public Result login(String username,String password,String roleId) {
        Result r = loginService.login(username, password,roleId);
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return r;
    }

    /**
     * 系统日志
     * @return
     */
    @RequiresPermissions("syslog:list")
    @GetMapping("/sys-log/list")
    public String toSysLogPage(){
        return "syslog/list";
    }
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
///////////////////////////////////////////////////////////////////////////   用户跳转页面 start //////////////////////////////////////////////////////////////
    /**
     * @description 用户列表页面
     *
     * @return
     */
    @RequiresPermissions("user:list")
    @GetMapping("/sys-user/list")
    public String userPage() {
        return "user/list";
    }

    /**
     *@desciption 用户添加页面
     *
     * @return
     */
    @RequiresPermissions("user:add")
    @GetMapping("/sys-user/add")
    public String toUserAdd(Model model) {
        List<BaCity> cities = baCityService.getBaCityList();
        model.addAttribute("cities",cities);
        return "user/add";
    }

    /**
     * @description 用户编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("user:edit")
    @GetMapping("/sys-user/edit/{id}")
    public String toUserEdit(@PathVariable  Integer id, Model model) {
        // 根据ID 取得用户信息
        SysUser user = this.sysUserService.getOne(new QueryWrapper<SysUser>().eq("id", id));
        //SysUserRole userRole = sysUserRoleService.getOne(new QueryWrapper<SysUserRole>().eq("user_id",id));
        List<SysRoleVo> sysUserRoles = sysUserRoleService.getUserRoleIds(user.getId());
        SysUserVo userVo = new SysUserVo(user.getId(),user.getEmail(),null,user.getEmployeeNo(),user.getRealName(),user.getCity(),user.getMobile(),user.getEnabled(),user.getCreateUserId(),user.getCreateTime(),user.getDeptId(),user.getDelFlag(),null);
        //userVo.setRole(new SysRole().setId(userRole.getRoleId()));
        userVo.setRoleVos(sysUserRoles);
        // 根据部门ID，查出角色
        List<SysRole> roles = (List<SysRole>)sysRoleService.getRolesByDeptId(user.getDeptId()).getData();
        List<BaCity> cities = baCityService.getBaCityList();
        model.addAttribute("cities",cities);
        model.addAttribute("userVo",userVo);
        model.addAttribute("roles",roles);
        return "user/edit";
    }

    /**
     * @description 用户停用页面
     *
     * @auth admin
     * @date 2019-1-3
     * @return
     */
    @RequiresPermissions("user:stop")
    @GetMapping("/sys-user/stop/{id}")
    public String toUserStop(@PathVariable  Integer id, Model model) {
        // 根据数据字典 取得停用原因
        List<SysDataDicVo> stopTypeList = this.sysDataDicService.getDataDicSelectByParentCode(Const.USER_STOP_TYPE_CODE);
        model.addAttribute("stopTypeList", stopTypeList);
        model.addAttribute("userVo",sysUserService.getUserStop(id));
        // 同部门下的同角色用户
        SysUser user = this.sysUserService.getById(id);
        SysUserRole sur = this.sysUserRoleService.getRoleByUserId(id);
        List<SelectVo> userSelect = (List<SelectVo>) this.sysUserService.getUserSelectByRoleDept(sur.getRoleId(), id, user.getDeptId()).getData();
        model.addAttribute("userSelect", userSelect);
        return "user/stop";
    }

    /**
     * 密码修改页面
     * @return
     */
    @GetMapping("/sys-user/updatepassword")
    public String updatePassword() {
        return "user/updatepassword";
    }
    /////////////////////////////////////////////////////////////////////////////////   用户跳转页面 end //////////////////////////////////////////////////////////////

    @RequiresPermissions("role:list")
    @GetMapping("/sys-role/list")
    public String toRolePageList() {
        return "role/list";
    }

    /**
     * 跳转角色添加页面
     * @return
     */
    @RequiresPermissions({"role:toAdd","role:toUpdate"})
    @GetMapping("/sys-role/toAddorUpdate/{mode}/{id}")
    public String toRoleAdd(@PathVariable String mode,@PathVariable(required = false) Integer id,Model model) {
        if(Const.MODE_ADD.equals(mode)) {
            Map<SysPermission,List<Map<SysPermission, List<SysPermission>>>> maproot = sysPermissionService.getPermissons();
            model.addAttribute("maproot",maproot);
            model.addAttribute("mode",Const.MODE_ADD);
        } else if(Const.MODE_UPDADTE.equals(mode)){
            SysRole sysRole = sysRoleService.getById(id);
            Map<SysPermissionVo,List<Map<SysPermissionVo, List<SysPermissionVo>>>> maproot = sysPermissionService.getPermissons(id);
            model.addAttribute("maproot",maproot);
            model.addAttribute("sysRole",sysRole);
            model.addAttribute("mode",Const.MODE_UPDADTE);
        }
        return "role/add";
    }

    /**
     * 权限分配页
     * @param roleId
     * @param model
     * @return
     */
    @RequiresPermissions("role:toAssignRole")
    @GetMapping("/sys-role/toAssignRole/{roleId}")
    public String toAssignRole(@PathVariable(value="roleId") Integer roleId,Model model){
        model.addAttribute("roleId",roleId);
        return "role/assign_role";
    }
    /////////////////////////////////////////////////////////////////////////////////   角色页面 end //////////////////////////////////////////////////////////////
    /**
     * 菜单管理列表
     * @return
     */
    @RequiresPermissions("permission:list")
    @GetMapping("/sys-permission/list")
    public String toPermissionList() {
        return "permission/list";
    }

    /**
     * 添加菜单
     * @param id
     * @param mode
     * @param model
     * @return
     */
    @RequiresPermissions({"permission:toAdd","permission:toUpdate"})
    @GetMapping("/sys-permission/permContent")
    public String permissionIframeContent(@RequestParam(required =false,defaultValue = "1")  Integer id, @RequestParam(required = false,defaultValue = "") String mode, Model model) {
        //菜单类型
        List<SysDataDicVo> sysDataDicVos =sysDataDicService.getDataDicSelectByParentCode(Const.PERMISSION_TYPE_CODE);
        model.addAttribute("sysDataDicVos",sysDataDicVos);
        if("update".equals(mode)) {
            SysPermissionVo sysPermission =sysPermissionService.getSysPermissionVoById(id);
            model.addAttribute("mode","update");
            model.addAttribute("sysPermission",sysPermission);
        } else if ("add".equals(mode)) {
            SysPermissionVo sysPermissionP =sysPermissionService.getSysPermissionVoById(id);
            SysPermissionVo sysPermission = new SysPermissionVo();
            sysPermission.setParentId(id);
            sysPermission.setParentContent(sysPermissionP.getPName());
            sysPermission.setType(Const.MENU_TYPE_M);
            sysPermission.setSort(sysPermissionService.getAddSort(id));
            model.addAttribute("mode", "add");
            model.addAttribute("sysPermission",sysPermission);
        } else {
            SysPermissionVo sysPermission = new SysPermissionVo(); //sysPermissionService.getRootSysPermissionVoById(Const.PERMISSION_ROOT_ID);
            sysPermission.setType(Const.MENU_TYPE_B);
            model.addAttribute("mode","default");
            model.addAttribute("sysPermission",sysPermission);
        }
        return "permission/permContent";
    }
    /////////////////////////////////////////////////////////////////////////////////   权限页面 end //////////////////////////////////////////////////////////////

    /**
     * 城市页面
     * @return
     */
    @RequiresPermissions("city:list")
    @GetMapping("/ba-city/list")
    public String toCityList() {
        return "city/list";
    }

    /**
     * 城市 新增 修改
     * @param mode
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions({"city:toUpdate","city:toAdd"})
    @GetMapping("/ba-city/toAddorUpdate/{mode}/{id}")
    public String toBaCityAddOrUpdate(@PathVariable String mode,@PathVariable(required = false) Integer id,Model model) {
        if(Const.MODE_ADD.equals(mode)) {
            model.addAttribute("mode",Const.MODE_ADD);
            BaCity baCity = new BaCity();
            model.addAttribute("baCity",baCity);
        } else if(Const.MODE_UPDADTE.equals(mode)){
            BaCity baCity = baCityService.getById(id);
            model.addAttribute("baCity",baCity);
            model.addAttribute("mode",Const.MODE_UPDADTE);
        }
        return "city/add";
    }
    /////////////////////////////////////////////////////////////////////////////////   城市页面 end //////////////////////////////////////////////////////////////
    /**
     * 跳转到部门列表页
     * @return
     */
    @RequiresPermissions("dept:list")
    @GetMapping("/sys-dept/list")
    public String toDeptList() {
        return "dept/list";
    }

    /**
     * 添加部门
     * @param id
     * @param mode
     * @param model
     * @return
     */
    @RequiresPermissions({"dept:toUpdate","dept:toAdd"})
    @GetMapping("/sys-dept/deptContent")
    public String deptIframeContent(@RequestParam(required =false,defaultValue = "1") Integer id, @RequestParam(required = false,defaultValue = "view") String mode, Model model) {
        List<SysRoleVo> sysRoleVos = sysRoleService.getRoleVoExclude(Const.ROLE_CODE_ADMIN);
        if("update".equals(mode)) {
            SysDeptVo sysDept=sysDeptService.getSysDeptVoById(id);
            sysRoleVos = sysRoleService.getRoleVoByDeptId(sysDept.getId());
            model.addAttribute("sysRoleVos",sysRoleVos);
            model.addAttribute("mode","update");
            model.addAttribute("sysDept",sysDept);
        } else if ("add".equals(mode)) {
            SysDeptVo sysDept = new SysDeptVo();
            SysDeptVo sysDeptP=sysDeptService.getSysDeptVoById(id);
            sysDept.setSort(sysDeptService.getSortValue(id));
            sysDept.setParentId(id);
            sysDept.setParentContent(sysDeptP.getDeptName());
            model.addAttribute("mode", "add");
            model.addAttribute("sysDept",sysDept);
        } else {
            SysDeptVo sysDept=sysDeptService.getSysDeptVoById(id);
            if(sysDept!=null && sysDept.getId() !=null) {
                sysRoleVos = sysRoleService.getRoleVoByDeptId(sysDept.getId());
            } else {
                sysDept = new SysDeptVo();
                sysRoleVos = new ArrayList<>();
            }
            model.addAttribute("mode","view");
            model.addAttribute("sysDept",sysDept);
        }
        model.addAttribute("sysRoleVos",sysRoleVos);
        return "dept/deptContent";
    }

/////////////////////////////////////////////////////////////////////////////////   部门页面 end //////////////////////////////////////////////////////////////

    /**
     * 数据字典列表
     * @return
     */
    @RequiresPermissions("dic:list")
    @GetMapping("/sys-data-dic/list")
    public String toDatatDicList() {
        return "dic/list";
    }

    /**
     *@desciption 数据字典添加页面
     *
     * @return
     */
    @RequiresPermissions({"dic:toUpdate","dic:toAdd"})
    @GetMapping("/sys-data-dic/toAddOrUpdate/{mode}/{id}")
    public String toDataDicAdd(@PathVariable(value = "mode") String mode, @PathVariable(value="id",required = false) Integer id, Model model) {

       QueryWrapper<SysDataDic> dicQueryWrapper = new QueryWrapper<>();
        dicQueryWrapper.eq("pid",Const.DATA_DIC_ROOT);
       List<SysDataDic> sysDataDicList = sysDataDicService.list(dicQueryWrapper);
        if(Const.MODE_ADD.equals(mode)) {
//            SysDataDic sysDataDic = new SysDataDic();
//            if(id.equals(Const.DATA_DIC_ROOT)){
//                sysDataDic.setId(Const.DATA_DIC_ROOT);
//                sysDataDic.setPid("0");
//            } else {
//                QueryWrapper<SysDataDic> queryWrapper = new QueryWrapper<>();
//                queryWrapper.eq("id", id);
//                sysDataDic = sysDataDicService.getOne(queryWrapper);
//            }
            SysDataDic sysDataDic = sysDataDicService.getRoot(Const.DATA_DIC_ROOT);
            Integer sort=(Integer)sysDataDicService.getChildSort(Const.DATA_DIC_ROOT).getData();
            sysDataDic.setSort(sort);
            model.addAttribute("mode","add");
            model.addAttribute("sysDataDic",sysDataDic);
        } else if(Const.MODE_UPDADTE.equals(mode)) {
            QueryWrapper<SysDataDic> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",id);
            SysDataDic sysDataDic = sysDataDicService.getOne(queryWrapper);
            model.addAttribute("mode","update");
            model.addAttribute("sysDataDic",sysDataDic);
        }
        model.addAttribute("sysDataDicList",sysDataDicList);
        return "dic/add";
    }


    /////////////////////////////////////////////////////////////  项目组跳转start ///////////////////////////////////////////////////////////////////

    /**
     * @description 项目组列表
     *
     * @auth    admin
     * @dte     2019-11-20
     */
    @RequiresPermissions("group:list")
    @GetMapping("/ba-project-group/list")
    public String toPgList() {
        return "pg/list";
    }

    /**
     * @description 项目组添加
     *
     * @auth admin
     * @date 2019-11-21
     * @return
     */
    @RequiresPermissions("group:add")
    @GetMapping("/ba-project-group/add")
    public String toPgAdd(Model model) {
        // 取得客户经理下拉列表
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deptId",user.getDeptId());
        paramMap.put("amRoleCode",Const.ROLE_CODE_AM);
        paramMap.put("enabled", Const.ENABLED_Y);
        model.addAttribute("amList", this.sysUserService.getAmListByDeptAndRole(paramMap));
        return "pg/add";
    }

    /////////////////////////////////////////////////////////////  项目组跳转end  ///////////////////////////////////////////////////////////////////

    /**
     * 跳转时间点参数页面
     * @return
     */
    @RequiresPermissions("time:list")
    @GetMapping("/ba-time-param/list")
    public String toTimeParam(){
        return "timeparam/list";
    }

    /**
     * 时间点-关联项目组
     * @return
     */
    @RequiresPermissions("time:relateProject")
    @GetMapping("/ba-time-param/relate/project/{id}/{type}")
    public String toRelateProject(@PathVariable Integer id,@PathVariable String type, Model model){
        model.addAttribute("id",id);
        model.addAttribute("type",type);
        return "timeparam/project";
    }

/////////////////////////////////////////////////////////////////////////////////   时间参数页面 end //////////////////////////////////////////////////////////////
    /**
     * 跳转导出参数页面
     * @return
     */
    @RequiresPermissions("export:list")
    @GetMapping("/ba-export-param/list")
    public String toExportParam(){
        return "exportparam/list";
    }



/////////////////////////////////////////////////////////////////////////////////   导出参数页面 end //////////////////////////////////////////////////////////////
    /**
     * 跳转扩展参数页面
     * @return
     */
    @RequiresPermissions("extend:list")
    @GetMapping("/ba-extend-param/list")
    public String toExtendParam(){
        return "extendparam/list";
    }


/////////////////////////////////////////////////////////////////////////////////   导出参数页面 end //////////////////////////////////////////////////////////////



    /**
     * 跳转结算公式参数页面
     * @return
     */
    @RequiresPermissions("formula:list")
    @GetMapping("/ba-formula-param/list")
    public String toFormulaParam(Model model){
        List<SysDataDicVo> formulaTypes = sysDataDicService.getDataDicSelectByParentCode(Const.FORMULA_PARAM_TYPE);
        model.addAttribute("formulaTypes",formulaTypes);
        return "formulaparam/list";
    }


/////////////////////////////////////////////////////////////////////////////////   结算页面 end //////////////////////////////////////////////////////////////

    /**
     * 跳转职位级别页面
     * @return
     */
    @RequiresPermissions("emplevel:list")
    @GetMapping("/ba-emp-level/list")
    public String toEmpLevel(){
        return "emplevel/list";
    }


    /////////////////////////////////////////////////////////////////////////////////   职位级别页面 end //////////////////////////////////////////////////////////////
    /**
     * 跳转结算公式参数页面
     * @return
     */
    @RequiresPermissions("customer:list")
    @GetMapping("/ba-customer/list")
    public String toBaCustomer(){
        return "customer/list";
    }


    /**
     * 添加部门
     * @param id
     * @param mode
     * @param model
     * @return
     */
    @RequiresPermissions({"customer:toUpdate","customer:toAdd"})
    @GetMapping("/ba-customer/customerContent")
    public String customerIframeContent(@RequestParam(required =false,defaultValue = "1") Integer id, @RequestParam(required = false,defaultValue = "view") String mode, Model model) {
        List<SysRoleVo> sysRoleVos = sysRoleService.getRoleVoExclude(Const.ROLE_CODE_ADMIN);
        if("update".equals(mode)) {
            SysDept sysDept = sysDeptService.getDeptByCustomerId(id);
            //List<SysDeptRoleUserVo> chiefs = (List<SysDeptRoleUserVo>)sysDeptService.getDeptRoleUsers(sysDept.getId()).getData();
            List<SysUserVo> chiefs = (List<SysUserVo>) sysUserService.getUserByDeptIdAndRoleCode(sysDept.getId()).getData();
            BaCustomerVo baCustomerVo = baCustomerService.getBaCustomerVoById(id);
            model.addAttribute("baCustomer",baCustomerVo);
            model.addAttribute("chiefs",chiefs);
            model.addAttribute("mode",Const.MODE_UPDADTE);
        } else if ("add".equals(mode)) {
            BaCustomerVo baCustomerVoP = baCustomerService.getBaCustomerVoById(id);
            int sort = baCustomerService.getAddCustomerSort(id);
            model.addAttribute("mode",Const.MODE_ADD);
            BaCustomerVo baCustomerVo = new BaCustomerVo();
            baCustomerVo.setParentName(baCustomerVoP.getCustomerName());
            baCustomerVo.setPid(baCustomerVoP.getId());
            baCustomerVo.setSort(sort);
            model.addAttribute("baCustomer",baCustomerVo);
        } else {
            SysDept sysDept = sysDeptService.getDeptByCustomerId(id);
            List<SysUserVo> chiefs ;
            BaCustomerVo baCustomerVo ;
            if(sysDept!=null) {
               chiefs = (List<SysUserVo>) sysUserService.getUserByDeptIdAndRoleCode(sysDept.getId()).getData();
                baCustomerVo = baCustomerService.getBaCustomerVoById(id);
            } else {
                chiefs = new ArrayList<>();
                baCustomerVo = new BaCustomerVo();
            }
            //List<SysDeptRoleUserVo> chiefs = (List<SysDeptRoleUserVo>)sysDeptService.getDeptRoleUsers(sysDept.getId()).getData();
            model.addAttribute("baCustomer",baCustomerVo);
            model.addAttribute("chiefs",chiefs);
            model.addAttribute("mode",Const.MODE_VIEW);
        }
        return "customer/add";
    }


    /**
     * 跳转列表客户添加页面
     * @return
     */
    @RequiresPermissions({"customer:toUpdate","customer:toAdd"})
    @GetMapping("/ba-customer/toAddorUpdate/{mode}/{id}")
    public String toBaCustomerAddOrUpdate(@PathVariable String mode,@PathVariable(required = false) Integer id,Model model) {
        if(Const.MODE_ADD.equals(mode)) {
            model.addAttribute("mode",Const.MODE_ADD);
            BaCustomerVo baCustomerVo = new BaCustomerVo();
            model.addAttribute("baCustomer",baCustomerVo);
        } else if(Const.MODE_UPDADTE.equals(mode)){
            SysDept sysDept = sysDeptService.getDeptByCustomerId(id);
            //List<SysDeptRoleUserVo> chiefs = (List<SysDeptRoleUserVo>)sysDeptService.getDeptRoleUsers(sysDept.getId()).getData();
            List<SysUserVo> chiefs = (List<SysUserVo>) sysUserService.getUserByDeptIdAndRoleCode(sysDept.getId()).getData();
            BaCustomerVo baCustomerVo = baCustomerService.getBaCustomerVoById(id);
            model.addAttribute("baCustomer",baCustomerVo);
            model.addAttribute("chiefs",chiefs);
            model.addAttribute("mode",Const.MODE_UPDADTE);
        }
        return "customer/add";
    }
    /**
     * 客户移交user
     * @param ids
     * @param model
     * @return
     */
    @RequiresPermissions("customer:transfer")
    @GetMapping("/ba-customer/transfer/{ids}")
    public String toTransferCustomerPage(@PathVariable Integer[] ids,Model model){
        SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
        SysUserRole sysRole = sysUserRoleService.getRoleByUserId(user.getId());
        //当前用户所在的部门下的am
        //List<SysUser> sysUsers = (List<SysUser>)sysUserService.getUserSelectByRoleDept(sysRole.getRoleId(),user.getId(),user.getDeptId()).getData();
        //显示所有的am
        List<SysUser> sysUsers = sysUserService.getUsersByRoleCodeExcludeSelf(Const.ROLE_CODE_AM, user.getId());
        model.addAttribute("ids",ids);
        model.addAttribute("applyUser",user.getId());
        model.addAttribute("users",sysUsers);
        return "customer/transfer";
    }
    /////////////////////////////////////////////////////////////////////////////////   客户页面 end //////////////////////////////////////////////////////////////
    /**
     * @description 员工录入
     *
     * @auth admin
     * @date 2019-11-28
     * @param pgId
     * @return
     */
    @RequiresPermissions("employee:enterEmp")
    @GetMapping("/ba-employee/enter-emp/{pgId}")
    public String toEnterEmployPage(@PathVariable  Integer pgId, Model model) {
        model.addAttribute("pgId", pgId);
        return "emp/enter";
    }


    @GetMapping("/charts")
    public String toCharts(){
        return "charts/charts";
    }

}

