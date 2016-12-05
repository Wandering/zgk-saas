var Common = {
    init: function () {
        this.loginInfo();
        this.logout();
        this.checkAll();
        this.renderMenu();
        $('body').on('click','.close-btn',function(){
            layer.closeAll();
        });
    },
    getLinkey: function(name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(window.location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
        return "";
    },
    provinceKey: function () {
        var key;
        var urlDomain = window.location.hostname + '';
        var urlArr = urlDomain.split('.');
        key = urlArr[0];
        return key;
    },
    flowSteps:function(){
        var pathName = window.location.pathname;
        var pathNum = pathName.slice((pathName.length-1),pathName.length);
        var tnId = this.cookie.getCookie('tnId');
        Common.ajaxFun('/config/get/step/'+ tnId +'.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                if(pathNum!=res.bizData.result){
                    if(res.bizData.result =='0'){
                        window.location.href='/course-guide';
                    }else{
                        window.location.href='/seting-process'+res.bizData.result;
                    }
                }
            }
        }, function (res) {
            layer.msg('出错了');
        });
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
            Common.cookie.delCookie('isInit');
            Common.cookie.delCookie('meuns');
            Common.cookie.delCookie('tnId');
            Common.cookie.delCookie('tnName');
            window.location.href = '/login';
        });
    },
    checkAll:function(){
        // 全选
        $(document).on('click', '#checkAll', function () {
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
    renderMenu:function(){
        var pathName = window.location.pathname;
        if(Common.cookie.getCookie('siderMenu')){
            var siderMenu = $.parseJSON(Common.cookie.getCookie('siderMenu'));
            var menus = [];
            //menus.push('<li class="nav-li" style="display: block;">');
            //menus.push('<a href="index.html">');
            //menus.push('<i class="icon-home"></i>');
            //menus.push('<span class="menu-text">首页</span>');
            //menus.push('</a>');
            //menus.push('</li>');
            var icons = ['icon-desktop','icon-edit','icon-list-alt','icon-dashboard','icon-tag','icon-tag','icon-tasks','icon-group']
            $.each(siderMenu,function(i,v){

                if(pathName==v.meunUrl){
                    menus.push('<li class="nav-li active">');
                }else{
                    menus.push('<li class="nav-li">');
                }
                if(v.sonMeuns.length==0){
                    menus.push('<a href="'+ v.meunUrl +'" class="dropdown-toggle" id="'+ v.meunId +'">');
                }else{
                    menus.push('<a href="javascript:;" class="dropdown-toggle" id="'+ v.meunId +'">');
                }
                // menus.push('<i class="icon-desktop"></i>');
                menus.push('<i class="'+icons[i]+'"></i>');
                menus.push('<span class="menu-text">'+ v.meunName +'</span>');
                menus.push('</a>');
                if(v.sonMeuns.length>0){
                    //console.log(v.sonMeuns)
                    menus.push('<ul class="submenu">');
                    $.each(v.sonMeuns,function(k,m){
                        if(pathName == m.meunUrl){
                            menus.push('<li class="active">');
                        }else{
                            menus.push('<li>');
                        }
                        menus.push('<a href="'+ m.meunUrl +'" id="'+ m.meunId +'"><i class="icon-double-angle-right"></i>'+ m.meunName +'</a>');
                        menus.push('</li>');
                    });
                    menus.push('</ul>');
                }
                menus.push('</li>');
            });
            $('#nav-list').append(menus.join(''));
            $('body').find('.submenu li.active').parents('li.nav-li').addClass('open active');
        }
    },
    getFormatTime:function (timestamp,formatStr) {
        var newDate = new Date();
        newDate.setTime(timestamp);
        return newDate.Format(formatStr || "yyyy-MM-dd hh:mm:ss");
    },
    getPageName:function(urls){
        var strUrl = urls;
        var arrUrl = strUrl.split("/");
        var strPage = arrUrl[arrUrl.length - 1];
        return strPage;
    }
};
Common.init();

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
