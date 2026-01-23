let EventManager = {
    modals: {},

    init: function () {
        this.modals.detail = new bootstrap.Modal(document.getElementById('eventDetailModal'));
        this.modals.create = new bootstrap.Modal(document.getElementById('eventCreateModal'));
        this.modals.edit = new bootstrap.Modal(document.getElementById('eventEditModal'));

        document.getElementById('btnCancelEventEdit').onclick = () => {
            this.modals.edit.hide();
            this.modals.detail.show();
        };
    },

    showEventDetail: function (id) {
        axios.get('/api/v1/events/' + id).then(res => {
            const event = res.data.data;
            document.getElementById('modalEventName').innerText = event.name;
            document.getElementById('modalEventPlace').innerText = `${event.placeName} (${event.region})`;
            document.getElementById('modalEventScheduledAt').innerText = event.scheduledAt.replace('T', ' ').substring(0, 16);
            document.getElementById('modalEventCategory').innerHTML = `<span class="badge ${event.displayCategoryBadge}">${event.displayCategory}</span>`;
            document.getElementById('modalEventCapacity').innerText = event.capacity ? `${event.capacity}` : '불명확';
            document.getElementById('modalEventEntryFee').innerText = event.entryFee && event.entryFee > 0 ? `${new Intl.NumberFormat('ko-KR').format(event.entryFee)}원` : '무료';

            const tableBody = document.getElementById('eventStageTableBody');
            tableBody.innerHTML = '';
            const typeLabels = {
                'SWISS': '스위스 라운드',
                'TOURNAMENT': '토너먼트'
            };
            event.stages.forEach(stage => {
                const tr = document.createElement('tr');
                const displayType = typeLabels[stage.type] || stage.type;
                tr.innerHTML = `<td>${stage.stageNo}</td><td>${displayType}</td><td>${stage.roundCount}R</td><td>${stage.gameCount}G</td>`;
                tableBody.appendChild(tr);
            });

            document.getElementById('btnDeleteEvent').onclick = () => this.deleteEvent(id);
            document.getElementById('btnEditEvent').onclick = () => this.openEditModal(event);
            this.modals.detail.show();
        }).catch(err => AdminCommon.handleError(err, '정보를 불러오지 못했습니다.'));
    },

    prepareEventCreate: function () {
        document.getElementById('eventCreateForm').reset();
        const placeSelect = document.getElementById('createEventPlace');
        placeSelect.innerHTML = '<option value="">지역을 먼저 선택하세요</option>';
        placeSelect.disabled = true;
        document.getElementById('createEventStageContainer').innerHTML = '';
        this.addEventStageRow('create');
    },

    loadPlacesByRegion: function (regionNo) {
        const placeSelect = document.getElementById('createEventPlace');
        if (!regionNo) {
            placeSelect.innerHTML = '<option value="">지역을 먼저 선택하세요</option>';
            placeSelect.disabled = true;
            return;
        }
        axios.get('/api/v1/places/summaries?regionNo=' + regionNo).then(res => {
            placeSelect.innerHTML = '<option value="">매장을 선택하세요</option>';
            res.data.data.forEach(s => {
                const opt = document.createElement('option');
                opt.value = s.id;
                opt.text = s.name;
                placeSelect.appendChild(opt);
            });
            placeSelect.disabled = false;
        });
    },

    openEditModal: function (data) {
        document.getElementById('editEventId').value = data.id;
        document.getElementById('editEventName').value = data.name;
        document.getElementById('editEventScheduledAt').value = data.scheduledAt.substring(0, 16);
        document.getElementById('editEventPlace').innerHTML = `<option value="${data.placeId}">${data.placeName} (${data.region})</option>`;
        document.getElementById('editEventCategory').value = data.category;
        document.getElementById('editEventCapacity').value = data.capacity;
        document.getElementById('editEventEntryFee').value = data.entryFee;

        const container = document.getElementById('editEventStageContainer');
        container.innerHTML = '';
        if (data.stages?.length > 0) data.stages.forEach(s => this.addEventStageRow('edit', s));
        else this.addEventStageRow('edit');

        this.modals.detail.hide();
        this.modals.edit.show();
    },

    addEventStageRow: function (mode, data) {
        const container = document.getElementById(mode === 'create' ? 'createEventStageContainer' : 'editEventStageContainer');
        const row = document.createElement('div');
        row.className = 'row g-2 mb-2 event-stage-row border-bottom pb-2';
        row.innerHTML = `
            <div class="col-md-4"><select class="form-select form-select-sm stage-type"><option value="SWISS" ${data?.type === 'SWISS' ? 'selected' : ''}>스위스 라운드</option><option value="TOURNAMENT" ${data?.type === 'TOURNAMENT' ? 'selected' : ''}>토너먼트</option></select></div>
            <div class="col-md-3"><div class="input-group input-group-sm"><span class="input-group-text">R</span><input type="number" class="form-control stage-round" value="${data?.roundCount || 3}" min="1"></div></div>
            <div class="col-md-3"><div class="input-group input-group-sm"><span class="input-group-text">G</span><input type="number" class="form-control stage-game" value="${data?.gameCount || 1}" min="1"></div></div>
            <div class="col-md-2 text-end"><button type="button" class="btn btn-sm btn-outline-danger border-0" onclick="this.closest('.event-stage-row').remove()"><i class="bi bi-trash"></i></button></div>
        `;
        container.appendChild(row);
    },

    submitEventCreate: function () {
        const data = {
            name: document.getElementById('createEventName').value,
            placeId: document.getElementById('createEventPlace').value,
            scheduledAt: document.getElementById('createEventScheduledAt').value + ":00",
            category: document.getElementById('createEventCategory').value,
            capacity: document.getElementById('createEventCapacity').value,
            entryFee: document.getElementById('createEventEntryFee').value,
            stages: Array.from(document.querySelectorAll('#createEventStageContainer .event-stage-row')).map((r, i) => ({
                stageNo: i + 1,
                type: r.querySelector('.stage-type').value,
                roundCount: parseInt(r.querySelector('.stage-round').value),
                gameCount: parseInt(r.querySelector('.stage-game').value)
            }))
        };
        axios.post('/api/v1/events', data).then(() => location.reload()).catch(err => AdminCommon.handleError(err, '등록 실패'));
    },

    submitEventEdit: function () {
        const id = document.getElementById('editEventId').value;
        let time = document.getElementById('editEventScheduledAt').value;
        if (time.length === 16) time += ":00";
        const data = {
            name: document.getElementById('editEventName').value, scheduledAt: time,
            placeId: document.getElementById('editEventPlace').value,
            category: document.getElementById('editEventCategory').value,
            capacity: document.getElementById('editEventCapacity').value,
            entryFee: document.getElementById('editEventEntryFee').value,
            stages: Array.from(document.querySelectorAll('#editEventStageContainer .event-stage-row')).map((r, i) => ({
                stageNo: i + 1,
                type: r.querySelector('.stage-type').value,
                roundCount: parseInt(r.querySelector('.stage-round').value),
                gameCount: parseInt(r.querySelector('.stage-game').value)
            }))
        };
        axios.put('/api/v1/events/' + id, data).then(() => location.reload()).catch(err => AdminCommon.handleError(err, '수정 실패'));
    },

    deleteEvent: function (id) {
        if (!confirm('정말 삭제하시겠습니까?')) return;
        axios.delete('/api/v1/events/' + id).then(() => location.reload()).catch(err => AdminCommon.handleError(err, '삭제 실패'));
    }
};