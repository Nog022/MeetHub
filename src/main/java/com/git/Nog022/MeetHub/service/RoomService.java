package com.git.Nog022.MeetHub.service;

import com.git.Nog022.MeetHub.dto.LocalDTO;
import com.git.Nog022.MeetHub.dto.RoomDTO;
import com.git.Nog022.MeetHub.dto.RoomDetailDTO;
import com.git.Nog022.MeetHub.entity.Room;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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
}
