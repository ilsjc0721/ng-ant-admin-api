package com.demo.app.mapper.user;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.sys.user.InsertUserRoleDto;
import model.entity.sys.SysUser;
import model.dto.sys.user.SearchUserDto;
import model.entity.sys.UserChild;
import model.vo.sys.DetailUserVo;
import model.vo.sys.SelectUserVo;
import model.vo.sys.UserChildVo;

import java.util.List;

/**
 * @Author fanbaolin
 * @Description
 * @Date 8:33 2021/1/12
 */
public interface UserMapper extends BaseMapper<SysUser> {

    List<SelectUserVo> listUser(SearchUserDto searchUserDto);

    DetailUserVo selectUserVoById(Integer id);

    List<UserChildVo> listUserChild(String name);
    List<UserChildVo> selectUserChildByParentId(Integer id);

    void deleteUserChild(Integer id);

    Integer insertUserChildByList(List<UserChild> userChildVoList);

    Integer insertUserChild(UserChild userChild);

    Integer updateUserChild(UserChild userChild);

    List<Integer> getParentId(Integer studentId);

}
