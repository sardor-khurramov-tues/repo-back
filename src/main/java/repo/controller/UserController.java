package repo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repo.constant.Endpoint;
import repo.dto.ResponseDto;
import repo.dto.user.AppUserResponseDto;
import repo.dto.user.UpdateAppUserDto;
import repo.dto.user.UpdatePasswordDto;
import repo.service.AppUserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.USER)
public class UserController {

    private final AppUserService appUserService;

    @PutMapping(path = Endpoint.PASSWORD, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Object>> updatePasswordByUser(
            @RequestBody @Valid UpdatePasswordDto dto
    ) {
        return appUserService.updatePasswordByUser(dto);
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<AppUserResponseDto>> updateAppUserByAdmin(
            @RequestBody @Valid UpdateAppUserDto dto
    ) {
        return appUserService.updateAppUser(dto);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<AppUserResponseDto>> getUserData() {
        return appUserService.getUserData();
    }

}
