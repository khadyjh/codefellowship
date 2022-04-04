package com.example.codefellowship.repositries;

import com.example.codefellowship.domain.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post,Integer> {
    List<Post> findByApplicationUserId(int id);
}
