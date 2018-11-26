package com.group.zhtx.service;

import com.group.zhtx.model.Administrator;
import com.group.zhtx.model.User;
import com.group.zhtx.repository.AdministratorRepository;
import com.group.zhtx.repository.UserRepository;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdministratorService implements IRepositoryService {
    @Resource
    private AdministratorRepository administratorRepository;
    @Resource
    private UserRepository userRepository;

    public Administrator findAdministrator(String id) {
        Administrator administrators = administratorRepository.findById(id).orElse(null);
        return administrators;
    }

    public List<User> findAllUsers() {
        return userRepository.findThreedUser();
    }

    public List<User>findByKeyWord(String key){
        return userRepository.findByUserIdOrUserName(key);
    }
}
