package com.team.bookstore.Dtos.Responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterResponse {
    int id;
    int chapterIndex;
    int bookId;
    byte[] sourcefile;
}
