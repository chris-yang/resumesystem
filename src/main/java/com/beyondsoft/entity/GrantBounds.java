package com.beyondsoft.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 奖励管理
 * </p>
 *
 * @author kun
 * @since 2020-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GrantBounds extends Model<GrantBounds> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 职位id
     */
    private Integer demandId;

    /**
     * 奖励id
     */
    private Integer boundsId;

    /**
     * 领取奖励人
     */
    private Integer userId;

    /**
     * 推荐人id
     */
    private Integer recommondId;

    /**
     * 发放状态
     */
    private String grantStatus;

    /**
     * 申请发放时间
     */
    private LocalDateTime applyTime;

    /**
     * 发放奖励时间
     */
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
