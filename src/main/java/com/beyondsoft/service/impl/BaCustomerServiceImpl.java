package com.beyondsoft.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.CustomerCo;
import com.beyondsoft.entity.*;
import com.beyondsoft.mapper.*;
import com.beyondsoft.service.BaCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beyondsoft.utils.Const;
import com.beyondsoft.utils.HttpResultEnum;
import com.beyondsoft.utils.Result;
import com.beyondsoft.utils.Status;
import com.beyondsoft.vo.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-11-28
 */
@Service
@Transactional
public class BaCustomerServiceImpl extends ServiceImpl<BaCustomerMapper, BaCustomer> implements BaCustomerService {
    @Autowired
    private BaDeptCustomerMapper baDeptCustomerMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    /**
     * 加载列表页面
     * @param customerCo
     * @return
     */
    @Override
    public PageData listPageData(CustomerCo customerCo) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
//        Map<String,Object> map = new HashMap<>();
//        map.put("delFlag",Const.DEL_FLAG_N);
//        map.put("userId",user.getId());
//        SysRole sysRole = sysRoleMapper.getSysRoleByUserId(map);
        //从session中取角色信息
        SysRole sysRole = (SysRole)SecurityUtils.getSubject().getSession().getAttribute("role");
        customerCo.setUserId(user.getId());
        customerCo.setRoleCode(sysRole.getRoleCode());
        customerCo.setDelFlag(Const.DEL_FLAG_N);
        Page<BaCustomerVo> page = new Page<>(customerCo.getPage(),customerCo.getLimit());
        page.setRecords(this.baseMapper.listPageData(customerCo,page));
        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 添加
     * @param baCustomerVo
     * @return
     */
    @Override
    public Result add(BaCustomerVo baCustomerVo) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        try {
            baCustomerVo.setDelFlag(Const.DEL_FLAG_N);
            baCustomerVo.setEnabled(Const.ENABLED_Y);
            baCustomerVo.setCreateTime(new Date());
            baCustomerVo.setLevel(this.baseMapper.selectById(baCustomerVo.getPid()).getLevel()+1);
            Integer ret = this.baseMapper.insert(baCustomerVo);
            if (ret != null && ret > 0) {
                BaDeptCustomer baDeptCustomer = new BaDeptCustomer();
                baDeptCustomer.setDeptId(baCustomerVo.getDeptId());
                baDeptCustomer.setCustomerId(baCustomerVo.getId());
                Integer ret2 = baDeptCustomerMapper.insert(baDeptCustomer);
                if (ret2 != null && ret2 > 0) {
                    r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                    r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return  r;

    }

    /**
     * 修改
     * @param baCustomerVo
     * @return
     */
    @Override
    public Result update(BaCustomerVo baCustomerVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try{
        UpdateWrapper<BaCustomer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",baCustomerVo.getId());
        Integer ret=this.baseMapper.update(baCustomerVo,updateWrapper);
        if(ret!=null && ret>0) {
            QueryWrapper<BaDeptCustomer> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("customer_id",baCustomerVo.getId());
            BaDeptCustomer baDeptCustomer = baDeptCustomerMapper.selectOne(queryWrapper);
            Integer ret2 = 0;
            //查询关联表不存在新增一条
            if(baDeptCustomer==null) {
                BaDeptCustomer baDeptCustomer2 = new BaDeptCustomer();
                baDeptCustomer2.setCustomerId(baCustomerVo.getId());
                baDeptCustomer2.setDeptId(baCustomerVo.getDeptId());
                ret2 = baDeptCustomerMapper.insert(baDeptCustomer2);
            } else {
                baDeptCustomer.setDeptId(baCustomerVo.getDeptId());
                ret2 = baDeptCustomerMapper.updateById(baDeptCustomer);
            }

            if(ret2!=null && ret2>0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return r;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public Result delete(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        try{
            UpdateWrapper<BaCustomer> updateWrapper = new UpdateWrapper<BaCustomer>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id",id);
            BaCustomer baExportParam = this.baseMapper.selectById(id);
            Integer ret=this.baseMapper.update(baExportParam,updateWrapper);
            if(ret!=null && ret>0) {
                QueryWrapper<BaDeptCustomer> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("customer_id",id);
                Integer ret2=baDeptCustomerMapper.delete(queryWrapper);
                if(ret2!=null && ret2>0) {
                    r.setCode(HttpResultEnum.DEL_CODE_200.getCode());
                    r.setMsg(HttpResultEnum.DEL_CODE_200.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 启用状态
     * @param id
     * @return
     */
    @Override
    public Result updateEnableStart(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try {
            UpdateWrapper<BaCustomer> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id);
            updateWrapper.set("enabled", Const.ENABLED_Y);
            BaCustomer baCustomer = this.baseMapper.selectById(id);

            Integer ret = this.baseMapper.update(baCustomer, updateWrapper);
            if (ret != null && ret > 0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 停用状态
     * @param id
     * @return
     */
    @Override
    public Result updateEnableStop(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<BaCustomer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_N);
        BaCustomer baCustomer = this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(baCustomer,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;
    }

    /**
     * 根据id查询得到BaCustomerVo
     * @param id
     * @return
     */
    @Override
    public BaCustomerVo getBaCustomerVoById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("delFlag",Const.DEL_FLAG_N);
        map.put("id",id);
        if(1==id) {
            return getRootCustomerById(Const.CUSTOMER_ROOT);
        } else {
            return this.baseMapper.getBaCustomerVoById(map);
        }
    }

    private BaCustomerVo getRootCustomerById(String rootCode){
        Map<String,Object> map = new HashMap<>();
        map.put("rootCode",rootCode);
        map.put("delFlag",Const.DEL_FLAG_N);
        BaCustomerVo baCustomerVo = this.baseMapper.getRootCustomerById(map);
        baCustomerVo.setParentName("客户管理");
        return  baCustomerVo;
    }

    /**
     * 根据用户id查询当前的客户信息和项目组
     * @author kun
     * @param userId
     * @return
     */
    @Override
    public List<BaCustomerAndProjectVo> getCustomerAndProjectByUserId(Integer userId) {
//        Map<String,Object> roleMap = new HashMap<>();
//        roleMap.put("delFlag",Const.DEL_FLAG_N);
//        roleMap.put("userId",userId);
       // SysRole sysRole = sysRoleMapper.getSysRoleByUserId(roleMap);
        //从session中取角色信息
        SysRole sysRole = (SysRole)SecurityUtils.getSubject().getSession().getAttribute("role");
        Map<String,Object> map = new HashMap<>();
        map.put("enabled",Const.ENABLED_Y);
        map.put("delFlag",Const.DEL_FLAG_N);
        map.put("userId",userId);
        map.put("roleCode",sysRole.getRoleCode());
        List<BaCustomerAndProjectVo> baCustomers = this.baseMapper.getCustomerAndProjectByUserId(map);
        return baCustomers;
    }

    /**
     * 根据用户id查询当前的客户信息和项目组生成树
     * @author kun
     * @param userId
     * @return
     */
    @Override
    public Object getCustomerAndProjectTreeByUserId(Integer userId) {
        JSONObject josn = new JSONObject();
        Status status = new Status();
        status.setCode(200);
        status.setMessage("操作成功");
        josn.put("status", JSONArray.toJSON(status));
        josn.put("data", JSONArray.toJSON(getCustomerAndProjectTreeListByUserId(userId)));
        return josn.toJSONString();
    }

    /**
     * 根据用户id查询当前的first node
     * @param userId
     * @return
     */
    @Override
    public Result getFirstNode(Integer userId){
        Result r = new Result(HttpResultEnum.CODE_1.getCode(),HttpResultEnum.CODE_1.getMessage());
        String first = null;
        List<BaCustomerAndProjectTreeVo> baCustomerAndProjectTreeVos = getCustomerAndProjectTreeListByUserId(userId);
        for(BaCustomerAndProjectTreeVo ba:baCustomerAndProjectTreeVos) {
            if(ba.isLeaf()){
                first = ba.getId();
                break;
            }
        }
        if(first!=null) {
            r.setMsg(HttpResultEnum.CODE_0.getMessage());
            r.setCode(HttpResultEnum.CODE_0.getCode());
            r.setData(first);
        }
        return r;
    }
    /**
     * 根据用户id查询当前的客户信息和项目组list
     * @param userId
     * @return
     */
    @Override
    public List<BaCustomerAndProjectTreeVo> getCustomerAndProjectTreeListByUserId(Integer userId){
        /**
         * 1、用户id查询对应的角色
         * 2、客户经理可以查看所有的项目
         **/
//        Map<String,Object> roleMap = new HashMap<>();
//        roleMap.put("delFlag",Const.DEL_FLAG_N);
//        roleMap.put("userId",userId);
        //SysRole sysRole = sysRoleMapper.getSysRoleByUserId(roleMap);
        //从session中取角色信息
        SysRole sysRole = (SysRole)SecurityUtils.getSubject().getSession().getAttribute("role");
        Map<String,Object> mapkey = new HashMap<>();
        mapkey.put("enabled",Const.ENABLED_Y);
        mapkey.put("delFlag",Const.DEL_FLAG_N);
        mapkey.put("checkStatus",Const.CHECK_STATUS_CHECK_PASS);
        mapkey.put("userId",userId);
        mapkey.put("roleCode",sysRole.getRoleCode());
        List<BaCustomerAndProjectVo> baCustomerAndProjectVos = this.baseMapper.getCustomerAndProjectByUserId(mapkey);
        //存储判断子结点
        Map<String,BaCustomerAndProjectVo> map = new HashMap<String,BaCustomerAndProjectVo>();
        //存储判断父结点
        Map<String,BaCustomerAndProjectTreeVo> projectMap = new HashMap<String,BaCustomerAndProjectTreeVo>();
        //返回树结构数据
        List<BaCustomerAndProjectTreeVo> baCustomerAndProjectTreeVos = new ArrayList<>();
        for(BaCustomerAndProjectVo ba: baCustomerAndProjectVos) {
            //判断当前是否已经存在父结点
            if(map.containsKey(ba.getCustomerCode())) {
                //判断当前是否已经存在相同子结点,存在跳出本次循环
                String projectCode = ba.getProjectCode();
                if(!projectMap.containsKey(projectCode)) {
                    //不存在相同子结点,构造数据添加
                    BaCustomerAndProjectTreeVo baVo = new BaCustomerAndProjectTreeVo();
                    baVo.setId(ba.getProjectId()+"");
                    baVo.setProjectCode(projectCode);
                    baVo.setTitle(ba.getProjectName());
                    baVo.setParentId(ba.getId()+ba.getCustomerName().hashCode()+"");
                    baVo.setCheckArr("0");
                    baVo.setLeaf(true);
                    baCustomerAndProjectTreeVos.add(baVo);
                    projectMap.put(projectCode,baVo);
                }

            } else {
                //不存在父结点 构造父结点并添加到map中
                map.put(ba.getCustomerCode(),ba);
                //构建父结点
                BaCustomerAndProjectTreeVo baVo = new BaCustomerAndProjectTreeVo();
                String baVoId = ba.getId()+ba.getCustomerName().hashCode()+"";
                baVo.setId(baVoId);
                baVo.setTitle(ba.getCustomerName());
                baVo.setParentId("0");
                baVo.setCheckArr("0");
                baCustomerAndProjectTreeVos.add(baVo);
                //同时构造子结点
                BaCustomerAndProjectTreeVo baChildVo = new BaCustomerAndProjectTreeVo();
                baChildVo.setId(ba.getProjectId()+"");
                baChildVo.setProjectCode(ba.getProjectCode());
                baChildVo.setTitle(ba.getProjectName());
                baChildVo.setParentId(baVo.getId());
                baChildVo.setCheckArr("0");
                baChildVo.setLeaf(true);
                if(!projectMap.containsKey(baChildVo.getProjectCode())){
                    baCustomerAndProjectTreeVos.add(baChildVo);
                    projectMap.put(baChildVo.getProjectCode(),baChildVo);
                }
            }
        }
        return baCustomerAndProjectTreeVos;
    }
    @Override
    public List<BaCustomerVo> getCustomerByDeptId(Integer deptId) {
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deptId",deptId);
        paramMap.put("delFlag",Const.DEL_FLAG_N);
        paramMap.put("enabled", Const.ENABLED_Y);
        return this.baseMapper.getBaCustomerByDeptId(paramMap);
    }

    @Override
    public List<BaCustomer> getCustomerByChief(Integer chief) {
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("chief",chief);
        paramMap.put("delFlag",Const.DEL_FLAG_N);
        paramMap.put("enabled", Const.ENABLED_Y);
        return this.baseMapper.getBaCustomerByChief(paramMap);
    }

    /**
     * 返回dtree的客户列表
     * @return
     */
    @Override
    public List<BaCustomerAndProjectTreeVo> getCustomersDtreeList() {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
//        Map<String,Object> map = new HashMap<>();
//        map.put("delFlag",Const.DEL_FLAG_N);
//        map.put("userId",user.getId());
       // SysRole sysRole = sysRoleMapper.getSysRoleByUserId(map);
        //从session中取角色信息
        SysRole sysRole = (SysRole)SecurityUtils.getSubject().getSession().getAttribute("role");
        String roleCode = sysRole.getRoleCode();
        QueryWrapper<BaCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        List<BaCustomer> baCustomers = new ArrayList<>();

        if(roleCode.equals(Const.ROLE_CODE_AM)) {
            BaCustomer rootCustomer = this.baseMapper.selectById(1);
            baCustomers.add(rootCustomer);
            //部门负责人
            //部门负责人 看到所有的客户
            SysDept sysDept = sysDeptMapper.selectOne(new QueryWrapper<SysDept>()
                    .eq("email",user.getEmail())
                    .eq("del_flag",Const.DEL_FLAG_N));
            if(sysDept!=null ) {
                Map<String,Object> map = new HashMap<>();
                map.put("enabled",Const.ENABLED_Y);
                map.put("delFlag",Const.DEL_FLAG_N);
                map.put("deptId",sysDept.getId());
                baCustomers.addAll(this.baseMapper.getBaCustomerListByDeptId(map));
            } else {
                queryWrapper.eq("chief",user.getId());
                queryWrapper.orderByAsc("sort");

                baCustomers.addAll(this.baseMapper.selectList(queryWrapper));
            }

        } else if(roleCode.equals(Const.ROLE_CODE_ADMIN)) {
            queryWrapper.orderByAsc("sort");
            baCustomers = this.baseMapper.selectList(queryWrapper);
        }
        List<BaCustomerAndProjectTreeVo> baCustomerAndProjectTreeVos = new ArrayList<>();
        for(BaCustomer baCustomer : baCustomers){
            BaCustomerAndProjectTreeVo baCustomerTree = new BaCustomerAndProjectTreeVo();
            baCustomerTree.setId(baCustomer.getId().toString());
            baCustomerTree.setTitle(baCustomer.getCustomerName());
            baCustomerTree.setParentId(baCustomer.getPid().toString());
            baCustomerTree.setCheckArr("0");
            baCustomerAndProjectTreeVos.add(baCustomerTree);
        }
        return baCustomerAndProjectTreeVos;
    }

    /**
     * 获得新增时的结点的排序数值
     * @param pid
     * @return
     */
    @Override
    public Integer getAddCustomerSort(Integer pid) {
        Integer sort = this.baseMapper.getAddCustomerSort(pid);
        if(sort!=null && sort>0) {
            sort+=1;
        } else {
            sort=1;
        }
        return sort;
    }

    /**
     * 多级部门下拉列表
     * @return
     */
    @Override
    public Result getCustomerSelect() {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        //从session中取角色信息
        SysRole sysRole = (SysRole)SecurityUtils.getSubject().getSession().getAttribute("role");
        List<SelectVo> customerSelectVoList = new ArrayList<SelectVo>();
        List<BaCustomer> customerList = new ArrayList<>();
        if(sysRole.getRoleCode().equals(Const.ROLE_CODE_SETTLEMENT)) {
            //结算总负责人
            //ToDo 查看所有的项目
            //结算专员
            customerList = this.getCustomerBySettleChief(user.getId());
            Map<Integer,SelectVo> map = new HashMap<>();
            for(BaCustomer customer : customerList) {
                BaCustomer parent = baseMapper.selectById(customer.getPid());
                if(map.containsValue(parent.getId())) {
                    SelectVo selectChildVo = new SelectVo();
                    selectChildVo.setName(customer.getCustomerName());
                    selectChildVo.setValue(customer.getId());
                    map.get(parent.getId()).getChildren().add(selectChildVo);
                } else {
                    SelectVo selectVo = new SelectVo();
                    selectVo.setName(parent.getCustomerName());
                    selectVo.setValue(parent.getId());
                    map.put(parent.getId(),selectVo);

                    SelectVo selectChildVo = new SelectVo();
                    selectChildVo.setName(customer.getCustomerName());
                    selectChildVo.setValue(customer.getId());
                    selectVo.getChildren().add(selectChildVo);

                    customerSelectVoList.add(selectVo);
                }
            }
        } else {
            customerList = this.getCustomerByChief(user.getId());
            if (customerList != null && customerList.size() > 0) {
                customerSelectVoList = buildCustomerTree(customerList);
            }
        }

         Result r = new Result(HttpResultEnum.CODE_0.getCode(), HttpResultEnum.CODE_0.getMessage());
        r.setData(customerSelectVoList);
        return r;
    }

    /**
     * 根据结算负责人，查询客户
     * @param settleChiefId
     * @return
     */
    private List<BaCustomer> getCustomerBySettleChief(Integer settleChiefId) {
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("settleChiefId",settleChiefId);
        paramMap.put("enabled", Const.ENABLED_Y);
        paramMap.put("delFlag",Const.DEL_FLAG_N);
        return this.baseMapper.getCustomersBySettleChief(paramMap);
    }
    /**
     * @description 构建客户下拉框树形结构
     *
     * @auth admin
     * @date 2019-11-29
     * @param customerList
     * @return
     */
    private List<SelectVo> buildCustomerTree(List<BaCustomer> customerList ) {
        List<SelectVo> deptTreeList = new ArrayList<SelectVo>();
        SelectVo selectVo = null;
        for (BaCustomer customer : getLevelOneCustomer(customerList)) {
            selectVo = new SelectVo();
            selectVo.setValue(customer.getId());
            selectVo.setName(customer.getCustomerName());
            buildChildTree(selectVo, customer.getId(), customerList);
            deptTreeList.add(selectVo);
        }
        return deptTreeList;
    }

    /**
     * @description 构建子节点树形结构
     *
     * @auth admin
     * @date 2019-11-29
     * @param sv
     * @param id
     * @return
     */
    private SelectVo buildChildTree(SelectVo sv, Integer id, List<BaCustomer> list) {
        List<SelectVo> childSelectVo = new ArrayList<SelectVo>();
        SelectVo svo = null;
        for (BaCustomer customer : list) {
            if (customer.getPid() == id) {
                svo = new SelectVo();
                svo.setValue(customer.getId());
                svo.setName(customer.getCustomerName());
                childSelectVo.add(buildChildTree(svo, customer.getId(), list));
            }
        }
        sv.setChildren(childSelectVo);
        return sv;
    }


    /**
     * @description 获取一级客户
     *
     * @auth admin
     * @date 2019-11-29
     * @param list
     * @return
     */
    private List<BaCustomer> getLevelOneCustomer(List<BaCustomer> list) {
        BaCustomer customerRoot = this.baseMapper.selectOne(new QueryWrapper<BaCustomer>()
                .eq("customer_code",Const.CUSTOMER_ROOT)
                .eq("del_flag",Const.DEL_FLAG_N)
        );
        if(customerRoot==null) {
            customerRoot = new BaCustomer();
            customerRoot.setId(1);
        }
        List<BaCustomer> levelOneList = new ArrayList<BaCustomer>();
        for (BaCustomer customer : list)  {
            if (customer.getPid()== customerRoot.getId()) {
                levelOneList.add(customer);
            }
        }
        return levelOneList;
    }
}
