export const getBorderColor = (isCurrent: boolean, isCorrect: boolean, isActive: boolean) => {
    if (isCurrent) {
        return "border-blue-500";
    } else if(!isActive) {
        return "border-gray-400";
    } else if (isCorrect === false) {
        return "border-red-500";
    } else if (isCorrect === true) {
        return "border-green-500";
    } else {
        return "border-gray-900";
    }
};

