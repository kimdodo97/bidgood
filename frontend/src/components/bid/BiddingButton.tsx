import React from 'react';

interface BiddingButtonProps {
    currentPrice : number,
    increment : number,
}

const BiddingButton: React.FC<BiddingButtonProps> = ({currentPrice,increment}) => {
  const handleBid = () => {
    console.log(`입찰`);
  };

  return (
    <div className='flex flex-col justify-center items-center gap-2'>
        <div className='w-full flex justify-between'>
            <div>최고입찰자</div>
            <div>김도도</div>
        </div>
        <div className='w-full flex justify-between'>
            <div>현재입찰액</div>
            <div><span  className='text-[#ff5952]'>{currentPrice.toLocaleString()}</span> 원</div>
        </div>
        <button
        className='w-[300px] bg-[#ff5952] text-xl font-bold text-white text-le p-4 rounded-xl'
        onClick={handleBid}
        >
        {(currentPrice + increment).toLocaleString()} 원 입찰
        </button>
  </div>  
  );
};

export default BiddingButton;
