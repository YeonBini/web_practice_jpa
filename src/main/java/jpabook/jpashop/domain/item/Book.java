package jpabook.jpashop.domain.item;

import jpabook.jpashop.dto.BookForm;
import jpabook.jpashop.service.ItemService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import jpabook.jpashop.service.ItemService.BookUpdate;

@Entity
@DiscriminatorValue("B")
@Getter
@Setter
public class Book extends Item{

    private String author;
    private String isbn;

    //== 생성 메소드 ==
    public static Book createBook(BookForm form) {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        return book;
    }

    public void updateBook(BookUpdate form) {
        this.setName(form.getName());
        this.setPrice(form.getPrice());
        this.setStockQuantity(form.getStockQuantity());
        this.setAuthor(form.getAuthor());
        this.setIsbn(form.getIsbn());
    }
}
