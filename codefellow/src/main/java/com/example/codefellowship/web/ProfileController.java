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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    // profile page
    @GetMapping("/myprofile")
    public String gitProfile(Model model){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);
        model.addAttribute("userInfo",applicationUserRepository.findByUsername(username));
        return "profile";
    }

    // search for user rout
    @GetMapping("/users")
    public String GetUser(Model model){
        model.addAttribute("usersList",applicationUserRepository.findAll());
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username",currentUser);

        model.addAttribute("currentUser",currentUser);

        return "usersInfo";
    }

    @PostMapping("/users")
    public String postUsers(@RequestParam String username, Model model){
      ApplicationUser applicationUser= applicationUserRepository.findByUsername(username);
      if(applicationUser==null){
          return "userNotFound";
      }
      model.addAttribute("userPost",postRepository.findByApplicationUserId(applicationUser.getId()));
      model.addAttribute("users",applicationUser);

        String currentUser= SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("user",currentUser);
      return "usersInfo";
    }

    // create post
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


    // follow rout
    @PostMapping("/follow")
    public String followUser( Model model, @RequestParam int id){
        //logged in user
        String currentUser=SecurityContextHolder.getContext().getAuthentication().getName();
        ApplicationUser applicationUser=applicationUserRepository.findByUsername(currentUser);
        // user to follow
       ApplicationUser userToFollow=applicationUserRepository.findById(id).orElseThrow();

       applicationUser.getFollowers().add(userToFollow);
       userToFollow.getFollowing().add(applicationUser);

       applicationUserRepository.save(applicationUser);
       applicationUserRepository.save(userToFollow);

        model.addAttribute("userPost",postRepository.findByApplicationUserId(applicationUser.getId()));
        model.addAttribute("users",applicationUser);
       return "usersInfo";

    }

    // feeds
    @GetMapping("/feed")
    public String getFeed(Model model){
        String currentUser=SecurityContextHolder.getContext().getAuthentication().getName();
        ApplicationUser loggedInUser=applicationUserRepository.findByUsername(currentUser);

        List<ApplicationUser> allFollow=loggedInUser.getFollowers();

      List<Post> allUserPost =new ArrayList<>();

        for (ApplicationUser user :
                allFollow) {
            List<Post> postList=user.getPosts();
            allUserPost.addAll(postList);
        }

        model.addAttribute("posts",allUserPost);
        model.addAttribute("username",currentUser);

        return "feed";
    }
    
    @PostMapping("/account")
    public String getAccount(@RequestParam int id,Model model){

        ApplicationUser user=applicationUserRepository.findById(id).orElseThrow();
        model.addAttribute("users",user);
        model.addAttribute("userPost",postRepository.findByApplicationUserId(id));
        String currentUser= SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("currentUser",currentUser);


        return "usersInfo";
    }


}
