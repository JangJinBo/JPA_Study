package org.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name ="ORDERS") //예약어인 경우가 많아 ORDERS로 사용한다.
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @Column(name = "MEMBER_ID")
    private Long memberId;

   //@Embedded
    private Member member;

    private LocalDateTime orderDate; //SpringBoot에서는 네이밍 관례를 변경할 수 있다. orderDate -> ORDER_DATE

    @Enumerated(EnumType.STRING)
    private OrderStatus status;




}
