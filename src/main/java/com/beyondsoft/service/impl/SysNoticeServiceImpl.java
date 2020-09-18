package com.beyondsoft.service.impl;

import com.beyondsoft.entity.SysNotice;
import com.beyondsoft.entity.SysNoticeUser;
import com.beyondsoft.mapper.SysNoticeMapper;
import com.beyondsoft.service.SysNoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beyondsoft.service.SysNoticeUserService;
import com.beyondsoft.vo.SysNoticeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统公告 服务实现类
 * </p>
 *
 * @author kun
 * @since 2020-04-28
 */
@Service
@Transactional
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {
    @Autowired
    private SysNoticeUserService sysNoticeUserService;
    /***
     * 保存通知类型
     * @param sysNoticeVo
     * @return
     */
    @Override
    public boolean saveNotice(SysNoticeVo sysNoticeVo) {
        SysNotice sysNotice = new SysNotice();
        BeanUtils.copyProperties(sysNoticeVo,sysNotice);
        sysNotice.setCreateTime(new Date());
        Integer ret = this.baseMapper.insert(sysNotice);
        if(ret!=null && ret>0) {
            List<SysNoticeUser> sysNoticeUsers = new ArrayList<>();
            for(Integer to:sysNoticeVo.getNoticeTo()) {
                SysNoticeUser sysNoticeUser = new SysNoticeUser();
                sysNoticeUser.setNoticeId(sysNotice.getId());
                sysNoticeUser.setNoticeTo(to);
                sysNoticeUsers.add(sysNoticeUser);
            }

            if(sysNoticeUsers.size()>0) {
                boolean f = sysNoticeUserService.saveBatch(sysNoticeUsers);
                if(f){
                    return true;
                } else{
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /***
     * 根据id获取通知内容
     * @param userId
     * @return
     */
    @Override
    public List<SysNoticeVo> getNoticeVoByUserId(Integer userId) {
        return null;
    }
}
