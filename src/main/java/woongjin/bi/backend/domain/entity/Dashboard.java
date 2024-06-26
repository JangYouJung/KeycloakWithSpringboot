package woongjin.bi.backend.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import woongjin.bi.backend.domain.common.BaseEntity;
import woongjin.bi.backend.domain.enums.DashboardStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Dashboard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String url; // 대시보드 링크

    @NotNull
    private String detail; // 대시보드 설명

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "realm")
    private Realm realm;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator")
    private User creator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modifier")
    private User modifier;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DashboardStatus dashboardStatus;

}
