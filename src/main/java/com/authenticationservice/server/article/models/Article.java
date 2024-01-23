package com.authenticationservice.server.article.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Article {
    @Id
    private String id;
    private String title;
    private String authorId;
    private String content;
//    private Date date;
}
