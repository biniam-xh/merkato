package edu.mum.mercato.controller;

import edu.mum.mercato.domain.Role;
import edu.mum.mercato.repository.RoleRepository;
import edu.mum.mercato.service.SecurityService;
import edu.mum.mercato.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import edu.mum.mercato.domain.User;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.management.relation.Role;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private SecurityService securityService;

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
                securityService.autoLogin(user.getEmail(), user.getPassword());
                if(user.getRole().getId()==1){
                    return "redirect:/admin";
                }else if(user.getRole().getId()==2){
                    return "redirect:/seller";
                }else {
                    return "redirect:/buyer";
                }
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
