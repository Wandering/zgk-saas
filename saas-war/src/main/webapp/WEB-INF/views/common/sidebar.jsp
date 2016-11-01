<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="sidebar" id="sidebar">
  <div class="sidebar-shortcuts" id="sidebar-shortcuts">
    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
      <span>管理员</span><small>试用阶段</small>
    </div>

    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
      <span>管理员</span><small class="jd">试用阶段</small>
    </div>
  </div>
  <ul class="nav nav-list">
    <li class="active">
      <a href="index.html">
        <i class="icon-home"></i>
        <span class="menu-text"> 首页 </span>
      </a>
    </li>
    <li style="dn">
      <a href="#" class="dropdown-toggle">
        <i class="icon-desktop"></i>
        <span class="menu-text">高考改革 </span>
        <b class="arrow icon-angle-down"></b>
      </a>
      <ul class="submenu">
        <li>
          <a href="/class-management">
            <i class="icon-double-angle-right"></i>
            班级管理
          </a>
        </li>
      </ul>
    </li>
    <li>
      <a href="#" class="dropdown-toggle">
        <i class="icon-desktop"></i>
        <span class="menu-text">成绩分析 </span>
        <b class="arrow icon-angle-down"></b>
      </a>
      <ul class="submenu">
        <li>
          <a href="">
            <i class="icon-double-angle-right"></i>
            班级管理
          </a>
        </li>
      </ul>
    </li>
    <li>
      <a href="#" class="dropdown-toggle">
        <i class="icon-desktop"></i>
        <span class="menu-text">数据查询 </span>
        <b class="arrow icon-angle-down"></b>
      </a>
      <ul class="submenu">
        <li>
          <a href="">
            <i class="icon-double-angle-right"></i>
            班级管理
          </a>
        </li>
      </ul>
    </li>
    <li>
      <a href="#" class="dropdown-toggle">
        <i class="icon-desktop"></i>
        <span class="menu-text">基础信息管理 </span>
        <b class="arrow icon-angle-down"></b>
      </a>
      <ul class="submenu">
        <li>
          <a href="/grade-management">
            <i class="icon-double-angle-right"></i>
            年级管理
          </a>
        </li>
        <li>
          <a href="/class-management">
            <i class="icon-double-angle-right"></i>
            班级管理
          </a>
        </li>

        <li>
          <a href="/classroom-management">
            <i class="icon-double-angle-right"></i>
            教室管理
          </a>
        </li>
        <li>
          <a href="/teacher-management">
            <i class="icon-double-angle-right"></i>
            教师管理
          </a>
        </li>
        <li>
          <a href="/graduation-rates-management">
            <i class="icon-double-angle-right"></i>
            升学率管理
          </a>
        </li>
        <li>
          <a href="/student-management">
            <i class="icon-double-angle-right"></i>
            学生管理
          </a>
        </li>
      </ul>
    </li>
    <li class="active open">
      <a href="#" class="dropdown-toggle">
        <i class="icon-desktop"></i>
        <span class="menu-text">账号信息 </span>
        <b class="arrow icon-angle-down"></b>
      </a>
      <ul class="submenu">
        <li class="active">
          <a href="/role-management">
            <i class="icon-double-angle-right"></i>
            角色管理
          </a>
        </li>
        <li>
          <a href="/account-management">
            <i class="icon-double-angle-right"></i>
            账号管理
          </a>
        </li>
      </ul>
    </li>
  </ul><!-- /.nav-list -->

  <div class="sidebar-collapse" id="sidebar-collapse">
    <i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
  </div>

  <script type="text/javascript">
    try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
  </script>
</div>