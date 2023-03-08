package com.restfulWebServices.in28minutes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restfulWebServices.in28minutes.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>
{

}
