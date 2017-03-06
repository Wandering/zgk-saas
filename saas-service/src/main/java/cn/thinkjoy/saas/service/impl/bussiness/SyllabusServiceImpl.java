package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.IJwCourseTableDAO;
import cn.thinkjoy.saas.dao.IJwScheduleTaskDAO;
import cn.thinkjoy.saas.dao.IJwSyllabusDAO;
import cn.thinkjoy.saas.dao.bussiness.ISyllabusDAO;
import cn.thinkjoy.saas.domain.JwCourseTable;
import cn.thinkjoy.saas.domain.JwScheduleTask;
import cn.thinkjoy.saas.domain.JwSyllabus;
import cn.thinkjoy.saas.domain.bussiness.CourseResultView;
import cn.thinkjoy.saas.dto.JwCourseTableDTO;
import cn.thinkjoy.saas.service.bussiness.IEXJwScheduleTaskService;
import cn.thinkjoy.saas.service.bussiness.ISyllabusService;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import static com.google.common.io.Files.newReader;

/**
 * 课程表增删改查业务
 * Created by yangyongping on 2017/2/27.
 */
@Service("SyllabusServiceImpl")
public class SyllabusServiceImpl implements ISyllabusService {
    @Autowired
    private ISyllabusDAO syllabusDAO;
    @Autowired
    private IJwCourseTableDAO jwCourseTableDAO;
    @Autowired
    private IJwScheduleTaskDAO jwScheduleTaskDAO;
    @Autowired
    private IEXJwScheduleTaskService iexJwScheduleTaskService;
    @Autowired
    private IJwSyllabusDAO jwSyllabusDAO;

    /**
     * 获得总课表
     * @param tnId
     * @param taskId
     * @return
     */
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
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId",teacherId);
        return this.genSyllabus(tnId,taskId,Constant.TABLE_TYPE_TEACHER,params);
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
        Map<String, Object> params = new HashMap<>();
        params.put("classId",classId);
        return this.genSyllabus(tnId,taskId,Constant.TABLE_TYPE_CLASS,params);
    }

    /**
     * 交换班级
     * @param tnId
     * @param taskId
     * @param classId
     * @param source
     * @param target
     * @return
     */
    @Override
    public boolean classExchange(int tnId,int taskId,int classId,int[] source, int[] target) {
        List<JwCourseTable> sourceList = this.getSyllabusByCoordinate(tnId,taskId,classId,source,Constant.TABLE_TYPE_CLASS);
        List<JwCourseTable> targetList = this.getSyllabusByCoordinate(tnId,taskId,classId,target,Constant.TABLE_TYPE_CLASS);
        Map<String,Object> timeConfigMap = iexJwScheduleTaskService.getCourseTimeConfig(tnId,taskId);
        int y = (int) timeConfigMap.get("count");
        return  updateExchange(sourceList,targetList) && updateExchange(y,tnId,taskId,sourceList,targetList);
    }


    /**
     * 老师交换课表
     * @param tnId
     * @param taskId
     * @param teacherId
     * @param source
     * @param target
     * @return
     */
    @Override
    public boolean teacherExchange(int tnId,int taskId,int teacherId,int[] source, int[] target) {
        List<JwCourseTable> sourceList = this.getSyllabusByCoordinate(tnId,taskId,teacherId,source,Constant.TABLE_TYPE_TEACHER);
        if (sourceList.size() == 0){
            throw new BizException("error","原坐标内容不能为空!");
        }
//        自己不能和自己调课
//        List<JwCourseTable> targetList = this.getSyllabusByCoordinate(tnId,taskId,teacherId,target,Constant.TABLE_TYPE_TEACHER);
        List<JwCourseTable> targetList = new ArrayList<>();
        for (JwCourseTable jwCourseTable : sourceList){
            targetList.addAll(this.getSyllabusByCoordinate(tnId,taskId,jwCourseTable.getClassId(),target,Constant.TABLE_TYPE_CLASS));
        }
        Map<String,Object> timeConfigMap = iexJwScheduleTaskService.getCourseTimeConfig(tnId,taskId);
        int y = (int) timeConfigMap.get("count");
        return updateExchange(sourceList,targetList) && updateExchange(y,tnId,taskId,sourceList,targetList);
    }

    /**
     * 更新交换
     * @param sourceList
     * @param targetList
     * @return
     */
    private boolean updateExchange(List<JwCourseTable> sourceList,List<JwCourseTable> targetList){
        //缓存二维坐标
        int[] sourceTemp = new int[2];
        sourceTemp[0] = sourceList.get(0).getWeek();
        sourceTemp[1] = sourceList.get(0).getSort();
        int[] targetTemp = new int[2];
        targetTemp[0] = targetList.get(0).getWeek();
        targetTemp[1] = targetList.get(0).getSort();

        for (JwCourseTable  jwCourseTable: sourceList){
            jwCourseTableDAO.deleteById(jwCourseTable.getId());
        }

        for (JwCourseTable  jwCourseTable: targetList){
            jwCourseTableDAO.deleteById(jwCourseTable.getId());
        }

        for (JwCourseTable  jwCourseTable: targetList){
            jwCourseTable.setWeek(sourceTemp[0]);
            jwCourseTable.setSort(sourceTemp[1]);
            jwCourseTableDAO.insert(jwCourseTable);
        }
        for (JwCourseTable  jwCourseTable: sourceList){
            jwCourseTable.setWeek(targetTemp[0]);
            jwCourseTable.setSort(targetTemp[1]);
            jwCourseTableDAO.insert(jwCourseTable);
        }

        return true;
    }

    /**
     * 根据坐标查询课表记录
     * @param tnId
     * @param taskId
     * @param id
     * @param coordinate
     * @param type
     * @return
     */
    @Override
    public List<JwCourseTable> getSyllabusByCoordinate(int tnId,int taskId,int id,int[] coordinate,String type){
        Map<String, Object> params = new HashMap<>();
        switch (type){
            case Constant.TABLE_TYPE_TEACHER:
                params.put("teacherId", id);
                break;
            case Constant.TABLE_TYPE_CLASS:
                params.put("classId", id);
            default:
                break;
        }
        params.put("week",coordinate[0]);
        params.put("sort",coordinate[1]);
        //
        List<JwCourseTable> jwCourseTables = jwCourseTableDAO.queryList(params,"id","desc");
        return jwCourseTables;
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


    /**
     * 获取课表列表
     * @param tnId
     * @param taskId
     * @param params
     * @return
     */
    private List<JwCourseTableDTO> queryList(int tnId,int taskId,Map<String,Object> params){
        String teacherTableName;
        String classTableName;
        List<JwCourseTableDTO> jwCourseTableDTOs;
        params.put("tnId",tnId);
        params.put("taskId",taskId);
        teacherTableName = ParamsUtils.combinationTableName(Constant.TABLE_TYPE_TEACHER, tnId);
        classTableName = ParamsUtils.combinationTableName(Constant.CLASS_ADM, tnId);
        jwCourseTableDTOs = syllabusDAO.queryList(params, teacherTableName, classTableName);
        return jwCourseTableDTOs;
    }


    /**
     * 将树化课表转换为返回格式
     * @param jwCourseTableDTOs
     * @return
     */
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

            listArrayList.add(lists);
        }
        builderLength = builder.length();
        if (builderLength > 0){
            builder.delete(builderLength-1,builderLength);
        }
        rtnMap.put("roomData",listArrayList);
        rtnMap.put("room",builder.toString());
       return rtnMap;
    }


    /**
     * 树化课表
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
            weekMap.put(weekId,dayMap);
            rtnMap.put(classId,classMap);

        }

        return rtnMap;
    }


    /**
     * 统一课表单元格返回格式
     * @param type
     * @param jwCourseTableDTO
     * @return
     */
    private static String genStringByDTO(String type,JwCourseTableDTO jwCourseTableDTO){
        StringBuffer rtnStrBf = new StringBuffer();
        if (jwCourseTableDTO.getStatus() == 0) return "";
        switch (type){
            case Constant.TABLE_TYPE_TEACHER:
                rtnStrBf.append(jwCourseTableDTO.getCourseName())
                        .append(Constant.GEN_COURSE_TABLE_CLASS_SPLIT)
                        .append(jwCourseTableDTO.getClassName())
                        .append(Constant.GEN_COURSE_TABLE_BASE_SPLIT)
                        .append(Constant.GEN_COURSE_TABLE_WRAP_SPLIT)
                        .append(Constant.GEN_COURSE_TABLE_TEACHER_AROUND_S)
                        .append(jwCourseTableDTO.getTeacherName())
                        .append(Constant.GEN_COURSE_TABLE_TEACHER_AROUND_E);
                break;
            case Constant.COURSE_TABLE_ALL:
                rtnStrBf.append(jwCourseTableDTO.getCourseName())
                        .append(Constant.GEN_COURSE_TABLE_BASE_SPLIT)
                        .append(Constant.GEN_COURSE_TABLE_WRAP_SPLIT)
                        .append(Constant.GEN_COURSE_TABLE_TEACHER_AROUND_S)
                        .append(jwCourseTableDTO.getTeacherName())
                        .append(Constant.GEN_COURSE_TABLE_TEACHER_AROUND_E);
                break;
            case Constant.TABLE_TYPE_CLASS:
                rtnStrBf.append(jwCourseTableDTO.getCourseName())
                        .append(Constant.GEN_COURSE_TABLE_BASE_SPLIT)
                        .append(Constant.GEN_COURSE_TABLE_WRAP_SPLIT)
                        .append(Constant.GEN_COURSE_TABLE_TEACHER_AROUND_S)
                        .append(jwCourseTableDTO.getTeacherName())
                        .append(Constant.GEN_COURSE_TABLE_TEACHER_AROUND_E);
                break;
            default:
                rtnStrBf.append(jwCourseTableDTO.getCourseName())
                        .append(Constant.GEN_COURSE_TABLE_BASE_SPLIT)
                        .append(Constant.GEN_COURSE_TABLE_WRAP_SPLIT)
                        .append(Constant.GEN_COURSE_TABLE_TEACHER_AROUND_S)
                        .append(jwCourseTableDTO.getTeacherName())
                        .append(Constant.GEN_COURSE_TABLE_TEACHER_AROUND_E);
                break;
        }
        return rtnStrBf.toString();
    }


    /**
     * 生成并填充课表内容
     * @param tnId
     * @param taskId
     * @param type
     * @param params
     * @return
     */
    public CourseResultView genSyllabus(int tnId,int taskId,String type,Map<String, Object> params){
        List<List<String>> lists;
        Map<String, Object> timeConfigMap;
        int courseCount,dayCount;
        timeConfigMap = iexJwScheduleTaskService.getCourseTimeConfig(tnId, taskId);

        if (timeConfigMap == null)
            throw new BizException("error", "当前租户下排课任务课时设置为空,请设置后再试");

        dayCount = ((List)timeConfigMap.get("list")).size();
        courseCount = (int)timeConfigMap.get("count");
        lists = genDefaultSyllabus(dayCount,courseCount);

        return this.genSyllabus(tnId,taskId,type,params,lists,timeConfigMap);
    }

    /**
     * 生成并填充课表内容
     * @param tnId
     * @param taskId
     * @param type
     * @param params
     * @return
     */
    @Override
    public CourseResultView genSyllabus(int tnId,int taskId,String type,Map<String, Object> params,List<List<String>> lists,Map<String, Object> timeConfigMap){
        List<JwCourseTableDTO> jwCourseTableDTOs;
        int week,day;
        String tempCourse;
        CourseResultView courseResultView = new CourseResultView();

        jwCourseTableDTOs = this.queryList(tnId,taskId,params);
        if (jwCourseTableDTOs.size() == 0)
            throw new BizException("error", "当前租户下排课任务"+type+"课表为空,请稍后再试");

        for (JwCourseTableDTO jwCourseTableDTO : jwCourseTableDTOs){
            week = jwCourseTableDTO.getWeek();
            day = jwCourseTableDTO.getSort();
            tempCourse = lists.get(week).get(day);
            //判定是否已经被插入过课程
            if (StringUtils.isEmpty(tempCourse))
                //没有插入过课程
                lists.get(week).set(day,this.genStringByDTO(type,jwCourseTableDTO));
            else
                lists.get(week).set(day,tempCourse+ Constant.GEN_COURSE_TABLE_WRAP_SPLIT + this.genStringByDTO(type,jwCourseTableDTO));
        }

        courseResultView.setWeek(lists);
        courseResultView.setTeachDate(timeConfigMap.get("teachDate").toString());
        courseResultView.setTeachTime(timeConfigMap.get("time").toString());
        return courseResultView;
    }

    /**
     * 构造一张空课表
     * @param dayCount
     * @param courseCount
     * @return
     */
    @Override
    public List<List<String>> genDefaultSyllabus(int dayCount,int courseCount){
        List<List<String>> lists = new ArrayList<>();
        List<String> list;
        //构造一张课表
        for (int i = 0; i < dayCount;i++){
            list = new ArrayList<>();
            for (int j = 0;j< courseCount ; j++)
                list.add(Constant.COURSE_TABLE_DEFAULT_VALUE);
            lists.add(list);
        }
        return lists;
    }

    private boolean updateExchange(int y,int tnId,int taskId,List<JwCourseTable> sourceList,List<JwCourseTable> targetList){
        Map<String,Object> params = new HashMap<>();
        params.put("taskId",taskId);
        List<JwSyllabus> jwSyllabuses = jwSyllabusDAO.queryList(params,"id","asc");
        Iterator<JwSyllabus> jwSyllabusIterator = jwSyllabuses.iterator();
        Map<Integer,JwSyllabus> map = new LinkedHashMap<>();
        while (jwSyllabusIterator.hasNext()){
            JwSyllabus jwSyllabus = jwSyllabusIterator.next();
            map.put(jwSyllabus.getClassId(),jwSyllabus);
        }
        for (JwCourseTable source : sourceList) {
            JwCourseTable target = targetList.get(0);
            JwSyllabus jwSyllabus = map.get(target.getClassId());
            String info = replace(jwSyllabus.getInfo(),y,source, target);
            jwSyllabus.setInfo(info);
            jwSyllabusDAO.update(jwSyllabus);
        }
        for (JwCourseTable source : targetList) {
            JwCourseTable target = sourceList.get(0);
            JwSyllabus jwSyllabus = map.get(target.getClassId());
            jwSyllabus.setInfo(replace(jwSyllabus.getInfo(),y,source, target));
            jwSyllabusDAO.update(jwSyllabus);
        }
//        BufferedWriter bufferedWriter =null;
        try {
            List<String> list = new ArrayList<>();
            for (JwSyllabus jwSyllabus : jwSyllabuses) {
                list.add(this.toLine(jwSyllabus));
            }
//            bufferedWriter =  Files.newWriter(new File("/Users/yangyongping/Desktop/yqhc/zgk-saas/saas-service/src/main/resources/config/admin_course_2.txt"), Charset.defaultCharset());
            CharSink charSink = Files.asCharSink(new File(iexJwScheduleTaskService.getScheduleTaskPath(taskId, tnId) + Constant.PATH_SCHEDULE), Charset.defaultCharset());
            charSink.writeLines(list);
        }catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }
    private String replace(String s,int y,JwCourseTable source,JwCourseTable target){
        StringBuilder stringBuilder = new StringBuilder();
        String[] strings = s.split(Constant.COURSE_TABLE_LINE_SPLIT_CHAR);
        strings[source.getWeek()*y+source.getSort()] = target.getCourseId().toString();
        for (String ss : strings){
            stringBuilder.append(ss).append(Constant.COURSE_TABLE_LINE_SPLIT_CHAR);
        }
        return stringBuilder.toString();
    }

    private static String toLine(JwSyllabus jwSyllabus){
        return new StringBuffer().append(jwSyllabus.getClassId()).append(Constant.COURSE_TABLE_LINE_SPLIT_CLASS).append(jwSyllabus.getInfo()).toString();
    }
}
