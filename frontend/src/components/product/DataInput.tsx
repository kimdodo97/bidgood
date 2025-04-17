import React from 'react';

interface DataInputProps {
    placeholder?: string;
    value: string;
    onChange: (value: string) => void;
}

const DataInput: React.FC<DataInputProps> = ({ placeholder, value, onChange }) => {
    return (
        <div className='border-gray-300 border-2 h-10 p-1 mb-2 mr-2 ml-2 rounded-md'>
            <input
                className='text-gray-500 outline-none w-full h-full text-left flex items-center text-[14px]'
                type="text"
                placeholder={placeholder}
                value={value}
                onChange={(e) => onChange(e.target.value)}
            />
        </div>
    );
};

export default DataInput;
