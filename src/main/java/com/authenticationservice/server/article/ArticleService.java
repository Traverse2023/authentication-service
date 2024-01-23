package com.authenticationservice.server.article;

import com.authenticationservice.server.article.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> getAllArticles() {
        Article article1 = new Article();
//        article1.setDate(new Date());
        article1.setContent("Hello");
        article1.setTitle("Today");
        article1.setAuthorId("123");
        return List.of(article1);
    }

    public List<Article> getAllArticles2() {
//        Article article1 = new Article();
////        article1.setDate(new Date());
//        article1.setContent("Hello");
//        article1.setTitle("Today");
//        article1.setAuthorId("123");
//        return List.of(article1);
        return articleRepository.findAll();
    }

    public Article createArticle(Article article) {
        return articleRepository.insert(article);
    }

    public Article updateProductByFields(String id, Map<String, Object> fields) {
        Optional<Article> existingArticle = articleRepository.findById(id);

        if (existingArticle.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Article.class, key);
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingArticle.get(), value);
            });
            return articleRepository.save(existingArticle.get());
        }
        return null;
    }
}
