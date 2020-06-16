/**
 * @Description:    公共表单验证规则【供项目使用】
 * @Author:         LiangDong
 * @CreateDate:     2019/1/25 11:16
 */
/**
 * 使用说明：
 * 在form表单元素中使用lay-verify添加校验的规则，多个校验规则可以使用“|”隔开，如：lay-verify="required|phone|number"
 * 项目上可以根据自己的业务，在此基础上继续添加、修改或删除校验规则，
 * 在需要用到表单校验的html中引入此js即可，使用layui的表单提交时便会自动验证。
 * 避免了同一规则多处编写的不便。
 *
 */
var FormVerify = {
    layerIndex : -1
};
layui.use([ 'jquery', 'layer', 'Hussar', 'HussarAjax', 'form' ], function() {
    var Hussar = layui.Hussar;
    var $ = layui.jquery;
    var layer = layui.layer;
    var $ax = layui.HussarAjax;
    var form = layui.form;



    /**
     * 初始化表单验证
     */
    FormVerify.initFormVerify = function () {
        form.verify({
            chinese: [/^[\u4e00-\u9fa5]{0,}$/, '请输入汉字'],
            email: [/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, '请输入正确的邮箱'],
            phone: [/^1[3|4|5|7|8]\d{9}$/, '请输入正确的手机号'],
            zipCode: [/^[0-9]{6}$/, '请输入正确的邮政编码'],
            account: [/^[a-zA-Z][a-zA-Z0-9_]{4,15}$/, '包含字母、数字、下划线，并以字母开头'],
            //也可以使用function这样的形式
            date: function (value, item) {
                if(!/^\d{4}-\d{1,2}-\d{1,2}$/.test(value)){
                    return '请输入正确的日期'
                }
            }
        });
    };


    /**
     * 加载完成后
     */
    $(function() {
        FormVerify.initFormVerify();
    });

});
