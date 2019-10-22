package edu.mum.mercato.controller;

import edu.mum.mercato.domain.User;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String displayHomePage(){

        return "home";
    }
    @GetMapping("/error/access-denied")
    public String deniedPage(){
        return "accessDenied";
    }

    @GetMapping("login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("login-error")
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
    @GetMapping("register")
    public String registration(@ModelAttribute("user") User user) {
        return "register";
    }

    @PostMapping("register")
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) throws ChangeSetPersister.NotFoundException {
        User userExists;
        userExists = userService.findUserByEmail(user.getEmail());
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
