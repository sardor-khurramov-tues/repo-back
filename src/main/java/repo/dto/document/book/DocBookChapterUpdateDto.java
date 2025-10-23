package repo.dto.document.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DocBookChapterUpdateDto(
        @NotBlank
        @Size(max = 255)
        String title,
        Long firstPage,
        Long lastPage,
        @Size(max = 5000)
        String docAbstract
) {}
