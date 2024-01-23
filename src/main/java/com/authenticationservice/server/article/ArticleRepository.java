package com.authenticationservice.server.article;

import com.authenticationservice.server.article.models.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, String> {
}
