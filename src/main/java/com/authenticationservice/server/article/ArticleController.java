package com.authenticationservice.server.article;

import com.authenticationservice.server.article.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @GetMapping("/getAll")
    @PreAuthorize("hasRole('USER')")
    public List<Article> getArticles() { return articleService.getAllArticles();}

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public Article createArticle(@RequestBody String requestBody) {
        System.out.println("REQUEST BODY: " + requestBody);
        JSONObject jsonBody = new JSONObject(requestBody);
//        String date = jsonBody.getString("date");
        String content = jsonBody.getString("content");
        String title = jsonBody.getString("title");
        Article article = new Article();
//        article.setDate(new Date());
        article.setContent(content);
        article.setTitle(title);
        article.setAuthorId("123");
        return articleService.createArticle(article);
    }

    @PatchMapping("/{id}")
    public Article updateArticleFields(@PathVariable String id,@RequestBody Map<String, Object> fields){
        return articleService.updateProductByFields(id,fields);
    }

}
