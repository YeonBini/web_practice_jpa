package jpabook.jpashop.service;

import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private EntityManager em;

    @Test
    public void item_crud_test() {
        // given
        Item item = new Book();
        item.setName("jpa");
        item.addStock(10);

        // when
        itemService.saveItem(item);
        em.flush();

        item.subtractStock(5);
        itemService.saveItem(item);
        em.flush();

        // then
//        assertEquals(item.getId(), itemService.findOne(1L).getId());
    }

    @Test(expected = NotEnoughStockException.class)
    public void NotEnoughStockException_Test() {
        // given
        Item item = new Book();
        item.setName("jpa");
        item.addStock(10);

        // when
        item.subtractStock(15);

        // then
        fail("lack of stock");
    }

}