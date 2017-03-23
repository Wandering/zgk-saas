package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.IJwCourseTableDAO;
import cn.thinkjoy.saas.dao.IJwScheduleTaskDAO;
import cn.thinkjoy.saas.dao.IJwSyllabusDAO;
import cn.thinkjoy.saas.dao.bussiness.IEXTeantCustomDAO;
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
import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 课程表增删改查业务
 * Created by yangyongping on 2017/2/27.
 */
@Service("SyllabusTeachServiceImpl")
public class SyllabusTeachServiceImpl extends SyllabusServiceImpl implements ISyllabusService {
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
    @Autowired
    private IEXTeantCustomDAO iexTeantCustomDAO;
    /**
     * 更新交换
     * @param sourceList
     * @param targetList
     * @return
     */
    protected boolean updateExchange(List<JwCourseTable> sourceList,List<JwCourseTable> targetList){
        //缓存三维坐标
        int[] sourceTemp = new int[3];
        sourceTemp[0] = sourceList.get(0).getWeek();
        sourceTemp[1] = sourceList.get(0).getSort();
        sourceTemp[2] = sourceList.get(0).getRoomId();
        int[] targetTemp = new int[3];
        targetTemp[0] = targetList.get(0).getWeek();
        targetTemp[1] = targetList.get(0).getSort();
        targetTemp[2] = targetList.get(0).getRoomId();
        for (JwCourseTable  jwCourseTable: targetList){
            jwCourseTable.setWeek(sourceTemp[0]);
            jwCourseTable.setSort(sourceTemp[1]);
            jwCourseTable.setRoomId(sourceTemp[2]);
            jwCourseTableDAO.update(jwCourseTable);
        }
        for (JwCourseTable  jwCourseTable: sourceList){
            jwCourseTable.setWeek(targetTemp[0]);
            jwCourseTable.setSort(targetTemp[1]);
            jwCourseTable.setRoomId(targetTemp[2]);
            jwCourseTableDAO.update(jwCourseTable);
        }

        return true;
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
        return updateExchange(sourceList,targetList);
    }


}
