package woongjin.bi.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "realm")
public class Realm {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "realm", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<User> employees = new ArrayList<>();

    @OneToMany(mappedBy = "realm", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Dashboard> dashboards = new ArrayList<>();

}
