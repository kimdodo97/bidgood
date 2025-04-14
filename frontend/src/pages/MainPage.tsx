import React from 'react';
import AuctionList from '../components/auction/AuctionList';

interface MainPageProps {
    
}

const MainPage: React.FC<MainPageProps> = () => {
    return (
        <div>
            <AuctionList/>
        </div>
    );
};

export default MainPage;