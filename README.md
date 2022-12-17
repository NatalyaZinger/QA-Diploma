# Порядок запуска проекта и тестов

1. Склонировать репозиторий с помощью команды ```git clone```

2. Открыть тестовый проект в IntelliJ IDEA

3. Для запуска контейнеров с MySql, PostgreSQL и Node.js втерминале IntelliJ IDEA использовать команду ```docker-compose up``` (необходим установленный Docker)


4. В терминале IntelliJ IDEA выполнить команду для запуска приложения:


- для MySQL: 
 
 ```
 java -jar ./artifacts/aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app
 ```


- для Postgres: 
```
java -jar ./artifacts/aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app
```

5. В терминале IntelliJ IDEA выполнить команду для запуска тестов:

- для MySQL: 
```
./gradlew clean test -DdbUrl=jdbc:mysql://localhost:3306/app -DdbUser=app -DdbPass=pass
```

- для Postgres: 
```
./gradlew clean test -DdbUrl=jdbc:postgresql://localhost:5432/app -DdbUser=app -DdbPass=pass
```

6. В терминале IntelliJ IDEA выполнить команду для получения отчета: ```./gradlew allureServe```

7. После завершения прогона автотестов и получения отчета:
- Завершить обработку отчета нажатием клавиш CTRL + C -> y -> Enter
- Закрыть приложение нажатием клавиш CTRL + C в терминале запуска.
- Остановить работу контейнеров командой ```docker-compose down```
