package pnu.pnurestaurant.domain;

import jakarta.persistence.*;
import lombok.*;

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
