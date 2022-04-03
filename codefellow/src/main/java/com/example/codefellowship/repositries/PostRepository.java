package com.example.codefellowship.repositries;

import com.example.codefellowship.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post,Integer> {
}
