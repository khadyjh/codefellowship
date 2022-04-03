package com.example.codefellowship.web;


import com.example.codefellowship.domain.ApplicationUser;
import com.example.codefellowship.domain.Post;
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

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ProfileController {

    private final ApplicationUserRepository applicationUserRepository;
    private final PostRepository postRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ProfileController(ApplicationUserRepository applicationUserRepository, PostRepository postRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/myprofile")
    public String gitProfile(Model model){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);
        model.addAttribute("userInfo",applicationUserRepository.findByUsername(username));
        return "profile";
    }

    @GetMapping("/users")
    public String GetUser(Model model){
        model.addAttribute("usersList",applicationUserRepository.findAll());
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username",currentUser);

        return "usersInfo";
    }

    @PostMapping("/users")
    public String postUsers(@RequestParam String username, Model model){
      ApplicationUser applicationUser= applicationUserRepository.findByUsername(username);
      model.addAttribute("users",applicationUser);
      return "usersInfo";
    }

    @PostMapping("/post")
    public String postNewPost(@RequestParam String body,Model model){
        String currentUser=SecurityContextHolder.getContext().getAuthentication().getName();
        ApplicationUser applicationUser=applicationUserRepository.findByUsername(currentUser);
        LocalDateTime time=LocalDateTime.now();
        Post post=new Post(body,time);
        post.setCreatedAt(time);

        List<Post> userPost=applicationUser.getPosts();
        userPost.add(post);
        post.setApplicationUser(applicationUser);
        applicationUser.setPosts(userPost);
        postRepository.save(post);

        model.addAttribute("posts",applicationUser.getPosts());
        model.addAttribute("username",currentUser);
        model.addAttribute("userInfo", applicationUser);
        return "profile";
    }
}
