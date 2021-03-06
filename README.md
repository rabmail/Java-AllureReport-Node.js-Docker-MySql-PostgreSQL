# Тестирование веб-сервиса "Путешествие дня"
**Приложение предлагает купить тур по определённой цене с помощью двух способов:**

1. Обычная оплата по дебетовой карте
1. Оплата в кредит по дебетовой карте

**_Уникальная технология: выдача кредита по данным банковской карты_**

*Приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:*
1. сервису платежей (Payment Gate)
1. кредитному сервису (Credit Gate)

_Приложение должно в собственной СУБД сохранять информацию о том, каким способом был совершён платёж и успешно ли он был совершён,
для хранения информации можно использовать на выбор одну из перечисленных СУБД (MySql, PostgreSQL)_

## Процедура запуска тестов:

### Для запуска тестов на вашем ПК должно быть установлено следующее ПО:
1. IntelliJ IDEA 
2. Node.js
3. Docker Desktop

### Запуск сервиса "Путешествие дня"
1. Запускаем контейнеры MySql, PostgreSQL и Node:  docker-compose up -d --force-recreate
1. Проверяем, что контейнеры запустились: docker-compose ps

### Запуск приложения с базой MySql:
1. Запускаем приложение и передаем базу MySql:

java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar

_при успешном запуске на экране появится сообщение: Started ShopApplication in 9.766 seconds (JVM running for 11.274)_

1. Запускаем тесты командой:

gradlew clean test -Durl=jdbc:mysql://localhost:3306/app -Duser=user -Dpassword=pass 

## Запуск приложения с базой PostgreSQL
1. Запускаем приложение и передаем базу PostgreSQL: 

java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar

при успешном запуске на экране появится сообщение: _Started ShopApplication in 9.766 seconds (JVM running for 11.274)_

2. Запускаем тесты командой:

gradlew clean test -Durl=jdbc:postgresql://localhost:5432/app -Duser=user -Dpassword=pass 


## Формирование отчета AllureReport по результатам тестирования
_При первоначальном формировании отчета необходимо выполнить команду 1 раз:_

gradlew allureReport (для загрузки AllureReport )

_Отчет формируется командой_ 

gradlew allureServe

Отчет откроется в браузере автоматически.

## Перезапуск приложения и тестов

Если необходимо перезапустить приложение, нужно остановить работу сервисов в терминале, нажав в них Ctrl+С



## Документация: 

Комплексный отчёт о проведённой автоматизации тестирования

**[План тестирования](https://github.com/rabmail/Diplom_Netology/blob/fcaea2c26b6be865a70da1833ff4acea3d121d4c/report/Plan.md "План тестирования")**

**[Отчет о тестировании](https://github.com/rabmail/Diplom_Netology/blob/80cf34ac7eef9c7c50010faba39656d2692cca69/report/Report.md "Отчет о тестировании")**

**[Итоговый отчёт о проведённой автоматизации](https://github.com/rabmail/Diplom_Netology/blob/54d28798fad4f1b186e72822dbe2d614d5e6c761/report/Summary.md "Итоговый отчёт о проведённой автоматизации")**
