package repo.entity;

import jakarta.persistence.*;
import lombok.*;
import repo.entity.enums.DegreeType;
import repo.entity.enums.DocType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "document")
public class DocumentEntity {

    @Id
    @SequenceGenerator(name = "document_id_generator", sequenceName = "document_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "document_id_generator")
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "doc_key")
    private String docKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "doc_type")
    private DocType docType;

    private String title;

    @Column(name = "series_title")
    private String seriesTitle;

    private String issn;

    private String isbn;

    private Long volume;

    @Column(name = "series_number")
    private Long seriesNumber;

    @Column(name = "edition_number")
    private Long editionNumber;

    private String ror;

    @Enumerated(EnumType.STRING)
    private DegreeType degree;

    @Column(name = "first_page")
    private Long firstPage;

    @Column(name = "last_page")
    private Long lastPage;

    @Column(name = "proceed_subj")
    private String proceedSubj;

    @Column(name = "doc_abstract")
    private String docAbstract;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @Column(name = "pub_date")
    private LocalDate pubDate;

    @Column(name = "is_published")
    private Boolean isPublished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitter")
    private AppUserEntity submitter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department")
    private DepartmentEntity department;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "document")
    private Set<DocContributorEntity> contributorSet;

}
