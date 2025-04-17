import React, { useState } from 'react';
import DataInput from '../components/product/DataInput';
import Description from '../components/product/Description';
import DateSelect from '../components/product/DateSelect';
import TimePicker from '../components/product/TimePicker';
import CommonButton from '../components/common/CommonButton';
import CategorySelect from '../components/product/CategorySelect';
import { useNavigate } from 'react-router';
import AddImage from '../components/product/AddImage';

interface ProductAddPageProps {}

const ProductAddPage: React.FC<ProductAddPageProps> = () => {
  const navigate = useNavigate();

  const [name, setName] = useState('');
  const [category, setCategory] = useState('');
  const [defect, setDefect] = useState('');
  const [purchaseLocation, setPurchaseLocation] = useState('');
  const [description, setDescription] = useState('');
  const [startDate, setStartDate] = useState<Date | null>(null);
  const [startTime, setStartTime] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);
  const [endTime, setEndTime] = useState<Date | null>(null);

  const [images, setImages] = useState<File[]>([]);

  const handleImageAdd = (file: File) => {
    if (images.length >= 10) return;
    setImages((prev) => [...prev, file]);
  };

  const mergeDateTime = (date: Date | null, time: Date | null): string | null => {
    if (!date || !time) return null;
    const merged = new Date(date);
    merged.setHours(time.getHours());
    merged.setMinutes(time.getMinutes());
    merged.setSeconds(0);
    return merged.toISOString().slice(0, 19); // yyyy-MM-ddTHH:mm:ss
  };

  const handleRegister = async () => {
    const productData = {
      name,
      category,
      defect,
      purchaseLocation,
      description,
      auctionStart: mergeDateTime(startDate, startTime),
      auctionEnd: mergeDateTime(endDate, endTime),
    };

    if (images.length > 0) {
        const formData = new FormData();
        images.forEach((file) => formData.append('images', file));
    }   
  };

  return (
    <div className="flex-col justify-start items-start p-2">
      <div>
        <AddImage imageCount={images.length} onImageAdd={handleImageAdd} />
      </div>

      <div className="w-full flex-col flex gap-3">
        <div className="flex flex-col gap-2">
          <div className="w-full pl-2">이름</div>
          <DataInput placeholder="제품명을 입력해주세요" value={name} onChange={setName} />
        </div>
        <div>
          <div className="w-full pl-2">카테고리</div>
          <CategorySelect selected={category} onChange={setCategory} />
        </div>
        <div>
          <div className="w-full pl-2">하자 설명</div>
          <DataInput placeholder="제품 하자가 있다면 간단하게 적어주세요" value={defect} onChange={setDefect} />
        </div>
        <div>
          <div className="w-full pl-2">구매처</div>
          <DataInput placeholder="제품명의 구매처를 입력해주세요" value={purchaseLocation} onChange={setPurchaseLocation} />
        </div>
        <div>
          <div className="w-full pl-2">상세설명</div>
          <Description value={description} onChange={setDescription} />
        </div>
      </div>

      <div className="w-full pl-2 pr-2 flex flex-col gap-1 mt-3">
        <div>
          <div className="mb-2">경매시작일</div>
          <div className="w-full flex items-start justify-start gap-3 mb-4">
            <DateSelect selectedDate={startDate} onChange={setStartDate} />
            <TimePicker selectedTime={startTime} onChange={setStartTime} />
          </div>
        </div>
        <div>
          <div className="mb-2">경매마감일</div>
          <div className="flex items-start justify-start gap-3 mb-4">
            <DateSelect selectedDate={endDate} onChange={setEndDate} />
            <TimePicker selectedTime={endTime} onChange={setEndTime} />
          </div>
        </div>
      </div>

      <div className="pl-2 pr-2">
        <CommonButton buttonName="등록하기" onClick={handleRegister} />
      </div>
    </div>
  );
};

export default ProductAddPage;
