package co.edu.uptc.user_service.repository;

import co.edu.uptc.user_service.model.User;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Repository
public class FileUserRepository implements UserRepository {

    private final ObjectMapper objectMapper;

    @Value("${app.storage.path:data}")
    private String dbPath;

    @Value("${app.storage.file:database_users.txt}")
    private String dbFileName;

    public FileUserRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private Path getFullPath() {
        return Paths.get(dbPath, dbFileName);
    }

    @Override
    public User save(User user) {
        List<User> currentUsers = loadFromFile();

        long nextId = currentUsers.isEmpty() ? 1 : currentUsers.get(currentUsers.size() - 1).getId() + 1;
        user.setId(nextId);

        currentUsers.add(user);
        saveToFile(currentUsers);

        return user;
    }

    @Override
    public List<User> findAll() {
        return loadFromFile();
    }

    private void saveToFile(List<User> usersToSave) {
        try {
            String json = objectMapper.writeValueAsString(usersToSave);
            String base64Encoded = Base64.getEncoder().encodeToString(json.getBytes());

            Path path = getFullPath();

            // ¡ESTA ES LA MAGIA SALVAVIDAS PARA EL NFS!
            if (path.getParent() != null && !Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            Files.write(path, base64Encoded.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<User> loadFromFile() {
        try {
            // Usamos getFullPath()
            Path path = getFullPath();
            if (!Files.exists(path)) return new ArrayList<>();

            byte[] fileContent = Files.readAllBytes(path);
            if (fileContent.length == 0) {
                return new ArrayList<>();
            }

            String base64Content = new String(fileContent);
            if (base64Content.trim().isEmpty()) return new ArrayList<>();

            byte[] decodedBytes = Base64.getDecoder().decode(base64Content);
            return objectMapper.readValue(decodedBytes, new TypeReference<List<User>>() {});

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
