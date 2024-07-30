package depth.mvp.thinkerbell.domain.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @Column(updatable = false)
    protected String title;

    @Column(updatable = false)
    protected String url;

    @Column(updatable=false)
    protected LocalDate pubDate;
}