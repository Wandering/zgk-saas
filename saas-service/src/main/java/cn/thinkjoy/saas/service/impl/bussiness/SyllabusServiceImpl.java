package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.IJwCourseTableDAO;
import cn.thinkjoy.saas.dao.IJwScheduleTaskDAO;
import cn.thinkjoy.saas.dao.bussiness.ISyllabusDAO;
import cn.thinkjoy.saas.domain.JwScheduleTask;
import cn.thinkjoy.saas.domain.bussiness.CourseResultView;
import cn.thinkjoy.saas.dto.JwCourseTableDTO;
import cn.thinkjoy.saas.service.bussiness.IEXJwScheduleTaskService;
import cn.thinkjoy.saas.service.bussiness.ISyllabusService;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.sun.tools.jdi.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 课程表增删改查业务
 * Created by yangyongping on 2017/2/27.
 */
@Service("SyllabusServiceImpl")
public class SyllabusServiceImpl implements ISyllabusService {
    @Autowired
    private ISyllabusDAO syllabusDAO;
    @Autowired
    private IJwScheduleTaskDAO jwScheduleTaskDAO;
    @Autowired
    private IEXJwScheduleTaskService iexJwScheduleTaskService;

    @Override
    public Map<String,Object> getAllSyllabus(int tnId,int taskId){
        /**变量声明**/
        Map<String, Object> rtnMap;
        List<JwCourseTableDTO> jwCourseTableDTOs;
        Map<String, Object> timeConfigMap;

        Map<String, Object> params = new HashMap<>();
        /**变量声明**/

        jwCourseTableDTOs = this.queryList(tnId,taskId,params);

        if (jwCourseTableDTOs.size() == 0)
            throw new BizException("error", "当前租户下排课任务" + taskId + "课表为空,请稍后再试");
        timeConfigMap = iexJwScheduleTaskService.getCourseTimeConfig(tnId, taskId);
        rtnMap = treeListByTree(jwCourseTableDTOs);

        rtnMap.put("teachDate", timeConfigMap.get("teachDate").toString());
        rtnMap.put("teachTime", timeConfigMap.get("time").toString());
        return rtnMap;
    }

    /**
     * 获取某个老师课程表
     *
     * @param tnId
     * @param taskId
     * @param teacherId
     * @return
     */
    @Override
    public CourseResultView getTeacherSyllabus(int tnId, int taskId, int teacherId) {

        List<JwCourseTableDTO> jwCourseTableDTOs;
        Map<String, Object> timeConfigMap;

        Map<String, Object> params = new HashMap<>();
        params.put("teacherId",teacherId);
        jwCourseTableDTOs = this.queryList(tnId,taskId,params);
        if (jwCourseTableDTOs.size() == 0)
            throw new BizException("error", "当前租户下排课任务" + taskId + "==老师ID"+teacherId+"课表为空,请稍后再试");
        timeConfigMap = iexJwScheduleTaskService.getCourseTimeConfig(tnId, taskId);

        return null;
    }

    /**
     * 获取某个班级课程表
     *
     * @param tnId
     * @param taskId
     * @param classId
     * @return
     */
    @Override
    public CourseResultView getClassSyllabus(int tnId, int taskId, int classId) {

        List<JwCourseTableDTO> jwCourseTableDTOs;
        Map<String, Object> timeConfigMap;

        Map<String, Object> params = new HashMap<>();
        params.put("classId",classId);
        jwCourseTableDTOs = this.queryList(tnId,taskId,params);
        if (jwCourseTableDTOs.size() == 0)
            throw new BizException("error", "当前租户下排课任务" + taskId + "==班级ID"+classId+"课表为空,请稍后再试");
        timeConfigMap = iexJwScheduleTaskService.getCourseTimeConfig(tnId, taskId);

        return null;
    }


    /**
     * 抽取租户任务信息
     * @param tnId
     * @param taskId
     * @return
     */
    private JwScheduleTask getTaskByTaskIdAndTnId(int tnId,int taskId){
        Map<String,Object> map = new HashMap<>();
        map.put("tnId",tnId);
        map.put("taskId",taskId);
        JwScheduleTask jwScheduleTask = jwScheduleTaskDAO.queryOne(map,"id","desc");
        return jwScheduleTask;
    }



    private List<JwCourseTableDTO> queryList(int tnId,int taskId,Map<String,Object> params){
        String teacherTableName;
        String classTableName;
        List<JwCourseTableDTO> jwCourseTableDTOs;

        teacherTableName = ParamsUtils.combinationTableName(Constant.TABLE_TYPE_TEACHER, tnId);
        classTableName = ParamsUtils.combinationTableName(Constant.CLASS_ADM, tnId);
        jwCourseTableDTOs = syllabusDAO.queryList(params, teacherTableName, classTableName);
        return jwCourseTableDTOs;
    }


    private Map<String,Object> treeListByTree(List<JwCourseTableDTO> jwCourseTableDTOs){
        /**变量声明**/
        Map<String,Object> rtnMap = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        Map<Integer,Map<String,Object>> treeMap = allCourseTreeByList(jwCourseTableDTOs);
        List<List<List<String>>> listArrayList = new ArrayList<>();

        List<List<String>> lists;
        List<String> list;
        Map.Entry<Integer,Map<String,Object>> classEntry;
        Map.Entry<Integer,Object> weekEntry;
        Map.Entry<Integer,JwCourseTableDTO> dayEntry;
        JwCourseTableDTO jwCourseTableDTO;
        Iterator<Map.Entry<Integer,Object>> weekIterator;
        Iterator<Map.Entry<Integer,JwCourseTableDTO>> dayIterator;
        Map<String,Object> classMap;
        Map<Integer,Object> weekMap;
        Map<Integer,JwCourseTableDTO> dayMap;
        Integer builderLength;
        String className;
        /**变量声明**/

        Iterator<Map.Entry<Integer,Map<String,Object>>> entryIterator = treeMap.entrySet().iterator();
        while (entryIterator.hasNext()){
            lists = new ArrayList<>();
            //班级课表层
            classEntry = entryIterator.next();
            classMap = classEntry.getValue();
            weekMap = (Map<Integer,Object>)classMap.get("week");
            className = (String) classMap.get("className");
            weekIterator = weekMap.entrySet().iterator();
            builder.append(className).append("|");
            while (weekIterator.hasNext()){
                list = new ArrayList<>();

                weekEntry = weekIterator.next();
                dayMap = (Map<Integer,JwCourseTableDTO>) weekEntry.getValue();
                dayIterator = dayMap.entrySet().iterator();
                while (dayIterator.hasNext()){
                    dayEntry = dayIterator.next();
                    jwCourseTableDTO = dayEntry.getValue();
                    list.add(genStringByDTO(Constant.COURSE_TABLE_ALL,jwCourseTableDTO));
                }
                lists.add(list);
            }
            builderLength = builder.length();
            if (builderLength > 0){
                builder.delete(builderLength-1,builderLength);
            }
            listArrayList.add(lists);
        }
        rtnMap.put("roomData",listArrayList);
        rtnMap.put("room",builder.toString());
       return rtnMap;
    }


    /**
     * rtnMap -classId
     *          -className
     *          -week(Map)
     *              -day(Map)
     *                  -dayData1
     *                  -dayData2
     *                  -..
     * @param jwCourseTableDTOs
     * @return
     */
    private Map<Integer,Map<String,Object>> allCourseTreeByList(List<JwCourseTableDTO> jwCourseTableDTOs){

        Map<Integer,Map<String,Object>> rtnMap = new LinkedHashMap();
        JwCourseTableDTO jwCourseTableDTO;
        Iterator<JwCourseTableDTO> jwCourseTableIterator = jwCourseTableDTOs.iterator();
        while (jwCourseTableIterator.hasNext()){
            jwCourseTableDTO = jwCourseTableIterator.next();
            Integer classId = jwCourseTableDTO.getClassId();
            Integer weekId = jwCourseTableDTO.getWeek();
            Integer dayId = jwCourseTableDTO.getSort();
            Map<String,Object> classMap;
            Map<Integer,Object> weekMap;
            Map<Integer,JwCourseTableDTO> dayMap;
            //以班级分类
            if (rtnMap.containsKey(classId)){
                classMap  = rtnMap.get(classId);
                weekMap = (Map<Integer,Object>)classMap.get("week");
            }else {
                classMap  = new LinkedHashMap();
                classMap.put("className",jwCourseTableDTO.getClassName());
                weekMap = new LinkedHashMap();
                classMap.put("week",weekMap);
            }
            if (weekMap.containsKey(weekId)) {
                dayMap = (Map<Integer,JwCourseTableDTO>)weekMap.get(weekId);
            }else {
                dayMap = new LinkedHashMap();
            }
            dayMap.put(dayId,jwCourseTableDTO);

        }

        return rtnMap;
    }

    private static String genStringByDTO(String type,JwCourseTableDTO jwCourseTableDTO){
        StringBuilder rtnStrBf = new StringBuilder();
        switch (type){
            case Constant.TABLE_TYPE_TEACHER:
                rtnStrBf.append(jwCourseTableDTO.getCourseName())
                        .append(Constant.GEN_COURSE_TABLE_CLASS_SPLIT)
                        .append(jwCourseTableDTO.getClassName())
                        .append(Constant.GEN_COURSE_TABLE_BASE_SPLIT)
                        .append(Constant.GEN_COURSE_TABLE_WRAP_SPLIT)
                        .append(Constant.GEN_COURSE_TABLE_TEACHER_AROUND_S)
                        .append(jwCourseTableDTO.getClassName())
                        .append(Constant.GEN_COURSE_TABLE_TEACHER_AROUND_E);
                break;
            case Constant.COURSE_TABLE_ALL:
                rtnStrBf.append(jwCourseTableDTO.getCourseName())
                        .append(Constant.GEN_COURSE_TABLE_BASE_SPLIT)
                        .append(Constant.GEN_COURSE_TABLE_WRAP_SPLIT)
                        .append(Constant.GEN_COURSE_TABLE_TEACHER_AROUND_S)
                        .append(jwCourseTableDTO.getClassName())
                        .append(Constant.GEN_COURSE_TABLE_TEACHER_AROUND_E);
                break;
            case Constant.TABLE_TYPE_CLASS:
                rtnStrBf.append(jwCourseTableDTO.getCourseName())
                        .append(Constant.GEN_COURSE_TABLE_BASE_SPLIT)
                        .append(Constant.GEN_COURSE_TABLE_WRAP_SPLIT)
                        .append(Constant.GEN_COURSE_TABLE_TEACHER_AROUND_S)
                        .append(jwCourseTableDTO.getClassName())
                        .append(Constant.GEN_COURSE_TABLE_TEACHER_AROUND_E);
                break;
            default:
                break;
        }
        return rtnStrBf.toString();
    }
}
