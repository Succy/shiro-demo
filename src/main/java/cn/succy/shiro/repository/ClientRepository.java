package cn.succy.shiro.repository;

import cn.succy.shiro.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Succy
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
    /**
     * 通过client表的id删除client记录
     * @param id client表的id字段
     */
    void deleteById(Long id);

    /**
     * 通过clientI查询client记录
     * @param clientId 客户端id
     */
    Client findByClientId(String clientId);

    /**
     * 通过clientSecret查询client记录
     * @param clientSecret 客户端密钥
     */
    Client findByClientSecret(String clientSecret);

    /**
     * 根据客户端id和客户端秘钥查询
     * @param clientId 客户端id
     * @param clientSecret 客户端秘钥
     * @return
     */
    Client findByClientIdAndClientSecret(String clientId, String clientSecret);
}
