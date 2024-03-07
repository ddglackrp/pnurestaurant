package pnu.pnurestaurant.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberJoinDto {

    @NotEmpty
    private String memberId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String email;
}
