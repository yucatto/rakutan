# =====================================================================
# Stage 1: Builder - アプリケーションをビルドするステージ
# =====================================================================
FROM gradle:jdk17-alpine AS builder

# タイムゾーンと作業ディレクトリを設定
ENV TZ=Asia/Tokyo
WORKDIR /workspace/app

# --- 依存関係のキャッシュレイヤー ---
# 1. まずビルドに必要なファイルのみをコピーする
COPY build.gradle settings.gradle ./
COPY gradlew ./
COPY gradle ./gradle/


# 2. gradlewに実行権限を付与し、依存関係をダウンロードする
# このステップはbuild.gradleやsettings.gradleが変更された場合にのみ実行される
RUN chmod +x ./gradlew && ./gradlew dependencies --no-daemon

# --- ソースコードのビルドレイヤー ---
# 3. アプリケーションのソースコードをコピーする
COPY src ./src

# 4. 依存関係を再利用してアプリケーションをビルドする
# ソースコードの変更時には、キャッシュされた依存関係が使われるためビルドが高速になる
RUN ./gradlew build -x test --no-daemon


# =====================================================================
# Stage 2: Runner - アプリケーションを実行するステージ
# =====================================================================
FROM eclipse-temurin:17-jre-alpine

# タイムゾーンと作業ディレクトリを設定
WORKDIR /app
ENV TZ=Asia/Tokyo

# --- セキュリティ向上のための非rootユーザー設定 ---
# 5. アプリケーション実行用の専用ユーザーとグループを作成
RUN addgroup -S spring && adduser -S spring -G spring

# 6. 作成したユーザーに切り替え
USER spring:spring

# --- アプリケーションの実行 ---
# 7. ビルダーステージからビルドされたJARファイルのみをコピー
COPY --from=builder /workspace/app/build/libs/*.jar app.jar

# 8. ポートを公開
EXPOSE 8080

# 9. アプリケーションを起動
ENTRYPOINT ["java", "-jar", "/app/app.jar"]