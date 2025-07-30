const TEST_URL = 'http://localhost:8080/';
const PRODUCTION_URL = 'https://www.example.com/';


const SERVER_URL = TEST_URL;
// const SERVER_URL = PRODUCTION_URL;


// ログイン状態のチェック
// userIdがローカルストレージになく、かつ現在のページがlogin.htmlでない場合に、login.htmlにリダイレクト
if (!localStorage.getItem('userId') && !window.location.pathname.endsWith('/login.html') && !window.location.pathname.endsWith('/community_register.html') && !window.location.pathname.endsWith('/user_register.html')) {
    window.location.href = 'login.html';
}
