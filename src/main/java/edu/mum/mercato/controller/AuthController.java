package edu.mum.mercato.controller;

import edu.mum.mercato.config.MerkatoUserDetails;
import edu.mum.mercato.domain.Role;
import edu.mum.mercato.domain.User;
import edu.mum.mercato.repository.RoleRepository;
import edu.mum.mercato.service.SecurityService;
import edu.mum.mercato.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/error/access-denied")
    public String deniedPage(){
        return "accessDenied";
    }

    @GetMapping("login")
    public String loginPage(@ModelAttribute("user") User user,Model model) {
        return "login";
    }

    @GetMapping("login-error")
    public String loginPage(@ModelAttribute("user") User user,
                            @RequestParam(value = "error", required = false) String error,
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
    @GetMapping("login-success")
    public String logForward( Model model) {
        MerkatoUserDetails userDetai=securityService.findLoggedInUser();
        Optional<Role> role=roleRepository.findById(userDetai.getRoles().getId());
        if(role.isPresent()){
            if(role.get().getId()==1){
                return "redirect:/admin";
            }else if(role.get().getId()==2){
                return "redirect:/seller";
            }else {
                return "redirect:/products";
            }
        }

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
                userService.save(user);
                securityService.autoLogin(user.getEmail(), user.getPassword());
                if(user.getRole().getId()==1){
                    return "redirect:/admin";
                }else if(user.getRole().getId()==2){
                    return "redirect:/seller";
                }else {
                    return "redirect:/products";
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
