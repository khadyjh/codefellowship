package com.example.codefellowship.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties({"followers","following"})
@Entity
public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String username;

    private String password;

    private String firstName;
    private String lastName;
    private String bio;
    private String dateOfBirth;

    public ApplicationUser(String username, String password, String firstName, String lastName, String bio, String dateOfBirth) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.dateOfBirth = dateOfBirth;
    }

    public ApplicationUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @OneToMany(mappedBy = "applicationUser")
    List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }


    @ManyToMany
    @JoinTable(name = "user_user"
            ,joinColumns = {@JoinColumn(name = "from_id")}
            ,inverseJoinColumns = {@JoinColumn(name = "to_id")})
    List<ApplicationUser> following;

   @ManyToMany(mappedBy = "following" , fetch = FetchType.EAGER)
   List<ApplicationUser> followers;

    public List<ApplicationUser> getFollowing() {
        return following;
    }

    public void setFollowing(List<ApplicationUser> following) {
        this.following = following;
    }

    public List<ApplicationUser> getFollowers() {
        return followers;
    }

    public void setFollowers(List<ApplicationUser> followers) {
        this.followers = followers;
    }


    @Override
    public String toString() {
        return "ApplicationUser{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bio='" + bio + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", posts=" + posts +
                '}';
    }
}
