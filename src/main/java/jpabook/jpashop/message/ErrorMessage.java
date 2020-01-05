package jpabook.jpashop.message;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorMessage {
    private String status;
    private String message;
}
