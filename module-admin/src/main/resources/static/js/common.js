const AdminCommon = {
    formatTime: function (time) {
        if (!time) return '-';
        if (Array.isArray(time)) {
            return String(time[0]).padStart(2, '0') + ':' + String(time[1]).padStart(2, '0');
        }
        return typeof time === 'string' ? time.substring(0, 5) : time;
    },
    // 에러 핸들링 공통화
    handleError: function (error, defaultMsg) {
        console.error(error);
        const message = error.response?.data?.message || defaultMsg;
        alert(message);
    }
};