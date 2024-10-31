package javalugovoytask.dto;

import lombok.*;

import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Book {
    private String title;
    private String author;
    private Date publishedDate;
    private String isbn;
}
