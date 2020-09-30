package ru.denis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.denis.model.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(String name);
}
