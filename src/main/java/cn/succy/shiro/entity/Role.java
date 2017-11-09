package cn.succy.shiro.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Succy
 * @date 2017-11-08 16:28
 **/
@Entity
@Table(name = "role")
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = -2445570015313277362L;
    @Id
    @GeneratedValue
    private Long id;
}
