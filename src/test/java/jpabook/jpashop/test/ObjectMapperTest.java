package jpabook.jpashop.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpabook.jpashop.domain.Member;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ObjectMapperTest {

    @Test
    public void basic_json_to_string() throws IOException {
        Member member = new Member();
        member.setName("bin");

        ObjectMapper objectMapper = new ObjectMapper();

        // file write
        objectMapper.writeValue(new File("src/test/java/jpabook/jpashop/test/test.txt"), member);

        // toString
        String memberMapping = objectMapper.writeValueAsString(member);
        System.out.println(memberMapping);

    }

    @Test
    public void basic_json_to_object() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Member member = objectMapper.readValue(new File("src/test/java/jpabook/jpashop/test/test.txt"), Member.class);

        assertEquals(member.getName(), "bin");

    }
}
