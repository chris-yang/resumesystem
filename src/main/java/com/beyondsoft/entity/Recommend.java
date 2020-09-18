package com.beyondsoft.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 推荐记录
 * </p>
 *
 * @author kun
 * @since 2020-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Recommend extends Model<Recommend> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 被推人姓名
     */
    private String recommendName;

    /**
     * 被推荐人手机号
     */
    private String recommendMobile;

    private String resumeFile;

    private String resumeFilePath;

    /**
     * 推荐人id
     */
    private Integer userId;

    /**
     * 推荐进度
     */
    private String processStatus;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
