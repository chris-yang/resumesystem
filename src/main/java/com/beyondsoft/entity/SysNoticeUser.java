package com.beyondsoft.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author kun
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysNoticeUser extends Model<SysNoticeUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 通知 id
     */
    private Integer noticeId;

    /**
     * 通知人
     */
    private Integer noticeTo;

    /**
     * 通知状态 N未读 Y 已读
     */
    private String noticeStatus;

    /**
     * 处理时间
     */
    private Date readTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
