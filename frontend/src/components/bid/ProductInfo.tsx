import React from 'react';

interface ProductInfoProps {
    imageUrl : string,
    productName: string,
    productDesciption : string,
    category: string
}

const ProductInfo: React.FC<ProductInfoProps> = ({imageUrl, productName, productDesciption, category}) => {
    return (
        <div className='justify-center'>
            <div className='w-full flex justify-center'>
                <img src={imageUrl} className="w-[350px] h-[350px] object-cover ml-2" />
            </div>
            <div className='flex flex-col p-3 gap-2'>
                <div className='font-bold text-[20px]'>{productName}</div>
                <div className='text-[14px] text-gray-500'>{category}</div>
                <div className='text-[16px] mt-2'>{productDesciption}</div>
            </div>
        </div>
    );
};

export default ProductInfo;