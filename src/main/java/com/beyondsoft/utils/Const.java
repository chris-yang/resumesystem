package com.beyondsoft.utils;

/**
 * @description 常量类
 *
 * @author  admin
 * @date 2019/11/11.
 */
public class Const {
    /** 功能类型M 菜单*/
    public final static String MENU_TYPE_M = "M";
    /** 功能类型 B 按钮 */
    public final static String MENU_TYPE_B = "B";
    /** 功能类型 A Ajax */
    public final static String MENU_TYPE_A = "A";
    /** 启用状态Y-可用 */
    public final static String ENABLED_Y = "Y";
    /** 启用状态N-不可用*/
    public final static String ENABLED_N = "N";
    /** 删除状态N-使用中 */
    public final static String DEL_FLAG_N = "N";
    /** 删除状态D-删除 */
    public final static String DEL_FLAG_D = "D";
    /** 部门根节点CODE */
    public final static String DEPT_ROOT = "ROOT";
    /** 客户根节点CODE */
    public static final String CUSTOMER_ROOT = "ROOT";
    /** 部门根节点ID */
    public final static Integer DEPT_ROOT_ID = 1;
    /** 数据字典根节点ID */
    public final static String DATA_DIC_ROOT = "DIC_ROOT";
    /** 权限菜单根节点ID */
    public final static Integer PERMISSION_ROOT_ID = 1;
    /** 添加标志 */
    public final static String MODE_ADD = "add";
    /** 修改标志 */
    public final static String MODE_UPDADTE = "update";
    /** 查看显示的标志 */
    public static final Object MODE_VIEW = "view";
    /** slat */
    public final static String SALT = "12345678";
    /** 客户经理角色CODE:AM */
    public final static String ROLE_CODE_AM = "AM";
    /** 交付经理角色CODE:DM */
    public static final String ROLE_CODE_DM = "DM";
    /** 管理员角色CODE:ADMIN */
    public static final String ROLE_CODE_ADMIN = "ADMIN";
    /** 助理角色CODE：ASSISTANT */
    public final static String ROLE_CODE_ASSISTANT = "ASSISTANT";
    /** 结算负责人角色CODE：SETTLEMENT */
    public final static String ROLE_CODE_SETTLEMENT = "SETTLEMENT";
    /**审核状态父结点**/
    public static final String CHECK_STATUS_PARENT_CODE = "CHECKSTATUS";
    /** 审核状态NO_CHECK：待审核 */
    public final static String CHECK_STATUS_NO_CHECK = "NO_CHECK";
    /** 审核状态CHECK_PASS：审核通过*/
    public final static String CHECK_STATUS_CHECK_PASS = "CHECK_PASS";
    /** 审核状态CHECK_NOTPASS：审核未通过*/
    public final static String CHECK_STATUS_CHECK_NOPASS = "CHECK_NOPASS";
    /** 员工提交状态S：已提交 */
    public final static String EMP_SUBMIT_STATUS_S = "S";
    /** 员工提交状态N：未提交*/
    public final static String EMP_SUBMIT_STATUS_N = "N";
    /** 级别填写模式CODE： LEVEL_INPUT_TYPE*/
    public final static String LEVEL_TYPE_PARENT_CODE = "LEVEL_INPUT_TYPE";
    /** 合同状态 Y*/
    public final static String CONTRACT_STATUS_Y ="Y";
    /** 合同状态 N*/
    public final static String CONTRACT_STATUS_N ="N";
    ///////////////////////////////////////数据字典/////////////////////////////////////////////////////
    /**菜单类型根结点**/
    public final static String PERMISSION_TYPE_CODE="PERMISSION_TYPE";
    /** 级别填写模式F： 框架协议 */
    public final static String LEVEL_MODE_F = "F";
    /** 级别填写模式H：手动填写 */
    public final static String LEVEL_MODE_H = "H";
    /** 单位父CODE: UNIT */
    public final static String UNIT_PARENT_CODE = "UNIT";
    /** 上传图片存放实际路径 */
//    public final static String IMG_UPLOAD_PATH = "file:F:/MyProjectName/";
    public final static String IMG_UPLOAD_PATH = "file:/tmp/uploadfile/";
    /**结算时间点参数 时间点**/
    public static final String TIME_PARAM_PARENT = "TIME_PARAM";
    /**结算时间点参数 结算时间点**/
    public final static String TIME_PRAMA_STOP="TIME_STOP";
    /**结算时间点参数 结算完成时间点**/
    public final static String TIME_PRAMA_COMPELETE="TIME_COMPLETE";
    /** 考勤提交状态父：已提交  未提交 */
    public final static String SUB_STATUS= "SUB_STATUS";
    /** 考勤提交状态S：申请修改中 */
    public static final String SUB_STATUS_A = "A";
    /** 考勤提交状态S：已提交 */
    public final static String SUB_STATUS_S = "S";
    /** 考勤提交状态N：未提交*/
    public final static String SUB_STATUS_N = "N";
    /**员工项目L: 离场 **/
    public static final String EMP_ENTRANCE_STATUS_L ="L" ;
    /**员工项目I: 在场**/
    public static final String EMP_ENTRANCE_STATUS_I ="I" ;
    /** 员工项目N：未入场 */
    public final static String EMP_ENTRANCE_STATUS_N = "N";
    /**员工O: 在职**/
    public static final String EMP_JOB_STATUS_O = "O";
    /**员工L: 离职**/
    public static final String EMP_JOB_STATUS_L = "L";
    /** 在职状态 */
    public final static String JOB_STATUS_O = "O";
    /** 离职状态 */
    public final static String JOB_STATUS_L = "L";
    /** 入场状态 */
    public final static String ENTRANCE_STATUS_I = "I";
    /** 离场状态 */
    public final static String ENTRANCE_STATUS_L = "L";
    /** 数据字典：项目组审核CODE */
    public final static String PG_CHECKSTATUS_CODE = "PG_CHECKSTATUS";
    /** 数据字典： 审核结果CODE */
    public final static String CHECK_RESULT_CODE = "CHECK_RESULT";
    /** 数据字典：审核结果：通过-PASS */
    public final static String CHECK_RESULT_PASS_CODE = "PASS";
    /** 数据字典：审核结果：不通过-NOPASS */
    public final static String CHECK_RESULT_NOPASS_CODE = "NOPASS";
    /** 数据字典：考勤 申请修改类型 */
    public static final Object APPLY_TYPE = "WORKATTENDANCE";
    /** 员工申请修改状态A：申请修改中 */
    public final static String EMP_APPLY_UPDATE_STATUS_A = "APPLYING";
    /** 员工申请修改状态F：修改完成 */
    public final static String EMP_APPLY_UPDATE_STATUS_F = "UPDATE_FINISH";
    /** 完成状态：完成 */
    public final static String END_STATUS_END_CODE = "END";
    /** 完成状态：修改中 */
    public final static String END_STATUS_APPLYING_CODE = "APPLYING";
    /** 完成状态：未完成 */
    public final static String END_STATUS_NO_END_CODE = "NO_END";
    /** 完成状态CODE */
    public final static String END_STATUS_CODE = "END_STATSU";
    /** 用户停用类型CODE */
    public final static String USER_STOP_TYPE_CODE = "STOP_TYPE";
    /** 申请移交 **/
    public static final String APPLY_TRANSFER = "TRANSFER_TYPE";
    /** 申请移交 客户**/
    public static final String APPLY_TRANSFER_CUSTOMER = "TRANSFER_CUSTOMER";
    /** 申请移交 项目**/
    public static final String APPLY_TRANSFER_PROJECT = "TRANSFER_PROJECT";

    /**公式规则参数类型**/
    public static final String SETTLE_FORMULA_TYPE = "SETTLE_FORMULA_TYPE";
    /**公式规则参数类型 基本规则**/
    public static final String SETTLE_FORMULA_BASIC = "SETTLE_FORMULA_BASIC";
    /**公式规则参数类型  计算规则**/
    public static final String SETTLE_FORMULA_CACULATE = "SETTLE_FORMULA_CACULATE";
    /**结算表名**/
    public static final String FORMULA_TABLE = "FORMULA_TABLE";
    /**导出参数表名**/
    public static final String EXPORT_TABLE = "EXPORT_TABLE";
    /**结算状态**/
    public static final String SETTLE_STATUS = "SETTLE_STATUS";
    /**结算状态 已结算**/
    public static final String SETTLE_STATUS_Y ="SETTLE_STATUS_Y" ;
    /**结算状态 未结算**/
    public static final String SETTLE_STATUS_N ="SETTLE_STATUS_N" ;
    /**是否回款**/
    public static final String IS_RETURN = "IS_RETURN";
    /**是否回款 已回款**/
    public static final String IS_RETURN_Y = "IS_RETURN_Y";
    /**是否回款 未回款**/
    public static final String IS_RETURN_N = "IS_RETURN_N";
    /**是否发送邮件 **/
    public static final String IS_SEND = "IS_SEND";
    /**是否发送邮件 已发送**/
    public static final String IS_SEND_Y = "IS_SEND_Y";
    /**是否发送邮件 未发送**/
    public static final String IS_SEND_N = "IS_SEND_N";
    /**是否开票**/
    public static final String IS_INVOICE = "IS_INVOICE";
    /**是否开票 已开票**/
    public static final String IS_INVOICE_Y = "IS_INVOICE_Y";
    /**是否开票 未开票**/
    public static final String IS_INVOICE_N = "IS_INVOICE_N";
    /**扩展表对应**/
    public static final String EXTEND_TABLE_PARAM =  "EXTEND_TABLE_PARAM";
    /**公式参数类型**/
    public static final String FORMULA_PARAM_TYPE =  "FORMULA_PARAM_TYPE";
    /**公式参数类型 --计算参数**/
    public static final String FORMULA_PARAM_CAC =  "FORMULA_PARAM_CAC";
    /**公式参数类型 --保存参数**/
    public static final String FORMULA_PARAM_SAVE =  "FORMULA_PARAM_SAVE";

    /////////////////////////////////////消息通知///////////////////////////////////////////
    /**消息通知 --系统通知**/
    public static final String SYS_NOTICE =  "SYS_NOTICE";
    /**消息通知 --业务通知**/
    public static final String BUSSINESS_NOTICE =  "BUSSINESS_NOTICE";

    /**消息通知 --考勤通知**/
    public static final String WORKATTDANCE_NOTICE =  "WORKATTDANCE_NOTICE";
    /**消息通知 --项目组提交通知**/
    public static final String PROJECT_SUBMIT_NOTICE =  "PROJECT_SUBMIT_NOTICE";
    /**消息通知 --项目组审核通知**/
    public static final String PROJECT_CHECK_NOTICE =  "PROJECT_CHECK_NOTICE";

    /**消息通知 --项目组关联助理通知**/
    public static final String PROJECT_RELATE_AS_NOTICE =  "PROJECT_RELATE_AS_NOTICE";
    /**消息通知 --项目组关联结算通知**/
    public static final String PROJECT_RELATE_SE_NOTICE =  "PROJECT_RELATE_SE_NOTICE";
    /**消息通知 --结算通知**/
    public static final String SETTLE_NOTICE =  "SETTLE_NOTICE";

    /**消息通知 --员工申请修改**/
    public static final String EMP_APPLY_NOTICE =  "EMP_APPLY_NOTICE";
    /**消息通知 --员工申请审核**/
    public static final String EMP_CHECK_NOTICE =  "EMP_CHECK_NOTICE";

    /**消息通知 --考勤申请修改**/
    public static final String ATTDANCE_APPLY_NOTICE =  "ATTDANCE_APPLY_NOTICE";
    /**消息通知 --考勤申请审核**/
    public static final String ATTDANCE_CHECK_NOTICE =  "ATTDANCE_CHECK_NOTICE";

    /**消息通知 --项目组移交修改**/
    public static final String PROJECT_TRANSFER_APPLY_NOTICE =  "PROJECT_TRANSFER_APPLY_NOTICE";
    /**消息通知 --项目组移交审核**/
    public static final String PROJECT_TRANSFER_CHECK_NOTICE =  "PROJECT_TRANSFER_CHECK_NOTICE";

    /**消息通知 --客户移交修改**/
    public static final String CUSTOMER_TRANSFER_APPLY_NOTICE =  "CUSTOMER_TRANSFER_APPLY_NOTICE";
    /**消息通知 --客户组移交审核**/
    public static final String CUSTOMER_TRANSFER_CHECK_NOTICE =  "CUSTOMER_TRANSFER_CHECK_NOTICE";

    /**职位类型**/
    public static final String JOB_STYLES = "JOB_STYLES";
    /**薪资范围**/
    public static final String SALARY_AREAS = "SALARY_AREAS";
    /**职位级别**/
    public static final String JOB_LEVELS = "JOB_LEVELS";
    /**学历**/
    public static final String DEGREES = "DEGREES";
    /**性别**/
    public static final String GENDER = "GENDER";
    /**工作年限**/
    public static final String WORK_YEARS = "WORK_YEARS";
    /**是否内推职位**/
    public static final String INTERNAL_RECEOMMEND ="INTERNAL_RECEOMMEND" ;
}
