// components/ProductInfo.tsx
import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import 'swiper/css/scrollbar';
import './css/BidImage.css'


interface ProductInfoProps {
    imageUrl: string[];
    productName: string;
    productDescription: string;
    category: string;
  }
  
  const ProductInfo: React.FC<ProductInfoProps> = ({
    imageUrl,
    productName,
    productDescription,
    category,
  }) => {
    return (
      <div className="justify-center">
        <div className="w-full flex justify-center items-center">
          <Swiper
            modules={[Navigation, Pagination]}
            navigation
            pagination={{
              clickable: true,
            }}
            className="w-[350px] h-[350px] rounded overflow-hidden"
          >
            {imageUrl.map((url, idx) => (
              <SwiperSlide key={idx}>
                <img
                  src={url}
                  className="w-full h-full object-cover"
                  alt={`product-${idx}`}
                />
              </SwiperSlide>
            ))}
          </Swiper>
        </div>
        <div className="flex flex-col p-3 gap-2">
          <div className="font-bold text-[20px]">{productName}</div>
          <div className="text-[14px] text-gray-500">{category}</div>
          <div className="text-[16px] mt-2">{productDescription}</div>
        </div>
      </div>
    );
  };
  
  export default ProductInfo;
