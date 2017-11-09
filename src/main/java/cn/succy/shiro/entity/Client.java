package cn.succy.shiro.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Succy
 * @date 2017-11-08 16:10
 **/
@Entity
@Table(name = "client")
@Data
public class Client implements Serializable {
    private static final long serialVersionUID = 1053255437884870775L;
    @Id
    @GeneratedValue
    private Long id;
    private String clientName;
    private String clientId;
    private String clientSecret;
}
