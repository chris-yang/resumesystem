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
 * 系统公告
 * </p>
 *
 * @author kun
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysNotice extends Model<SysNotice> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 通知类型 SYS_NOTICE 系统通知  BUSSINESS_NOTICE业务通知
     */
    private String noticeType;

    /**
     * 通知标题
     */
    private String noticeTitle;

    /**
     * 消息发送者
     */
    private Integer noticeFrom;

    /**
     * 通知消息内容
     */
    private String noticeMessage;

    private String url;

    /**
     * 创建时间
     */
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
