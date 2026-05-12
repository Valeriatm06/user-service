package co.edu.uptc.user_service.controller;

import co.edu.uptc.user_service.dto.UserDTO;
import co.edu.uptc.user_service.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que expone los endpoints del microservicio de usuarios.
 *
 * <p>Recibe las solicitudes HTTP entrantes, delega el procesamiento a la capa de
 * servicio ({@link co.edu.uptc.user_service.service.UserService}) y devuelve las
 * respuestas encapsuladas en {@link ResponseEntity} con los códigos HTTP apropiados.</p>
 *
 * <p>Todas las rutas están disponibles bajo el prefijo {@code /user}. Los datos se
 * intercambian en formato JSON mediante la serialización automática de Spring Boot
 * (Jackson).</p>
 *
 * @author Valeria Tocarruncho
 * @version 1.0
 * @see co.edu.uptc.user_service.service.UserService
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * Servicio de lógica de negocio para la gestión de usuarios.
     */
    private final UserService userService;

    /**
     * Constructor con inyección de dependencia del servicio de usuarios.
     *
     * @param userService servicio que implementa la lógica de negocio del microservicio
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Crea y persiste un nuevo usuario en el sistema.
     *
     * <p>Endpoint: {@code POST /user}</p>
     *
     * @param request cuerpo de la solicitud HTTP con los datos del usuario a crear,
     *                deserializado automáticamente desde JSON
     * @return {@link ResponseEntity} con estado HTTP 200 (OK) y el {@link UserDTO}
     *         del usuario creado, incluyendo los metadatos del servidor que procesó la solicitud
     */
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO request) {
        return ResponseEntity.ok(userService.saveUser(request));
    }

    /**
     * Recupera de forma paginada todos los usuarios registrados en el sistema.
     *
     * <p>Endpoint: {@code GET /user?page={page}&size={size}}</p>
     *
     * @param page número de página a consultar (base 0); valor por defecto: {@code 0}
     * @param size cantidad máxima de registros por página; valor por defecto: {@code 10}
     * @return {@link ResponseEntity} con estado HTTP 200 (OK) y una {@link Page} de
     *         {@link UserDTO} con los usuarios de la página solicitada
     */
    @GetMapping
    public ResponseEntity<Page<UserDTO>> listAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(userService.getAll(page, size));
    }
}