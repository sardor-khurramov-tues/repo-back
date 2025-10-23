package repo.controller.author;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repo.constant.Endpoint;
import repo.dto.ResponseDto;
import repo.dto.document.DocumentDto;
import repo.dto.document.book.DocBookChapterSubmitDto;
import repo.dto.document.book.DocBookChapterUpdateDto;
import repo.dto.document.book.DocBookSubmitDto;
import repo.dto.document.book.DocBookUpdateDto;
import repo.service.DocBookService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.AUTHOR + Endpoint.DOCUMENT)
public class AuthorDocBookController {

    private final DocBookService docBookService;

    @PostMapping(path = Endpoint.BOOK, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Long>> createBook(
            @RequestBody @Valid DocBookSubmitDto dto
    ) {
        return docBookService.createBook(dto);
    }

    @PutMapping(path = Endpoint.ID_PATH_VARIABLE + Endpoint.BOOK, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<DocumentDto>> updateBook(
            @PathVariable @Valid Long id,
            @RequestBody @Valid DocBookUpdateDto dto
    ) {
        return docBookService.updateBook(id, dto);
    }

    @PostMapping(path = Endpoint.BOOK_CHAPTER, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Long>> createBookChapter(
            @RequestBody @Valid DocBookChapterSubmitDto dto
    ) {
        return docBookService.createBookChapter(dto);
    }

    @PutMapping(path = Endpoint.ID_PATH_VARIABLE + Endpoint.BOOK_CHAPTER, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<DocumentDto>> updateBookChapter(
            @PathVariable @Valid Long id,
            @RequestBody @Valid DocBookChapterUpdateDto dto
    ) {
        return docBookService.updateBookChapter(id, dto);
    }

}
