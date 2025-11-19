package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
@Tag(name = "Usuário", description = "Gerenciamento de usuários")
public class UserController {
    public static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Criar um novo usuário",
            description = "Cria um novo registro de usuário no sistema"
    )

    public User save(@RequestBody @Validated User user) {
        logger.info("Entered save");
        return userService.save(user);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Excluir um usuário",
            description = "Exclui um usuário pelo seu ID"
    )

    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Atualizar dados do usuário",
            description = "Atualiza os dados de um usuário existente pelo seu ID"
    )

    public void update(@PathVariable Integer id, @RequestBody @Validated User user) {
        userService.update(id, user);
    }

    @GetMapping("/listAll")
    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma lista com todos os usuários cadastrados"
    )
    public List<User> listUser() {
        return userService.listUser();
    }
}
