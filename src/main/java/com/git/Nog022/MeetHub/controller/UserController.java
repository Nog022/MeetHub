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
@Tag(name = "User", description = "User management")
public class UserController {
    public static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user", description = "Creates a new user entry in the system")
    public User save(@RequestBody @Validated User user) {
        logger.info("Entered save");
        return userService.save(user);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user by their ID")
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update user details", description = "Updates the details of an existing user by their ID")
    public void update(@PathVariable Integer id, @RequestBody @Validated User user) {
        userService.update(id, user);
    }

    @GetMapping("/listAll")
    @Operation(summary = "List all users", description = "Returns a list of all registered users")
    public List<User> listUser() {
        return userService.listUser();
    }
}
