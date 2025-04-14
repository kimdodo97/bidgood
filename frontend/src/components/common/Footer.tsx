import React from 'react';
import homeIcon from '../../assets/home.svg';
import boardIcon from '../../assets/board.svg';
import addIcon from '../../assets/add-icon.svg';
import aboutIcon from '../../assets/about.svg';
import chatIcon from '../../assets/chat.svg';
import { useNavigate } from 'react-router';


const Footer: React.FC = () => {
    const navigate = useNavigate();
	const onClick = (page : string) => {
		navigate(page);
	};
    
  return (
    <div className="flex flex-row justify-between w-full bottom-0 border-t  border-gray-300 bg-white">
			<div
				aria-label="홈"
				className="flex flex-col items-center text-gray-600 hover:text-gray-500 flex-1 text-center py-2"
				onClick={() => onClick("main")}
			>
				<img
					alt="홈 아이콘"
                    src={homeIcon}
					className="w-8 h-8 m-1"
				/>
			</div>
            
            <div
				aria-label="경매품 목록"
				className="flex flex-col items-center text-gray-600 hover:text-gray-500 flex-1 text-center py-2"
				onClick={() => onClick("/board")}
			>
				<img
					alt="보드 아이콘"
                    src={boardIcon}
					className="w-8 h-8 m-1"
				/>
			</div>
            <div
				aria-label="신규 경매 등록"
				className="flex flex-col items-center text-gray-600 hover:text-gray-500 flex-1 text-center py-2"
				onClick={() => onClick("/add-auction")}
			>
				<img
					alt="신규 경매 등록"
                    src={addIcon}
					className="w-8 h-8 m-1"
				/>
			</div>
            <div
				aria-label="채팅 목록"
				className="flex flex-col items-center text-gray-600 hover:text-gray-500 flex-1 text-center py-2"
				onClick={() => onClick("/chat")}
			>
				<img
					alt="채팅"
                    src={chatIcon}
					className="w-8 h-8 m-1"
				/>
			</div>
            <div
				aria-label="더보기"
				className="flex flex-col items-center text-gray-600 hover:text-gray-500 flex-1 text-center py-2"
				onClick={() => onClick("/my-page")}
			>
				<img
					alt="더보기"
                    src={aboutIcon}
					className="w-8 h-8 m-1"
				/>
			</div>
		</div>
  );
};

export default Footer;