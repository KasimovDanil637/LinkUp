# Социальная сеть - Linkup

<hr>

## Описание

<div style="text-align: left; width: 600px">
    Проект представляет собой социальную сеть, где каждый пользователь может отправлять посты, лайкать, а также добавляться
    в друзья к другим пользователям
</div>

<hr>

## Схема
<img style="border-radius: 10px" src="Screenshots/scheme_service.png" width="300px" alt="">

<br>

<div style="text-align: left; width: 600px">
Приложение представляет собой микросервис. В frontend используется фреймворк Vue.js. Backend представляет собой 
SpringBoot приложение, который общается с frontend с помощью REST API. Также присутствует небольшой сервер
на Python Flask, который принимает данные с основного сервера, производит некоторые вычисления, формирует 
json объект и отправляет его обратно на сервер, где этот объект будет передан во frontend для отображения данных
статистики пользователя. В качестве базы данных используется Postgresql
</div>

<hr>

## Схема базы данных
<img style="border-radius: 10px" src="Screenshots/scheme_db.png" width="500px" alt="">

<hr>

## Функционал
### Вход и регистрация
<img  src="Screenshots/login.png" width="400px" alt="">
<img  src="Screenshots/Регистрация.png" width="400px" alt="">

### Различные проверки данных пользователя 
<img  src="Screenshots/LoginError_1.png" width="400px" height="350px" alt="">
<img  src="Screenshots/LoginError_2.png" width="400px" height="350px" alt="">

### Переход на профиль пользователя

<img  src="Screenshots/Профиль.png" width="800px" height="400px" alt="">

### Возможность редактирования данных пользователя

<img  src="Screenshots/Редактирование_профиля.png" width="800px" height="400px" alt="">

### Отправка постов 
<img  src="Screenshots/Отправка%20поста.png" width="800px" alt="">
<img  src="Screenshots/Оформление_Поста.png" width="800px" alt="">

### Отметки "Нравится"

<img  src="Screenshots/Likes.png" width="800px" alt="">

### Отправление запроса на добавление в друзья 

<img  src="Screenshots/invite.png" width="800px" alt="">

### Список друзей

<img  src="Screenshots/friends.png" width="800px" alt="">

### Новости (посты всех пользователей)

<img  src="Screenshots/news.png" width="800px" alt="">

"Возможные друзья" - это список друзей, друзей пользователя

### Статистика

<img  src="Screenshots/stats.png" width="800px" alt="">

### Полнотекстовый поиск 

Используется "to_tsvector" для поиска пользователя по его имени аккаунта

<img  src="Screenshots/search.png" width="300px" alt="">

### Администратор

В отличие от обычного пользователя, может удалять чужие посты
<img  src="Screenshots/admin.png" width="800px" alt="">

