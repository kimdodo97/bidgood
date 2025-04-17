import React from 'react';

interface CommonButtonProps {
    buttonName: string;
    onClick?: () => number;
}

const CommonButton: React.FC<CommonButtonProps> = ({ buttonName, onClick }) => {
    return (
        <div>
            <button className="w-full h-12 bg-[#ff5952] text-lg font-bold text-white rounded-md flex justify-center items-center"
                    onClick={onClick}>
                {buttonName}
            </button>
        </div>
    );
};

export default CommonButton;
