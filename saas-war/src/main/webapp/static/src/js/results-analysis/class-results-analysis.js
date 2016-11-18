var tnId = Common.cookie.getCookie('tnId');
function ClassResultsAnalysis() {
    this.init();
}
ClassResultsAnalysis.prototype = {
    constructor: ClassResultsAnalysis,
    init: function () {
        var that = this;
        that.defaultGrade();
    },
    // 默认年级赋值
    defaultGrade:function(){
        var that = this;
        that.getGrade();
        that.selGrade();
    },
    // 获取年级
    getGrade: function () {
        Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#grade-template").html());
                $('#grade-body').html(myTemplate(res));
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
    },
    // 选择年级
    selGrade: function () {
        var that = this;
        // 选择高一
        $('input[name="results-radio"]').change(function () {
            $(this).attr('checked','checked');
            var radioV = $(this).val();
            that.getClassesNameByGrade(radioV);
            if (radioV.indexOf('高三') >= 0 || radioV.indexOf('高3') >= 0) {

            } else {

            }
        });
        $('.grade-item:first').change();
    },
    // 选择班级
    selClass: function (grade) {
        var that = this;
        // 选择高一
        $('#select').on('change',function () {
            var selV =  $(this).find("option:selected").val();
            $('.sel-class-txt').text(selV);
            that.getAvgScoresForClass(grade,selV);
            that.getStuNumberScoreChangeForClass(grade,selV);
            that.getOverLineDetailForClass(grade,selV,'batchAll');
        });
        $('#select').find('option:eq(0)').attr('selected','selected');
        var firstV = $('#select').find('option:eq(0)').val();
        $('.sel-class-txt').text(firstV);
        that.getAvgScoresForClass(grade,firstV);
        that.getStuNumberScoreChangeForClass(grade,firstV);
        that.getOverLineDetailForClass(grade,firstV,'batchAll');
        that.chartTab();
    },
    // 通过年级获取班级
    getClassesNameByGrade:function(grade){
        var that = this;
        $('.title-2 .class-sel').html('');
        Common.ajaxFun('/scoreAnalyse/getClassesNameByGrade', 'GET', {
            'tnId':tnId,
            'grade':grade
        }, function (res) {
            if (res.rtnCode == "0000000") {
                if(res.bizData.length==0){
                    layer.msg('您还没有设置班级');
                }else{
                    var classOption=[];
                    $.each(res.bizData,function(i,v){
                        classOption.push('<option class="opt-item" value="'+ v +'">'+ v +'</option>');
                    });
                    $('.title-2 .class-sel').append(classOption);
                    that.selClass(grade);
                }
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // chartTab
    chartTab:function(){
        var that = this;
        $('.chart-tab button').on('click',function(){
            var index = $(this).index();
            $(this).addClass('cur').siblings().removeClass('cur');
            $('.chart-box').hide().eq(index).show();
        });
        $('.chart-tab button:eq(1)').click();
        that.totalScoreChart();
    },
    // 拉取班级趋势数据
    getAvgScoresForClass:function(grade,className){
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getAvgScoresForClass', 'GET', {
            'tnId':tnId,
            'grade':grade,
            'className':className
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var dataJson = res.bizData;
                if(dataJson.length>0){
                    var dateData = [];
                    var totalScoreData = [];
                    var subjectData = [];
                    var datas = [];
                    var datasArr = [];
                    for(var k in dataJson[0]){
                        switch (k){
                            case 'className':
                            case 'examTime':
                            case '总分':
                                break;
                            default:
                                subjectData.push(k);
                                break;
                        }
                    }
                    for(var i in dataJson) {
                        dateData.push(dataJson[i].examTime);
                        totalScoreData.push(parseInt(dataJson[i]['总分']));
                    }



                    for(var j in subjectData){
                        var seriesObj = {
                            name:subjectData[j],
                            type : 'line',
                            data:datasArr
                        };
                        datas.push(seriesObj);
                    }
                    console.log(dateData)
                    console.log(subjectData)
                    console.log(datas)
                    that.totalScoreChart(dateData,totalScoreData);
                    that.subjectsChart(subjectData,dateData,datas);
                }
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 查看总分趋势
    totalScoreChart:function(dateData,totalScoreData){
        var totalScoreChart = echarts.init(document.getElementById('totalScoreChart-chart'));
        var totalScoreChartOption = {
            title: {
                text: '班级平均分排名',
                left: 'left',
                textStyle:{
                    fontSize:12
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c}'
            },
            legend: {
                left: 'left'
            },
            xAxis: {
                type: 'category',
                name: 'x',
                splitLine: {show: false},
                //data: ['一', '二', '三', '四', '五', '六', '七', '八', '九']
                data: ["2016-02-02", "2016-03-03", "2016-04-07", "2016-05-10", "2016-06-14"]
                //data: dateData
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            yAxis: {
                type: 'value',
                min:Array.min(totalScoreData),
                max:Array.max(totalScoreData)
            },
            series: [
                {
                    name: '3的指数',
                    type: 'line',
                    //data: [1, 3, 9, 27, 81, 247, 741, 2223, 6669]
                    data: totalScoreData
                }
            ]
        };
        totalScoreChart.setOption(totalScoreChartOption);
    },
    subjectsChart:function(subjectData,dateData,datas){
        var subjectsChart = echarts.init(document.getElementById('subjectsChart-chart'));
        var subjectsChartOption = {
            title: {
                text: '班级平均分排名',
                left: 'left',
                textStyle:{
                    fontSize:12
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c}'
            },
            legend: {
                left: 'center',
                //data: ['2的指数', '3的指数']
                data: subjectData
            },
            xAxis: {
                type: 'category',
                name: 'x',
                splitLine: {show: false},
                //data: ['一', '二', '三', '四', '五', '六', '七', '八', '九']
                data: dateData
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            yAxis: {
                type: 'log',
                name: 'y'
            },
            series:datas
            //series: [
            //    {
            //        name: '3的指数',
            //        type: 'line',
            //        data: [1, 3, 9, 27, 81, 247, 741, 2223, 6669]
            //    },
            //    {
            //        name: '2的指数',
            //        type: 'line',
            //        data: [1, 2, 4, 8, 16, 32, 64, 128, 256]
            //    },
            //    {
            //        name: '1/2的指数',
            //        type: 'line',
            //        data: [1/2, 1/4, 1/8, 1/16, 1/32, 1/64, 1/128, 1/256, 1/512]
            //    }
            //]
        };
        subjectsChart.setOption(subjectsChartOption);
    },
    // 查看各科情况
    // 人数分布变化
    getStuNumberScoreChangeForClass:function(grade,className){
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getStuNumberScoreChangeForClass', 'GET', {
            'tnId':tnId,
            'grade':grade,
            'className':className
        }, function (res) {
            var res = {
                "bizData": [
                    {
                        "年级排名": "1-100",
                        "最近第二次考试": 13,
                        "最近第一次考试": 13,
                        "变化人数": 24,
                        "data": [
                            {
                                "变化趋势": "505名 - 2名",
                                "学生姓名": "弓旭炎"
                            },
                            {
                                "变化趋势": "263名 - 54名",
                                "学生姓名": "阚枫"
                            },
                            {
                                "变化趋势": "312名 - 80名",
                                "学生姓名": "茹行"
                            },
                            {
                                "变化趋势": "15名 - 1名",
                                "学生姓名": "里 羽"
                            },
                            {
                                "变化趋势": "262名 - 60名",
                                "学生姓名": "东波安"
                            },
                            {
                                "变化趋势": "58名 - 70名",
                                "学生姓名": "洪行炎"
                            },
                            {
                                "变化趋势": "487名 - 73名",
                                "学生姓名": "阎薇菲"
                            },
                            {
                                "变化趋势": "418名 - 95名",
                                "学生姓名": "续明武"
                            },
                            {
                                "变化趋势": "412名 - 84名",
                                "学生姓名": "通翠"
                            },
                            {
                                "变化趋势": "512名 - 50名",
                                "学生姓名": "金莲"
                            },
                            {
                                "变化趋势": "249名 - 51名",
                                "学生姓名": "归婉丹"
                            },
                            {
                                "变化趋势": "363名 - 61名",
                                "学生姓名": "马浩林"
                            },
                            {
                                "变化趋势": "364名 - 77名",
                                "学生姓名": "万言敬"
                            },
                            {
                                "变化趋势": "35名 - 437名",
                                "学生姓名": "贲达"
                            },
                            {
                                "变化趋势": "1名 - 266名",
                                "学生姓名": "后毅若"
                            },
                            {
                                "变化趋势": "94名 - 496名",
                                "学生姓名": "翁轮江"
                            },
                            {
                                "变化趋势": "28名 - 205名",
                                "学生姓名": "曹有富"
                            },
                            {
                                "变化趋势": "48名 - 446名",
                                "学生姓名": "越琦筠"
                            },
                            {
                                "变化趋势": "98名 - 119名",
                                "学生姓名": "山翰林"
                            },
                            {
                                "变化趋势": "52名 - 328名",
                                "学生姓名": "阴爽"
                            },
                            {
                                "变化趋势": "51名 - 242名",
                                "学生姓名": "姚林厚"
                            },
                            {
                                "变化趋势": "16名 - 244名",
                                "学生姓名": "法德"
                            },
                            {
                                "变化趋势": "82名 - 380名",
                                "学生姓名": "燕宏"
                            },
                            {
                                "变化趋势": "53名 - 355名",
                                "学生姓名": "米轮"
                            }
                        ]
                    },
                    {
                        "年级排名": "101-200",
                        "最近第二次考试": 10,
                        "最近第一次考试": 12,
                        "变化人数": 20,
                        "data": [
                            {
                                "变化趋势": "136名 - 103名",
                                "学生姓名": "柴萍欣"
                            },
                            {
                                "变化趋势": "502名 - 117名",
                                "学生姓名": "管瑗玉"
                            },
                            {
                                "变化趋势": "298名 - 138名",
                                "学生姓名": "辛菲"
                            },
                            {
                                "变化趋势": "215名 - 194名",
                                "学生姓名": "花广学"
                            },
                            {
                                "变化趋势": "397名 - 133名",
                                "学生姓名": "倪晶"
                            },
                            {
                                "变化趋势": "98名 - 119名",
                                "学生姓名": "山翰林"
                            },
                            {
                                "变化趋势": "481名 - 179名",
                                "学生姓名": "鲍影珊"
                            },
                            {
                                "变化趋势": "214名 - 148名",
                                "学生姓名": "关舒慧"
                            },
                            {
                                "变化趋势": "190名 - 139名",
                                "学生姓名": "梁菲苑"
                            },
                            {
                                "变化趋势": "515名 - 128名",
                                "学生姓名": "贝雁菲"
                            },
                            {
                                "变化趋势": "206名 - 163名",
                                "学生姓名": "於世"
                            },
                            {
                                "变化趋势": "243名 - 116名",
                                "学生姓名": "皮富"
                            },
                            {
                                "变化趋势": "108名 - 405名",
                                "学生姓名": "茅建震"
                            },
                            {
                                "变化趋势": "162名 - 370名",
                                "学生姓名": "璩桂聪"
                            },
                            {
                                "变化趋势": "135名 - 363名",
                                "学生姓名": "谷蓓娜"
                            },
                            {
                                "变化趋势": "142名 - 371名",
                                "学生姓名": "饶娴"
                            },
                            {
                                "变化趋势": "149名 - 201名",
                                "学生姓名": "蔚香惠"
                            },
                            {
                                "变化趋势": "171名 - 382名",
                                "学生姓名": "良洁娅"
                            },
                            {
                                "变化趋势": "140名 - 227名",
                                "学生姓名": "山芬"
                            },
                            {
                                "变化趋势": "170名 - 241名",
                                "学生姓名": "索天平"
                            }
                        ]
                    },
                    {
                        "年级排名": "201-300",
                        "最近第二次考试": 15,
                        "最近第一次考试": 15,
                        "变化人数": 28,
                        "data": [
                            {
                                "变化趋势": "269名 - 284名",
                                "学生姓名": "巫强保"
                            },
                            {
                                "变化趋势": "1名 - 266名",
                                "学生姓名": "后毅若"
                            },
                            {
                                "变化趋势": "299名 - 233名",
                                "学生姓名": "高月"
                            },
                            {
                                "变化趋势": "305名 - 243名",
                                "学生姓名": "杜星山"
                            },
                            {
                                "变化趋势": "472名 - 226名",
                                "学生姓名": "司民"
                            },
                            {
                                "变化趋势": "334名 - 232名",
                                "学生姓名": "乔敬"
                            },
                            {
                                "变化趋势": "28名 - 205名",
                                "学生姓名": "曹有富"
                            },
                            {
                                "变化趋势": "349名 - 216名",
                                "学生姓名": "吴心"
                            },
                            {
                                "变化趋势": "51名 - 242名",
                                "学生姓名": "姚林厚"
                            },
                            {
                                "变化趋势": "378名 - 271名",
                                "学生姓名": "寇瑗珍"
                            },
                            {
                                "变化趋势": "350名 - 267名",
                                "学生姓名": "蓬昭叶"
                            },
                            {
                                "变化趋势": "16名 - 244名",
                                "学生姓名": "法德"
                            },
                            {
                                "变化趋势": "149名 - 201名",
                                "学生姓名": "蔚香惠"
                            },
                            {
                                "变化趋势": "140名 - 227名",
                                "学生姓名": "山芬"
                            },
                            {
                                "变化趋势": "170名 - 241名",
                                "学生姓名": "索天平"
                            },
                            {
                                "变化趋势": "263名 - 54名",
                                "学生姓名": "阚枫"
                            },
                            {
                                "变化趋势": "203名 - 317名",
                                "学生姓名": "五鸣林"
                            },
                            {
                                "变化趋势": "213名 - 411名",
                                "学生姓名": "单希婷"
                            },
                            {
                                "变化趋势": "298名 - 138名",
                                "学生姓名": "辛菲"
                            },
                            {
                                "变化趋势": "295名 - 433名",
                                "学生姓名": "韩霞嘉"
                            },
                            {
                                "变化趋势": "262名 - 60名",
                                "学生姓名": "东波安"
                            },
                            {
                                "变化趋势": "205名 - 375名",
                                "学生姓名": "欧文诚"
                            },
                            {
                                "变化趋势": "215名 - 194名",
                                "学生姓名": "花广学"
                            },
                            {
                                "变化趋势": "249名 - 51名",
                                "学生姓名": "归婉丹"
                            },
                            {
                                "变化趋势": "290名 - 376名",
                                "学生姓名": "邴琼纨"
                            },
                            {
                                "变化趋势": "214名 - 148名",
                                "学生姓名": "关舒慧"
                            },
                            {
                                "变化趋势": "206名 - 163名",
                                "学生姓名": "於世"
                            },
                            {
                                "变化趋势": "243名 - 116名",
                                "学生姓名": "皮富"
                            }
                        ]
                    },
                    {
                        "年级排名": "301-400",
                        "最近第二次考试": 13,
                        "最近第一次考试": 13,
                        "变化人数": 25,
                        "data": [
                            {
                                "变化趋势": "203名 - 317名",
                                "学生姓名": "五鸣林"
                            },
                            {
                                "变化趋势": "162名 - 370名",
                                "学生姓名": "璩桂聪"
                            },
                            {
                                "变化趋势": "135名 - 363名",
                                "学生姓名": "谷蓓娜"
                            },
                            {
                                "变化趋势": "205名 - 375名",
                                "学生姓名": "欧文诚"
                            },
                            {
                                "变化趋势": "435名 - 312名",
                                "学生姓名": "利富志"
                            },
                            {
                                "变化趋势": "52名 - 328名",
                                "学生姓名": "阴爽"
                            },
                            {
                                "变化趋势": "327名 - 399名",
                                "学生姓名": "司丹静"
                            },
                            {
                                "变化趋势": "142名 - 371名",
                                "学生姓名": "饶娴"
                            },
                            {
                                "变化趋势": "290名 - 376名",
                                "学生姓名": "邴琼纨"
                            },
                            {
                                "变化趋势": "429名 - 318名",
                                "学生姓名": "逄明士"
                            },
                            {
                                "变化趋势": "171名 - 382名",
                                "学生姓名": "良洁娅"
                            },
                            {
                                "变化趋势": "82名 - 380名",
                                "学生姓名": "燕宏"
                            },
                            {
                                "变化趋势": "53名 - 355名",
                                "学生姓名": "米轮"
                            },
                            {
                                "变化趋势": "311名 - 421名",
                                "学生姓名": "宫莎"
                            },
                            {
                                "变化趋势": "312名 - 80名",
                                "学生姓名": "茹行"
                            },
                            {
                                "变化趋势": "305名 - 243名",
                                "学生姓名": "杜星山"
                            },
                            {
                                "变化趋势": "334名 - 232名",
                                "学生姓名": "乔敬"
                            },
                            {
                                "变化趋势": "397名 - 133名",
                                "学生姓名": "倪晶"
                            },
                            {
                                "变化趋势": "349名 - 216名",
                                "学生姓名": "吴心"
                            },
                            {
                                "变化趋势": "378名 - 271名",
                                "学生姓名": "寇瑗珍"
                            },
                            {
                                "变化趋势": "350名 - 267名",
                                "学生姓名": "蓬昭叶"
                            },
                            {
                                "变化趋势": "363名 - 61名",
                                "学生姓名": "马浩林"
                            },
                            {
                                "变化趋势": "328名 - 509名",
                                "学生姓名": "茅乐有"
                            },
                            {
                                "变化趋势": "351名 - 438名",
                                "学生姓名": "梁竹卿"
                            },
                            {
                                "变化趋势": "364名 - 77名",
                                "学生姓名": "万言敬"
                            }
                        ]
                    },
                    {
                        "年级排名": "401-500",
                        "最近第二次考试": 10,
                        "最近第一次考试": 11,
                        "变化人数": 18,
                        "data": [
                            {
                                "变化趋势": "492名 - 428名",
                                "学生姓名": "贾飘雪"
                            },
                            {
                                "变化趋势": "35名 - 437名",
                                "学生姓名": "贲达"
                            },
                            {
                                "变化趋势": "213名 - 411名",
                                "学生姓名": "单希婷"
                            },
                            {
                                "变化趋势": "311名 - 421名",
                                "学生姓名": "宫莎"
                            },
                            {
                                "变化趋势": "108名 - 405名",
                                "学生姓名": "茅建震"
                            },
                            {
                                "变化趋势": "295名 - 433名",
                                "学生姓名": "韩霞嘉"
                            },
                            {
                                "变化趋势": "94名 - 496名",
                                "学生姓名": "翁轮江"
                            },
                            {
                                "变化趋势": "462名 - 471名",
                                "学生姓名": "俟娣"
                            },
                            {
                                "变化趋势": "48名 - 446名",
                                "学生姓名": "越琦筠"
                            },
                            {
                                "变化趋势": "488名 - 499名",
                                "学生姓名": "马婷"
                            },
                            {
                                "变化趋势": "351名 - 438名",
                                "学生姓名": "梁竹卿"
                            },
                            {
                                "变化趋势": "487名 - 73名",
                                "学生姓名": "阎薇菲"
                            },
                            {
                                "变化趋势": "472名 - 226名",
                                "学生姓名": "司民"
                            },
                            {
                                "变化趋势": "435名 - 312名",
                                "学生姓名": "利富志"
                            },
                            {
                                "变化趋势": "418名 - 95名",
                                "学生姓名": "续明武"
                            },
                            {
                                "变化趋势": "481名 - 179名",
                                "学生姓名": "鲍影珊"
                            },
                            {
                                "变化趋势": "412名 - 84名",
                                "学生姓名": "通翠"
                            },
                            {
                                "变化趋势": "429名 - 318名",
                                "学生姓名": "逄明士"
                            }
                        ]
                    },
                    {
                        "年级排名": "501-600",
                        "最近第二次考试": 4,
                        "最近第一次考试": 1,
                        "变化人数": 5,
                        "data": [
                            {
                                "变化趋势": "328名 - 509名",
                                "学生姓名": "茅乐有"
                            },
                            {
                                "变化趋势": "505名 - 2名",
                                "学生姓名": "弓旭炎"
                            },
                            {
                                "变化趋势": "502名 - 117名",
                                "学生姓名": "管瑗玉"
                            },
                            {
                                "变化趋势": "512名 - 50名",
                                "学生姓名": "金莲"
                            },
                            {
                                "变化趋势": "515名 - 128名",
                                "学生姓名": "贝雁菲"
                            }
                        ]
                    }
                ],
                "rtnCode": "0000000",
                "ts": 1479375687887
            }
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#student-change-template").html());
                Handlebars.registerHelper('agree_button', function() {
                    return new Handlebars.SafeString(
                       '<tr><td>' + this.学生姓名 + '</td><td>' + this.变化趋势 + '</td></tr>'
                    );
                });
                $('#student-change-tbody').html(myTemplate(res));
                that.changeStudent();
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    changeStudent:function(){
        $('.change-student-btn').on('click',function(){
            var data = $(this).attr('data');
            var studentChangeTable = '<table class="student-change-table table table-hover"><thead><tr><th class="center">学生姓名</th><th class="center">变化趋势</th></tr></thead><tbody>'+ data +'</tbody></table>'
            layer.open({
                type: 1,
                title: '变化人数详情',
                offset: 'auto',
                area: ['362px', '350px'],
                content: studentChangeTable
            });
        });
    },
    // 变化人数弹层

    // 个人成绩变化趋势
    getAvgScoresForClassStudent:function(){
        Common.ajaxFun('/scoreAnalyse/getStuNumberScoreChangeForClass', 'GET', {
            'tnId':tnId,
            'grade':grade,
            'className':className,
            'batch':batch
        }, function (res) {
            var res = {
                "bizData": [
                    {
                        "学生姓名": "冯月芝",
                        "年级排名": "223",
                        "成绩": "595",
                        "班级排名": "33"
                    },
                    {
                        "学生姓名": "辕宁妍",
                        "年级排名": "234",
                        "成绩": "592",
                        "班级排名": "34"
                    },
                    {
                        "学生姓名": "汤伦风",
                        "年级排名": "245",
                        "成绩": "590",
                        "班级排名": "35"
                    },
                    {
                        "学生姓名": "于瑾琰",
                        "年级排名": "246",
                        "成绩": "590",
                        "班级排名": "36"
                    },
                    {
                        "学生姓名": "寿朗泰",
                        "年级排名": "258",
                        "成绩": "587",
                        "班级排名": "37"
                    },
                    {
                        "学生姓名": "索功",
                        "年级排名": "263",
                        "成绩": "585",
                        "班级排名": "38"
                    },
                    {
                        "学生姓名": "秋信",
                        "年级排名": "293",
                        "成绩": "576",
                        "班级排名": "39"
                    },
                    {
                        "学生姓名": "居菲",
                        "年级排名": "301",
                        "成绩": "574",
                        "班级排名": "40"
                    },
                    {
                        "学生姓名": "阚毓芳",
                        "年级排名": "302",
                        "成绩": "574",
                        "班级排名": "41"
                    },
                    {
                        "学生姓名": "暴玉霄",
                        "年级排名": "320",
                        "成绩": "569",
                        "班级排名": "42"
                    },
                    {
                        "学生姓名": "颛天",
                        "年级排名": "321",
                        "成绩": "567",
                        "班级排名": "43"
                    }
                ],
                "rtnCode": "0000000",
                "ts": 1479377602813
            }
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#online-template").html());
                $('#online-tbody').html(myTemplate(res));
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 设置关注位次线
    // 班级前多少名人数统计
    getOverLineDetailForClass:function(grade,className,batch){
        Common.ajaxFun('/scoreAnalyse/getStuNumberScoreChangeForClass', 'GET', {
            'tnId':tnId,
            'grade':grade,
            'className':className,
            'batch':batch
        }, function (res) {
            var res = {
                "bizData": [
                    {
                        "学生姓名": "冯月芝",
                        "年级排名": "223",
                        "成绩": "595",
                        "班级排名": "33"
                    },
                    {
                        "学生姓名": "辕宁妍",
                        "年级排名": "234",
                        "成绩": "592",
                        "班级排名": "34"
                    },
                    {
                        "学生姓名": "汤伦风",
                        "年级排名": "245",
                        "成绩": "590",
                        "班级排名": "35"
                    },
                    {
                        "学生姓名": "于瑾琰",
                        "年级排名": "246",
                        "成绩": "590",
                        "班级排名": "36"
                    },
                    {
                        "学生姓名": "寿朗泰",
                        "年级排名": "258",
                        "成绩": "587",
                        "班级排名": "37"
                    },
                    {
                        "学生姓名": "索功",
                        "年级排名": "263",
                        "成绩": "585",
                        "班级排名": "38"
                    },
                    {
                        "学生姓名": "秋信",
                        "年级排名": "293",
                        "成绩": "576",
                        "班级排名": "39"
                    },
                    {
                        "学生姓名": "居菲",
                        "年级排名": "301",
                        "成绩": "574",
                        "班级排名": "40"
                    },
                    {
                        "学生姓名": "阚毓芳",
                        "年级排名": "302",
                        "成绩": "574",
                        "班级排名": "41"
                    },
                    {
                        "学生姓名": "暴玉霄",
                        "年级排名": "320",
                        "成绩": "569",
                        "班级排名": "42"
                    },
                    {
                        "学生姓名": "颛天",
                        "年级排名": "321",
                        "成绩": "567",
                        "班级排名": "43"
                    }
                ],
                "rtnCode": "0000000",
                "ts": 1479377602813
            }
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#online-template").html());
                $('#online-tbody').html(myTemplate(res));
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    }
    // 重点关注学生
    // 进步较大学生
    // 选择进步名次
    // 选择年级排名

};

var ClassResultsAnalysisIns = new ClassResultsAnalysis();

$(function () {



});


Array.max = function (array) {
    return Math.max.apply(Math, array);
};

Array.min = function (array) {
    return Math.min.apply(Math, array);
};

