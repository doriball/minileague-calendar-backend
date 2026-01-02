let StoreManager = {
    modals: {},
    currentStoreRules: [],

    init: function () {
        this.modals.detail = new bootstrap.Modal(document.getElementById('storeDetailModal'));
        this.modals.create = new bootstrap.Modal(document.getElementById('storeCreateModal'));
        this.modals.edit = new bootstrap.Modal(document.getElementById('storeEditModal'));

        // 수정 취소 시 상세 복귀 이벤트 바인딩
        document.getElementById('btnCancelStoreEdit').onclick = () => {
            const id = document.getElementById('editStoreId').value;
            this.modals.edit.hide();
            this.showStoreDetail(id);
        };
    },

    showStoreDetail: function (id) {
        document.getElementById('ruleStoreId').value = id;
        this.toggleRuleForm(false);

        Promise.all([
            axios.get('/api/v1/stores/' + id),
            axios.get('/api/v1/stores/' + id + '/rules')
        ]).then(results => {
            const store = results[0].data.data;
            this.currentStoreRules = results[1].data.data;

            document.getElementById('modalStoreName').innerText = store.name;
            document.getElementById('modalStoreRegion').innerText = store.region;
            document.getElementById('modalStoreAddress').innerText = store.address;
            document.getElementById('modalStoreMap').innerText = store.map || '-';
            document.getElementById('modalStoreSns').innerText = store.sns || '-';

            this.innerRenderRules(id, this.currentStoreRules);

            document.getElementById('btnDeleteStore').onclick = () => this.deleteStore(id);
            document.getElementById('btnEditStore').onclick = () => this.openEditModal(id);

            this.modals.detail.show();
        }).catch(err => AdminCommon.handleError(err, '정보를 불러오지 못했습니다.'));
    },

    innerRenderRules: function (storeId, rules) {
        this.toggleRuleForm(false);
        const tableBody = document.getElementById('ruleTableBody');
        tableBody.innerHTML = '';

        if (!rules || rules.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="5" class="text-center text-muted py-3">등록된 정기 일정이 없습니다.</td></tr>';
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
                <td class="fw-bold">${rule.name} ${rule.official ? '<span class="badge bg-primary ms-1" style="font-size: 0.7rem;">공인</span>' : ''}</td>
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
            detailTr.innerHTML = `<td colspan="5" class="p-0"><div class="detail-content-area p-3"><div class="rule-info-display"><div class="small"><strong>구성:</strong> ${stagesHtml || '없음'}</div></div></div></td>`;

            const icon = tr.querySelector('i.bi');
            detailTr.addEventListener('show.bs.collapse', () => icon.classList.replace('bi-chevron-down', 'bi-chevron-up'));
            detailTr.addEventListener('hide.bs.collapse', () => icon.classList.replace('bi-chevron-up', 'bi-chevron-down'));

            tr.querySelector('.btn-rule-edit').onclick = () => this.editRule(rule);
            tr.querySelector('.btn-rule-delete').onclick = () => this.deleteRule(storeId, rule.id);

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
            document.querySelector('#storeDetailModal .modal-body').insertBefore(container, document.querySelector('#storeDetailModal .mt-4'));
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
        document.getElementById('ruleOfficial').checked = rule.official;

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
        const storeId = document.getElementById('ruleStoreId').value;
        const ruleId = document.getElementById('ruleId').value;
        const stageRows = document.querySelectorAll('.stage-row');
        if (stageRows.length === 0) return alert('최소 1개의 스테이지가 필요합니다.');

        let scheduledAt = document.getElementById('ruleScheduledAt').value;
        if (scheduledAt.length === 5) scheduledAt += ":00";

        const data = {
            name: document.getElementById('ruleName').value,
            dayOfWeek: document.getElementById('ruleDayOfWeek').value,
            scheduledAt: scheduledAt,
            official: document.getElementById('ruleOfficial').checked,
            stages: Array.from(stageRows).map((r, i) => ({
                stageNo: i + 1,
                type: r.querySelector('.stage-type').value,
                roundCount: parseInt(r.querySelector('.stage-round').value),
                gameCount: parseInt(r.querySelector('.stage-game').value)
            }))
        };

        const req = ruleId ? axios.put(`/api/v1/stores/${storeId}/rules/${ruleId}`, data) : axios.post(`/api/v1/stores/${storeId}/rules`, data);
        req.then(() => {
            alert('저장되었습니다.');
            this.toggleRuleForm(false);
            this.renderRules(storeId);
        })
            .catch(err => AdminCommon.handleError(err, '저장 실패'));
    },

    renderRules: function (id) {
        axios.get('/api/v1/stores/' + id + '/rules').then(res => this.innerRenderRules(id, res.data.data));
    },

    deleteRule: function (storeId, ruleId) {
        if (confirm('정말 삭제하시겠습니까?')) {
            axios.delete(`/api/v1/stores/${storeId}/rules/${ruleId}`).then(() => {
                alert('삭제되었습니다.');
                this.renderRules(storeId);
            });
        }
    }
};