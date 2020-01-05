package jpabook.jpashop.hangul;

import com.github.kimkevin.hangulparser.HangulParser;
import com.github.kimkevin.hangulparser.HangulParserException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParserTest {

    @Test
    public void test_hangul_parser() throws HangulParserException {
        String movie_title = "영화는 영화다";
        String[] split = movie_title.split("");

        StringBuilder sb = new StringBuilder();
        for(String s : split) {
            if(!s.equals(" ")) {
                List<String> parser = HangulParser.disassemble(s);
                sb.append(parser.get(0));
            } else {
                sb.append(s);
            }
        }

        System.out.println(sb.toString());

        String title = "영";
        List<String> parse = HangulParser.disassemble("영화는");
        System.out.println(Arrays.toString(parse.toArray()));

    }

    @Test
    public void test_hangul_assemble() {
        
    }

}
