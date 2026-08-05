package org.example.thssr.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.example.thssr.model.entity.BookEntity;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookFormDTO {
    //    @NotBlank(message = "제목은 필수입니다")
//    @Size(min = 1, max = 50, message = "제목은 1~50자 이내로 입력해주세요")
    @NotBlank
//    @Size(min = 1, max = 50)
    @Size(min = 1, max = 50, message = "{Size.bookForm.title2}")
    private String title;
    // ...
    @NotEmpty(message = "저자는 필수입니다")
    private String author;
    //    private int price;
    @NotNull(message = "가격은 필수입니다")
    @PositiveOrZero(message = "가격은 0 이상이어야 합니다")
//    @Min()
//    @Max(value = 1_000_000, message = "가격은 100만원 이하여야 합니다")
    @Max(value = 1_000_000)
    private Integer price;
    private int discountPrice;
    private Boolean isAvailable;
    private String category;

    public BookEntity toEntity() {
        return BookEntity.builder()
                .title(title)
                .author(author)
                .price(price)
                .discountPrice(discountPrice)
                .isAvailable(isAvailable)
                .category(category)
                .build();
    }

    public static BookFormDTO fromEntity(BookEntity entity) {
        return BookFormDTO.builder()
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .price(entity.getPrice())
                .discountPrice(entity.getDiscountPrice())
                .isAvailable(entity.isAvailable())
                .category(entity.getCategory())
                .build();
    }
}
