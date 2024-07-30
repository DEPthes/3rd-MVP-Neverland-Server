package depth.mvp.thinkerbell.domain.dept_info.entity;

import depth.mvp.thinkerbell.domain.common.entity.BaseEntity;
import depth.mvp.thinkerbell.domain.common.entity.Univ;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "Dept_Contact")
public class DeptContact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String campus;
    private String college;
    private String contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "univ_id")
    private Univ univ;

    @Builder
    public DeptContact(String campus, String college, String contact, Univ univ) {
        this.campus = campus;
        this.college = college;
        this.contact = contact;
        this.univ = univ;
    }
}