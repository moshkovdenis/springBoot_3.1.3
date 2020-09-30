package ru.denis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.denis.model.Role;
import ru.denis.model.User;
import ru.denis.model.UserDTO;
import ru.denis.repository.RoleRepository;
import ru.denis.repository.UserRepository;

import java.util.*;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            users.add(getDtoFromUser(user));
        }
        return users;
    }

    public UserDTO getUserById(Long id) {
        return getDtoFromUser(userRepository.findById(id).get());
    }

    public UserDTO getUserByName(String name) {
        return getDtoFromUser(userRepository.findByName(name));
    }

    public void addUser(UserDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(getUserFromDto(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void editUser(UserDTO user) {
        User editUsr = userRepository.findById(user.getId()).get();
        if (!user.getPassword().isEmpty()) {
            editUsr.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        editUsr.setRoles(getSetOfRoles(user.getRoles()));
        editUsr.setName(user.getName());
        editUsr.setLastName(user.getLastName());
        editUsr.setEmail(user.getEmail());
        userRepository.save(editUsr);
    }

    public Set<String> getNameRoles() {
        Set<String> nameRoles = new HashSet<>();
        for (Role role : roleRepository.findAll()) {
            nameRoles.add(role.getRole());
        }
        return nameRoles;
    }


    private User getUserFromDto(UserDTO userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getLastName(), userDto.getAge(),
                userDto.getEmail(), userDto.getPassword(), getSetOfRoles(userDto.getRoles()));
    }

    private UserDTO getDtoFromUser(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getLastName(), user.getAge(),
                user.getEmail(), user.getPassword(), getSetOfString(user.getRoles()));
    }

    private Set<Role> getSetOfRoles(Set<String> nameRoles) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : nameRoles) {
            roles.add(roleRepository.findByRole(roleName));
        }
        return roles;
    }

    public Set<String> getSetOfString(Set<Role> roles) {
        Set<String> nameRoles = new HashSet<>();
        for (Role role : roles) {
            nameRoles.add(role.getRole());
        }
        return nameRoles;
    }
}
