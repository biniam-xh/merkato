package edu.mum.mercato.config;

import edu.mum.mercato.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class MerkatoUserDetails implements UserDetails {
    private String email;
    private String password;
    private boolean active;
    private Set<GrantedAuthority> roles;

    public MerkatoUserDetails(User user) {
        this.email=user.getEmail();
        this.password=user.getPassword();
        this.active=user.isActive();
        this.roles= Arrays.asList(user.getRole()).stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toSet());
        System.out.println(Arrays.asList(this.roles));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
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
