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
     * 政策解读
     */
    @RequestMapping("/policy-interpret")
    public ModelAndView policyInterpret() {
        return new ModelAndView("/school-reform/policy-interpret");
    }


}
