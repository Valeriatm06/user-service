package co.edu.uptc.user_service.dto;

import lombok.Data;

@Data
public class UserDTO {

    private String name;
    private String lastName;
    private int age;
    private String serverMark;
    private String serverIp;
}
