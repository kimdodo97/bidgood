import React from 'react';
import BiddingButton from '../components/bid/BiddingButton';
import BiddingInfo from '../components/bid/BiddingInfo';
import ProductInfo from '../components/bid/ProductInfo';
import logo from '../assets/bidgood_logo.png';

interface BidPageProps {
    auctionId : number,
    onMessage: (message: unknown) => void;
}

const BidPage: React.FC<BidPageProps> = ({ auctionId, onMessage }) => {
    return (
        <div className='flex-col justify-center p-4 mt-10'>
            <ProductInfo imageUrl={logo} productName={'테스트상품'} productDesciption={'해당 상품은 테스트 상품입니다.'} category={'건담'}/>
            <div className="fixed bottom-0 left-0 w-fullbackdrop-blur-sm shadow-md p-5 z-50 w-full">
                <BiddingInfo
                    biddingUserCount={10}
                    maxBidUserid={2}
                    maxBidUserName={'김도도'}
                    biddingPrice={100000}
                />
                </div>
        </div>
    );
};

export default BidPage;