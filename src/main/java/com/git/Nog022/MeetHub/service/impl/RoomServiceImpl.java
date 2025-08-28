package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.dto.LocalDTO;
import com.git.Nog022.MeetHub.dto.RoomDTO;
import com.git.Nog022.MeetHub.dto.RoomDetailDTO;
import com.git.Nog022.MeetHub.entity.Institution;
import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.entity.Reservation;
import com.git.Nog022.MeetHub.entity.Room;
import com.git.Nog022.MeetHub.repository.LocalRepository;
import com.git.Nog022.MeetHub.repository.RoomRepository;
import com.git.Nog022.MeetHub.service.InstitutionService;
import com.git.Nog022.MeetHub.service.LocalService;
import com.git.Nog022.MeetHub.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private LocalService localService;

    @Autowired
    private InstitutionService institutionService;

    @Override
    public RoomDTO save(RoomDTO dto) {
        log.info("Room : {}", dto);
        Local local = localService.localById(dto.localId());

        Room room = new Room();
        room.setName(dto.name());
        room.setCapacity(dto.capacity());
        room.setLocal(local);


        List<String> listResources = dto.resources();
        for (String resource : listResources) {
            room.getResources().add(resource);
        }



       roomRepository.save(room);


        return new RoomDTO(
                room.getId(),
                room.getName(),
                room.getCapacity(),
                room.getLocal().getId(),
                room.getResources()
        );
    }

    @Override
    public void delete(Long id) {
        roomRepository.findById(id).map(roomId -> {
            roomRepository.delete(roomId);
            return roomId;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not find"));

    }

    @Override
    public void update(RoomDTO dto) {
        Room room = roomRepository.findById(dto.id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not find"));

        if(room != null) {
            room.setName(dto.name());
            room.setCapacity(dto.capacity());
            room.getResources().clear();
            room.getResources().addAll(dto.resources());
            roomRepository.save(room);

        }

    }

    @Override
    public List<RoomDTO> listRoomByLocal(Long id) {
        List<Room> rooms = roomRepository.findByLocalId(id);
        return getRoomDTOS(rooms);

    }

    @Override
    public Room roomById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not find"));
    }

    @Override
    public List<RoomDetailDTO> listRoomByInstitution(Long id) {
        Institution institution = institutionService.findById(id);
        List<RoomDTO> dtos = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        if(institution != null) {
            List<Local> locals = institution.getLocals();

            for (Local local : locals) {
                rooms.addAll(roomRepository.findByLocalId(local.getId()));
            }
        }
        return this.setRoomDTO(rooms);


    }

    private List<RoomDetailDTO> setRoomDTO(List<Room> rooms) {
        return getRoomDetailDTO(rooms);
    }

    private List<RoomDetailDTO> getRoomDetailDTO(List<Room> rooms) {
        List<RoomDetailDTO> dtos = new ArrayList<>();
        for (Room room : rooms) {
            RoomDetailDTO dto = new RoomDetailDTO(
                    room.getId(),
                    room.getName(),
                    room.getCapacity(),
                    room.getLocal().getName(),
                    room.getResources()
            );
            dtos.add(dto);
        }

        return dtos;
    }

    private List<RoomDTO> getRoomDTOS(List<Room> rooms) {
        List<RoomDTO> dtos = new ArrayList<>();
        for (Room room : rooms) {
            RoomDTO dto = new RoomDTO(
                    room.getId(),
                    room.getName(),
                    room.getCapacity(),
                    room.getLocal().getId(),
                    room.getResources()
            );
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public List<Room> listRoom() {
        return roomRepository.findAll();
    }

    @Override
    public RoomDTO roomDtoById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not find"));
        return new RoomDTO(
                room.getId(),
                room.getName(),
                room.getCapacity(),
                room.getLocal().getId(),
                room.getResources()
        );

    }
}
