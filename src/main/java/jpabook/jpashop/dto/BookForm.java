package jpabook.jpashop.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BookForm {

    private Long id;
    @NotEmpty(message = "제목명은 필수값입니다.")
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

}
