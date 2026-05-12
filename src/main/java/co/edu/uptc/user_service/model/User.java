package co.edu.uptc.user_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA que representa un usuario en el sistema.
 *
 * <p>Modela la tabla {@code users} de la base de datos relacional. Lombok genera
 * automáticamente los métodos {@code getters}, {@code setters}, {@code equals},
 * {@code hashCode} y {@code toString} a través de {@code @Data}, así como los
 * constructores con y sin argumentos.</p>
 *
 * <p>En la arquitectura del microservicio, esta clase forma parte de la capa de
 * modelo (dominio) y es utilizada exclusivamente por la capa de repositorio para
 * la persistencia. La capa de servicio transforma esta entidad en un {@link co.edu.uptc.user_service.dto.UserDTO}
 * antes de exponer los datos al exterior.</p>
 *
 * @author Valeria Tocarruncho
 * @version 1.0
 * @see co.edu.uptc.user_service.repository.UserRepository
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    /**
     * Identificador único del usuario, generado automáticamente por la base de datos
     * mediante la estrategia de identidad ({@code AUTO_INCREMENT} o equivalente).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de pila del usuario.
     */
    private String name;

    /**
     * Apellido del usuario.
     */
    private String lastName;

    /**
     * Edad del usuario expresada en años completos.
     */
    private int age;
}
