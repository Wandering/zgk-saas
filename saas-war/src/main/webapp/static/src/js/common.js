var Common = {
    init: function () {
        this.loginInfo();
        this.logout();
    },
    url:{

    },
    cookie:{
        /*
         *功能：设置Cookie
         *cookieName 必选项，cookie名称
         *cookieValue 必选项，cookie值
         *seconds 生存时间，可选项，单位：秒；默认时间是3600秒
         *path cookie存放路径，可选项
         *domain cookie域，可选项
         *secure 安全性，指定Cookie是否只能通过https协议访问，一般的Cookie使用HTTP协议既可访问，如果设置了Secure（没有值），则只有当使用https协议连接时cookie才可以被页面访问
         */
        setCookie : function (cookieName, cookieValue, seconds, path, domain, secure) {
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
    modal: function (ele, title, content, footer) {
        var dialogModalArr = [];
        dialogModalArr.push('<div class="modal fade" id="' + ele + '" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">');
        dialogModalArr.push('<div class="modal-dialog">');
        dialogModalArr.push('<div class="modal-content">');
        dialogModalArr.push('<div class="modal-header">');
        dialogModalArr.push('<button type="button" class="close dialogModal-close-btn" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>');
        dialogModalArr.push('<h4 class="modal-title" id="myModalLabel">' + title + '</h4>');
        dialogModalArr.push('</div>');
        dialogModalArr.push('<div class="modal-body">');
        dialogModalArr.push(content);
        dialogModalArr.push('</div>');
        if (footer) {
            dialogModalArr.push(footer);
        }
        dialogModalArr.push('</div>');
        dialogModalArr.push('</div>');
        dialogModalArr.push('</div>');
        $('body').append(dialogModalArr.join(''));
        $(".dialogModal-close-btn").click(function () {
            $("#" + ele).remove();
            $('.modal-backdrop').remove();
            $("body").removeClass('modal-open');
        });
    },
    ajaxFun: function (url, method, reqData, callback, callbackError, isasync) {
        var data = {};
        var isasyncB = null;
        if (isasync) {
            isasyncB = false;
        } else {
            isasyncB = true;
        }
        $.ajax({
            url: url,
            type: method,
            data: data || {},
            async: isasyncB,
            success: callback,
            error: callbackError
        });
    },
    loginInfo:function(){
        $('#header-school-name').text(this.cookie.getCookie('tnName'));
    },
    logout:function(){
        // 登出
        $('#logout-btn').on('click', function () {
            alert(232)
            Common.cookie.delCookie('tnName');
            window.location.href='/login';
        });
    }
};
Common.init();
