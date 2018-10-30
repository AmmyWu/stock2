/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.controller.pcweb
 * 类/接口名	: SysRolesController
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午2:44:34
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午2:44:34
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.controller.pcweb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.sys.SysRole;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.service.sys.SysOrganizationStructureService;
import com.stock.service.sys.SysRoleService;

@Controller
@RequestMapping(value = "/role")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysOrganizationStructureService organizationService;

    /**
     * getRoles:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     *
     * @param req
     * @param role
     * @return
     * @author chengxl
     * @since JDK 1.7
     */
    @RequestMapping(value = "/getRoles.do")
    public @ResponseBody
    Object getRoles(HttpServletRequest req) {

        // for easy ui
        // Integer pageSize = Integer.parseInt(req.getParameter("rows"));
        // Integer pageIndex = Integer.parseInt(req.getParameter("page"));

        String keys = req.getParameter("keys");

        Integer pageSize = Integer.parseInt(req.getParameter("length"));
        Integer pageIndex = Integer.parseInt(req.getParameter("start"));

        return roleService.getRoles(keys, pageSize, pageIndex);
    }


    /**
     * del:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     *
     * @param request
     * @return
     * @author chengxl
     * @since JDK 1.7
     */
    @RequestMapping(value = "/delete.do")
    public @ResponseBody
    RequestResultVO delete(String roleIds) {

        RequestResultVO j = new RequestResultVO();

        if (roleService.deleteRoles(roleIds)) {
            j.setCode(0);
            j.setMessage("删除成功!");
        } else {

            j.setCode(1000);
            j.setMessage("你所选择的角色已分配给用户或数据访问失败，删除失�?");
        }

        return j;
    }

    /**
     * save:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     *
     * @param request
     * @return
     * @author chengxl
     * @since JDK 1.7
     */
    // 角色更新
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/save.do")
    public @ResponseBody
    RequestResultVO save(HttpServletRequest request) {

        List<SysRole> lroles = (List<SysRole>) JSONArray.toCollection(
                JSONArray.fromObject(request.getParameter("data")),
                SysRole.class);

        RequestResultVO j = new RequestResultVO();

        SysRole role = lroles.get(0);

        // do insert
        if (role.getRoleId() == null) {
            if (roleService.insert(role)) {
                j.setCode(0);
                j.setMessage("新增保存成功!");
            } else {
                j.setCode(1000);
                j.setMessage("新增保存失败!");
            }

        } else { // do update
            if (roleService.update(role)) {
                j.setCode(0);
                j.setMessage("修改成功!");
            } else {
                j.setCode(1000);
                j.setMessage("修改失败!");
            }
        }

        return j;

    }

    /**
     * save:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     *
     * @param request
     * @return
     * @author chengxl
     * @since JDK 1.7
     */
    // 角色更新
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/insert.do")
    public @ResponseBody
    RequestResultVO insert(SysRole role) {


        RequestResultVO j = new RequestResultVO();

        role.setCreateTime(role.getAmendTime());
        role.setCreator(role.getAmender());

        if (roleService.insert(role)) {
            j.setCode(0);
            j.setMessage("新增保存成功!");
        } else {
            j.setCode(1000);
            j.setMessage("新增保存失败!");
        }

        return j;

    }

    /**
     * save:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     *
     * @param request
     * @return
     * @author chengxl
     * @since JDK 1.7
     */
    // 角色更新
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/update.do")
    public @ResponseBody
    RequestResultVO update(SysRole role) {


        RequestResultVO j = new RequestResultVO();


        if (roleService.update(role)) {
            j.setCode(0);
            j.setMessage("修改成功!");
        } else {
            j.setCode(1000);
            j.setMessage("修改失败!");
        }
        // }

        return j;

    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


}
