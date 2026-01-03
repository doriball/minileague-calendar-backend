let NoticeManager = {
    modals: {},

    init: function () {
        this.modals.detail = new bootstrap.Modal(document.getElementById('noticeDetailModal'));
        this.modals.create = new bootstrap.Modal(document.getElementById('noticeCreateModal'));
        this.modals.edit = new bootstrap.Modal(document.getElementById('noticeEditModal'));

        document.getElementById('btnCancelEdit').onclick = () => {
            const id = document.getElementById('editNoticeId').value;
            this.modals.edit.hide();
            this.showDetail(id);
        };
    },

    showDetail: function (id) {
        axios.get('/api/v1/notices/' + id)
            .then(res => {
                const data = res.data.data;
                document.getElementById('modalTitle').innerText = data.title;
                document.getElementById('modalCreatedAt').innerText = data.createdAt ? data.createdAt.replace('T', ' ').substring(0, 16) : '-';
                document.getElementById('modalContent').innerText = data.content;

                document.getElementById('btnDeleteNotice').onclick = () => this.deletenotice(id);
                document.getElementById('btnEditNotice').onclick = () => this.openEditModal(data);

                this.modals.detail.show();
            })
            .catch(err => AdminCommon.handleError(err, '상세 정보를 불러오지 못했습니다.'));
    },

    openEditModal: function (data) {
        document.getElementById('editNoticeId').value = data.id;
        document.getElementById('editTitle').value = data.title;
        document.getElementById('editContent').value = data.content;

        this.modals.detail.hide();
        this.modals.edit.show();
    },

    submitCreate: function () {
        const title = document.getElementById('createTitle').value;
        const content = document.getElementById('createContent').value;

        if (!title || !content) return alert('제목과 내용을 입력해주세요.');

        axios.post('/api/v1/notices', { title, content })
            .then(() => { alert('등록되었습니다.'); location.reload(); })
            .catch(err => AdminCommon.handleError(err, '등록 실패'));
    },

    submitEdit: function () {
        const id = document.getElementById('editNoticeId').value;
        const title = document.getElementById('editTitle').value;
        const content = document.getElementById('editContent').value;

        axios.put('/api/v1/notices/' + id, { title, content })
            .then(() => { alert('수정되었습니다.'); this.modals.edit.hide(); this.showDetail(id); })
            .catch(err => AdminCommon.handleError(err, '수정 실패'));
    },

    deletenotice: function (id) {
        if (!confirm('정말 삭제하시겠습니까?')) return;
        axios.delete('/api/v1/notices/' + id)
            .then(() => { alert('삭제되었습니다.'); location.reload(); })
            .catch(err => AdminCommon.handleError(err, '삭제 실패'));
    }
};