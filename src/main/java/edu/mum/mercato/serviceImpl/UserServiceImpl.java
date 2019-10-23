package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.domain.User;
import edu.mum.mercato.exception.NotFoundException;

import edu.mum.mercato.repository.UserRepository;
import edu.mum.mercato.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        List<User> users= (List<User>) userRepository.findAll();
        for (User u :
                users) {
            System.out.println(u.getEmail().equalsIgnoreCase(email));
            if(u.getEmail().equalsIgnoreCase(email)){
                System.out.println(u.getEmail());
                return u;
            }

        }
        return null;
    }

    @Override
    public User save(User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        System.out.println(user);
        return userRepository.save(user);

    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
