package org.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Embeddable
public class Member {

    @Id @GeneratedValue //생략하면 Auto전략
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;


}
