package bidgood.auction.service;

import bidgood.auction.domain.Auction;
import bidgood.auction.domain.AuctionStatus;
import bidgood.auction.dto.req.BiddingRequest;
import bidgood.auction.dto.res.BiddingResultResponse;
import bidgood.auction.exception.AlreadyMaxPriceBidder;
import bidgood.auction.exception.AuctionNotAvailableForBidding;
import bidgood.auction.exception.AuctionNotFound;
import bidgood.auction.exception.MaxPriceIsInvalid;
import bidgood.auction.repository.AuctionRepository;
import bidgood.product.domain.Product;
import bidgood.product.exception.ProductNotFound;
import bidgood.product.repository.ProductRepository;
import bidgood.user.domain.User;
import bidgood.user.exception.UserNotFound;
import bidgood.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionService {
    private final AuctionScheduleService auctionScheduleService;
    private final AuctionRepository auctionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

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

    @Transactional
    public void bidding(BiddingRequest biddingRequest, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFound::new);

        Auction auction = auctionRepository.findByIdForUpdate(biddingRequest.getAuctionId())
                .orElseThrow(AuctionNotFound::new);

        if (auction.getStatus() == AuctionStatus.SALE || auction.getStatus() == AuctionStatus.COMPLETE) {
            throw new AuctionNotAvailableForBidding();
        }

        if(auction.getMaxPriceUser().getId().equals(user.getId())){
            throw new AlreadyMaxPriceBidder();
        }

        if(biddingRequest.getPrice().compareTo(auction.getMaxPrice()) < 1){
            throw new MaxPriceIsInvalid();
        }

        auction.updateMaxPrice(biddingRequest.getPrice(),user);
        auctionRepository.save(auction);

        BiddingResultResponse biddingResultResponse = BiddingResultResponse.builder()
                .bidderName(user.getEmail())
                .currentPrice(biddingRequest.getPrice())
                .auctionId(auction.getId())
                .build();

        messagingTemplate.convertAndSend("/sub/auction/" + auction.getId(), biddingResultResponse);
    }
}
