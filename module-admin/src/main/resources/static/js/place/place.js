let PlaceManager = {
    modals: {},
    currentPlaceRules: [],

    init: function () {
        this.modals.detail = new bootstrap.Modal(document.getElementById('placeDetailModal'));
        this.modals.create = new bootstrap.Modal(document.getElementById('placeCreateModal'));
        this.modals.edit = new bootstrap.Modal(document.getElementById('placeEditModal'));

        // 수정 취소 시 상세 복귀 이벤트 바인딩
        document.getElementById('btnCancelPlaceEdit').onclick = () => {
            const id = document.getElementById('editPlaceId').value;
            this.modals.edit.hide();
            this.showPlaceDetail(id);
        };
    },

    showPlaceDetail: function (id) {
        document.getElementById('rulePlaceId').value = id;
        this.toggleRuleForm(false);

        Promise.all([
            axios.get('/api/v1/places/' + id),
            axios.get('/api/v1/places/' + id + '/rules')
        ]).then(results => {
            const place = results[0].data.data;
            this.currentPlaceRules = results[1].data.data;

            document.getElementById('modalPlaceName').innerText = place.name;
            document.getElementById('modalPlaceRegion').innerText = place.region;
            document.getElementById('modalPlaceType').innerText = `${place.displayType}`;
            document.getElementById('modalPlaceAddress').innerText = place.address;
            document.getElementById('modalPlaceMap').innerText = place.map || '-';
            document.getElementById('modalPlaceSns').innerText = place.sns || '-';

            this.innerRenderRules(id, this.currentPlaceRules);

            document.getElementById('btnDeletePlace').onclick = () => this.deletePlace(id);
            document.getElementById('btnEditPlace').onclick = () => this.openEditModal(place);

            this.modals.detail.show();
        }).catch(err => AdminCommon.handleError(err, '정보를 불러오지 못했습니다.'));
    },

    innerRenderRules: function (placeId, rules) {
        this.toggleRuleForm(false);
        const tableBody = document.getElementById('ruleTableBody');
        tableBody.innerHTML = '';

        if (!rules || rules.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="8" class="text-center text-muted py-3">등록된 정기 일정이 없습니다.</td></tr>';
            return;
        }

        rules.forEach((rule, index) => {
            const displayTime = AdminCommon.formatTime(rule.scheduledAt);
            const collapseId = `ruleDetail_${index}`;
            const tr = document.createElement('tr');

            tr.innerHTML = `
                <td>
                    <button class="btn btn-xs btn-light p-0 collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#${collapseId}">
                        <i class="bi bi-chevron-down"></i>
                    </button>
                </td>
                <td><span class="badge bg-light text-dark border">${rule.dayOfWeek}</span></td>
                <td>${displayTime}</td>
                <td class="fw-bold">${rule.name}</td>
                <td><span class="badge ${rule.displayCategoryBadge}" style="font-size: 0.7rem;">${rule.displayCategory}</span></td>
                <td>${rule.capacity ? rule.capacity : '불명확'}</td>
                <td>${rule.entryFee && rule.entryFee > 0 ? `${new Intl.NumberFormat('ko-KR').format(rule.entryFee)}원` : '무료'}</td>
                <td>
                    <button type="button" class="btn btn-sm btn-outline-secondary py-0 px-2 me-1 btn-rule-edit">수정</button>
                    <button type="button" class="btn btn-sm btn-outline-danger py-0 px-2 btn-rule-delete">삭제</button>
                </td>
            `;

            const detailTr = document.createElement('tr');
            detailTr.className = 'collapse bg-light';
            detailTr.id = collapseId;
            detailTr.setAttribute('data-detail-id', rule.id);

            const stagesHtml = rule.stages.map(s => `<span class="badge border text-dark me-1">${s.stageNo}. ${s.type} (${s.roundCount}R/${s.gameCount}G)</span>`).join('');
            detailTr.innerHTML = `
                <td colspan="8" class="p-0">
                    <div class="detail-content-area p-3">
                        <div class="rule-info-display">
                            <div class="small">
                                <strong>구성:</strong> ${stagesHtml || '없음'}
                            </div>
                        </div>
                    </div>
                </td>
            `;

            const icon = tr.querySelector('i.bi');
            detailTr.addEventListener('show.bs.collapse', () => icon.classList.replace('bi-chevron-down', 'bi-chevron-up'));
            detailTr.addEventListener('hide.bs.collapse', () => icon.classList.replace('bi-chevron-up', 'bi-chevron-down'));

            tr.querySelector('.btn-rule-edit').onclick = () => this.editRule(rule);
            tr.querySelector('.btn-rule-delete').onclick = () => this.deleteRule(placeId, rule.id);

            tableBody.appendChild(tr);
            tableBody.appendChild(detailTr);
        });
    },

    toggleRuleForm: function (show) {
        const container = document.getElementById('ruleFormContainer');
        if (show === false) {
            const activeDetailArea = container.parentElement;
            if (activeDetailArea?.classList.contains('detail-content-area')) {
                activeDetailArea.querySelector('.rule-info-display')?.classList.remove('d-none');
            }
            document.querySelector('#placeDetailModal .modal-body').insertBefore(container, document.querySelector('#placeDetailModal .mt-4'));
            container.classList.add('d-none');
            document.getElementById('ruleForm').reset();
            document.getElementById('ruleId').value = '';
            document.getElementById('stageListContainer').innerHTML = '';
        } else {
            container.classList.remove('d-none');
        }
    },

    editRule: function (rule) {
        this.toggleRuleForm(false);
        const container = document.getElementById('ruleFormContainer');
        const detailTr = document.querySelector(`[data-detail-id="${rule.id}"]`);
        if (detailTr) {
            const detailContent = detailTr.querySelector('.detail-content-area');
            detailContent.querySelector('.rule-info-display')?.classList.add('d-none');
            detailContent.appendChild(container);
            this.toggleRuleForm(true);
            bootstrap.Collapse.getOrCreateInstance(detailTr).show();
        }

        document.getElementById('ruleId').value = rule.id;
        document.getElementById('ruleName').value = rule.name;
        document.getElementById('ruleDayOfWeek').value = rule.dayOfWeek;
        document.getElementById('ruleScheduledAt').value = AdminCommon.formatTime(rule.scheduledAt);
        document.getElementById('ruleCategory').value = rule.category;
        document.getElementById('ruleCapacity').value = rule.capacity;
        document.getElementById('ruleEntryFee').value = rule.entryFee;

        const stageContainer = document.getElementById('stageListContainer');
        stageContainer.innerHTML = '';
        if (rule.stages?.length > 0) rule.stages.forEach(s => this.addStageRow(s));
        else this.addStageRow();
    },

    addStageRow: function (data) {
        const container = document.getElementById('stageListContainer');
        const div = document.createElement('div');
        div.className = 'row g-2 mb-2 stage-row border-bottom pb-2';
        div.innerHTML = `
            <div class="col-md-4"><select class="form-select form-select-sm stage-type"><option value="SWISS" ${data?.type === 'SWISS' ? 'selected' : ''}>스위스 라운드</option><option value="TOURNAMENT" ${data?.type === 'TOURNAMENT' ? 'selected' : ''}>토너먼트</option></select></div>
            <div class="col-md-3"><div class="input-group input-group-sm"><span class="input-group-text">R</span><input type="number" class="form-control stage-round" value="${data?.roundCount || 3}" min="1"></div></div>
            <div class="col-md-3"><div class="input-group input-group-sm"><span class="input-group-text">G</span><input type="number" class="form-control stage-game" value="${data?.gameCount || 1}" min="1"></div></div>
            <div class="col-md-2 text-end"><button type="button" class="btn btn-sm btn-outline-danger border-0" onclick="this.closest('.stage-row').remove()"><i class="bi bi-trash"></i></button></div>
        `;
        container.appendChild(div);
    },

    submitRule: function () {
        const placeId = document.getElementById('rulePlaceId').value;
        const ruleId = document.getElementById('ruleId').value;
        const stageRows = document.querySelectorAll('.stage-row');
        if (stageRows.length === 0) return alert('최소 1개의 스테이지가 필요합니다.');

        let scheduledAt = document.getElementById('ruleScheduledAt').value;
        if (scheduledAt.length === 5) scheduledAt += ":00";

        const data = {
            name: document.getElementById('ruleName').value,
            dayOfWeek: document.getElementById('ruleDayOfWeek').value,
            scheduledAt: scheduledAt,
            category: document.getElementById('ruleCategory').value,
            capacity: document.getElementById('ruleCapacity').value,
            entryFee: document.getElementById('ruleEntryFee').value,
            stages: Array.from(stageRows).map((r, i) => ({
                stageNo: i + 1,
                type: r.querySelector('.stage-type').value,
                roundCount: parseInt(r.querySelector('.stage-round').value),
                gameCount: parseInt(r.querySelector('.stage-game').value)
            }))
        };

        const req = ruleId ? axios.put(`/api/v1/places/${placeId}/rules/${ruleId}`, data) : axios.post(`/api/v1/places/${placeId}/rules`, data);
        req.then(() => {
            alert('저장되었습니다.');
            this.toggleRuleForm(false);
            this.renderRules(placeId);
        })
            .catch(err => AdminCommon.handleError(err, '저장 실패'));
    },

    renderRules: function (id) {
        axios.get('/api/v1/places/' + id + '/rules').then(res => this.innerRenderRules(id, res.data.data));
    },

    deleteRule: function (placeId, ruleId) {
        if (confirm('정말 삭제하시겠습니까?')) {
            axios.delete(`/api/v1/places/${placeId}/rules/${ruleId}`).then(() => {
                alert('삭제되었습니다.');
                this.renderRules(placeId);
            });
        }
    },

    openEditModal: function (data) {
        document.getElementById('editPlaceId').value = data.id;
        document.getElementById('editPlaceName').value = data.name;
        document.getElementById('editPlaceType').value = data.type;
        document.getElementById('editPlaceAddress').value = data.address;
        document.getElementById('editPlaceMap').value = data.map;
        document.getElementById('editPlaceSns').value = data.sns;

        const select = document.getElementById('editPlaceRegionNo');
        const options = select.options;

        for (let i = 0; i < options.length; i++) {
            if (options[i].text === data.region) { // data.region: REST API에서 온 지역 이름
                options[i].selected = true;
                break;
            }
        }

        this.modals.detail.hide();
        this.modals.edit.show();
    },

    submitPlaceCreate: function () {
        const data = {
            name: document.getElementById('createPlaceName').value,
            regionNo: document.getElementById('createPlaceRegionNo').value,
            type: document.getElementById('createPlaceType').value,
            address: document.getElementById('createPlaceAddress').value,
            map: document.getElementById('createPlaceMap').value,
            sns: document.getElementById('createPlaceSns').value
        };
        axios.post('/api/v1/places', data).then(() => location.reload()).catch(err => AdminCommon.handleError(err, '등록 실패'));
    },

    submitPlaceEdit: function () {
        const id = document.getElementById('editPlaceId').value;
        const data = {
            name: document.getElementById('editPlaceName').value,
            regionNo: document.getElementById('editPlaceRegionNo').value,
            type: document.getElementById('editPlaceType').value,
            address: document.getElementById('editPlaceAddress').value,
            map: document.getElementById('editPlaceMap').value,
            sns: document.getElementById('editPlaceSns').value
        };
        axios.put('/api/v1/places/' + id, data).then(() => location.reload()).catch(err => AdminCommon.handleError(err, '수정 실패'));
    },

    deletePlace: function (id) {
        if (!confirm('정말 삭제하시겠습니까?')) return;
        axios.delete('/api/v1/places/' + id).then(() => location.reload()).catch(err => AdminCommon.handleError(err, '삭제 실패'));
    }
};