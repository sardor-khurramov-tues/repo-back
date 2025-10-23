package repo.dto.document.dissertation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import repo.dto.document.DocContributorSubmitDto;
import repo.entity.enums.DegreeType;

import java.util.List;

public record DocDissertationSubmitDto(
        @NotNull
        Long departmentId,
        @NotBlank
        @Size(max = 255)
        String title,
        @NotBlank
        @Size(min = 9, max = 9)
        @Pattern(regexp = "^[a-zA-Z0-9]{9}$")
        String ror,
        DegreeType degreeType,
        @Size(min = 13, max = 13)
        @Pattern(regexp = "^97[89]-\\d{1,5}-\\d{1,7}-\\d{1,7}-\\d$")
        String isbn,
        @Size(max = 5000)
        String docAbstract,
        List<DocContributorSubmitDto> docContributorList
) {}
