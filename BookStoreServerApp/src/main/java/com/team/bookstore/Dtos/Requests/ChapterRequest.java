package com.team.bookstore.Dtos.Requests;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Named;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterRequest {
    int chapterIndex;
    int bookId;
}
