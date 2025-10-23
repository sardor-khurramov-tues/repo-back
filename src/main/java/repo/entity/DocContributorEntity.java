package repo.entity;

import jakarta.persistence.*;
import lombok.*;
import repo.entity.enums.DocRole;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doc_contributor")
public class DocContributorEntity {

    @Id
    @SequenceGenerator(name = "doc_contributor_id_generator", sequenceName = "doc_contributor_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "doc_contributor_id_generator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document")
    private DocumentEntity document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user")
    private AppUserEntity appUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "doc_role")
    private DocRole docRole;

}
