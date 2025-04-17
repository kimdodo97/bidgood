import React from 'react';
import Select from 'react-select';

interface CategoryOption {
    value: string;
    label: string;
}

interface CategorySelectProps {
    selected: string;
    onChange: (value: string) => void;
}

const CategorySelect: React.FC<CategorySelectProps> = ({ selected, onChange }) => {
    const categories: CategoryOption[] = [
        { value: '전자제품', label: '전자제품' },
        { value: '패션', label: '패션' },
        { value: '음악', label: '음악' },
        { value: '도서', label: '도서' },
        { value: '미술품', label: '미술품' },
    ];

    const handleCategoryChange = (selectedOption: CategoryOption | null) => {
        onChange(selectedOption ? selectedOption.value : '');
    };

    const customStyles = {
        control: (styles: any) => ({
            ...styles,
            width: '100%',
            borderRadius: '8px',
            color: '#4A4A4A',
            height: '40px',
            minHeight: '40px',
        }),
        singleValue: (styles: any) => ({
            ...styles,
            color: 'black',
            textAlign: 'left',
        }),
        placeholder: (styles: any) => ({
            ...styles,
            color: '#A0A0A0',
            textAlign: 'left',
            fontSize: '14px',
            fontWeight: 'lighter',
        }),
        option: (styles: any, { isSelected }: any) => ({
            ...styles,
            fontSize: '14px',
            color: isSelected ? 'black' : '#4A4A4A',
            backgroundColor: isSelected ? '#E5E5E5' : 'white',
            '&:hover': {
                backgroundColor: '#F0F0F0',
            },
        }),
    };

    return (
        <div className="mr-2 ml-2 mb-2">
            <Select
                id="category"
                value={categories.find(option => option.value === selected) || null}
                onChange={handleCategoryChange}
                options={categories}
                placeholder="카테고리를 선택하세요"
                classNamePrefix="react-select"
                isSearchable={true}
                styles={customStyles}
            />
        </div>
    );
};

export default CategorySelect;
