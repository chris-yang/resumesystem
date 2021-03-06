package com.beyondsoft.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beyondsoft.co.CustomerCo;
import com.beyondsoft.entity.BaCustomer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beyondsoft.vo.BaCustomerAndProjectVo;
import com.beyondsoft.vo.BaCustomerVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2019-11-28
 */
@Repository
public interface BaCustomerMapper extends BaseMapper<BaCustomer> {

    @Select("select c1.customer_name as parentName,c.*,dc.dept_id from ba_customer c " +
            " left join ba_dept_customer dc on c.id=dc.customer_id " +
            " inner join ba_customer c1 on c.pid=c1.id"+
            " where c.id=#{id} and c.del_flag=#{delFlag}"
    )
     BaCustomerVo getBaCustomerVoById(Map<String,Object> map);

    /**
     * 根据用户id查询当前的客户信息和项目组 员工所负责的客户下的项目
     * @param map
     * @return
     */
     List<BaCustomerAndProjectVo> getCustomerAndProjectByUserId(Map<String,Object> map);
    /**客户列表**/
    List<BaCustomerVo> listPageData(CustomerCo customerCo, Page<BaCustomerVo> page);

    /**
     * 根据部门ID取得客户
     *
     * @auth admin
     * @date 2019-12-12
     * @param map
     * @return
     */
    List<BaCustomerVo> getBaCustomerByDeptId(Map<String, Object> map);
    List<BaCustomer>  getBaCustomerListByDeptId(Map<String, Object> map);
    /**
     * @description 根据客户负责人，查询客户
     *
     * @auth admin
     * @date 2019-12-16
     * @param map
     * @return
     */
    List<BaCustomer> getBaCustomerByChief(Map<String, Object> map);
    /***根据结算负责人，查询客户**/
    List<BaCustomer> getCustomersBySettleChief(Map<String, Object> map);
    /**客户移交**/
    Integer customerApplyTransfer(BaCustomerVo baCustomerVo);
    /**批量更新客户拥有者人员信息**/
    Integer updateBatchs(List<BaCustomer> baCustomers);
    /**获取客户根结点**/
    @Select("select * from ba_customer where del_flag=#{delFlag} and customer_code=#{rootCode}")
    BaCustomerVo getRootCustomerById(Map<String, Object> map);

    /**获得新增时的结点的排序数值**/
    @Select("select max(sort) from ba_customer where del_flag = 'N' and pid=#{pid}")
    Integer getAddCustomerSort(Integer id);
}


