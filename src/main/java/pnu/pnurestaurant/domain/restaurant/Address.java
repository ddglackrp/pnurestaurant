package pnu.pnurestaurant.domain.restaurant;


import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String latitude; // 위도

    private String longitude; // 경도
}
