package pnu.pnurestaurant.domain;


import jakarta.persistence.*;

@Entity
public class Review extends TimeStamp{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private Float rating;

    private String content;

    private String reviewPictureUrl;
}
