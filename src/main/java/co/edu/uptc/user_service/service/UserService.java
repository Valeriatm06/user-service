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

@Service
public class UserService {

    private final UserRepository userRepository;

    @Value("${app.server.mark:Servidor Desconocido - Usuarios}")
    private String serverMark;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO saveUser(UserDTO request) {
        User user = new User();
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setAge(request.getAge());

        User savedUser = userRepository.save(user);

        return convertToDTO(savedUser);
    }

    public Page<UserDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // userRepository.findAll(pageable) hace la magia de traer solo la porción necesaria
        return userRepository.findAll(pageable).map(this::convertToDTO);
    }

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