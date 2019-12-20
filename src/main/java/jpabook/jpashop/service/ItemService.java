package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.dto.BookForm;
import jpabook.jpashop.repository.ItemRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }

    @Transactional
    public void updateItem(Long itemId, BookUpdate form) {
        Book book = (Book) itemRepository.findOne(itemId);
        book.updateBook(form);
    }

    @Getter
    public static class BookUpdate {
        String name;
        int price;
        int stockQuantity;
        String author;
        String isbn;

        @Builder
        public BookUpdate(String name, int price, int stockQuantity, String author, String isbn) {
            this.name = name;
            this.price = price;
            this.stockQuantity = stockQuantity;
            this.author = author;
            this.isbn = isbn;
        }
    }
}
