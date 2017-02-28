package cn.thinkjoy.saas.service.web.impl;

import cn.thinkjoy.saas.dao.*;
import cn.thinkjoy.saas.dao.bussiness.EXITenantConfigInstanceDAO;
import cn.thinkjoy.saas.dao.web.ISelectCourseDAO;
import cn.thinkjoy.saas.domain.*;
import cn.thinkjoy.saas.dto.StudentSelectCourseDto;
import cn.thinkjoy.saas.service.IJwScheduleTaskService;
import cn.thinkjoy.saas.service.web.ISelectCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 17/2/22.
 */
@Service("SelectCourseServiceImpl")
public class SelectCourseServiceImpl implements ISelectCourseService{

    @Autowired
    private ISelectCourseDAO iSelectCourseDAO;

    @Autowired
    private EXITenantConfigInstanceDAO exiTenantConfigInstanceDAO;

    @Autowired
    private ITenantDAO iTenantDAO;

    @Autowired
    private IJwScheduleTaskDAO iJwScheduleTaskDAO;

    @Autowired
    private ISelectCourseSettingDAO iSelectCourseSettingDAO;

    @Autowired
    private ISelectCourseStuDetailDAO iSelectCourseStuDetailDAO;

    @Autowired
    private IGradeDAO iGradeDAO;


    @Override
    public Map bindingSchool(String schoolId, String studentNo, String studentName,String userId) {
        Map<String,Object> resultMap=new HashMap<>();
        //【1】根据学校code查询tnId
        Tenant tenant=iTenantDAO.findOne("gk_school_id", schoolId, null, null);
        if (tenant==null){//学校没有使用saas
            resultMap.put("msg","学校没有使用saas");
            return resultMap;
        }
        String tnId=tenant.getId().toString();
        //【2】查询学生信息表是否存在
        String tableName="saas_"+tnId+"_student_excel";
        if (exiTenantConfigInstanceDAO.existTable(tableName)!=1){//对应学校没有上传学生信息
            resultMap.put("msg","对应学校没有上传学生信息");
            return resultMap;
        }
        //【3】查询对应学校是否有该同学
        Map<String,Object> map=new HashMap<>();
        map.put("tableName",tableName);
        map.put("studentNo",studentNo);
        map.put("studentName",studentName);
        map.put("isBinding",1);
        if (iSelectCourseDAO.hasStudent(map)!=1){//没有查找到改学生信息，绑定信息有误，绑定失败
            resultMap.put("msg","没有查找到改学生信息，绑定信息有误，绑定失败");
            return resultMap;
        }else {
            //【4】进行绑定操作
            map.put("id",userId);
            iSelectCourseDAO.bindingSchool(map);
            resultMap.put("msg","绑定成功");
        }
        return resultMap;
    }

    @Override
    public Map<String,Object> getSelectCourseInfo(String schoolId,String studentNo){
        Map<String,Object> resultMap=new HashMap<>();
        //【1】判断对应学校的对应年级是否设置选课
        Tenant tenant=iTenantDAO.findOne("gk_school_id", schoolId, null, null);
        if (tenant==null){//学校没有使用saas
            resultMap.put("msg","学校没有使用saas");
            return resultMap;
        }
        String tnId=tenant.getId().toString();
        String tableName="saas_"+tnId+"_student_excel";
        Map<String,Object> map=new HashMap<>();
        map.put("tableName",tableName);
        map.put("studentNo",studentNo);
        Map<String,String> studentMap=iSelectCourseDAO.getStudentInfo(map);
        String gradeName=studentMap.get("student_grade");
        int gradeCode = getGradeCode(tnId, gradeName);
        map.put("tnId",tnId);
        map.put("grade",gradeCode);
        List<SelectCourseTask> selectCourseTaskList=iSelectCourseDAO.getSelectCourseInfo(map);
        if (selectCourseTaskList.isEmpty()||selectCourseTaskList.size()<1){
            resultMap.put("msg","还未设置选课");
            return resultMap;
        }
        if (selectCourseTaskList.size()>1){
            resultMap.put("msg","统一租户同一年级选课出现多个");
            return resultMap;
        }
        SelectCourseTask selectCourseTask=selectCourseTaskList.get(0);
        //【2】获取选课信息
        Date startDate=selectCourseTask.getStartTime();
        Date endDate=selectCourseTask.getEndTime();
        long nowTime=System.currentTimeMillis();
        if (startDate!=null&&endDate!=null){
            String taskId = getTaskId(tnId, gradeCode);
            if (taskId.equals("-1")){
                resultMap.put("msg","对应租户的对应年级没有任务");
                return resultMap;
            }
            int flog=0;//选课状态，1：未开始  2：进行中  3：已结束
            int isSelect=0;//学生是否选课
            Object result=null;
            List<SelectCourseSetting> selectCourseSettingList=null;
            //【3】选课开始前
            if (nowTime<startDate.getTime()){
                getSelectCourseList(taskId);
                flog=1;
            }
            //【4】选课中
            else if (startDate.getTime()<nowTime&&nowTime<endDate.getTime()){
                result=hasSelectCourse(tableName,taskId,studentNo);
                //尚未选课
                if (result==null) {
                    selectCourseSettingList=getSelectCourseList(taskId);
                }
                //已选课
                else {
                    isSelect=1;
                }
                flog=2;
            }
            //【5】选课结束后
            else {
                result=hasSelectCourse(tableName,taskId,studentNo);
                //已选课
                if (result!=null) {
                    isSelect=1;
                }
                flog=3;
            }
            //返回选课时间段及状态
            resultMap.put("startDate",startDate);
            resultMap.put("endDate",endDate);
            resultMap.put("flog",flog);
            resultMap.put("isSelect",isSelect);
            resultMap.put("result",result);
            resultMap.put("selectCourseSettingList",selectCourseSettingList);
        }

        return resultMap;
    }

    private int getGradeCode(String tnId, String gradeName) {
        Map<String, Object> map2 = new HashMap<>();
        map2.put("grade", gradeName);
        map2.put("tnId", tnId);
        Grade grade = iGradeDAO.queryOne(map2,"id","asc");
        return grade.getGradeCode();
    }

    private String getTaskId(String tnId, int grade) {
        Map<String,Object> map1=new HashMap<>();
        map1.put("tnId",tnId);
        map1.put("grade",grade);
        JwScheduleTask jwScheduleTask=iJwScheduleTaskDAO.queryOne(map1, null, null);
        if (jwScheduleTask==null) {
            //对应租户的对应年级没有任务
            return "-1";
        }
        return jwScheduleTask.getId().toString();
    }

    /**
     * 选课前，选课中获取可选课列表
     * @param taskId
     * @return
     */
    private List<SelectCourseSetting> getSelectCourseList(String taskId){
        Map<String,Object> map1=new HashMap<>();
        map1.put("status",0);
        map1.put("taskId",taskId);
        List<SelectCourseSetting> selectCourseSettingList=iSelectCourseSettingDAO.queryList(map1, null, null);
        return selectCourseSettingList;
    }

    /**
     * 已选课
     * @param tableName
     * @param taskId
     * @param stuNo
     * @return
     */
    private Object hasSelectCourse(String tableName,String taskId,String stuNo){
        //高考科目
        Map<String,Object> map=new HashMap<>();
        map.put("tableName",tableName);
        map.put("studentNo",stuNo);
        Map studentMap=iSelectCourseDAO.getStudentInfo(map);
        if (!(studentMap.containsKey("student_check_major1")&&studentMap.get("student_check_major1")!=null&&!studentMap.get("student_check_major1").equals("")
                &&studentMap.containsKey("student_check_major2")&&studentMap.get("student_check_major2")!=null&&!studentMap.get("student_check_major2").equals("")
                &&studentMap.containsKey("student_check_major3")&&studentMap.get("student_check_major3")!=null&&!studentMap.get("student_check_major3").equals(""))){
            return null;
        }
        String selectCourseList=studentMap.get("student_check_major1").toString()+"-"+studentMap.get("student_check_major2")+"-"+studentMap.get("student_check_major3");
        StudentSelectCourseDto studentSelectCourseDto=new StudentSelectCourseDto();
        studentSelectCourseDto.setSelectCourseList(selectCourseList);
        //校本课
        if (isSchoolCourse(taskId)) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("taskId", taskId);
            map1.put("stuNo", stuNo);
            map1.put("status", 0);
            SelectCourseStuDetail selectCourseStuDetail = iSelectCourseStuDetailDAO.queryOne(map1, null, null);
            studentSelectCourseDto.setSchoolCourseId(selectCourseStuDetail.getCourseId().toString());
            studentSelectCourseDto.setSchoolCourse(selectCourseStuDetail.getCourseName());
        }
        return studentSelectCourseDto;

    }

    /**
     * 判断是否有校本课
     * @param taskId
     * @return
     */
    private boolean isSchoolCourse(String taskId){
        Map<String,Object> map1=new HashMap<>();
        map1.put("status",0);
        map1.put("taskId",taskId);
        map1.put("type",1);
        List<SelectCourseSetting> selectCourseSettingList=iSelectCourseSettingDAO.queryList(map1, null, null);
        if (selectCourseSettingList.size()==1){
            return true;
        }
        return false;
    }
}
