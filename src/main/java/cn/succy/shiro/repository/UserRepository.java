package cn.succy.shiro.repository;

import cn.succy.shiro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Succy
 * @date 2017-11-08 16:14
 **/

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 通过id删除对应用户记录
     * @param id 用户id
     */
    void deleteById(Long id);

    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return
     */
    User findByUsername(String username);
}
