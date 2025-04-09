package bidgood.auction.scheduler;

import bidgood.auction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuctionStartJob implements Job {
    private final AuctionService auctionService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Long auctionId = jobExecutionContext.getMergedJobDataMap().getLong("auctionId");
        auctionService.startAuction(auctionId);
    }
}
