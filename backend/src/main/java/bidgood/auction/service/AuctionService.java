package bidgood.auction.service;

import bidgood.auction.domain.Auction;
import bidgood.auction.domain.AuctionStatus;
import bidgood.auction.exception.AuctionNotFound;
import bidgood.auction.repository.AuctionRepository;
import bidgood.product.domain.Product;
import bidgood.product.exception.ProductNotFound;
import bidgood.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionService {
    private final AuctionScheduleService auctionScheduleService;
    private final AuctionRepository auctionRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Auction registerAuction(Long productId) throws SchedulerException {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFound::new);

        Auction auction = Auction.builder()
                .maxPrice(product.getStartPrice())
                .product(product)
                .status(AuctionStatus.PREPARE)
                .build();

        auctionRepository.save(auction);
        auctionScheduleService.scheduleAuctionLifecycle(auction.getId(),product.getAtAuctionStart(),product.getAtAuctionEnd());
        return auction;
    }

    @Transactional
    public void startAuction(Long auctionId){
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(AuctionNotFound::new);
        auction.modifyStatus(AuctionStatus.SALE);
    }


    @Transactional
    public void closeAuction(Long auctionId){
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(AuctionNotFound::new);
        auction.modifyStatus(AuctionStatus.COMPLETE);
    }
}
