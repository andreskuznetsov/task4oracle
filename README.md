Приложение по расписанию получает данные от сторонних сайтов (в данном случае - текущая погода, получаем с яндекса) 
через парсинг страницы (api нет), сохраняет их в БД, и раз в сутки по дням указанным в профиле пользователя,
отправляет ему e-mail с последними не отправленными данными.
Использовано: WebSphere, Oracle, Java mail, Spring Scheduler.
