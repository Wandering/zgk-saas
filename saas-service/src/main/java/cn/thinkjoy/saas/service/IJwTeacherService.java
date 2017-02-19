/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: zgk-saas
 * $Id:  JwTeacherService.java 2017-02-16 14:02:50 $
 */

package cn.thinkjoy.saas.service;
import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;

public interface IJwTeacherService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T>{

}
