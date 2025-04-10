import React from 'react';
import AuctionCard from '../components/auction/AuctionCard';
import AuctionList from '../components/auction/AuctionList';

interface MainPageProps {
    
}

const MainPage: React.FC<MainPageProps> = () => {
    return (
        <div>
            <div></div>
            <div>
            <AuctionList/>
              </div>
            <div></div>
        </div>
    );
};

export default MainPage;