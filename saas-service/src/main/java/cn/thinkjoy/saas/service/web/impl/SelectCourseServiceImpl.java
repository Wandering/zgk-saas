package cn.thinkjoy.saas.service.web.impl;

import cn.thinkjoy.saas.dao.ITenantDAO;
import cn.thinkjoy.saas.dao.bussiness.EXITenantConfigInstanceDAO;
import cn.thinkjoy.saas.dao.web.ISelectCourseDAO;
import cn.thinkjoy.saas.domain.Tenant;
import cn.thinkjoy.saas.service.web.ISelectCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    @Override
    public Map bindingSchool(String schoolId, String studentNo, String studentName) {
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
            iSelectCourseDAO.bindingSchool(map);
            resultMap.put("msg","绑定成功");
        }
        return resultMap;
    }
}
