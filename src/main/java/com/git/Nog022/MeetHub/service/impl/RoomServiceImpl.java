package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.entity.Room;
import com.git.Nog022.MeetHub.repository.RoomRepository;
import com.git.Nog022.MeetHub.service.RoomService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepository;
    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void delete(Integer id) {
        roomRepository.findById(id).map(roomId -> {
            roomRepository.delete(roomId);
            return roomId;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not find"));

    }

    @Override
    public void update(Integer id, Room room) {
        roomRepository.findById(id).map(
                roomFind -> {
                    room.setId(roomFind.getId());
                    roomRepository.save(room);
                    return roomFind;
                }
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not find"));

    }

    @Override
    public List<Room> listRoom() {
        return roomRepository.findAll();
    }
}
