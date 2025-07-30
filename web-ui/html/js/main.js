document.addEventListener('DOMContentLoaded', () => {
    fetchTasks();
    populateCompanyFilter();

    document.querySelectorAll('input[name="taskFilter"]').forEach(radio => {
        radio.addEventListener('change', fetchTasks);
    });

    document.getElementById('company-filter').addEventListener('change', fetchTasks);

    const logoutButton = document.getElementById('logout-button');
    if (logoutButton) {
        logoutButton.addEventListener('click', (event) => {
            event.preventDefault(); // aタグのデフォルトの挙動をキャンセル
            localStorage.removeItem('communityId');
            localStorage.removeItem('userId');
            window.location.href = 'login.html';
        });
    }
});

async function populateCompanyFilter() {
    const communityId = localStorage.getItem('communityId');
    if (!communityId) {
        return;
    }

    try {
        const response = await fetch(`${SERVER_URL}api/companies/community/${communityId}`);
        if (response.ok) {
            const companies = await response.json();
            const companyFilter = document.getElementById('company-filter');
            companyFilter.innerHTML = '<option value="">すべての企業</option>'; // 既存のオプションをクリア
            companies.forEach(company => {
                const option = document.createElement('option');
                option.value = company.companyId;
                option.textContent = company.companyName;
                companyFilter.appendChild(option);
            });
        } else {
            console.error('企業リストの取得に失敗しました。');
        }
    } catch (error) {
        console.error('企業リスト取得処理中にエラーが発生しました:', error);
    }
}

async function fetchTasks() {
    const filter = document.querySelector('input[name="taskFilter"]:checked').value;
    const userId = localStorage.getItem('userId');
    const communityId = localStorage.getItem('communityId');
    const companyId = document.getElementById('company-filter').value;

    if (!userId || !communityId) {
        alert('ログイン情報が見つかりません。再度ログインしてください。');
        window.location.href = 'login.html';
        return;
    }

    const params = {};
    if (filter === 'user') {
        params.userId = userId;
    } else if (filter === 'community') {
        params.communityId = communityId;
    }

    if (companyId) {
        params.companyId = companyId;
    }

    try {
        // GETリクエストなので、データをクエリパラメータとしてURLに追加します
        const queryParams = new URLSearchParams(params);
        console.log(`Fetching tasks with parameters: ${queryParams.toString()}`);
        const response = await fetch(`${SERVER_URL}api/task/list?${queryParams}`);

        if (response.ok) {
            const tasks = await response.json();
            displayTasks(tasks);
        } else {
            const errorData = await response.json().catch(() => ({ message: 'タスクの取得に失敗しました。' }));
            alert(errorData.message);
            displayTasks([]); // エラー時もテーブルを空にする
        }
    } catch (error) {
        console.error('タスク取得処理中にエラーが発生しました:', error);
        alert('サーバーとの通信に失敗しました。');
        displayTasks([]); // エラー時もテーブルを空にする
    }
}

async function displayTasks(tasks) {
    const taskListBody = document.getElementById('task-list-body');
    const filter = document.querySelector('input[name="taskFilter"]:checked').value;
    taskListBody.innerHTML = ''; // テーブルをクリア

    if (tasks.length === 0) {
        const tr = document.createElement('tr');
        const td = document.createElement('td');
        td.colSpan = 6; // 列数に合わせて変更
        td.textContent = '表示するタスクがありません。';
        td.classList.add('text-center');
        tr.appendChild(td);
        taskListBody.appendChild(tr);
        return;
    }

    for (const task of tasks) {
        const tr = document.createElement('tr');

        // 完了チェックボックス
        const completeTd = document.createElement('td');
        const completeCheckbox = document.createElement('input');
        completeCheckbox.type = 'checkbox';
        completeCheckbox.classList.add('form-check-input');
        completeCheckbox.checked = task.status === 'done';
        completeCheckbox.disabled = filter !== 'user';
        completeCheckbox.addEventListener('change', () => {
            updateTaskStatus(task, completeCheckbox.checked);
        });
        completeTd.appendChild(completeCheckbox);
        tr.appendChild(completeTd);

        const taskNameTd = document.createElement('td');
        taskNameTd.textContent = task.taskName;
        tr.appendChild(taskNameTd);

        const taskTypeTd = document.createElement('td');
        taskTypeTd.textContent = task.taskType;
        tr.appendChild(taskTypeTd);

        const companyTd = document.createElement('td');
        if (task.companyId !== null && task.companyId !== undefined) {
            try {
                const response = await fetch(`${SERVER_URL}api/companies/${task.companyId}`);
                if (response.ok) {
                    const company = await response.json();
                    companyTd.textContent = company.companyName;
                } else {
                    companyTd.textContent = '企業情報取得エラー';
                }
            } catch (error) {
                console.error(`企業情報(id: ${task.companyId})の取得に失敗しました:`, error);
                companyTd.textContent = '企業情報取得失敗';
            }
        } else {
            companyTd.textContent = task.tag; // companyIdがない場合はtagを表示
        }
        tr.appendChild(companyTd);

        const userNameTd = document.createElement('td');
        if (task.userId) {
            try {
                const userResponse = await fetch(`${SERVER_URL}api/users?userId=${task.userId}`);
                if (userResponse.ok) {
                    const userData = await userResponse.json();
                    userNameTd.textContent = userData.userName;
                } else {
                    userNameTd.textContent = 'ユーザー情報なし';
                }
            } catch (error) {
                console.error(`ユーザー情報(id: ${task.userId})の取得に失敗しました:`, error);
                userNameTd.textContent = '取得失敗';
            }
        } else {
            userNameTd.textContent = '-';
        }
        tr.appendChild(userNameTd);

        const deadlineTd = document.createElement('td');
        // YYYY-MM-DD形式にフォーマット
        deadlineTd.textContent = new Date(task.deadline).toLocaleDateString('sv-SE');
        tr.appendChild(deadlineTd);

        taskListBody.appendChild(tr);
    }
}

async function updateTaskStatus(task, isDone) {
    const newStatus = isDone ? 'done' : 'open';

    // 更新用のタスクオブジェクトを作成
    const updatedTask = { ...task, status: newStatus };

    try {
        const response = await fetch(`${SERVER_URL}api/task`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(updatedTask),
        });

        if (response.ok) {
            // 成功したらタスクリストを再読み込みして表示を更新
            fetchTasks();
        } else {
            const errorData = await response.json().catch(() => ({ message: 'タスクの更新に失敗しました。' }));
            alert(errorData.message);
            // 失敗した場合はチェックボックスの状態を元に戻す
            fetchTasks();
        }
    } catch (error) {
        console.error('タスク更新処理中にエラーが発生しました:', error);
        alert('サーバーとの通信に失敗しました。');
        // 失敗した場合はチェックボックスの状態を元に戻す
        fetchTasks();
    }
}
