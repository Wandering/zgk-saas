/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao360
 * $Id:  AgentServiceImpl.java 2015-12-15 17:52:12 $
 */
package cn.thinkjoy.saas.common;

import cn.thinkjoy.zgk.cloudstack.ISimpleCloud;
import org.springframework.stereotype.Service;


@Service("SimpleCloudImpl")
public class SimpleCloudImpl implements ISimpleCloud{

    @Override
    public String getCloudProduct() {
        return null;
    }

    @Override
    public String getCloudArea() {
        return null;
    }

    @Override
    public boolean hasWhiteList(String s) {
        return false;
    }

    @Override
    public void setArea(String s) {

    }

    @Override
    public void clearArea() {

    }
}
