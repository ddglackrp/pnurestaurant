package pnu.pnurestaurant.domain;

import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public class TimeStamp {
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createMemberName;
    private String updateMemberName;

}
