package woongjin.bi.backend.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import woongjin.bi.backend.domain.entity.Dashboard;
import woongjin.bi.backend.domain.entity.Realm;
import woongjin.bi.backend.domain.enums.DashboardStatus;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

    Slice<Dashboard> findAllByRealmAndDashboardStatusAndDeletedAtIsNull(Realm realm, DashboardStatus dashboardStatus, Pageable pageable);

}
