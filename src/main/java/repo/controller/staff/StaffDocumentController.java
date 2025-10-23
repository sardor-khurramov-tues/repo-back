package repo.controller.staff;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repo.constant.Endpoint;
import repo.dto.ResponseDto;
import repo.dto.document.DocumentDto;
import repo.dto.document.DocumentListDto;
import repo.service.DocumentService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.STAFF + Endpoint.DOCUMENT)
public class StaffDocumentController {

    private final DocumentService documentService;

    @PostMapping(path = Endpoint.ID_PATH_VARIABLE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Object>> publishDocument(
            @PathVariable @Valid Long id
    ) {
        return documentService.publishDocument(id);
    }

    @DeleteMapping(path = Endpoint.ID_PATH_VARIABLE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Object>> deleteDocumentByStaff(
            @PathVariable @Valid Long id
    ) {
        return documentService.deleteDocumentByStaff(id);
    }

    @GetMapping(path = Endpoint.ID_PATH_VARIABLE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<DocumentDto>> getDocumentByIdAsStaff(
            @PathVariable @Valid Long id
    ) {
        return documentService.getDocumentByIdAsStaff(id);
    }

    @GetMapping(path = Endpoint.FIND_BY, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<DocumentListDto>> findDocumentBySubmitter(
            @Parameter(allowEmptyValue = true)
            @RequestParam @Valid @Size(max = 255) String key,
            @RequestParam @Valid @NotNull Boolean isPublished,
            @RequestParam @Valid @NotNull Long limit,
            @RequestParam @Valid @NotNull Long page
    ) {
        return documentService.findDocumentByStaff(key, isPublished, limit, page);
    }

}
