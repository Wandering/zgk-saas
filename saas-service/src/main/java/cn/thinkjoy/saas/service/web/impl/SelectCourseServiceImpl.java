package cn.thinkjoy.saas.service.web.impl;

import cn.thinkjoy.gk.pojo.Page;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.*;
import cn.thinkjoy.saas.dao.bussiness.EXITenantConfigInstanceDAO;
import cn.thinkjoy.saas.dao.bussiness.ICourseBaseInfoDAO;
import cn.thinkjoy.saas.dao.bussiness.IEXCourseManageDAO;
import cn.thinkjoy.saas.dao.bussiness.IEXTeantCustomDAO;
import cn.thinkjoy.saas.dao.web.ISelectCourseDAO;
import cn.thinkjoy.saas.domain.*;
import cn.thinkjoy.saas.domain.bussiness.CourseBaseInfo;
import cn.thinkjoy.saas.domain.bussiness.TeantCustom;
import cn.thinkjoy.saas.dto.*;
import cn.thinkjoy.saas.service.IJwScheduleTaskService;
import cn.thinkjoy.saas.service.bussiness.EXITenantConfigInstanceService;
import cn.thinkjoy.saas.service.bussiness.IEXTenantCustomService;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import cn.thinkjoy.saas.service.web.ISelectCourseService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
    private ISelectCourseTaskDAO iSelectCourseTaskDAO;

    @Autowired
    private ISelectCourseSettingDAO iSelectCourseSettingDAO;

    @Autowired
    private ISelectCourseStuDetailDAO iSelectCourseStuDetailDAO;

    @Autowired
    private IGradeDAO iGradeDAO;

    @Autowired
    private IEXCourseManageDAO iexCourseManageDAO;

    @Autowired
    private IEXTenantCustomService iexTenantCustomService;

    @Autowired
    private ICourseBaseInfoDAO iCourseBaseInfoDAO;


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

        Map<String,Object> map1=new HashMap<>();
        String gradeName=studentMap.get("student_grade");
        int gradeCode = getGradeCode(tnId, gradeName);
        map1.put("tnId",tnId);
        map1.put("grade",gradeCode);
        List<SelectCourseTask> selectCourseTaskList=iSelectCourseDAO.getSelectCourseInfo(map1);
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
                selectCourseSettingList=getSelectCourseList(taskId);
                flog=1;
            }
            //【4】选课中
            else if (startDate.getTime()<nowTime&&nowTime<endDate.getTime()){
                result=hasSelectCourse(taskId,studentNo);
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
                result=hasSelectCourse(taskId,studentNo);
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
        SelectCourseTask task=iSelectCourseTaskDAO.queryOne(map1, null, null);
        if (task==null) {
            //对应租户的对应年级没有任务
            return "-1";
        }
        return task.getId().toString();
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
     * @param taskId
     * @param stuNo
     * @return
     */
    private Object hasSelectCourse(String taskId,String stuNo){
        Map<String, Object> map1 = new HashMap<>();
        map1.put("taskId", taskId);
        map1.put("stuNo", stuNo);
        map1.put("status", 0);
        List<SelectCourseStuDetail> selectCourseStuDetailList = iSelectCourseStuDetailDAO.queryList(map1, null, null);
        if (selectCourseStuDetailList.size()<1){
            return null;
        }
        return selectCourseStuDetailList;

    }

    /**
     * 判断是否有校本课
     * @param taskId
     * @return
     */
    private boolean isSchoolCourse(String taskId,int type){
        Map<String,Object> map1=new HashMap<>();
        map1.put("status",0);
        map1.put("taskId",taskId);
        map1.put("type",type);
        List<SelectCourseSetting> selectCourseSettingList=iSelectCourseSettingDAO.queryList(map1, null, null);
        if (selectCourseSettingList.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public Map<String,Object> getSaasStudentInfo(String schoolId,String studentNo){
        Map<String,Object> resultMap=new HashMap<>();
        Tenant tenant=iTenantDAO.findOne("gk_school_id", schoolId, null, null);
        if (tenant==null){//学校没有使用saas
            resultMap.put("msg","学校没有使用saas，无法获取学生信息");
            return resultMap;
        }
        String tnId=tenant.getId().toString();
        String tableName="saas_"+tnId+"_student_excel";
        Map<String,Object> map=new HashMap<>();
        map.put("tableName",tableName);
        map.put("studentNo",studentNo);
        Map<String,String> studentMap=iSelectCourseDAO.getStudentInfo(map);
        resultMap.put("studentNo",studentNo);
        resultMap.put("studentName",studentMap.get("student_name"));
        resultMap.put("grade",studentMap.get("student_grade"));
        resultMap.put("class",studentMap.get("student_class"));
        return resultMap;
    }

    @Override
    public Map<String,Object> addSelectCourse(String schoolId,String studentNo,String majors,String schoolCourse){
        Map<String,Object> resultMap=new HashMap<>();
        Tenant tenant=iTenantDAO.findOne("gk_school_id", schoolId, null, null);
        String tnId=tenant.getId().toString();
        Map<String,Object> studentMap=getSaasStudentInfo(schoolId,studentNo);
        int gradeCode=getGradeCode(tnId,studentMap.get("grade").toString());
        //【1】保存高考课程
        String[] majorListId=majors.split("-");
        Map<String,Object> map=new HashMap<>();
        map.put("createDate",System.currentTimeMillis());
        map.put("modifyDate",System.currentTimeMillis());
        map.put("status",0);
        map.put("stuNo",studentNo);
        map.put("majorList",majorListId);
        map.put("taskId",getTaskId(tnId,gradeCode));
        map.put("type",0);
        iSelectCourseDAO.insertList(map);
        //【2】保存校本课程
        if (StringUtils.isNotBlank(schoolCourse)) {
            String[] schoolCourseList = schoolCourse.split("-");
            map.put("majorList",schoolCourseList);
            map.put("type",1);
            iSelectCourseDAO.insertList(map);
        }
        resultMap.put("result",true);
        return resultMap;
    }

    @Override
    public List<SelectCourseSetting> getSelectCourses(int taskId) {

        List<SelectCourseSetting> settings = Lists.newArrayList();

        SelectCourseTask task = iSelectCourseTaskDAO.findOne(
                "id",
                taskId,
                null,
                null
        );

        // TODO 需要优化实现思路
        // 课程类型  0：高考课程（对应课程表 1：默认课程）  1：校本课程 （对应课程表 2：附加课程）

        // 组装高考课程
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("taskId",taskId);
        paramMap.put("type",0);
        SelectCourseSetting gkSetting = iSelectCourseSettingDAO.queryOne(
                paramMap,
                null,
                null,
                null
        );
        if(gkSetting == null){
            // 没有设置过，根据课程信息重新组装
            List<SelectCourseBaseDto> gkCourses = iexCourseManageDAO.getSelectCourses(
                    task.getTnId(),
                    task.getGrade(),
                    1
            );
            List<SelectCourseBaseDto> tmpGkCourses = Lists.newArrayList();
            for(SelectCourseBaseDto gkCourse : gkCourses){
                if(!Constant.COURSEES.contains(gkCourse.getName())){
                    continue;
                }
                gkCourse.setIsSelect(false);
                tmpGkCourses.add(gkCourse);
            }

            gkSetting = new SelectCourseSetting();
            gkSetting.setCourses(JSON.toJSONString(tmpGkCourses));
            gkSetting.setType(0);
            gkSetting.setSelectCount(3);
            gkSetting.setTaskId(taskId);
        }
        settings.add(gkSetting);

        // 组装校本课程
        paramMap.put("type",1);
        SelectCourseSetting xbSetting = iSelectCourseSettingDAO.queryOne(
                paramMap,
                null,
                null,
                null
        );

        List<SelectCourseBaseDto> xbCourses = iexCourseManageDAO.getSelectCourses(
                task.getTnId(),
                task.getGrade(),
                2
        );
        if(xbSetting == null){
            // 没有设置过，根据课程信息重新组装
            for(SelectCourseBaseDto xbCourse : xbCourses){
                xbCourse.setIsSelect(false);
            }

            xbSetting = new SelectCourseSetting();
            xbSetting.setCourses(JSON.toJSONString(xbCourses));
            xbSetting.setType(1);
            xbSetting.setSelectCount(0);
            xbSetting.setTaskId(taskId);
        }else {
            // 设置过，填充选择过的校本课程
            for(SelectCourseBaseDto xbCourse : xbCourses){
                if(xbSetting.getCourses().indexOf(xbCourse.getName()) == -1){
                    xbCourse.setIsSelect(false);
                }
            }
        }
        settings.add(xbSetting);

        return settings;
    }

    @Override
    public SelectCourseSurveyDto SelectCourseSurvey(int taskId) {

        SelectCourseSurveyDto dto = new SelectCourseSurveyDto();

        SelectCourseTask task = iSelectCourseTaskDAO.findOne(
                "id",
                taskId,
                null,
                null
        );

        dto.setName(task.getName());
        dto.setGrade(task.getGrade());
        dto.setStartTime(task.getStartTime());
        dto.setEndTime(task.getEndTime());

        // 查询当前任务年级总学生信息
        List<LinkedHashMap<String, Object>> tenantCustom = iexTenantCustomService.getStuInfo(
                -1,
                task.getTnId(),
                Constant.GRADES[task.getGrade()-1],
                null,
                null
        );

        // 已选课总学生数
        Map<String,List<SelectCourseStuDetail>> selectedStuMap = getSelectedStuMap(taskId,0);
        dto.setSelectedCount(selectedStuMap.size());
        dto.setUnSelectedCount(tenantCustom.size()-selectedStuMap.size());

        // 组装未选课学生集合
        List<BaseStuDto> stuDtos = Lists.newArrayList();
        for(Map map : tenantCustom){
            if(!selectedStuMap.containsKey(map.get("student_no"))){
                BaseStuDto stuDto = new BaseStuDto();
                stuDto.setClassName(map.get("student_class").toString());
                stuDto.setStuName(map.get("student_name").toString());
                stuDto.setStuNo(map.get("student_no").toString());
                stuDtos.add(stuDto);
            }
        }
        dto.setUnSelectedList(stuDtos);

        return dto;
    }

    /**
     * 查询学生选课信息（按学号分类）
     *
     * @param taskId
     * @param type 课程类型  0：高考科目  1：校本课程
     * @return
     */
    private Map<String,List<SelectCourseStuDetail>> getSelectedStuMap(Integer taskId,Integer type){

        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("taskId",taskId);
        paramMap.put("type",type);
        List<SelectCourseStuDetail> allSelectedStus = iSelectCourseStuDetailDAO.queryList(
                paramMap,
                Constant.ID,
                Constant.DESC
        );

        Map<String,List<SelectCourseStuDetail>> selectedStuMap = Maps.newHashMap();
        for(SelectCourseStuDetail selectedStu : allSelectedStus){
            List<SelectCourseStuDetail> singleSelectedStus = selectedStuMap.get(selectedStu.getStuNo());
            if(singleSelectedStus == null){
                singleSelectedStus = Lists.newArrayList();
            }
            singleSelectedStus.add(selectedStu);

            selectedStuMap.put(selectedStu.getStuNo(),singleSelectedStus);
        }

        return selectedStuMap;
    }

    @Override
    public List<CourseBaseDto> getSingleCourseSituation(int taskId) {
        return iSelectCourseDAO.getSingleCourseSituation(taskId);
    }

    @Override
    public List<CourseBaseDto> getGroupCourseSituation(int taskId) {
        // TODO
        return null;
    }

    @Override
    public Page<BaseStuDto> getStuCourseDetail(int taskId, int type, int pageNo, int pageSize) {
        Page<BaseStuDto> page = new Page<>();

        // 根据条件查询选课学生学号集合
        List<String> stuNos = iSelectCourseDAO.getStuNoListByCondition(
                taskId,
                type,
                pageNo*pageSize,
                pageSize
        );

        if(stuNos.size() == 0){
            return page;
        }

        // 根据学生学号集合查询学生选课详情
        List<SelectCourseStuDetail> details = iSelectCourseDAO.getSelectDetailByStuNos(
                taskId,
                type,
                stuNos
        );

        // 组装学生课程信息
        Map<String,List<CourseBaseDto>> selectedStuMap = Maps.newHashMap();
        for(SelectCourseStuDetail detail : details){
            List<CourseBaseDto> dtos = selectedStuMap.get(detail.getStuNo());
            if(dtos == null){
                dtos = Lists.newArrayList();
            }
            CourseBaseDto dto = new CourseBaseDto();
            dto.setCourseId(detail.getCourseId());
            dto.setCourseName(detail.getCourseName());
            dtos.add(dto);

            selectedStuMap.put(detail.getStuNo(),dtos);
        }

        SelectCourseTask task = iSelectCourseTaskDAO.findOne(
                "id",
                taskId,
                null,
                null
        );

        // 查询当前任务年级总学生信息
        List<LinkedHashMap<String, Object>> tenantCustom = iexTenantCustomService.getStuInfo(
                -1,
                task.getTnId(),
                Constant.GRADES[task.getGrade()-1],
                null,
                null
        );


        // 组装学生基本信息
        List<BaseStuDto> stuDtos = Lists.newArrayList();
        for(Map map : tenantCustom){
            List<CourseBaseDto> baseDtos = selectedStuMap.get(map.get("student_no").toString());
            if(baseDtos != null){
                BaseStuDto stuDto = new BaseStuDto();
                stuDto.setClassName(map.get("student_class").toString());
                stuDto.setStuName(map.get("student_name").toString());
                stuDto.setStuNo(map.get("student_no").toString());
                stuDto.setCourses(baseDtos);
                stuDtos.add(stuDto);
            }
        }
        page.setList(stuDtos);

        Integer count = iSelectCourseDAO.getStuNoCountByCondition(taskId,type);
        page.setCount(count);
        return page;
    }

    @Override
    public void updateStuCourse(String stuNo, int taskId, int type, List<String> courseIds) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("stuNo",stuNo);
        map.put("taskId",taskId);
        map.put("type",type);
        iSelectCourseStuDetailDAO.deleteByCondition(map);

        for(String courseId :courseIds){
            SelectCourseStuDetail detail = new SelectCourseStuDetail();
            detail.setTaskId(taskId);
            CourseBaseInfo info = iCourseBaseInfoDAO.findOne("id",courseId,null,null);
            detail.setCourseName(info.getCourseName());
            detail.setCreateDate(System.currentTimeMillis());
            detail.setModifyDate(System.currentTimeMillis());
            detail.setCourseId(Integer.valueOf(courseId));
            detail.setStuNo(stuNo);
            detail.setStatus(0);
            detail.setType(type);
            iSelectCourseStuDetailDAO.insert(detail);
        }
    }

    @Override
    public void confirmSelectCourse(int taskId) {
        String tableName = ParamsUtils.combinationTableName(Constant.STUDENT, getTnIdByTaskId(taskId));
        Map<String,List<SelectCourseStuDetail>> stuMap = getSelectedStuMap(taskId,0);
        for (String stuNo : stuMap.keySet()) {

            List<TeantCustom> teantCustoms = Lists.newArrayList();
            List<SelectCourseStuDetail> details = stuMap.get(stuNo);
            for(int i=0;i<details.size();i++){
                TeantCustom teantCustom = new TeantCustom();
                teantCustom.setKey("student_check_major"+(i+1));
                teantCustom.setValue(details.get(i).getCourseName());
                teantCustoms.add(teantCustom);
            }

            iSelectCourseDAO.updateStuCourseByStuNo(tableName, stuNo, teantCustoms);
        }

        Map<String,Object> updateMap = Maps.newHashMap();
        updateMap.put("state",1);
        updateMap.put("modifyDate",System.currentTimeMillis());
        Map<String,Object> conditionMap = Maps.newHashMap();
        conditionMap.put("id",taskId);
        iSelectCourseTaskDAO.updateByCondition(updateMap,conditionMap);
    }

    /**
     * 根据任务ID查询租户ID
     *
     * @param taskId
     * @return
     */
    private Integer getTnIdByTaskId(int taskId){
        SelectCourseTask task = iSelectCourseTaskDAO.findOne(
                "id",
                taskId,
                null,
                null
        );
        return Integer.valueOf(task.getId().toString());
    }

}
