package co.edu.uptc.user_service.repository;

import co.edu.uptc.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de acceso a datos para la entidad {@link co.edu.uptc.user_service.model.User}.
 *
 * <p>Extiende {@link JpaRepository} de Spring Data JPA, heredando de forma automática
 * las operaciones CRUD estándar ({@code save}, {@code findById}, {@code findAll},
 * {@code delete}, etc.) y soporte para paginación y ordenamiento sin necesidad de
 * implementación adicional.</p>
 *
 * <p>En la arquitectura del microservicio ocupa la capa de repositorio (persistencia),
 * siendo el único punto de contacto con la base de datos relacional. La capa de
 * servicio lo inyecta vía constructor para mantener bajo acoplamiento.</p>
 *
 * @author Valeria Tocarruncho
 * @version 1.0
 * @see co.edu.uptc.user_service.service.UserService
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
