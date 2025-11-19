package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.dto.LocalDTO;
import com.git.Nog022.MeetHub.dto.RoomDTO;
import com.git.Nog022.MeetHub.dto.RoomDetailDTO;
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
@Tag(name = "Sala", description = "Gerenciamento de salas de reunião")
public class RoomController {

    @Autowired
    private RoomService roomService;


    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Criar uma nova sala",
            description = "Cria um novo registro de sala no sistema"
    )

    public RoomDTO save(@RequestBody @Validated RoomDTO room) {
        return roomService.save(room);
    }

    @GetMapping("/listRoom")
    @Operation(
            summary = "Listar todas as salas",
            description = "Retorna uma lista com todas as salas cadastradas"
    )

    public List<Room> listRoom() {
        return roomService.listRoom();
    }

    @GetMapping("/roomById/{id}")
    @Operation(
            summary = "Buscar sala por ID",
            description = "Retorna os detalhes de uma sala específica pelo seu ID"
    )
    public RoomDTO roomById(@PathVariable Long id) {
        return roomService.roomDtoById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Atualizar dados da sala",
            description = "Atualiza os dados de uma sala existente pelo seu ID"
    )

    public void update(@RequestBody @Validated RoomDTO room) {
        roomService.update(room);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Excluir uma sala",
            description = "Exclui uma sala pelo seu ID"
    )

    public void delete(@PathVariable Long id) {
        roomService.delete(id);
    }

    @GetMapping("/listRoomByLocal/{id}")
    @Operation(
            summary = "Listar salas por local",
            description = "Retorna uma lista de salas associadas a um local específico pelo seu ID"
    )
    public List<RoomDTO> listRoomByLocal(@PathVariable Long id) {
        return roomService.listRoomByLocal(id);
    }

    @GetMapping("/listRoomByInstitution/{id}")
    @Operation(
            summary = "Listar salas por instituição",
            description = "Retorna uma lista detalhada de salas associadas a uma instituição específica pelo seu ID"
    )
    public List<RoomDetailDTO> listRoomByInstitution(@PathVariable Long id) {
        return roomService.listRoomByInstitution(id);
    }
}
