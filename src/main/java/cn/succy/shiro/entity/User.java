package cn.succy.shiro.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author Succy
 * @date 2017-11-08 16:03
 **/
@Data
@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -8432768891689817846L;
    /**
     * 编号
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 加密密码的盐
     */
    private String salt;

    @Transient
    public String getCredentialsSalt() {
        return username + salt;
    }
}
