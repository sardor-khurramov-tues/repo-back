package repo.dto.document;

import repo.entity.enums.DocType;

import java.time.LocalDateTime;
import java.util.List;

public record DocumentListDto(
        Long totalCount,
        Long pageCount,
        List<DocumentDto> documentList
) {
    public record DocumentDto (
            Long id,
            LocalDateTime createdAt,
            DocType docType,
            String title,
            String subtitle,
            Boolean isPublished,
            LocalDateTime publishedAt
    ) {}
}
