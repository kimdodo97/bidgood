import React from 'react';
import searchLogo from '../../assets/search.svg'
import backIcon from '../../assets/back-icon.svg'

const ItemHeader: React.FC = () => {
    const handleBackClick = () => {
        window.history.back();  // 뒤로 가기
    };
  return (
    <div>
        <div className="flex justify-between h-16 bg-white border-b border-D9D9D9">
            <div className='w-full m-5 text-xl font-bold'>
            <img 
                src={backIcon} 
                alt="뒤로 가기" 
                onClick={handleBackClick}  // 클릭 시 뒤로가기
                className="cursor-pointer"
            />
            </div>
            <div className="flex w-full justify-end">
                <img
                    alt="종 모양 아이콘"
                    src={searchLogo}
                    className="w-10 h-10 m-2 cursor-pointer"
                />
            </div>
        </div>
    </div>
  );
};

export default ItemHeader;