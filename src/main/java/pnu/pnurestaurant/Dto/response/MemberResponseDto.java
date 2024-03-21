package pnu.pnurestaurant.Dto.response;

import lombok.Data;
import pnu.pnurestaurant.domain.Member;

@Data
public class MemberResponseDto {

    private Long id;
    private String memberName;
    private String email;

    public MemberResponseDto() {
    }

    public MemberResponseDto(Member member){
        this.id = member.getId();
        this.memberName = member.getMemberName();
        this.email = member.getEmail();
    }
}
