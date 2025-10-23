package repo.controller.author;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repo.constant.Endpoint;
import repo.dto.ResponseDto;
import repo.dto.document.DocumentDto;
import repo.dto.document.dissertation.DocDissertationSubmitDto;
import repo.dto.document.dissertation.DocDissertationUpdateDto;
import repo.service.DocDissertationService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.AUTHOR + Endpoint.DOCUMENT)
public class AuthorDocDissertationController {

    private final DocDissertationService docDissertationService;

    @PostMapping(path = Endpoint.DISSERTATION, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Long>> create(
            @RequestBody @Valid DocDissertationSubmitDto dto
    ) {
        return docDissertationService.create(dto);
    }

    @PutMapping(path = Endpoint.ID_PATH_VARIABLE + Endpoint.DISSERTATION, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<DocumentDto>> update(
            @PathVariable @Valid Long id,
            @RequestBody @Valid DocDissertationUpdateDto dto
    ) {
        return docDissertationService.update(id, dto);
    }

}
