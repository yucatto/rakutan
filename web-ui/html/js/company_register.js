async function registerCompany(event) {
    event.preventDefault(); // フォームのデフォルトの送信をキャンセル

    // フォームから値を取得
    const companyName = document.getElementById('companyName').value;
    const jobType = document.getElementById('jobType').value;

    // ローカルストレージから値を取得
    const communityId = localStorage.getItem('communityId');

    // 入力チェック
    if (!companyName || !jobType) {
        alert('企業名と職種を入力してください。');
        return;
    }

    if (!communityId) {
        alert('コミュニティ情報が見つかりません。再度ログインしてください。');
        window.location.href = 'login.html';
        return;
    }

    const data = {
        communityId,
        companyName,
        jobType,
    };

    try {
        const response = await fetch(`${SERVER_URL}api/companies`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            // 登録成功
            window.location.href = 'main.html'; // メイン画面に遷移
        } else {
            // 登録失敗
            const errorData = await response.json().catch(() => ({ message: '登録に失敗しました。' }));
            alert(errorData.message);
        }
    } catch (error) {
        console.error('登録処理中にエラーが発生しました:', error);
        alert('サーバーとの通信に失敗しました。');
    }
}
