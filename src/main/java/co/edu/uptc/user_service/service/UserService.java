package co.edu.uptc.user_service.service;

import co.edu.uptc.user_service.dto.UserDTO;
import co.edu.uptc.user_service.model.User;
import co.edu.uptc.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Capa de lógica de negocio del microservicio de usuarios.
 *
 * <p>Coordina las operaciones sobre los usuarios, delegando la persistencia al
 * repositorio {@link co.edu.uptc.user_service.repository.UserRepository} y
 * transformando las entidades del dominio en objetos de transferencia
 * ({@link UserDTO}) antes de devolverlos al controlador.</p>
 *
 * <p>Enriquece cada respuesta con metadatos de infraestructura: la marca del
 * servidor ({@code app.server.mark}) y la dirección IP local de la instancia en
 * ejecución, facilitando la trazabilidad en despliegues con múltiples réplicas.</p>
 *
 * @author Valeria Tocarruncho
 * @version 1.0
 * @see co.edu.uptc.user_service.controller.UserController
 * @see co.edu.uptc.user_service.repository.UserRepository
 */
@Service
public class UserService {

    /**
     * Repositorio JPA para la persistencia de usuarios.
     */
    private final UserRepository userRepository;

    /**
     * Etiqueta identificadora de la instancia del servidor, inyectada desde la
     * propiedad {@code app.server.mark} del archivo de configuración.
     * Si la propiedad no está definida, se usa el valor por defecto
     * {@code "Servidor Desconocido - Usuarios"}.
     */
    @Value("${app.server.mark:Servidor Desconocido - Usuarios}")
    private String serverMark;

    /**
     * Constructor con inyección de dependencia del repositorio de usuarios.
     *
     * @param userRepository repositorio JPA para operaciones de persistencia sobre {@link co.edu.uptc.user_service.model.User}
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Persiste un nuevo usuario en la base de datos a partir de los datos recibidos en el DTO.
     *
     * <p>Mapea los campos del {@link UserDTO} de entrada a una entidad {@link co.edu.uptc.user_service.model.User},
     * la almacena y retorna un nuevo DTO con los datos guardados más los metadatos del servidor.</p>
     *
     * @param request DTO con los datos del usuario a crear ({@code name}, {@code lastName}, {@code age})
     * @return {@link UserDTO} con los datos persistidos y los metadatos de la instancia que atendió la solicitud
     */
    public UserDTO saveUser(UserDTO request) {
        User user = new User();
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setAge(request.getAge());

        User savedUser = userRepository.save(user);

        return convertToDTO(savedUser);
    }

    /**
     * Recupera de forma paginada la lista de todos los usuarios registrados en el sistema.
     *
     * @param page número de página solicitada (base 0)
     * @param size cantidad máxima de registros por página
     * @return {@link Page} de {@link UserDTO} con los usuarios de la página solicitada,
     *         cada uno enriquecido con metadatos del servidor
     */
    public Page<UserDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(this::convertToDTO);
    }

    /**
     * Convierte una entidad {@link co.edu.uptc.user_service.model.User} en un {@link UserDTO},
     * agregando la marca del servidor y la dirección IP de la instancia en ejecución.
     *
     * <p>Si no es posible resolver la IP local (por ejemplo, en entornos con restricciones de red),
     * el campo {@code serverIp} se establece como {@code "IP Desconocida"}.</p>
     *
     * @param user entidad de dominio a convertir
     * @return {@link UserDTO} listo para ser serializado y devuelto al cliente
     */
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setLastName(user.getLastName());
        dto.setAge(user.getAge());
        dto.setServerMark(serverMark);
        try {
            dto.setServerIp(java.net.InetAddress.getLocalHost().getHostAddress());
        } catch (Exception e) {
            dto.setServerIp("IP Desconocida");
        }
        return dto;
    }
}