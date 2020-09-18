package com.beyondsoft.vo;

import com.beyondsoft.entity.Recommend;
import lombok.Data;

import java.util.Date;

@Data
public class RecommendVo extends Recommend {
private String demandName;
private String hrUser;
private Date recommendTime;
private String recommendUser;
}
