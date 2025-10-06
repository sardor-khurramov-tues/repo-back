package repo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ScheduledService {

    @Scheduled(cron = "0 0 0 * * *")
    public void test() {
        log.info("test message");
    }

}
