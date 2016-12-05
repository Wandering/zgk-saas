/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwClassRuleService.java 2016-12-05 17:07:21 $
 */

package cn.thinkjoy.saas.service;
import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IPageService;

public interface IJwClassRuleService<D extends IBaseDAO<T>, T extends BaseDomain>
    extends IDisSelectRoleService<D, T>,IPageService<D, T>

{

}
