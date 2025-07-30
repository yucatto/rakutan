// ページの読み込みが完了したときに企業リストを取得
window.addEventListener('DOMContentLoaded', async () => {
    const communityId = localStorage.getItem('communityId');
    if (!communityId) {
        alert('コミュニティ情報が見つかりません。再度ログインしてください。');
        window.location.href = 'login.html';
        return;
    }

    try {
        const response = await fetch(`${SERVER_URL}api/companies/community/${communityId}`);
        if (response.ok) {
            const companies = await response.json();
            const companySelect = document.getElementById('companyId');
            companies.forEach(company => {
                const option = document.createElement('option');
                option.value = company.companyId;
                option.textContent = company.companyName;
                companySelect.appendChild(option);
            });
        } else {
            console.error('企業リストの取得に失敗しました。');
            alert('企業リストの取得に失敗しました。');
        }
    } catch (error) {
        console.error('企業リストの取得中にエラーが発生しました:', error);
        alert('サーバーとの通信に失敗しました。');
    }
});

async function registerTask(event) {
    event.preventDefault(); // フォームのデフォルトの送信をキャンセル

    // フォームから値を取得
    const taskName = document.getElementById('taskName').value;
    const taskType = document.getElementById('taskType').value;
    const companyId = document.getElementById('companyId').value;
    const deadlineValue = document.getElementById('deadline').value;

    // ローカルストレージから値を取得
    const userId = localStorage.getItem('userId');
    const communityId = localStorage.getItem('communityId');

    // 入力チェック
    if (!taskName || !taskType || !companyId || !deadlineValue) {
        alert('必須項目をすべて入力してください。');
        return;
    }

    if (companyId === "企業を選択してください") {
        alert('企業を選択してください。');
        return;
    }

    if (!userId || !communityId) {
        alert('ログイン情報が見つかりません。再度ログインしてください。');
        window.location.href = 'login.html';
        return;
    }

    // deadlineをISO 8601形式に変換
    const deadline = new Date(deadlineValue).toISOString();

    const data = {
        taskName,
        taskType,
        tag: "", // スキーマにtagは存在するが、今回は使わないので空文字
        communityId,
        related_url: "", // HTMLにフォーム要素がないため空文字
        deadline,
        userId,
        companyId: parseInt(companyId, 10) // companyIdを数値に変換
    };

    console.log('登録データ:', data);

    try {
        const response = await fetch(`${SERVER_URL}api/task`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            // 登録成功
            alert('タスクを登録しました。');
            window.location.href = 'main.html'; // メイン画面に遷移
        } else {
            // 登録失敗
            const errorData = await response.json().catch(() => null);
            const errorMessage = errorData && errorData.message ? errorData.message : '登録に失敗しました。';
            alert(errorMessage);
        }
    } catch (error) {
        console.error('登録処理中にエラーが発生しました:', error);
        alert('サーバーとの通信に失敗しました。');
    }
}
