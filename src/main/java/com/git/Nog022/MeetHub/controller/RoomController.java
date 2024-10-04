package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.entity.Room;
import com.git.Nog022.MeetHub.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Room save(@RequestBody @Validated Room room){
        return roomService.save(room);
    }

    @GetMapping("/listRoom")
    public List<Room> listRoom(){
        return roomService.listRoom();
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Validated Room room){
        roomService.update(id,room);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        roomService.delete(id);
    }
}
