package woongjin.bi.backend.api.dashboard.dto;

import lombok.Builder;
import woongjin.bi.backend.domain.enums.DashboardStatus;

@Builder
public record DashboardRequestDto(
    String url,
    String detail,
    DashboardStatus dashboardStatus
) {
}
