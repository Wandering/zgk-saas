var Common = {
    init: function () {
        this.loginInfo();
        this.logout();
        this.checkAll();
    },
    url: {},
    cookie: {
        /*
         *功能：设置Cookie
         *cookieName 必选项，cookie名称
         *cookieValue 必选项，cookie值
         *seconds 生存时间，可选项，单位：秒；默认时间是3600秒
         *path cookie存放路径，可选项
         *domain cookie域，可选项
         *secure 安全性，指定Cookie是否只能通过https协议访问，一般的Cookie使用HTTP协议既可访问，如果设置了Secure（没有值），则只有当使用https协议连接时cookie才可以被页面访问
         */
        setCookie: function (cookieName, cookieValue, seconds, path, domain, secure) {
            var expires = new Date();
            var seconds = arguments[2] ? arguments[2] : (3600 * 5);
            expires.setTime(expires.getTime() + seconds * 1000);
            document.cookie = escape(cookieName) + '=' + escape(cookieValue) + (expires ? ';expires=' + expires.toGMTString() : '') + (path ? ';path=' + path : '/') + (domain ? ';domain=' + domain : '') + (secure ? ';secure' : '');
        },
        /*
         *功能：获取Cookie
         *name 必选项，cookie名称
         */
        getCookie: function (name) {
            var cookie_start = document.cookie.indexOf(name);
            var cookie_end = document.cookie.indexOf(";", cookie_start);
            return cookie_start == -1 ? '' : unescape(document.cookie.substring(cookie_start + name.length + 1, (cookie_end > cookie_start ? cookie_end : document.cookie.length)));
        },
        /*
         *功能：删除或清空Cookie
         *name 必选项，cookie名称
         */
        delCookie: function (name, value) {
            var value = arguments[1] ? arguments[1] : null;
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var val = Common.cookie.getCookie(name);
            if (val != null) {
                document.cookie = name + '=' + value + ';expires=' + exp.toGMTString();
            }
        }
    },
    ajaxFun: function (url, method, reqData, callback, callbackError, isasync,acceptType) {
        var isasyncB = null;
        if (isasync) {
            isasyncB = false;
        } else {
            isasyncB = true;
        }
        var headers=null;
        if(acceptType){
            headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }else{
            headers='';
        }

        $.ajax({
            url: url,
            type: method,
            data: reqData || {},
            async: isasyncB,
            success: callback,
            error: callbackError,
            headers:headers
        });
    },
    loginInfo: function () {
        $('#header-school-name').text(this.cookie.getCookie('tnName'));
    },
    logout: function () {
        // 登出
        $('#logout-btn').on('click', function () {
            alert(232)
            Common.cookie.delCookie('tnName');
            window.location.href = '/login';
        });
    },
    checkAll:function(){
        // 全选
        $('#checkAll').on('click', function () {
            if ($(this).is(':checked')) {
                $('.check-template :checkbox').prop('checked', true);
            } else {
                $('.check-template :checkbox').prop('checked', false);
            }
        });
        $(".check-template").on('click', ':checkbox', function () {
            allchk();
        });
        function allchk() {
            var chknum = $(".check-template :checkbox").size();//选项总个数
            var chk = 0;
            $(".check-template").find(':checkbox').each(function () {
                if ($(this).prop("checked") == true) {
                    chk++;
                }
            });
            if (chknum == chk) {//全选
                $("#checkAll").prop("checked", true);
            } else {//不全选
                $("#checkAll").prop("checked", false);
            }
        }
    },
    getClassRoom:function(){  // 获取教室
        var classRoom = '';
        Common.ajaxFun('/config/classRoom/get/'+ tnId +'.do', 'GET', {}, function (res) {
            //console.log(res)
            if (res.rtnCode == "0000000") {
                classRoom = res
            }
        }, function (res) {
            alert("出错了");
        }, true);
        return classRoom;
    },
    getGrade:function(){  // 获取年级
        var gradeV = '';
        Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                gradeV = res;
            }
        }, function (res) {
            alert("出错了");
        }, true);
        return gradeV;
    },
    initClass:function(){  // 获取年级
        var gradeV = '';
        Common.ajaxFun('/config/getInit/'+ tnId +'.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                gradeV = res;
            }
        }, function (res) {
            alert("出错了");
        }, true);
        return gradeV;
    },

};
Common.init();
