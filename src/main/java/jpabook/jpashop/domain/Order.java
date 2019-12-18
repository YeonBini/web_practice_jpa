package jpabook.jpashop.domain;

import jpabook.jpashop.status.DeleveryStatus;
import jpabook.jpashop.status.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // 연관관계 메소드
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 생성 메소드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        Arrays.stream(orderItems)
                .forEach(orderItem -> order.addOrderItem(orderItem));

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    // 비즈니스 로직
    // 주문 취소
    public void cancel() {
        if(delivery.getStatus() == DeleveryStatus.COMPLETE) {
            throw new IllegalStateException("이미 배송이 완료된 상품은 취소를 할 수 없습니다.");
        }

        this.setOrderStatus(OrderStatus.CANCEL);
        this.getOrderItemList().stream()
                .forEach(orderItem -> orderItem.cancel());
    }

    // 조회 로직
    // 주문 금액조회
    public int getTotalPrice() {
        return orderItemList.stream()
                .mapToInt(orderItem -> orderItem.getTotalPrice())
                .sum();

    }
}
