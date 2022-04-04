package com.example.codefellowship.web;

import com.example.codefellowship.domain.ApplicationUser;
import com.example.codefellowship.repositries.ApplicationUserRepository;
import com.example.codefellowship.repositries.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class GeneralController {

    private final ApplicationUserRepository applicationUserRepository;
    private final PostRepository postRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public GeneralController(ApplicationUserRepository applicationUserRepository, PostRepository postRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.postRepository = postRepository;
    }

    // root rout
    @GetMapping("/")
    public String getIndex(Model model){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("allpost",postRepository.findAll());
        model.addAttribute("username", username);
        return "index";
    }

    // sign up rout
    @GetMapping("/signup")
    public String getSignUp(){
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView singUpNewUser(@RequestParam String username , @RequestParam String password ,
                                      @RequestParam String firstName, @RequestParam String lastName ,
                                      @RequestParam String bio, @RequestParam String dateOfBirth
                             )
    {
        ApplicationUser applicationUser=new ApplicationUser(username,passwordEncoder.encode(password),firstName,lastName,bio,dateOfBirth);
        applicationUserRepository.save(applicationUser);
        return new RedirectView("/login");
    }

    // home rout
    @GetMapping("/home")
    public String getHome(){
        return "home";
    }

    // login rout
    @GetMapping("/login")
    public String getLogin(Model model){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);
        model.addAttribute("userInfo",applicationUserRepository.findByUsername(username));
        return "login";
    }
    @GetMapping("/hello")
    public String getHelloPage(Model model){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);
        return "hello";
    }

}
