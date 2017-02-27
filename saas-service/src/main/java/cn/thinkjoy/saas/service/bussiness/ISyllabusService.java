/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  CityService.java 2016-10-26 10:18:20 $
 */

package cn.thinkjoy.saas.service.bussiness;


import java.util.Map;

public interface ISyllabusService{

    Map<String,Object> getAllSyllabus(int tnId, int taskId);

}
