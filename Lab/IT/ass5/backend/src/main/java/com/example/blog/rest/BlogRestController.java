package com.example.blog.rest;

import com.example.blog.entity.Blog;
import com.example.blog.entity.User;
import com.example.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogRestController {

    private final BlogService blogService;

    @GetMapping("/all")
    public List<Blog> getAllBlogs() {
        List<Blog> blogs = blogService.findAll();
        return blogs;
    }

    @GetMapping("/{blogId}")
    public Blog getBlog(@PathVariable int blogId) {
        return blogService.findById(blogId);
    }

    @PostMapping("")
    public Optional<Blog> addBlog(@RequestBody Blog blog) {
        // Current logged in user is the author of the blog
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return blogService.createBlog(blog.getTitle(), blog.getContent(), user.getUsername());
    }

    @PutMapping("/{blogId}")
    public Boolean updateBlog(@PathVariable int blogId, @RequestBody Blog blog) {
        try {
            blogService.updateBlog(blogId, blog.getTitle(), blog.getContent());
            return true;
        }catch (Exception e) {
            return false;
        }
    }


}
