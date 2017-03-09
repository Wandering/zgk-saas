/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: zgk-saas
 * $Id:  JwGradeRuleService.java 2017-03-09 11:02:24 $
 */

package cn.thinkjoy.saas.service;
import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;

public interface IJwGradeRuleService<D extends IBaseDAO<T>, T extends BaseDomain>
extends IDisSelectRoleService<D, T>,IPageService<D, T>{

}
