import React from 'react';
import BiddingButton from './BiddingButton';

interface BiddingInfoProps {
    biddingUserCount : number,
    maxBidUserid : number,
    maxBidUserName : string,
    biddingPrice: number
}

const BiddingInfo: React.FC<BiddingInfoProps> = ({biddingUserCount, maxBidUserid, maxBidUserName, biddingPrice}) => {
    return (
        <div className='flex-col flex justify-center gap-2 items-center'>
            <BiddingButton currentPrice={1000000} increment={10000}/>
        </div>
    );
};

export default BiddingInfo;