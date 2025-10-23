package repo.dto.document;

import repo.dto.department.DepartmentDto;
import repo.dto.user.AppUserResponseDto;
import repo.entity.enums.DegreeType;
import repo.entity.enums.DocRole;
import repo.entity.enums.DocType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record DocumentDto(
        Long id,
        LocalDateTime createdAt,
        String docKey,
        DocType docType,
        String title,
        String seriesTitle,
        String issn,
        String isbn,
        Long volume,
        Long seriesNumber,
        Long editionNumber,
        String ror,
        DegreeType degree,
        Long firstPage,
        Long lastPage,
        String proceedSubj,
        String docAbstract,
        LocalDate approvalDate,
        LocalDate pubDate,
        Boolean isPublished,
        AppUserResponseDto submitter,
        DepartmentDto department,
        Set<DocContributor> contributorSet
) {
    public record DocContributor(
            Long id,
            AppUserResponseDto appUser,
            DocRole docRole
    ) {}
}
