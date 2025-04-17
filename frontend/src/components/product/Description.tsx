import React from 'react';

interface DescriptionProps {
    value: string;
    onChange: (value: string) => void;
}

const Description: React.FC<DescriptionProps> = ({ value, onChange }) => {
    return (
        <div className='border-gray-300 border-2 h-50 p-1 ml-2 mr-2 mb-2 rounded-md'>
            <textarea 
                className='text-gray-500 outline-none w-full h-full text-left flex items-center text-[14px]' 
                placeholder={'경매품으로 등록할 게시물의 상세한 설명을 작성해주세요.\n신뢰할 수 있는 거래를 위해서 작성해주세요'}
                value={value}
                onChange={(e) => onChange(e.target.value)}
            />            
        </div>
    );
};

export default Description;