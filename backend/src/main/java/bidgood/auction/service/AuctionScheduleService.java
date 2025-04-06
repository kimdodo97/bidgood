package bidgood.auction.service;

import bidgood.auction.scheduler.AuctionCloseJob;
import bidgood.auction.scheduler.AuctionStartJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuctionScheduleService {
    private final Scheduler scheduler;

    public void scheduleAuctionLifecycle(Long auctionId, LocalDateTime startTime, LocalDateTime endTime) throws SchedulerException {
        JobDetail startJob = JobBuilder.newJob(AuctionStartJob.class)
                .withIdentity("start-" + auctionId, "auction")
                .usingJobData("auctionId", auctionId)
                .build();

        Trigger startTrigger = TriggerBuilder.newTrigger()
                .withIdentity("start-trigger-" + auctionId, "auction")
                .startAt(Date.from(endTime.atZone(ZoneId.of("Asia/Seoul")).toInstant()))
                .build();

        scheduler.scheduleJob(startJob, startTrigger);

        // 경매 종료 Job
        JobDetail endJob = JobBuilder.newJob(AuctionCloseJob.class)
                .withIdentity("end-" + auctionId, "auction")
                .usingJobData("auctionId", auctionId)
                .build();

        Trigger endTrigger = TriggerBuilder.newTrigger()
                .withIdentity("end-trigger-" + auctionId, "auction")
                .startAt(Date.from(endTime.atZone(ZoneId.of("Asia/Seoul")).toInstant()))
                .build();

        scheduler.scheduleJob(endJob, endTrigger);
    }
}
