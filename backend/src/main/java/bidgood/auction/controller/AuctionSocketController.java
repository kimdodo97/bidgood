package bidgood.auction.controller;

import bidgood.auction.dto.req.BiddingRequest;
import bidgood.auction.exception.MaxPriceIsInvalid;
import bidgood.auction.service.AuctionService;
import bidgood.global.exception.BidGoodException;
import bidgood.global.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AuctionSocketController {
    private AuctionService auctionService;
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/bid")
    public void handleBid(BiddingRequest biddingRequest, Principal principal) {
        String email = principal.getName();
        auctionService.bidding(biddingRequest, email);
    }

    @MessageExceptionHandler(BidGoodException.class)
    public void handlerMaxPriceException(BidGoodException exception, Principal principal) {
        ErrorResponse errorResponse = new ErrorResponse(String.valueOf(exception.getStatusCode()),exception.getMessage());
        messagingTemplate.convertAndSendToUser(
                principal.getName(),
                "queue/errors",
                errorResponse
        );
    }

}
