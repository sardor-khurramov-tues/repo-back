package repo.dto.document.dissertation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import repo.entity.enums.DegreeType;

public record DocDissertationUpdateDto(
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
        String docAbstract
) {}
