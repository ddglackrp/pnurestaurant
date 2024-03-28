package pnu.pnurestaurant.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberRequestDto {

    @NotEmpty
    private String memberId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String email;
}
