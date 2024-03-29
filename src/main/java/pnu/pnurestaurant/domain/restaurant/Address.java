package pnu.pnurestaurant.domain.restaurant;


import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String latitude; // 위도

    private String longitude; // 경도

    private String locationId;

    public Address() {
    }

    public Address(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Builder
    public Address(String locationId) {
        this.locationId = locationId;
    }
}
