Склонировать репозиторий с помощью команды git clone

Запустить Docker

Открыть тестовый проект в IntelliJ IDEA

Для запуска контейнеров с MySql, PostgreSQL и Node.js втерминале IntelliJ IDEA использовать команду docker-compose up -d (необходим установленный Docker)


В терминале IntelliJ IDEA выполнить команду для запуска приложения:

для MySQL: java -jar ./artifacts/aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app


для Postgres: java -jar ./artifacts/aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app


В терминале IntelliJ IDEA выполнить команду для запуска тестов

для MySQL: ./gradlew clean test -D dbUrl=jdbc:mysql://localhost:3306/app -D dbUser=app -D dbPass=pass

для Postgres: ./gradlew clean test -D dbUrl=jdbc:postgresql://localhost:5432/app -DdbUser=app -D dbPass=pass

В терминале IntelliJ IDEA выполнить команду для получения отчета: ./gradlew allureServe

После завершения прогона автотестов и получения отчета:
Завершить обработку отчета нажатием клавиш CTRL + C -> y -> Enter
Закрыть приложение нажатием клавиш CTRL + C в терминале запуска.
Остановить работу контейнеров командой docker-compose down.
