package jpabook.jpashop.n_plus_1;

import jpabook.jpashop.domain.n_plus_1.Academy;
import jpabook.jpashop.domain.n_plus_1.AcademyRepository;
import jpabook.jpashop.domain.n_plus_1.AcademyService;
import jpabook.jpashop.domain.n_plus_1.Subject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AcademyServiceTest {

    @Autowired
    private AcademyRepository repository;

    @Autowired
    private AcademyService service;

    @After
    public void cleanAll() {
        repository.deleteAll();
    }

    @Before
    public void setUp() {
        System.out.println(1);
        List<Academy> academies = new ArrayList<>();
        System.out.println(2);
        for(int i=1; i<=10; i++) {
            Academy academy = Academy.builder()
                                .name("왕십리 " + i)
                                .build();
            System.out.println(3);
            academy.addSubject(Subject.builder().name("자바 웹 개발 " + i).build());
            System.out.println(4);
            academies.add(academy);
            System.out.println(5);
        }
        repository.saveAll(academies);
        System.out.println(6);
    }

    @Test
    public void Academy_여러개_조회_시_subject_n_plus_1() {
        List<String> all = service.findAllSubjectNames();
        assertThat(all.size(), is(10));
    }

}