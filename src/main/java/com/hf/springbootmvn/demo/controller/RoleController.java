package com.hf.springbootmvn.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hf.springbootmvn.demo.entity.Account;
import com.hf.springbootmvn.demo.entity.Permision;
import com.hf.springbootmvn.demo.entity.QueryVo;
import com.hf.springbootmvn.demo.entity.Role;
import com.hf.springbootmvn.demo.service.RoleService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    /**
     * 显示role列表
     */
    @RequestMapping("/list")
    public String list(HttpServletRequest request, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize, Model model) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> all = roleService.findAll();
        PageInfo<Role> pageInfo = new PageInfo<>(all, 5);
        model.addAttribute("page", pageInfo);
        model.addAttribute("type", "role");
        return "/index";
    }

    /**
     * 角色根据id-修改页面
     */
    @RequestMapping("/modifyRole/{id}")
    public String modifyRole(@PathVariable("id") String id, Model model) {
        System.out.println(id);
        Role role = roleService.findById(Integer.parseInt(id));
        List<Permision> permisions = roleService.findPermisionById(0);
        for (int i = 0; i < role.getPermisions().size(); i++) {
            for (int j = 0; j < permisions.size(); j++) {
                if (role.getPermisions().get(i).getUri().equals(permisions.get(j).getUri())) {
                    permisions.get(j).setFlag(true);
                }
            }
        }
        if (role == null) {
            return "error";
        }
        model.addAttribute("role", role);
        model.addAttribute("permisions", permisions);
        System.out.println(ToStringBuilder.reflectionToString(role));
        return "/role/roleModify";
    }

    /**
     * 角色根据id-提交修改
     */
    @RequestMapping(value = "/modifyRoleCommit", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String modifyRoleCommit(@RequestBody Role role, Model model) {
        System.out.println(ToStringBuilder.reflectionToString(role));
        Role newRole = roleService.updateRole(role);
        model.addAttribute("role", role);
        System.out.println(ToStringBuilder.reflectionToString(role));
        return "/role/roleModify";
    }
}
