import React from 'react';
import searchLogo from '../../assets/search.svg'
const Header: React.FC = () => {
  return (
    <div>
        <div className="flex justify-between h-16 bg-white border-b border-D9D9D9">
            <div className='w-full m-5 text-xl font-bold'>
                {'비드굿'}
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

export default Header;