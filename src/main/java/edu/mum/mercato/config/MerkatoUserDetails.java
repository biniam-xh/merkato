package edu.mum.mercato.config;

import edu.mum.mercato.domain.Role;
import edu.mum.mercato.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MerkatoUserDetails implements UserDetails {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean active;
    private Role role;

    public MerkatoUserDetails(User user) {
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.id=user.getId();
        this.email=user.getEmail();
        this.password=user.getPassword();
        this.active=user.isActive();
        this.role=user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles=new HashSet<>();
        roles.add(this.role);
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toSet());
    }

    public Role getRoles() {
        return role;
    }

    public long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
