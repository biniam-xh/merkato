package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.domain.User;
import edu.mum.mercato.repository.UserRepository;
import edu.mum.mercato.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
