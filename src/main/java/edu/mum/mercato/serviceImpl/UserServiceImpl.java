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
    public User findUserByEmail(String email) {
        List<User> users= (List<User>) userRepository.findAll();
        for (User u :
                users) {
            if(u.getEmail().equalsIgnoreCase(email)){
                return u;
            }

        }
        return null;
    }

    @Override
    public void createUser(User user) {
        if(findUserByEmail(user.getEmail())==null){
            userRepository.save(user);
        }
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
