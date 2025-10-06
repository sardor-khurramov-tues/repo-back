package repo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repo.constant.ResponseType;
import repo.dto.ResponseDto;
import repo.dto.user.*;
import repo.entity.AppUserEntity;
import repo.entity.DepartmentEntity;
import repo.entity.enums.UserRole;
import repo.exception.CustomBadRequestException;
import repo.repository.AppUserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AppUserService {

    private final DepartmentService departmentService;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final MapperService mapperService;
    private final EmailService emailService;

    public AppUserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (AppUserEntity) authentication.getPrincipal();
    }

    @Transactional
    public ResponseEntity<ResponseDto<AppUserResponseDto>> registerStaff(RegisterStaffDto dto) {
        DepartmentEntity department = departmentService.getDepartmentById(dto.departmentId());

        validateUsername(dto.username());
        validateHemisId(dto.hemisId());

        AppUserEntity user = new AppUserEntity();

        user.setUsername(dto.username());
        user.setPassword(
                passwordEncoder.encode(dto.password())
        );
        user.setUserRole(UserRole.STAFF);
        user.setHemisId(dto.hemisId());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setMiddleName(dto.middleName());
        user.setCreatedAt(LocalDateTime.now());
        user.setIsBlocked(false);
        user.setDepartment(department);

        AppUserEntity savedUser = appUserRepository.save(user);

        return ResponseEntity.ok(ResponseDto.getSuccess(
                mapperService.mapAppUserEntityToDto(savedUser)
        ));
    }

    @Transactional
    public ResponseEntity<ResponseDto<AppUserResponseDto>> registerAuthor(RegisterAuthorDto dto) {
        DepartmentEntity department = Objects.isNull(dto.departmentId()) ? null :
                departmentService.getDepartmentById(dto.departmentId());

        validateUsername(dto.email());
        validateHemisId(dto.hemisId());

        AppUserEntity user = new AppUserEntity();
        user.setUsername(dto.email());

        String newPassword = emailService.generatePasswordAndSendEmail(dto.email());
        user.setPassword(
                passwordEncoder.encode(newPassword)
        );

        user.setUserRole(UserRole.AUTHOR);
        user.setHemisId(dto.hemisId());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setMiddleName(dto.middleName());
        user.setCreatedAt(LocalDateTime.now());
        user.setIsBlocked(false);
        user.setDepartment(department);

        AppUserEntity savedUser = appUserRepository.save(user);

        return ResponseEntity.ok(ResponseDto.getSuccess(
                mapperService.mapAppUserEntityToDto(savedUser)
        ));
    }

    @Transactional
    public ResponseEntity<ResponseDto<Object>> updatePasswordByUser(UpdatePasswordDto dto) {
        AppUserEntity user = getAuthenticatedUser();
        validatePassword(user, dto.oldPassword());

        user.setPassword(
                passwordEncoder.encode(dto.newPassword())
        );
        appUserRepository.save(user);

        return ResponseEntity.ok(ResponseDto.getEmptySuccess());
    }

    @Transactional
    public ResponseEntity<ResponseDto<AppUserResponseDto>> updateAppUser(UpdateAppUserDto dto) {
        AppUserEntity user = getAuthenticatedUser();

        DepartmentEntity department = Objects.isNull(dto.departmentId()) ?
                null :
                departmentService.getDepartmentById(dto.departmentId());
        validateHemisIdOnUpdate(dto.hemisId(), user.getId());

        user.setHemisId(dto.hemisId());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setMiddleName(dto.middleName());
        user.setDepartment(department);

        AppUserEntity savedUser = appUserRepository.save(user);
        return ResponseEntity.ok(ResponseDto.getSuccess(
                mapperService.mapAppUserEntityToDto(savedUser)
        ));
    }

    @Transactional
    public ResponseEntity<ResponseDto<Object>> deleteAppUserByAdmin(Long id) {
        AppUserEntity user = getUserById(id);
        validateDeletion(user);

        appUserRepository.delete(user);
        return ResponseEntity.ok(ResponseDto.getEmptySuccess());
    }

    @Transactional
    public ResponseEntity<ResponseDto<Object>> deleteAuthorByStaff(Long id) {
        AppUserEntity staff = getAuthenticatedUser();
        AppUserEntity author = getUserById(id);

        validateIsAuthor(author);
        validateStaffAndAuthorDepartment(staff, author);

        validateDeletion(author);
        appUserRepository.delete(author);

        return ResponseEntity.ok(ResponseDto.getEmptySuccess());
    }

    @Transactional
    public ResponseEntity<ResponseDto<Object>> updatePasswordByAdmin(Long id, SetPasswordDto dto) {
        AppUserEntity user = getUserById(id);

        user.setPassword(
                passwordEncoder.encode(dto.newPassword())
        );
        appUserRepository.save(user);

        return ResponseEntity.ok(ResponseDto.getEmptySuccess());
    }

    public ResponseEntity<ResponseDto<AppUserResponseDto>> getUserData() {
        return ResponseEntity.ok(ResponseDto.getSuccess(
                mapperService.mapAppUserEntityToDto(getAuthenticatedUser())
        ));
    }

    public ResponseEntity<ResponseDto<FindClientResponseDto>> findUserByAdmin(String key, Long limit, Long page) {
        String searchKey = "%" + key.toLowerCase() + "%";
        long offset = page * limit;

        Long count = appUserRepository.countBySearchKey(searchKey);

        List<AppUserResponseDto> dtoList = appUserRepository.findBySearchKey(searchKey, limit, offset)
                .stream()
                .map(mapperService::mapAppUserEntityToDto)
                .toList();

        long pageCount = count % limit == 0 ? (count / limit) : (count / limit + 1L);

        return ResponseEntity.ok(ResponseDto.getSuccess(
                new FindClientResponseDto(count, pageCount, dtoList)
        ));
    }

    public ResponseEntity<ResponseDto<FindClientResponseDto>> findAuthorByStaff(String key, Long limit, Long page) {
        Long depId = getAuthenticatedUser().getDepartment().getId();

        String searchKey = "%" + key.toLowerCase() + "%";
        long offset = page * limit;

        Long count = appUserRepository.countAuthorBySearchKeyAndDepartment(searchKey, depId);

        List<AppUserResponseDto> dtoList = appUserRepository.findAuthorBySearchKeyAndDepartment(searchKey, depId, limit, offset)
                .stream()
                .map(mapperService::mapAppUserEntityToDto)
                .toList();

        long pageCount = count % limit == 0 ? (count / limit) : (count / limit + 1L);

        return ResponseEntity.ok(ResponseDto.getSuccess(
                new FindClientResponseDto(count, pageCount, dtoList)
        ));
    }

    public ResponseEntity<ResponseDto<Object>> setBlockByAdmin(Long id, SetBlockDto dto) {
        AppUserEntity client = getUserById(id);

        client.setIsBlocked(dto.isBlocked());
        appUserRepository.save(client);

        return ResponseEntity.ok(ResponseDto.getEmptySuccess());
    }

    public ResponseEntity<ResponseDto<Object>> setBlockByStaff(Long id, SetBlockDto dto) {
        AppUserEntity staff = getAuthenticatedUser();
        AppUserEntity author = getUserById(id);

        validateIsAuthor(author);
        validateStaffAndAuthorDepartment(staff, author);

        author.setIsBlocked(dto.isBlocked());
        appUserRepository.save(author);

        return ResponseEntity.ok(ResponseDto.getEmptySuccess());
    }



    private void validateUsername(String phone) {
        if (appUserRepository.existsByUsername(phone))
            throw new CustomBadRequestException(ResponseType.USERNAME_EXISTS);
    }

    private void validateHemisId(String hemisId) {
        if (Objects.nonNull(hemisId) && appUserRepository.existsByHemisId(hemisId))
            throw new CustomBadRequestException(ResponseType.HEMIS_ID_EXISTS);
    }

    private void validateHemisIdOnUpdate(String hemisId, Long id) {
        if (Objects.nonNull(hemisId) && appUserRepository.existsByHemisIdAndIdNot(hemisId, id))
            throw new CustomBadRequestException(ResponseType.HEMIS_ID_EXISTS);
    }

    private void validatePassword(AppUserEntity user, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, user.getPassword()))
            throw new CustomBadRequestException(ResponseType.WRONG_PASSWORD);
    }

    public AppUserEntity getUserById(Long id) {
        try {
            return appUserRepository.findById(id)
                    .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new CustomBadRequestException(ResponseType.NO_USER_WITH_THIS_ID);
        }
    }

    private void validateIsAuthor(AppUserEntity user) {
        if (user.getUserRole().equals(UserRole.AUTHOR))
            throw new CustomBadRequestException(ResponseType.USER_NOT_AUTHOR);
    }

    private void validateStaffAndAuthorDepartment(AppUserEntity staff, AppUserEntity author) {
        try {
            long staffDepId = staff.getDepartment().getId();
            long authorDepId = author.getDepartment().getId();

            if (staffDepId != authorDepId)
                throw new CustomBadRequestException(ResponseType.FALSE_DEPARTMENT);
        } catch (Exception e) {
            throw new CustomBadRequestException(ResponseType.FALSE_DEPARTMENT);
        }
    }

    private void validateDeletion(AppUserEntity user) {
        if (Objects.isNull(user))
            throw new CustomBadRequestException(ResponseType.USER_DELETION_FORBIDDEN);
    }

}
