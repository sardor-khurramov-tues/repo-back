package repo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repo.constant.ResponseType;
import repo.dto.ResponseDto;
import repo.dto.document.DocContributorSubmitDto;
import repo.dto.document.DocumentDto;
import repo.dto.document.DocumentListDto;
import repo.entity.AppUserEntity;
import repo.entity.DocContributorEntity;
import repo.entity.DocumentEntity;
import repo.entity.enums.DocRole;
import repo.exception.CustomBadRequestException;
import repo.repository.DocContributorRepository;
import repo.repository.DocumentRepository;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentService {

    private final AppUserService appUserService;
    private final DocumentRepository documentRepository;
    private final DocContributorRepository docContributorRepository;
    private final MapperService mapperService;

    private static final Integer MAX_NON_PUBLISHED_DOC_LIMIT = 5;

    @Transactional
    public ResponseEntity<ResponseDto<Object>> addContributor(Long id, DocContributorSubmitDto dto) {
        DocumentEntity document = getDocumentById(id);
        validateDocIsNotPublished(document);

        AppUserEntity submitter = appUserService.getAuthenticatedUser();
        validateDocSubmitter(document, submitter);

        validateAddContributor(document.getContributorSet(), dto);
        DocContributorEntity contributor = createContributor(dto, document);

        docContributorRepository.save(contributor);
        return ResponseEntity.ok(ResponseDto.getEmptySuccess());
    }

    @Transactional
    public ResponseEntity<ResponseDto<Object>> removeContributor(Long id) {
        DocContributorEntity contributor = getDocContributorById(id);

        DocumentEntity document = contributor.getDocument();
        validateDocIsNotPublished(document);

        AppUserEntity submitter = appUserService.getAuthenticatedUser();
        validateDocSubmitter(document, submitter);

        validateNonSubmitterDeletion(contributor, submitter);

        docContributorRepository.delete(contributor);
        return ResponseEntity.ok(ResponseDto.getEmptySuccess());
    }

    @Transactional
    public ResponseEntity<ResponseDto<Object>> deleteDocumentBySubmitter(Long id) {
        DocumentEntity document = getDocumentById(id);
        validateDocIsNotPublished(document);

        AppUserEntity submitter = appUserService.getAuthenticatedUser();
        validateDocSubmitter(document, submitter);

        safeDeleteDocument(document);
        return ResponseEntity.ok(ResponseDto.getEmptySuccess());
    }

    @Transactional
    public ResponseEntity<ResponseDto<DocumentDto>> getDocumentByIdAsSubmitter(Long id) {
        DocumentEntity document = getDocumentById(id);

        AppUserEntity submitter = appUserService.getAuthenticatedUser();
        validateDocSubmitter(document, submitter);

        return ResponseEntity.ok(ResponseDto.getSuccess(
                mapperService.mapDocumentEntityToDto(document)
        ));
    }

    @Transactional
    public ResponseEntity<ResponseDto<DocumentListDto>> findDocumentBySubmitter(String key, Long limit, Long page) {
        String searchKey = "%" + key.toLowerCase() + "%";
        long offset = page * limit;

        AppUserEntity submitter = appUserService.getAuthenticatedUser();

        Long count = documentRepository.countDocBySearchKeyAndSubmitterId(searchKey, submitter.getId());

        List<DocumentListDto.DocumentDto> dtoList = documentRepository.findDocBySearchKeyAndSubmitterId(
                        searchKey, submitter.getId(), limit, offset
                ).stream()
                .map(mapperService::mapDocumentEntityToListDto)
                .toList();

        long pageCount = count % limit == 0 ? (count / limit) : (count / limit + 1L);

        return ResponseEntity.ok(ResponseDto.getSuccess(
                new DocumentListDto(count, pageCount, dtoList)
        ));
    }

    @Transactional
    public ResponseEntity<ResponseDto<Object>> deleteDocumentByStaff(Long id) {
        DocumentEntity document = getDocumentById(id);

        AppUserEntity staff = appUserService.getAuthenticatedUser();
        validateDocStaff(document, staff);

        safeDeleteDocument(document);
        return ResponseEntity.ok(ResponseDto.getEmptySuccess());
    }

    @Transactional
    public ResponseEntity<ResponseDto<Object>> publishDocument(Long id) {
        DocumentEntity document = getDocumentById(id);
        validateDocIsNotPublished(document);

        AppUserEntity staff = appUserService.getAuthenticatedUser();
        validateDocStaff(document, staff);

        document.setIsPublished(true);
        document.setPubDate(LocalDate.now());

        documentRepository.save(document);
        return ResponseEntity.ok(ResponseDto.getEmptySuccess());
    }

    @Transactional
    public ResponseEntity<ResponseDto<DocumentDto>> getDocumentByIdAsStaff(Long id) {
        DocumentEntity document = getDocumentById(id);

        AppUserEntity staff = appUserService.getAuthenticatedUser();
        validateDocStaff(document, staff);

        return ResponseEntity.ok(ResponseDto.getSuccess(
                mapperService.mapDocumentEntityToDto(document)
        ));
    }

    @Transactional
    public ResponseEntity<ResponseDto<DocumentListDto>> findDocumentByStaff(String key, boolean isPublished, Long limit, Long page) {
        String searchKey = "%" + key.toLowerCase() + "%";
        long offset = page * limit;

        Long departmentId = appUserService.getAuthenticatedUser()
                .getDepartment().getId();

        Long count = documentRepository.countDocBySearchKeyAndDepartmentIdAndIsPublished(
                searchKey, departmentId, isPublished
        );

        List<DocumentListDto.DocumentDto> dtoList = documentRepository.findDocBySearchKeyAndDepartmentIdAndIsPublished(
                        searchKey, departmentId, isPublished, limit, offset
                ).stream()
                .map(mapperService::mapDocumentEntityToListDto)
                .toList();

        long pageCount = count % limit == 0 ? (count / limit) : (count / limit + 1L);

        return ResponseEntity.ok(ResponseDto.getSuccess(
                new DocumentListDto(count, pageCount, dtoList)
        ));
    }



    public DocumentEntity getDocumentById(Long id) {
        try {
            return documentRepository.findById(id)
                    .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new CustomBadRequestException(ResponseType.NO_DOCUMENT_WITH_THIS_ID);
        }
    }

    public String generateDocKey() {
        return String.format("repo-%s", UUID.randomUUID());
    }

    public void validateCreateDocument(AppUserEntity submitter) {
        int countOfNonPublishedDocs = documentRepository.findBySubmitterAndIsPublished(submitter, false)
                .size();

        if (countOfNonPublishedDocs > MAX_NON_PUBLISHED_DOC_LIMIT)
            throw new CustomBadRequestException(ResponseType.MAX_NON_PUBLISHED_DOC_LIMIT_REACHED);
    }

    public List<DocContributorEntity> createContributorlist(
            List<DocContributorSubmitDto> contributorSubmitDtoList,
            Long submitterId,
            DocumentEntity document
    ) {
        if (Objects.isNull(contributorSubmitDtoList))
            return List.of();

        return contributorSubmitDtoList.stream()
                .filter(dto -> ! dto.appUserId().equals(submitterId))
                .map(dto -> createContributor(dto, document))
                .toList();
    }

    public DocContributorEntity createContributorFromSubmitter(
            DocumentEntity document,
            AppUserEntity submitter,
            DocRole docRole
    ) {
        DocContributorEntity docContributor = new DocContributorEntity();
        docContributor.setDocument(document);
        docContributor.setAppUser(submitter);
        docContributor.setDocRole(docRole);
        return docContributor;
    }

    private DocContributorEntity createContributor(
            DocContributorSubmitDto contributorSubmitDto, DocumentEntity document
    ) {
        DocContributorEntity docContributor = new DocContributorEntity();
        docContributor.setDocument(document);

        AppUserEntity appUser = appUserService.getAuthorById(contributorSubmitDto.appUserId());
        docContributor.setAppUser(appUser);

        docContributor.setDocRole(contributorSubmitDto.docRole());
        return docContributor;
    }

    public void validateDocIsNotPublished(DocumentEntity document) {
        if (Boolean.TRUE.equals(document.getIsPublished()))
            throw new CustomBadRequestException(ResponseType.DOCUMENT_IS_PUBLISHED);
    }

    public void validateDocSubmitter(DocumentEntity document, AppUserEntity submitter) {
        if (! document.getSubmitter().getId()
                .equals(submitter.getId()))
            throw new CustomBadRequestException(ResponseType.WRONG_DOCUMENT_SUBMITTER);
    }

    private void validateDocStaff(DocumentEntity document, AppUserEntity staff) {
        if (! document.getDepartment().getId()
                .equals(staff.getDepartment().getId()))
            throw new CustomBadRequestException(ResponseType.WRONG_DOCUMENT_STAFF);
    }

    private void validateAddContributor(Set<DocContributorEntity> contributorSet, DocContributorSubmitDto dto) {
        boolean contributorExists = contributorSet.stream()
                .anyMatch(contributor -> contributor.getAppUser().getId()
                        .equals(dto.appUserId()));
        if (contributorExists)
            throw new CustomBadRequestException(ResponseType.CONTRIBUTOR_EXISTS);
    }

    public DocContributorEntity getDocContributorById(Long id) {
        try {
            return docContributorRepository.findById(id)
                    .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new CustomBadRequestException(ResponseType.NO_DOC_CONTRIBUTOR_WITH_THIS_ID);
        }
    }

    private void validateNonSubmitterDeletion(DocContributorEntity docContributor, AppUserEntity submitter) {
        boolean isSubmitter = docContributor.getAppUser().getId()
                .equals(submitter.getId());
        if (isSubmitter)
            throw new CustomBadRequestException(ResponseType.SUBMITTER_DELETION_FORBIDDEN);
    }

    private void safeDeleteDocument(DocumentEntity document) {
        docContributorRepository.deleteAll(document.getContributorSet());
        documentRepository.delete(document);
    }

}
