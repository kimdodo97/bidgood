package bidgood.auction.controller;

import bidgood.auction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuctionController {
    private final AuctionService auctionService;

    @PatchMapping("auction/start/{auctionId}")
    public ResponseEntity<Void> updateAuctionStart(@PathVariable Long auctionId){
        auctionService.startAuction(auctionId);

        return ResponseEntity.ok().build();
    }


    @PatchMapping("auction/close/{auctionId}")
    public ResponseEntity<Void> updateAuctionClose(@PathVariable Long auctionId){
        auctionService.startAuction(auctionId);

        return ResponseEntity.ok().build();

    }
}
