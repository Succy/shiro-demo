package cn.succy.shiro.service.impl;

import cn.succy.shiro.entity.User;
import cn.succy.shiro.repository.UserRepository;
import cn.succy.shiro.util.PasswordHelper;
import cn.succy.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Succy
 * @date 2017-11-08 19:15
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public User createUser(User user) {
        passwordHelper.encryptPassword(user);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findOne(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userRepository.save(user);
    }

    @Override
    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
