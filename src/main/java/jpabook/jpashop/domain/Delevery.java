package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delevery {

    @Id @GeneratedValue
    @Column(name = "delevery_id")
    private Long id;

    @OneToOne(mappedBy = "delevery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeleveryStatus status;

}
