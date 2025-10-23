package repo.controller.author;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repo.constant.Endpoint;
import repo.dto.ResponseDto;
import repo.dto.document.DocumentDto;
import repo.dto.document.conference.DocConfPaperSubmitDto;
import repo.dto.document.conference.DocConfPaperUpdateDto;
import repo.dto.document.conference.DocConfProceedSubmitDto;
import repo.dto.document.conference.DocConfProceedUpdateDto;
import repo.service.DocConferenceService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.AUTHOR + Endpoint.DOCUMENT)
public class AuthorDocConferenceController {

    private final DocConferenceService docConferenceService;

    @PostMapping(path = Endpoint.CONFERENCE_PROCEEDINGS, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Long>> createProceedings(
            @RequestBody @Valid DocConfProceedSubmitDto dto
    ) {
        return docConferenceService.createProceedings(dto);
    }

    @PutMapping(path = Endpoint.ID_PATH_VARIABLE + Endpoint.CONFERENCE_PROCEEDINGS, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<DocumentDto>> updateProceedings(
            @PathVariable @Valid Long id,
            @RequestBody @Valid DocConfProceedUpdateDto dto
    ) {
        return docConferenceService.updateProceedings(id, dto);
    }

    @PostMapping(path = Endpoint.CONFERENCE_PAPER, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Long>> createPaper(
            @RequestBody @Valid DocConfPaperSubmitDto dto
    ) {
        return docConferenceService.createPaper(dto);
    }

    @PutMapping(path = Endpoint.ID_PATH_VARIABLE + Endpoint.CONFERENCE_PAPER, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<DocumentDto>> updatePaper(
            @PathVariable @Valid Long id,
            @RequestBody @Valid DocConfPaperUpdateDto dto
    ) {
        return docConferenceService.updatePaper(id, dto);
    }

}
