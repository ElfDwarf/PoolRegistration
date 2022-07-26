# PoolRegistration
# Тестовое задание для собеседования
Выполнены все обязательные и необязательные положения, плюс добавлен не описанный в задании endpoint для получения полной информации по заказам на дату, 
иначе может потеряться ID заказа.
```
/api/v0/pool/timtetable/get
```
**Разворачивание приложения на локальной машине**

Сборка проекта (выполняется из корня)
```
./gradlew build
```

Сборка докер-образа приложения и создание постоянного хранилища для бд
```
docker image build -t poolregistration:latest . -f .\docker\Dockerfile
docker volume create --name poolregistrationdata -d local
```

Далее переходим в папку с конфигом Docker Compose и выполняем запуск сервиса и базы данных

```
cd ./docker
docker-compose up
```

## Swagger-документация
В сервисе используется автогенерируемая документация посредством Swagger ( одна из причин, почему возвращается не кастомный JSONObject, а объект нового класса для каждого запроса)
Посмотреть документацию можно запустив сервис и открыв в браузере страницу
```
http://localhost:8080/swagger-ui/index.html
```
