package woongjin.bi.backend.api.dashboard.dto;

import lombok.Builder;
import lombok.Getter;
import woongjin.bi.backend.domain.entity.Dashboard;

import java.time.LocalDateTime;

@Builder
@Getter
public class DashboardResponseDto {

    private Long id;
    private String detail;
    private String url;
    private LocalDateTime createdAt;
    private String managerFirstName;
    private String managerLastName;

    // TODO Role에 따라 can_edit 주어질 수 있도록 수정하라
    // private boolean can_edit;


    public static DashboardResponseDto of(Dashboard dashboard){

        return DashboardResponseDto.builder()
                .id(dashboard.getId())
                .detail(dashboard.getDetail())
                .url(dashboard.getUrl())
                .createdAt(dashboard.getCreatedAt())
                .managerFirstName(dashboard.getCreator().getFirstName())
                .managerLastName(dashboard.getCreator().getLastName())
                .build();

    }
}
