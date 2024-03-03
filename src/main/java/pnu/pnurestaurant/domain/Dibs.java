package pnu.pnurestaurant.domain;

import jakarta.persistence.*;

@Entity
public class Dibs {

    @Id
    @GeneratedValue
    private Long dibsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
