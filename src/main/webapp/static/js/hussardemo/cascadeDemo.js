/**
* Created by zhaopengcheng on 2018/12/4.
*/
layui.use(['element','form','cascadeSelect'],function(){
    var cas = layui.cascadeSelect,
        $ = layui.jquery,
        form = layui.form;

    // 示例1
    var cas1 = cas.render({
        container:"#content", // 指定容器
        id:"6812929ae0ab448b9f3cc0d5ec96783c" ,// 第一级开始的父级主键，不指定id则从最大类开始
        selectName:"cascade001,cascade002,cascade003",// 下拉框的name属性，以","隔开
        cascadeCount:3 // 最多可联动的下拉框数量
    });

    // 示例2
    var cas2 = cas.render({
        container:"#content2",
        id:"" ,// 或者不写
        selectName:"cascade1,cascade2,cascade3,cascade4",
        cascadeCount:4
    })
});