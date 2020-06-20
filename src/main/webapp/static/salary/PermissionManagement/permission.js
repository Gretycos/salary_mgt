var l = function(departmentName,permissionName){
    layui.use(['Hussar', 'HussarAjax'], function() {
        var  Hussar = layui.Hussar
            , $ = layui.jquery
            , $ax = layui.HussarAjax


        var ajax = new $ax(Hussar.ctxPath + "/util/getPermission", function (data) {
            if (data==true||false){
                return data
            }else {
                Hussar.error(data);
            }
        }, function (data) {
            Hussar.error("请求异常")
        });
        ajax.set({"departmentName":departmentName,"permissionName":permissionName});
        ajax.start();

    });
};

export default l;

