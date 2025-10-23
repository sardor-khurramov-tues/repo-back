package repo.dto.document.conference;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DocConfProceedUpdateDto(
        @NotBlank
        @Size(max = 255)
        String title,
        @Size(min = 13, max = 13)
        @Pattern(regexp = "^97[89]-\\d{1,5}-\\d{1,7}-\\d{1,7}-\\d$")
        String isbn,
        @Size(max = 255)
        String proceedSubj,
        @Size(max = 5000)
        String docAbstract
) {}
