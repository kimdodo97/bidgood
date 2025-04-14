import React from 'react';
import BiddingButton from '../components/bid/BiddingButton';
import BiddingInfo from '../components/bid/BiddingInfo';
import ProductInfo from '../components/bid/ProductInfo';
import logo1 from '../assets/bidgood_logo.png';
import logo2 from '../assets/bidgood_logo.png';
import logo3 from '../assets/bidgood_logo.png';

interface BidPageProps {
    auctionId : number,
    onMessage: (message: unknown) => void;
}

const BidPage: React.FC<BidPageProps> = ({ auctionId, onMessage }) => {
    return (
        <div className='flex-col justify-center p-4'>
            <ProductInfo imageUrl={[logo1,logo2,logo3]} productName={'테스트상품'} productDesciption={'해당 상품은 테스트 상품입니다.'} category={'건담'}/>
            <div className="fixed bottom-15 left-0 backdrop-blur-sm p-5 z-50 w-full">
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