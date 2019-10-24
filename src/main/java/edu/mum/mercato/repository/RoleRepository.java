package edu.mum.mercato.repository;

import edu.mum.mercato.domain.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Integer> {

    @Query("select r from Role r where r.role=:role")
    public Optional<Role> getRoleByName(String role);

    Role getByRole(String role);
}
