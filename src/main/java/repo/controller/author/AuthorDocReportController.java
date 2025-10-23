package repo.controller.author;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repo.constant.Endpoint;
import repo.dto.ResponseDto;
import repo.dto.document.DocumentDto;
import repo.dto.document.report.DocReportSubmitDto;
import repo.dto.document.report.DocReportUpdateDto;
import repo.service.DocReportService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.AUTHOR + Endpoint.DOCUMENT)
public class AuthorDocReportController {

    private final DocReportService docReportService;

    @PostMapping(path = Endpoint.REPORT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Long>> create(
            @RequestBody @Valid DocReportSubmitDto dto
    ) {
        return docReportService.create(dto);
    }

    @PutMapping(path = Endpoint.ID_PATH_VARIABLE + Endpoint.REPORT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<DocumentDto>> update(
            @PathVariable @Valid Long id,
            @RequestBody @Valid DocReportUpdateDto dto
    ) {
        return docReportService.update(id, dto);
    }

}
