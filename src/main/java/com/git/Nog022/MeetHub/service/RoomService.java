package com.git.Nog022.MeetHub.service;

import com.git.Nog022.MeetHub.dto.RoomDTO;
import com.git.Nog022.MeetHub.dto.RoomDetailDTO;
import com.git.Nog022.MeetHub.entity.Room;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
public interface RoomService {

    RoomDTO save(RoomDTO room);

    void delete(Long id);

    void update(RoomDTO room);

    List<Room> listRoom();

    RoomDTO roomDtoById(Long id);

    List<RoomDTO> listRoomByLocal(Long id);

    Room roomById(Long id);

    List<RoomDetailDTO> listRoomByInstitution(Long id);

    void save(Room room);

    Optional<Room> findById(Long id);
}
