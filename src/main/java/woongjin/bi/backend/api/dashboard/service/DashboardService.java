package woongjin.bi.backend.api.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woongjin.bi.backend.api.dashboard.dto.DashboardRequestDto;
import woongjin.bi.backend.api.dashboard.dto.DashboardResponseDto;
import woongjin.bi.backend.domain.entity.Dashboard;
import woongjin.bi.backend.domain.entity.User;
import woongjin.bi.backend.domain.enums.DashboardStatus;
import woongjin.bi.backend.domain.repository.CompanyRepository;
import woongjin.bi.backend.domain.repository.DashboardRepository;
import woongjin.bi.backend.domain.repository.UserRepository;
import woongjin.bi.backend.exception.ForbiddenException;
import woongjin.bi.backend.util.ResponseCode;
import woongjin.bi.backend.util.SliceResponse;

import java.util.NoSuchElementException;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DashboardService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final DashboardRepository dashboardRepository;

    @Transactional
    public SliceResponse<DashboardResponseDto> readDashboard(User user, Pageable pageable){

        System.out.println("소속 회사: " + user.getRealm().getName());

        //TODO 사용자 Role에 따라 보여지는 대시보드 다르게 하도록 변경하라
        Slice<DashboardResponseDto> slice =
                dashboardRepository.findAllByRealmAndDashboardStatusAndDeletedAtIsNull(user.getRealm(), DashboardStatus.ENABLED, pageable)
                        .map( dashboard -> DashboardResponseDto.of(dashboard));

        return new SliceResponse<>(slice);
    }


    @Transactional
    public ResponseCode createDashboard(User user, DashboardRequestDto dashboardRequestDto) {

        Dashboard dashboard = Dashboard.builder()
                .creator(user)
                .realm(user.getRealm())
                .url(dashboardRequestDto.url())
                .detail(dashboardRequestDto.detail())
                .dashboardStatus(dashboardRequestDto.dashboardStatus())
                .build();

        dashboardRepository.save(dashboard);

        return ResponseCode.CREATE_DASHBOARD_SUCCESS;
    }

    @Transactional
    public ResponseCode modifyDashboard(User user, DashboardRequestDto dashboardRequestDto, Long dashboardId) {

        // TODO user Role에 따라 수정 권한이 있는지 확인하라
        Dashboard dashboard = dashboardRepository.findById(dashboardId).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 대시보드 입니다.")
        );
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~수정할 대시보드 아이디 조회: " + dashboardId);

        if(!user.getRealm().equals(dashboard.getRealm())) throw new ForbiddenException(ResponseCode.DASHBOARD_MODIFY_FORBIDDEN_NOT_OWNER);

        dashboard.setDashboardStatus(dashboardRequestDto.dashboardStatus());
        dashboard.setUrl(dashboardRequestDto.url());
        dashboard.setDetail(dashboardRequestDto.detail());
        dashboard.setModifier(user);

        dashboardRepository.save(dashboard);

        return ResponseCode.MODIFY_DASHBOARD_SUCCESS;

    }

    @Transactional
    public ResponseCode deleteDashboard(Long dashboardId, User user) {

        // TODO user Role에 따라 삭제 권한이 있는지 확인하라
        Dashboard dashboard = dashboardRepository.findById(dashboardId).orElseThrow();
        if(!user.getRealm().equals(dashboard.getRealm())) throw new ForbiddenException(ResponseCode.DASHBOARD_DELETE_FORBIDDEN_NOT_OWNER);

        dashboard.setDashboardStatus(DashboardStatus.DISABLED);
        dashboard.softDelete();

        dashboardRepository.save(dashboard);

        return ResponseCode.DELETE_DASHBOARD_SUCCESS;

    }
}
