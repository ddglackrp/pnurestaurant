package pnu.pnurestaurant.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String memberName;

    private String password;
    private String email;
    private String role;

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    public Member() {
    }

    @Builder
    public Member(String memberName, String password, String email, String role) {
        this.memberName = memberName;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
