import React from 'react';
import { useNavigate } from 'react-router';

interface AuctionCardProps {
  id: number;
  imageUrl: string;
  name: string;
  status: string;
  startPrice: string;
}

const AuctionItemCard: React.FC<AuctionCardProps> = ({
  id,
  imageUrl,
  name,
  status,
  startPrice,
}) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/auction/${id}`);
  };

  return (
    <div
      className="cursor-pointer bg-white flex items-center border-t border-b border-t-gray-400 border-b-gray-400"
      onClick={handleClick}
    >
      <img src={imageUrl} alt={name} className="w-[120px] h-[120px] object-cover ml-2" />
      <div className="p-5 flex flex-col gap-1">
        <div className='font-bold text-[14px]'>예시상품</div>
        <div className='flex justify-between'>
            <div className='text-gray-500 text-[12px]'>카테고리</div>
            <div className='text-gray-500 text-[12px]'>{status}</div>
        </div>
        <div className='text-xs text-gray-500'>현재 5명 참여중</div>
        <div className='text-sm font-bold'>시작가 {startPrice}원</div>
      </div>
    </div>
  );
};

export default AuctionItemCard;
