import React from 'react';
import DatePicker from 'react-datepicker';
import { ko } from 'date-fns/locale';
import 'react-datepicker/dist/react-datepicker.css';
import './css/CustomDatePicker.css'; // 커스터마이징용 CSS


interface DateSelectProps {
    selectedDate: Date | null;
    onChange: (date: Date | null) => void;
}

const DateSelect: React.FC<DateSelectProps> = ({selectedDate, onChange}) => {
    return (
        <div className='w-full'>
            <DatePicker
                selected={selectedDate}
                onChange={onChange}
                dateFormat="yyyy-MM-dd"
                locale={ko}
                placeholderText="날짜를 선택하세요"
                className="w-full border-2 border-gray-300 rounded-md p-2 text-gray-700 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-blue-400 transition"
                popperPlacement="bottom-start"
                calendarClassName="custom-calendar"
                dayClassName={() => 'text-sm'}
            />
        </div>
    );
};

export default DateSelect;
