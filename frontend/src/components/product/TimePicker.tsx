import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import { ko } from 'date-fns/locale';
import 'react-datepicker/dist/react-datepicker.css';

interface TimePickerProps {
    selectedTime: Date | null;
    onChange: (date: Date | null) => void;
}

const TimePicker: React.FC<TimePickerProps> = ({selectedTime, onChange}) => {
    return (
        <div className='w-full'>
            <div>
                <DatePicker
                    selected={selectedTime}
                    onChange={onChange}
                    showTimeSelect
                    showTimeSelectOnly
                    timeIntervals={30}
                    timeCaption="시간"
                    dateFormat="HH:mm"
                    locale={ko}
                    placeholderText="시간을 선택하세요"
                    className="w-full border-2 border-gray-300 rounded-md p-2 text-gray-700 placeholder-gray-400 focus:outline-none focus:ring-2 transition"
                    calendarClassName="custom-calendar"
                />
            </div>
        </div>
    );
};

export default TimePicker;
