package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.repository.LocalRepository;
import com.git.Nog022.MeetHub.service.LocalService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        localRepository.findById(id).map(localId -> {
            localRepository.delete(localId);
            return localId;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local not find"));

    }

    @Override
    public void update(Local local) {
        localRepository.findById(local.getId()).map(
                localFind -> {
                    local.setId(localFind.getId());
                    localRepository.save(local);
                    return localFind;
                }
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local not find"));

    }

    @Override
    public List<Local> listLocal() {
        return localRepository.findAll();

    }

    @Override
    public Local localById(Integer id) {
        return localRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local not find"));

    }
}
