async function login(event) {
    event.preventDefault(); // フォームのデフォルトの送信をキャンセル

    const userId = document.getElementById('userID').value;
    if (!userId) {
        alert('ユーザーIDを入力してください。');
        return;
    }

    try {
        const response = await fetch(`${SERVER_URL}api/users?userId=${encodeURIComponent(userId)}`);

        if (response.ok) {
            // ログイン成功
            const userData = await response.json();
            var communityId = userData.communityId;

            localStorage.setItem('userId', userId);
            localStorage.setItem('communityId', communityId);

            window.location.href = 'main.html';
        } else {
            // ログイン失敗
            const errorData = await response.json().catch(() => null);
            const errorMessage = errorData ? errorData.message : 'ユーザーが存在しません。';
            if (!confirm(`${errorMessage}\nログイン画面にとどまりますか？`)) {
                // 「キャンセル」が押された場合の処理（もしあれば）
            }
        }
    } catch (error) {
        console.error('ログイン処理中にエラーが発生しました:', error);
        if (!confirm('サーバーとの通信に失敗しました。\nログイン画面にとどまりますか？')) {
            // 「キャンセル」が押された場合の処理（もしあれば）
        }
    }
}
