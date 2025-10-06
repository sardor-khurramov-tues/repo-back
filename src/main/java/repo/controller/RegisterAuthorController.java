package repo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repo.constant.Endpoint;
import repo.dto.ResponseDto;
import repo.dto.user.AppUserResponseDto;
import repo.dto.user.RegisterAuthorDto;
import repo.service.AppUserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.REGISTER + Endpoint.AUTHOR)
public class RegisterAuthorController {

    private final AppUserService appUserService;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<AppUserResponseDto>> updateAuthorByStaff(
            @RequestBody @Valid RegisterAuthorDto dto
    ) {
        return appUserService.registerAuthor(dto);
    }

}
