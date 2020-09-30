package ru.denis.service;

import ru.denis.model.UserDTO;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO getUserByName(String name);

    void addUser(UserDTO user);

    void deleteUser(Long id);

    void editUser(UserDTO user);

    Set<String> getNameRoles();
}
