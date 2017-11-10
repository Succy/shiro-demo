package cn.succy.shiro.service.impl;

import cn.succy.shiro.entity.Client;
import cn.succy.shiro.repository.ClientRepository;
import cn.succy.shiro.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author Succy
 * @date 2017-11-08 19:15
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client createClient(Client client) {
        client.setClientId(UUID.randomUUID().toString());
        client.setClientSecret(UUID.randomUUID().toString());
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Client findOne(Long id) {
        return clientRepository.findOne(id);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId);
    }

    @Override
    public Client findByClientSecret(String clientSecret) {
        return clientRepository.findByClientSecret(clientSecret);
    }

    @Override
    public Client findByClientIdAndClientSecret(String clientId, String clientSecret) {
        return clientRepository.findByClientIdAndClientSecret(clientId, clientSecret);
    }
}
