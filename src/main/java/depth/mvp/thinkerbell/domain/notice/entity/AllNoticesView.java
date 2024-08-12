package depth.mvp.thinkerbell.domain.notice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Immutable
@Getter
@Table(name = "All_Notices_View")
public class AllNoticesView {

    private Long id;

    @Column(name = "table_name")
    private String tableName;

    @Id
    private String title;
}
