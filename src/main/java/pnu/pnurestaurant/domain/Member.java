package pnu.pnurestaurant.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String memberId;

    private String password;
    private String email;
    private String role;

    public Member() {
    }

    private Member(Long id, String memberId, String password, String email, String role) {
        this.id = id;
        this.memberId = memberId;
        this.password = password;
        this.email = email;
        this.role = role;
    }



}
