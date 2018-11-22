package com.group.zhtx.service;

import com.group.zhtx.model.Administrator;
import com.group.zhtx.repository.AdministratorRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class AdministratorService implements IRepositoryService {
    @Resource
    private AdministratorRepository administratorRepository;
    public Administrator findAdministrator(String id){
        Administrator administrators=administratorRepository.findById(id).orElse(null);
        return administrators;
    }
}
