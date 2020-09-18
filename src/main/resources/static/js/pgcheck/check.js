var formSelects = layui.formSelects;
// 监听下拉框选中值
// formSelects.on('customerSelect', function(id, vals, val, isAdd, isDisabled){
//     //id:           点击select的id
//     //vals:         当前select已选中的值
//     //val:          当前select点击的值
//     //isAdd:        当前操作选中or取消
//     //isDisabled:   当前选项是否是disabled
//     //如果return false, 那么将取消本次操作
//     // return false;
//     var deptId = formSelects.value("customerSelect",'val');
//     if (deptId != null && deptId != '') {
//         // 取得部门对应的角色
//         $.get('/sys-role/dept/'+deptId,null, function(r) {
//             $("#roleDiv").empty();
//             if (r.code == 200) {
//                 for (var i = 0; i < r.data.length; i++) {
//                     var role = r.data[i];
//                     var inputRole = $("<input type='checkbox' name='roles[]' value='" + role.id + "' title='" + role.roleCnName + "'>");
//                     $("#roleDiv").append(inputRole);
//                 }
//                 render();
//             }
//         });
//     }
// },true);





function checkResultVerify(data, checkResultPass,checkResultNoPass,layer) {
    var ret = new Object();
    ret.flag = true;
    // 审核通过：必须选择客户
    if (data.checkResult == checkResultPass) {
        if (data.customerId == null || data.customerId == '') {
            ret.flag = false;
            ret.message = '审核通过，客户必须选择一个啊';
        }
        // 审核未通过： 必须填写备注
    } else if (data.checkResult == checkResultNoPass) {
        if (data.remark == null || data.remark == '') {
            ret.flag = false;
            ret.message = '审核未通过，备注必须填写啊';
        }
    }
    if (!ret.flag) {
        layer.msg(ret.message, {icon : 5});
    }
    return ret;
}