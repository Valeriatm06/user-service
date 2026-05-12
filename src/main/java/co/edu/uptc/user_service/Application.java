package co.edu.uptc.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal del microservicio de usuarios.
 *
 * <p>Punto de entrada de la aplicación Spring Boot. Activa la configuración
 * automática del contexto de aplicación, el escaneo de componentes y la
 * configuración de beans a través de la anotación {@code @SpringBootApplication}.</p>
 *
 * <p>En el contexto de la arquitectura de microservicios del sistema, esta clase
 * inicializa el servicio encargado de gestionar la información de los usuarios,
 * exponiendo una API REST consumible por otros servicios o clientes.</p>
 *
 * @author Valeria Tocarruncho
 * @version 1.0
 */
@SpringBootApplication
public class Application {

	/**
	 * Método principal que inicia la aplicación Spring Boot.
	 *
	 * @param args argumentos de línea de comandos pasados al iniciar la JVM
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
