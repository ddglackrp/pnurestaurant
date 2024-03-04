package pnu.pnurestaurant.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

}
