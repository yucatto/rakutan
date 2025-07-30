async function registerCommunity(event) {
    event.preventDefault(); // フォームのデフォルトの送信をキャンセル

    // フォームから値を取得
    const communityId = document.getElementById('communityID').value;
    const communityName = document.getElementById('communityName').value;

    // 入力チェック
    if (!communityId || !communityName) {
        alert('コミュニティIDとコミュニティ名称を入力してください。');
        return;
    }

    const data = {
        communityId,
        communityName,
    };

    try {
        const response = await fetch(`${SERVER_URL}api/communities`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            // 登録成功
            // ユーザー登録画面にコミュニティIDを渡して遷移
            window.location.href = `user_register.html?communityId=${encodeURIComponent(communityId)}`;
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
