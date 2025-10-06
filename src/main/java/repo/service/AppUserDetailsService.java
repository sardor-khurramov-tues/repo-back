package repo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repo.constant.ResponseType;
import repo.exception.CustomBadRequestException;
import repo.repository.AppUserRepository;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return appUserRepository.findByUsername(username)
                    .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new CustomBadRequestException(ResponseType.NO_USER_WITH_THIS_USERNAME);
        }
    }

}
