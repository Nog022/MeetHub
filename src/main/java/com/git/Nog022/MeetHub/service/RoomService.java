package com.git.Nog022.MeetHub.service;

import com.git.Nog022.MeetHub.entity.Room;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public interface RoomService {

    Room save(Room room);

    void delete(Integer id);

    void update(Integer id, Room room);

    List<Room> listRoom();
}
