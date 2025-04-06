package bidgood.auction.controller;

import bidgood.auction.dto.req.BiddingRequest;
import bidgood.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class AuctionSocketController {
    @Autowired
    private AuctionService auctionService;

    @MessageMapping("/bid")
    public void handleBid(BiddingRequest biddingRequest, Principal principal) {
        String email = principal.getName();
        auctionService.bidding(biddingRequest, email);
    }
}
