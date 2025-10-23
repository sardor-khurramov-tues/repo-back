package repo.dto.document.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import repo.dto.document.DocContributorSubmitDto;

import java.util.List;

public record DocBookChapterSubmitDto(
        @NotNull
        Long departmentId,
        @NotBlank
        @Size(max = 255)
        String title,
        Long firstPage,
        Long lastPage,
        @Size(max = 5000)
        String docAbstract,
        List<DocContributorSubmitDto> docContributorList
) {}
