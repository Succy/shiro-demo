package cn.succy.shiro.service;

import cn.succy.shiro.entity.Client;

import java.util.List;

/**
 * @author Succy
 * @date 2017-11-08 16:50
 **/

public interface ClientService {
    /**
     * 创建一个客户端
     *
     * @param client 客户端对象
     * @return 创建成功的客户端对象
     */
    Client createClient(Client client);

    /**
     * 更新一个客户端
     *
     * @param client 客户端对象
     * @return 更新后的客户端对象
     */
    Client updateClient(Client client);

    /**
     * 根据客户端表的id删除对应的客户端
     *
     * @param id 客户端表的id
     */
    void deleteClient(Long id);

    /**
     * 根据id查询客户端
     *
     * @param id 客户端表的id
     * @return 客户端对象
     */
    Client findOne(Long id);

    /**
     * 查询所有客户端对象
     *
     * @return 所有客户端的集合
     */
    List<Client> findAll();

    /**
     * 根据clientID查询客户端
     *
     * @param clientId 客户端对象
     * @return 查询到的客户端对象
     */
    Client findByClientId(String clientId);

    /**
     * 根据clientSecret查询客户端
     *
     * @param clientSecret 客户端对象
     * @return 查询到的客户端对象
     */
    Client findByClientSecret(String clientSecret);

    /**
     * 根据客户端id和客户端秘钥查询
     * @param clientId 客户端id
     * @param clientSecret 客户端秘钥
     */
    Client findByClientIdAndClientSecret(String clientId, String clientSecret);
}
