package repo.controller.admin;

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
import repo.entity.enums.UserRole;
import repo.service.AppUserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.ADMIN + Endpoint.USER)
public class AdminUserController {

    private final AppUserService appUserService;

    @GetMapping(path = Endpoint.FIND_BY, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<FindClientResponseDto>> findUserByAdmin(
            @Parameter(allowEmptyValue = true)
            @RequestParam @Valid @Size(max = 31) String key,
            @RequestParam(required = false) @Valid UserRole userRole,
            @RequestParam @Valid @NotNull Long limit,
            @RequestParam @Valid @NotNull Long page
    ) {
        return appUserService.findUserByAdmin(key, userRole, limit, page);
    }

    @PostMapping(path = Endpoint.REGISTER + Endpoint.STAFF, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<AppUserResponseDto>> registerStaff(
            @RequestBody @Valid RegisterStaffDto dto
    ) {
        return appUserService.registerStaff(dto);
    }

    @DeleteMapping(path = Endpoint.ID_PATH_VARIABLE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Object>> deleteAppUserByAdmin(
            @PathVariable @Valid Long id
    ) {
        return appUserService.deleteAppUserByAdmin(id);
    }

    @PutMapping(path = Endpoint.ID_PATH_VARIABLE + Endpoint.BLOCK, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Object>> setBlockByAdmin(
            @PathVariable @Valid Long id,
            @RequestBody @Valid SetBlockDto dto
    ) {
        return appUserService.setBlockByAdmin(id, dto);
    }

    @PutMapping(path = Endpoint.ID_PATH_VARIABLE + Endpoint.PASSWORD, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Object>> updatePasswordByAdmin(
            @PathVariable @Valid Long id,
            @RequestBody @Valid SetPasswordDto dto
    ) {
        return appUserService.updatePasswordByAdmin(id, dto);
    }

}
