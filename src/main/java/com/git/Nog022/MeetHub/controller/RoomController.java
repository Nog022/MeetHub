package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.dto.RoomDTO;
import com.git.Nog022.MeetHub.entity.Room;
import com.git.Nog022.MeetHub.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/rooms")
@Tag(name = "Room", description = "Meeting room management")
public class RoomController {

    @Autowired
    private RoomService roomService;


    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new room", description = "Creates a new room entry in the system")
    public RoomDTO save(@RequestBody @Validated RoomDTO room) {
        return roomService.save(room);
    }

    @GetMapping("/listRoom")
    @Operation(summary = "List all rooms", description = "Returns a list of all registered rooms")
    public List<Room> listRoom() {
        return roomService.listRoom();
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update room details", description = "Updates the details of an existing room by its ID")
    public void update(@PathVariable Long id, @RequestBody @Validated Room room) {
        roomService.update(id, room);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a room", description = "Deletes a room by its ID")
    public void delete(@PathVariable Long id) {
        roomService.delete(id);
    }

    @GetMapping("/listRoomByLocal/{id}")
    //@Operation(summary = "List all rooms", description = "Returns a list of all registered rooms")
    public List<RoomDTO> listRoomByLocal(@PathVariable Long id) {
        return roomService.listRoomByLocal(id);
    }
}
