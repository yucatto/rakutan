@echo off
chcp 65001 > nul

:: ---------------------------------------------------------------
:: Spring Initializr プロジェクトをダウンロードし、展開するバッチファイル
:: ---------------------------------------------------------------

:: --- 設定 (ここを自由に変更してください) ---

:: プロジェクト名（兼アーティファクトID）
set PROJECT_NAME=spring-app

:: グループID
set GROUP_ID=jp.kobe_u.eedept.es4

:: Spring Boot のバージョン
set BOOT_VERSION=3.4.5

:: Java のバージョン
set JAVA_VERSION=17

:: 含めたい依存関係 (コンマ区切りで指定)
set DEPENDENCIES=data-jpa,validation,web,webflux,lombok,devtools,mysql

:: --- 処理本体 (ここから下は変更不要) ---

set "ZIP_FILE=%PROJECT_NAME%.zip"

echo.
echo %PROJECT_NAME% プロジェクトをダウンロードします...
curl "https://start.spring.io/starter.zip?type=gradle-project&language=java&bootVersion=%BOOT_VERSION%&groupId=%GROUP_ID%&artifactId=%PROJECT_NAME%&name=%PROJECT_NAME%&javaVersion=%JAVA_VERSION%&dependencies=%DEPENDENCIES%" -o "%ZIP_FILE%" -L

if not exist "%ZIP_FILE%" (
    echo.
    echo [エラー] ダウンロードに失敗しました。ネットワーク接続や設定を確認してください。
    goto :eof
)

echo.
echo %ZIP_FILE% を展開します...
tar -xf "%ZIP_FILE%"

echo.
echo %ZIP_FILE% を削除します...
del "%ZIP_FILE%"

echo.
echo 完了しました！ '%PROJECT_NAME%' ディレクトリにプロジェクトが作成されました。
