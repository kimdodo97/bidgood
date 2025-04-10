import React from 'react';
import AuctionCard from './AuctionCard';
import logo from '../../assets/bidgood_logo.png';

interface AuctionListProps {
    
}

const auctionItems = [
    {
      id: 1,
      imageUrl: logo,
      name: '예시 상품 A',
      status: '진행중',
      startPrice: '10000',
    },
    {
      id: 2,
      imageUrl: logo,
      name: '예시 상품 B',
      status: '대기중',
      startPrice: '15000',
    },
    {
        id: 3,
        imageUrl: logo,
        name: '예시 상품 B',
        status: '대기중',
        startPrice: '15000',
      },
      {
        id: 4,
        imageUrl: logo,
        name: '예시 상품 B',
        status: '대기중',
        startPrice: '15000',
      },{
        id: 5,
        imageUrl: logo,
        name: '예시 상품 B',
        status: '대기중',
        startPrice: '15000',
      },
  ];

const AuctionList: React.FC<AuctionListProps> = () => {
    return (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3">
        {auctionItems.map((item) => (
          <AuctionCard
            key={item.id}
            id={item.id}
            imageUrl={item.imageUrl}
            name={item.name}
            status={item.status}
            startPrice={item.startPrice}
          />
        ))}
      </div>
    );
};

export default AuctionList;