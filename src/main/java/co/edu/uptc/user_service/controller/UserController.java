package co.edu.uptc.user_service.controller;

import co.edu.uptc.user_service.dto.UserDTO;
import co.edu.uptc.user_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO request) {
        // userService ya devuelve el DTO con la IP y la marca incluidas
        return ResponseEntity.ok(userService.saveUser(request));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listAll() {
        // userService ya devuelve la lista con las IPs y marcas incluidas
        return ResponseEntity.ok(userService.getAll());
    }
}