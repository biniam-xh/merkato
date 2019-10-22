package edu.mum.mercato.config;

import edu.mum.mercato.domain.User;
import edu.mum.mercato.repository.UserRepository;
import edu.mum.mercato.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MerkatoUserDetailService implements UserDetailsService {
    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=userService.findUserByEmail(s);
        System.out.println(user);
        return new MerkatoUserDetails(user);
    }
}
