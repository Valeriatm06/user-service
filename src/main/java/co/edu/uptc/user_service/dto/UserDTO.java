package co.edu.uptc.user_service.dto;

import lombok.Data;

/**
 * Objeto de transferencia de datos (DTO) para la entidad {@link co.edu.uptc.user_service.model.User}.
 *
 * <p>Desacopla la capa de presentación (controlador) de la capa de persistencia
 * (entidad JPA), permitiendo exponer únicamente los campos necesarios para el
 * cliente sin revelar detalles internos del modelo de dominio.</p>
 *
 * <p>Adicionalmente, enriquece la respuesta con metadatos de infraestructura
 * ({@code serverMark} y {@code serverIp}) que permiten identificar qué instancia
 * del microservicio atendió la solicitud, dato útil en entornos con múltiples
 * réplicas desplegadas detrás de un balanceador de carga.</p>
 *
 * @author Valeria Tocarruncho
 * @version 1.0
 * @see co.edu.uptc.user_service.service.UserService
 */
@Data
public class UserDTO {

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

    /**
     * Etiqueta identificadora de la instancia del servidor que procesó la solicitud.
     * Su valor proviene de la propiedad {@code app.server.mark} del archivo de configuración.
     */
    private String serverMark;

    /**
     * Dirección IP del servidor que procesó la solicitud, obtenida en tiempo de ejecución
     * mediante {@link java.net.InetAddress#getLocalHost()}.
     */
    private String serverIp;
}
