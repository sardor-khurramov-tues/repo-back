package repo.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repo.constant.Endpoint;
import repo.dto.ResponseDto;
import repo.dto.user.*;
import repo.service.AppUserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.STAFF + Endpoint.AUTHOR)
public class StaffAuthorController {

    private final AppUserService appUserService;

    @GetMapping(path = Endpoint.FIND_BY, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<FindClientResponseDto>> findUserByAdmin(
            @Parameter(allowEmptyValue = true)
            @RequestParam @Valid @Size(max = 31) String key,
            @RequestParam @Valid @NotNull Long limit,
            @RequestParam @Valid @NotNull Long page
    ) {
        return appUserService.findAuthorByStaff(key, limit, page);
    }

    @DeleteMapping(path = Endpoint.ID_PATH_VARIABLE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Object>> deleteAuthorByStaff(
            @PathVariable @Valid Long id
    ) {
        return appUserService.deleteAuthorByStaff(id);
    }

    @PutMapping(path = Endpoint.ID_PATH_VARIABLE + Endpoint.BLOCK, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Object>> setBlockByStaff(
            @PathVariable @Valid Long id,
            @RequestBody @Valid SetBlockDto dto
    ) {
        return appUserService.setBlockByStaff(id, dto);
    }

}
