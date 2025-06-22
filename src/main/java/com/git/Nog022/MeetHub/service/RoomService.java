package com.git.Nog022.MeetHub.service;

import com.git.Nog022.MeetHub.dto.RoomDTO;
import com.git.Nog022.MeetHub.entity.Room;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public interface RoomService {

    RoomDTO save(RoomDTO room);

    void delete(Long id);

    void update(Long id, Room room);

    List<Room> listRoom();

    List<RoomDTO> listRoomByLocal(Long id);

    Room roomById(Long id);
}
