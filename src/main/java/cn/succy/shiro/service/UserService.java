package cn.succy.shiro.service;

import cn.succy.shiro.entity.User;

import java.util.List;

/**
 * @author Succy
 * @date 2017-11-08 19:11
 **/

public interface UserService {
    /**
     * 创建用户
     */
    User createUser(User user);

    /**
     * 更新用户
     */
    User updateUser(User user);

    /**
     * 删除用户
     */
    void deleteUser(Long userId);

    /**
     * 修改密码
     */
    void changePassword(Long userId, String newPassword);

    /**
     * 通过用户id查找用户
     */
    User findOne(Long userId);

    /**
     * 查询所有用户
     */
    List<User> findAll();

    /**
     * 根据用户名查找用户
     */
    User findByUsername(String username);
}
