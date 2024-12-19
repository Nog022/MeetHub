package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.repository.LocalRepository;
import com.git.Nog022.MeetHub.service.LocalService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LocalServiceImpl implements LocalService {

    @Autowired
    private LocalRepository localRepository;


    @Override
    public Local save(Local local) {
        return localRepository.save(local);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Integer id, Local local) {

    }

    @Override
    public List<Local> listLocal() {
        return List.of();
    }
}
