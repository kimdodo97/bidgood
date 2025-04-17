import React, { useRef } from 'react';
import cameraIcon from '../../assets/camera.svg';

interface AddImageProps {
  imageCount: number;
  onImageAdd: (file: File) => void;
}

const AddImage: React.FC<AddImageProps> = ({ imageCount, onImageAdd }) => {
  const fileInputRef = useRef<HTMLInputElement | null>(null);

  const handleClick = () => {
    if (imageCount >= 10) {
      alert('이미지는 최대 10장까지 업로드할 수 있습니다.');
      return;
    }
    fileInputRef.current?.click();
  };

  const handleImageUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
    const files = event.target.files;
    if (!files || files.length === 0) return;

    onImageAdd(files[0]); // 부모로 전달
    event.target.value = ''; // 같은 파일 다시 선택 가능하게 초기화
  };

  return (
    <>
      <div
        onClick={handleClick}
        className="border-2 border-gray-300 w-[60px] h-[60px] rounded-md flex flex-col items-center justify-center cursor-pointer m-2"
      >
        <img src={cameraIcon} className="w-[30px] h-[30px] object-cover" />
        <div className="text-xs text-gray-500 mt-1">{imageCount}/10</div>
      </div>
      <input
        ref={fileInputRef}
        type="file"
        accept="image/*"
        style={{ display: 'none' }}
        onChange={handleImageUpload}
      />
    </>
  );
};

export default AddImage;
