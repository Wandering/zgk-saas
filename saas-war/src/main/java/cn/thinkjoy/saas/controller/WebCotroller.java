package cn.thinkjoy.saas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class WebCotroller {
    /**
     * index
     * 首页
     */
    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("/index");
    }
    /**
     * login
     * 登录
     */
    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/login/login");
    }
    /**
     * forgot-password
     * 重设面貌
     */
    @RequestMapping("/forgot-password")
    public ModelAndView forgotPassword() {
        return new ModelAndView("/login/forgot-password");
    }


    /**
     * seting-process1
     * 基础信息设置1
     */
    @RequestMapping("/seting-process1")
    public ModelAndView setingProcess1() {
        return new ModelAndView("/basis-settings/seting-process1");
    }
    /**
     * seting-process2
     * 基础信息设置2
     */
    @RequestMapping("/seting-process2")
    public ModelAndView setingProcess2() {
        return new ModelAndView("/basis-settings/seting-process2");
    }
    /**
     * seting-process3
     * 基础信息设置3
     */
    @RequestMapping("/seting-process3")
    public ModelAndView setingProcess3() {
        return new ModelAndView("/basis-settings/seting-process3");
    }
    /**
     * seting-process4
     * 基础信息设置4
     */
    @RequestMapping("/seting-process4")
    public ModelAndView setingProcess4() {
        return new ModelAndView("/basis-settings/seting-process4");
    }
    /**
     * seting-process5
     * 基础信息设置5
     */
    @RequestMapping("/seting-process5")
    public ModelAndView setingProcess5() {
        return new ModelAndView("/basis-settings/seting-process5");
    }
    /**
     * account-management
     * 账号管理
     */
    @RequestMapping("/account-management")
    public ModelAndView accountManagement() {
        return new ModelAndView("/account/account-management");
    }
    /**
     * role-management
     * 角色管理
     */
    @RequestMapping("/role-management")
    public ModelAndView roleManagement() {
        return new ModelAndView("/account/role-management");
    }
    /**
     * grade-management
     * 年级管理
     */
    @RequestMapping("/grade-management")
    public ModelAndView gradeManagement() {
        return new ModelAndView("/info-management/grade-management");
    }
    /**
     * class-management
     * 班级管理
     */
    @RequestMapping("/class-management")
    public ModelAndView classManagement() {
        return new ModelAndView("/info-management/class-management");
    }
    /**
     * class-settings
     * 班级设置
     */
    @RequestMapping("/class-settings")
    public ModelAndView classSettings() {
        return new ModelAndView("/info-management/class-settings");
    }
    /**
     * classroom-management
     * 教室管理
     */
    @RequestMapping("/classroom-management")
    public ModelAndView classroomManagement() {
        return new ModelAndView("/info-management/classroom-management");
    }
    /**
     * teacher-management
     * 教师管理
     */
    @RequestMapping("/teacher-management")
    public ModelAndView teacherManagement() {
        return new ModelAndView("/info-management/teacher-management");
    }
    /**
     * teacher-settings
     * 教师设置
     */
    @RequestMapping("/teacher-settings")
    public ModelAndView teacherSettings() {
        return new ModelAndView("/info-management/teacher-settings");
    }
    /**
     * graduation-rates-management
     * 升学率管理
     */
    @RequestMapping("/graduation-rates-management")
    public ModelAndView graduationRatesManagement() {
        return new ModelAndView("/info-management/graduation-rates-management");
    }
    /**
     * student-management
     * 学生管理
     */
    @RequestMapping("/student-management")
    public ModelAndView studentManagement() {
        return new ModelAndView("/info-management/student-management");
    }

    /**
     * student-management
     * 上传文件
     */
    @RequestMapping("/uploadFile")
    public ModelAndView uploadFile() {
        return new ModelAndView("/upload/uploadFile");
    }

    /**
     * row-course
     * 上传文件
     */
    @RequestMapping("/row-course")
    public ModelAndView rowCourse() {
        return new ModelAndView("/row-course/row-course");
    }

    /**
     * results-management
     * 成绩管理
     */
    @RequestMapping("/results-management")
    public ModelAndView resultsManagement() {
        return new ModelAndView("/results-analysis/results-management");
    }

    /**
     * school-results-analysis
     * 学校成绩分析
     */
    @RequestMapping("/school-results-analysis")
    public ModelAndView schoolResultsAnalysis() {
        return new ModelAndView("/results-analysis/school-results-analysis");
    }

    /**
     * class-results-analysis
     * 班级成绩分析
     */
    @RequestMapping("/class-results-analysis")
    public ModelAndView classResultsAnalysis() {
        return new ModelAndView("/results-analysis/class-results-analysis");
    }

    /**
     * results-details
     * 成绩明细
     */
    @RequestMapping("/results-details")
    public ModelAndView resultsDetails() {
        return new ModelAndView("/results-analysis/results-details");
    }





    /**
     * course-guide
     * 选课指导
     */
    @RequestMapping("/course-guide")
    public ModelAndView courseGuide() {
        return new ModelAndView("/school-reform/course-guide");
    }
    /**
     * trinity
     * 三位一体
     */
    @RequestMapping("/trinity")
    public ModelAndView trinity() {
        return new ModelAndView("/school-reform/trinity");
    }

    /**
     * course-analysis
     * 课程分析
     */
    @RequestMapping("/course-analysis")
    public ModelAndView courseAnalysis() {
        return new ModelAndView("/school-reform/course-analysis");
    }

    /**
     * policy-interpret
     * 政策解读 浙江
     */
    @RequestMapping("/policy-interpret")
    public ModelAndView policyInterpret() {
        return new ModelAndView("/policy/policy-interpret");
    }
    /**
     * jx-policy
     * 政策解读 江西
     */
    @RequestMapping("/jx-policy")
    public ModelAndView jxPolicy() {
        return new ModelAndView("/policy/jx-policy");
    }
    /**
     * school-admission
     * 院校录取数据
     */
    @RequestMapping("/school-admission")
    public ModelAndView schoolAdmission() {
        return new ModelAndView("/data-query/school-admission");
    }
    /**
     * school-admission
     * 院院校招生计划
     */
    @RequestMapping("/school-recruit")
    public ModelAndView schoolRecruit() {
        return new ModelAndView("/data-query/school-recruit");
    }
    /**
     * specialty-info
     * 专业信息
     */
    @RequestMapping("/specialty-info")
    public ModelAndView specialtyInfo() {
        return new ModelAndView("/data-query/specialty-info");
    }
    /**
     * professional-info
     * 职业信息
     */
    @RequestMapping("/professional-info")
    public ModelAndView professionalInfo() {
        return new ModelAndView("/data-query/professional-info");
    }
    /**
     * professional-assessment
     * 专业测评
     */
    @RequestMapping("/professional-assessment")
    public ModelAndView professionalAssessment() {
        return new ModelAndView("/professional-assessment/professional-assessment");
    }
    /**
     * course-scheduling
     * 排课任务
     */
    @RequestMapping("/course-scheduling")
    public ModelAndView courseScheduling() {
        return new ModelAndView("/course-scheduling/course-scheduling");
    }
    /**
     * course-scheduling-step1
     * 排课任务--基本信息设置
     */
    @RequestMapping("/course-scheduling-step1")
    public ModelAndView courseSchedulingBase() {
        return new ModelAndView("/course-scheduling/course-scheduling-step1");
    }
    /**
     * teaching-time
     * 排课任务--基本信息设置--课程信息
     */
    @RequestMapping("/course-info")
    public ModelAndView courseInfo() {
        return new ModelAndView("/course-scheduling/course-info");
    }
    /**
     * teaching-time
     * 排课任务--基本信息设置--教师信息
     */
    @RequestMapping("/teacher-info")
    public ModelAndView teachingInfo() {
        return new ModelAndView("/course-scheduling/teacher-info");
    }
    /**
     * course-scheduling-step2
     * 排课任务--排课第二步骤，排课规则
     */
    @RequestMapping("/course-scheduling-step2")
    public ModelAndView courseSchedulingStep2() {
        return new ModelAndView("/course-scheduling/course-scheduling-step2");
    }
    /**
     * course-no-proceed
     * 排课任务--排课第二步骤，不连堂
     */
    @RequestMapping("/course-no-proceed")
    public ModelAndView courseNoProceed() {
        return new ModelAndView("/course-scheduling/course-no-proceed");
    }
    /**
     * class-mixed
     * 排课任务--排课第二步骤，合班
     */
    @RequestMapping("/class-mixed")
    public ModelAndView classMixed() {
        return new ModelAndView("/course-scheduling/class-mixed");
    }
    /**
     * class-mixed
     * 排课任务--排课第二步骤，基本规则设置
     */
    @RequestMapping("/base-rule-settings")
    public ModelAndView baseRuleSettings() {
        return new ModelAndView("/course-scheduling/base-rule-settings");
    }
    /**
     * course-scheduling-step3
     * 排课任务--排课第三步骤，自动排课
     */
    @RequestMapping("/course-scheduling-step3")
    public ModelAndView courseSchedulingStep3() {
        return new ModelAndView("/course-scheduling/course-scheduling-step3");
    }


}
