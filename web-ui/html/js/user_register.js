async function registerUser(event) {
    event.preventDefault(); // フォームのデフォルトの送信をキャンセル

    const userId = document.getElementById('userID').value;
    const userName = document.getElementById('userName').value;
    const email = document.getElementById('email').value;
    const communityId = document.getElementById('communityID').value;

    if (!userId || !userName || !email || !communityId) {
        alert('すべての項目を入力してください。');
        return;
    }

    const data = {
        userId,
        userName,
        email,
        communityId
    };
    console.log('登録データ:', data);

    try {
        const response = await fetch(`${SERVER_URL}api/users`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            // 登録成功
            localStorage.setItem('userId', userId);
            localStorage.setItem('communityId', communityId);
            window.location.href = 'main.html';
        } else {
            // 登録失敗
            const errorData = await response.json().catch(() => null);
            const errorMessage = errorData ? errorData.message : '登録に失敗しました。';
            alert(errorMessage);
        }
    } catch (error) {
        console.error('登録処理中にエラーが発生しました:', error);
        alert('サーバーとの通信に失敗しました。');
    }
}
