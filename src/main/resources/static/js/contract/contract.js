var formSelects = layui.formSelects;
// 监听下拉框选中值
formSelects.on('customerSelect', function(id, vals, val, isAdd, isDisabled){
    //id:           点击select的id
    //vals:         当前select已选中的值
    //val:          当前select点击的值
    //isAdd:        当前操作选中or取消
    //isDisabled:   当前选项是否是disabled
    //如果return false, 那么将取消本次操作
    // return false;
    var customerId = formSelects.value("customerSelect",'val');
    if (customerId != null && customerId != '') {
        // 取得部门对应的角色
        $.get('/ba-project-group/groups/'+customerId,null, function(r) {
             $("#baProjectGroupId").empty();
            if(r.code=='0') {
                var groups = r.data;
                var baProjectGroup=$("#baProjectGroupId").empty();
                baProjectGroup.append("<option value=''>请选择项目</option>");
                for(var i=0;i<groups.length;i++) {
                    // alert("<option value='"+groups[i].id+"'>"+groups[i].pgName+"</option>");
                    baProjectGroup.append("<option value='"+groups[i].id+"'>"+groups[i].pgName+"</option>");
                }
                render();
            } else {
                layer.msg('加载失败',{icon:5});
            }
        });
    }
},true);

// 页面渲染
function render(){
    layui.use('form', function(){
        var form = layui.form;
        form.render();
    });
}