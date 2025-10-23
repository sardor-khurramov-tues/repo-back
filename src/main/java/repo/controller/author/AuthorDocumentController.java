package repo.controller.author;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repo.constant.Endpoint;
import repo.dto.ResponseDto;
import repo.dto.document.*;
import repo.service.DocumentService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.AUTHOR + Endpoint.DOCUMENT)
public class AuthorDocumentController {

    private final DocumentService documentService;

    @PutMapping(path = Endpoint.ID_PATH_VARIABLE + Endpoint.CONTRIBUTOR, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Object>> addContributor(
            @PathVariable @Valid Long id,
            @RequestBody @Valid DocContributorSubmitDto dto
    ) {
        return documentService.addContributor(id, dto);
    }

    @DeleteMapping(path = Endpoint.CONTRIBUTOR + Endpoint.ID_PATH_VARIABLE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Object>> removeContributor(
            @PathVariable @Valid Long id
    ) {
        return documentService.removeContributor(id);
    }

    @DeleteMapping(path = Endpoint.ID_PATH_VARIABLE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Object>> deleteDocumentBySubmitter(
            @PathVariable @Valid Long id
    ) {
        return documentService.deleteDocumentBySubmitter(id);
    }

    @GetMapping(path = Endpoint.ID_PATH_VARIABLE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<DocumentDto>> getDocumentByIdAsSubmitter(
            @PathVariable @Valid Long id
    ) {
        return documentService.getDocumentByIdAsSubmitter(id);
    }

    @GetMapping(path = Endpoint.FIND_BY, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<DocumentListDto>> findDocumentBySubmitter(
            @Parameter(allowEmptyValue = true)
            @RequestParam @Valid @Size(max = 255) String key,
            @RequestParam @Valid @NotNull Long limit,
            @RequestParam @Valid @NotNull Long page
    ) {
        return documentService.findDocumentBySubmitter(key, limit, page);
    }

}
