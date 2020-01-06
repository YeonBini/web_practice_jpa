package jpabook.jpashop.api;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import jpabook.jpashop.status.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * XtoOne 관계(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        orders.stream().forEach(o -> {
            o.getMember().getName(); // lazy 강제 초기화
            o.getDelivery().getStatus(); // lazy 강제 초기
        });
        return orders;
    }

    @GetMapping("/api/v2/simple-orders")
    public Result ordersV2() {
        // order 2개 -> query 5개
        // N + 1 문제 -> 1(주문) + 회원 N(2) + 배송 N(2)

        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());

        List<SimpleOrderDto> collect = orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/v3/simple-orders")
    public Result ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberAndAddress();

        List<SimpleOrderDto> collect = orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/v4/simple-orders")
    public Result ordersV4() {
        List<OrderSimpleQueryDto> orderDtos = orderSimpleQueryRepository.findOrderDtos();
        return new Result(orderDtos);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getOrderStatus();
            address = order.getDelivery().getAddress();
        }
    }

}
