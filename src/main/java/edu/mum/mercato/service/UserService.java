package edu.mum.mercato.service;

import edu.mum.mercato.domain.User;

public interface UserService {
    public User findUserByEmail(String email);
    public void createUser(User user) ;
    public User save(User user);
}
