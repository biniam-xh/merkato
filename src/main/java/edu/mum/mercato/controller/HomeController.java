package edu.mum.mercato.controller;

import edu.mum.mercato.domain.Role;
import edu.mum.mercato.domain.User;
import edu.mum.mercato.repository.RoleRepository;
import edu.mum.mercato.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;

    @RequestMapping("/")
    public String displayHomePage(){
        Role admin=roleRepository.findById(1).get();
        User user=new User();
        user.setFirstName("hklsolsls");
        user.setLastName("lsosls");
        user.setEmail("han7jello@gmail.com");
        user.setPassword("testme");
        user.setActive(true);
        user.setRole(admin);
        User user2=new User();
        user2.setFirstName("hklsolsls");
        user2.setLastName("lsosls");
        user2.setEmail("test@gmail.com");
        user2.setPassword("testme");
        user2.setActive(true);
        user2.setRole(admin);
        userService.createUser(user);
        userService.createUser(user2);
        return "home";
    }

    @GetMapping("/error/access-denied")
    public String deniedPage(){
        return "accessDenied";
    }

    @GetMapping("login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {

        String errorMessge = null;
        if (error != null) {
            errorMessge = "Email or Password is incorrect !!";
        }
        if (logout != null) {
            errorMessge = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMessge", errorMessge);
        return "login";
    }
    @GetMapping("/register")
    public String registration(@ModelAttribute("user") User user) {
        return "register";
    }

    @PostMapping("/register")
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) throws ChangeSetPersister.NotFoundException {
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            Optional<Role> role=roleRepository.findById(user.getRole().getId());
            if(role.isPresent()){
                user.setActive(true);
                user.setRole(role.get());
                userService.createUser(user);
                model.addAttribute("successMessage", "User has been registered successfully");
                model.addAttribute("user", new User());
                return "login";
            }else{
                bindingResult
                        .rejectValue("role", "error.user",
                                "Role Not found");
                return "register";
            }
        }
    }


    @GetMapping("/denied")
    public String accessDenied(){
        return "accessDenied";
    }
}
