# Начисление бонусов за покупку
Приложение ведёт счёт клиента, списывая с него деньги за покупки и начисляя бонусы.

Покупки совершаются путём вызова следующих сервисов:
- POST /api/payment/Shop/{amount} -- для совершения покупки через обычный магазин
- POST /api/payment/Online/{amount} -- для совершения покупки через онлайн магазин

где {amount} -- сумма покупки

За покупки, совершенные в обычном магазине, начисляется кэшбэк, равный 7% стоимости покупки. За покупки, совершенные 
через онлайн магазин, начисляется кэшбэк в размере 10% от стоимости покупки.

Также действуют следующие правила начисления кэшбэка:
- если сумма покупки меньше 10 у.е., то размер кэшбэка будет равен 10% вне зависимости от типа магазина. Так же, 
в этом случае увеличивается счётчик возврата (что бы это ни значило).
- если сумма покупки больше 300 у.е., то размер кэшбэка увеличивается до 30% для всех типов магазинов.

В случае нехватки средств от сервера приходит ответ с кодом 422 и сообщением: "Недостаточно средств на счёте".

Для проверки остатка средств, начисленных бонусов и количества "возвратов" используются следующие эндпойнты:
- GET /api/money -- для проверки остатка средств,
- GET /api/bankAccountOfEMoney -- для проверки количества бонусов
- GET /api/returns -- для проверки количества "возвратов"

## Технические подробности

Размеры скидок и пороги покупок устанавливаются через свойства в файле application.properties:
- cashback.shop -- процент кэшбэка за покупки в обычном магазине
- cashback.online -- процент кэшбэка за покупки в онлайн магазине
- cashback.big_purchase -- процент кэшбэка за большую покупку
- cashback.small_purchase -- процент кэшбэка за маленькую покупку
- price.big_purchase -- сумма большой покупки
- price.small_purchase=10 -- сумма маленькой покупки

База данных не используется, все значения хранятся в памяти.
