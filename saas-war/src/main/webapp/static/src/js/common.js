
var Common = {
    init: function () {
        this.loginInfo();
        this.logout();
        this.checkAll();
        this.renderMenu();
        $('body').on('click', '.close-btn', function () {
            layer.closeAll();
        });
        // $('.nav-li').each(function(i,v){
        //     console.info(v)
        // });
        // $(document).on('mouseover mouseout','.dropdown-toggle',function(){
        //     var foo = $(this).find('[name="toggle-icon-url"]').attr('class');
        //     if(event.type == "mouseover"){
        //         if(foo.length!=14){
        //             return false;
        //         }
        //         $(this).find('[name="toggle-icon-url"]').attr('class',foo+'-act');
        //     }else if(event.type == "mouseout"){
        //         $(this).find('[name="toggle-icon-url"]').attr('class',foo.substr(0,foo.length-4));
        //     }
        // })
    },
    getLinkey: function (name) {
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
    flowSteps: function () {
        var pathName = window.location.pathname;
        var pathNum = pathName.slice((pathName.length - 1), pathName.length);
        var tnId = this.cookie.getCookie('tnId');
        var indexUrl = this.cookie.getCookie('indexUrl');
        Common.ajaxFun('/config/get/step/' + tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                if (pathNum != res.bizData.result) {
                    if (res.bizData.result == '0') {
                        window.location.href = '/' + indexUrl;
                    } else {
                        window.location.href = '/seting-process' + res.bizData.result;
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
    ajaxFun: function (url, method, reqData, callback, callbackError, isasync, acceptType) {
        var isasyncB = null;
        if (isasync) {
            isasyncB = false;
        } else {
            isasyncB = true;
        }
        var headers = null;
        if (acceptType) {
            headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        } else {
            headers = '';
        }

        $.ajax({
            url: url,
            type: method,
            data: reqData || {},
            async: isasyncB,
            success: callback,
            error: callbackError,
            headers: headers
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
            Common.cookie.delCookie('taskId');
            Common.cookie.delCookie('siderMenu');
            Common.cookie.delCookie('userId');
            Common.cookie.delCookie('scheduleName');
            Common.cookie.delCookie('provinceId');
            Common.cookie.delCookie('isSuperManager');
            Common.cookie.delCookie('indexUrl');
            Common.cookie.delCookie('gradeName');
            Common.cookie.delCookie('countyId');
            window.location.href = '/login';
        });
    },
    checkAll: function () {
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
    getClassRoom: function () {  // 获取教室
        var classRoom = '';
        Common.ajaxFun('/config/classRoom/get/' + tnId + '.do', 'GET', {}, function (res) {
            //console.log(res)
            if (res.rtnCode == "0000000") {
                classRoom = res
            }
        }, function (res) {
            alert("出错了");
        }, true);
        return classRoom;
    },
    getGrade: function () {  // 获取年级
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
    initClass: function () {  // 获取年级
        var gradeV = '';
        Common.ajaxFun('/config/getInit/' + tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                gradeV = res;
            }
        }, function (res) {
            alert("出错了");
        }, true);
        return gradeV;
    },
    renderMenu: function () {
        var that = this;
        var pathName = window.location.pathname;
        if (pathName == '/course-scheduling-step1' ||
            pathName == '/course-scheduling-step2' ||
            pathName == '/course-scheduling-step3' ||
            pathName == '/course-info' ||
            pathName == '/teacher-info' ||
            pathName == '/course-no-proceed' ||
            pathName == '/class-mixed' ||
            pathName == '/base-rule-settings') {
            pathName = '/course-scheduling';
        }else if(pathName == '/select-course-settings'){
            pathName = '/select-course';
        }


        if (Common.cookie.getCookie('siderMenu')) {
            var siderMenu = $.parseJSON(Common.cookie.getCookie('siderMenu'));
            var indexUrl = that.cookie.getCookie('indexUrl');
            var menus = [];
            if(("/"+indexUrl)==pathName){
                menus.push('<li class="nav-li active">');
            }else{
                menus.push('<li class="nav-li">');
            }
            menus.push('<a href="/'+ indexUrl +'">');
            menus.push('<i class="icon-sidebar-0" name="toggle-icon-url"></i>');
            menus.push('<span class="menu-text">首页</span>');
            menus.push('</a>');
            menus.push('</li>');
            $.each(siderMenu, function (i, v) {
                if (pathName == v.meunUrl) {
                    menus.push('<li class="nav-li active">');
                } else {
                    menus.push('<li class="nav-li">');
                }
                if (v.sonMeuns.length == 0) {
                    menus.push('<a href="' + v.meunUrl + '" class="dropdown-toggle" id="' + v.meunId + '">');
                } else {
                    menus.push('<a href="javascript:;" class="dropdown-toggle" id="' + v.meunId + '">');
                }
                // menus.push('<i class="icon-desktop"></i>');
                // menus.push('<i class="'+icons[i]+'"></i>');
                menus.push('<i class="' + v.iconUrl + '" name="toggle-icon-url"></i>');
                // menus.push('<i class="'+v.iconUrl+'"></i>');
                menus.push('<span class="menu-text">' + v.meunName + '</span>');
                menus.push('</a>');
                if (v.sonMeuns.length > 0) {
                    menus.push('<ul class="submenu">');

                    $.each(v.sonMeuns, function (k, m) {
                        if (pathName == m.meunUrl) {
                            if(m.meunName=="三位一体招生"){
                                menus.push('<li class="active trinity">');
                            }else{
                                menus.push('<li class="active">');
                            }

                        } else {
                            if(m.meunName=="三位一体招生"){
                                menus.push('<li class="trinity">');
                            }else{
                                menus.push('<li>');
                            }

                        }
                        menus.push('<a href="' + m.meunUrl + '" id="' + m.meunId + '"><i class="icon-double-angle-right"></i>' + m.meunName + '</a>');
                        menus.push('</li>');
                    });
                    menus.push('</ul>');
                }
                menus.push('</li>');
            });
            $('#nav-list').append(menus.join(''));
            $('body').find('.submenu li.active').parents('li.nav-li').addClass('open active');
            var fooAtrr = $('body').find('.submenu li.active').parents('li.nav-li').find('[name="toggle-icon-url"]').attr('class');
            $('body').find('.submenu li.active').parents('li.nav-li').find('[name="toggle-icon-url"]').attr('class', fooAtrr + '-act');

            var firstNavClass = $('.nav-li.active i').attr('class');
            if((pathName == '/' + indexUrl) || pathName =='/professional-assessment'){
                $('.nav-li.active').find('i').attr('class', firstNavClass + '-act')
            }


            var countyId = Common.cookie.getCookie('countyId');
            if(countyId.substring(0,2)!='33'){
                $('li.trinity').remove();
            }



        }
    },
    getFormatTime: function (timestamp, formatStr) {
        var newDate = new Date();
        newDate.setTime(timestamp);
        return newDate.Format(formatStr || "yyyy-MM-dd hh:mm:ss");
    },
    getPageName: function (urls) {
        var strUrl = urls;
        var arrUrl = strUrl.split("/");
        var strPage = arrUrl[arrUrl.length - 1];
        return strPage;
    },
    checkInfoIsPerfect: function (taskId) {
        var checkInfoIsPerfect = false;
        Common.ajaxFun('/scheduleTask/checkInfoIsPerfect.do', 'GET', {
            'taskId': taskId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                checkInfoIsPerfect = true;
            } else {
                checkInfoIsPerfect = false;
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
        return checkInfoIsPerfect;
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


//Handlebars tool
Handlebars.registerHelper('compare', function (left, operator, right, options) {
    if (arguments.length < 3) {
        throw new Error('Handlerbars Helper "compare" needs 2 parameters');
    }
    var operators = {
        '==': function (l, r) {
            return l == r;
        },
        '===': function (l, r) {
            return l === r;
        },
        '!=': function (l, r) {
            return l != r;
        },
        '!==': function (l, r) {
            return l !== r;
        },
        '<': function (l, r) {
            return l < r;
        },
        '>': function (l, r) {
            return l > r;
        },
        '<=': function (l, r) {
            return l <= r;
        },
        '>=': function (l, r) {
            return l >= r;
        },
        'typeof': function (l, r) {
            return typeof l == r;
        }
    };
    if (!operators[operator]) {
        throw new Error('Handlerbars Helper "compare" doesn\'t know the operator ' + operator);
    }
    var result = operators[operator](left, right);
    if (result) {
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});