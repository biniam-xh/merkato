package edu.mum.mercato.service;

import edu.mum.mercato.domain.User;

public interface UserService {
    public User findUserByEmail(String email);
    public void createUser(User user) ;
    User save(User user);
    User findById(Long id);
}
