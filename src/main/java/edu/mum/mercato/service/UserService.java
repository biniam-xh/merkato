package edu.mum.mercato.service;

import edu.mum.mercato.domain.User;

public interface UserService {
    public User findUserByEmail(String email);
    User save(User user);
    User findById(Long id);
}
