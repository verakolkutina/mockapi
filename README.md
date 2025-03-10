<<<<<<< HEAD
# mockapi
=======


model
UserRequest нужен для запроса на регистрацию.
UserData нужен для хранения информации о пользователе (логин → { токен, пароль }).
UserService должен хранить UserData, а не просто String (токен).

service
как вариант
synchronized при регистрации и удалении пользователя
если два запроса регистрируют одного и того же пользователя одновременно. плюс проверка на ноль!
1)public synchronized String register(String login, String password) {
if (login == null || password == null) {
throw new IllegalArgumentException("Логин и пароль не могут быть null.");
}
if (userStorage.containsKey(login)) {
throw new IllegalArgumentException("Пользователь уже существует.");
}
String token = UUID.randomUUID().toString();
userStorage.put(login, new UserData(token, password));
tokenStorage.put(token, login);  // Храним токен отдельно
return token;}

2)public synchronized String deleteUser(String login) {
if (login == null) {
throw new IllegalArgumentException("Логин не может быть null.");
}
UserData removedUser = userStorage.remove(login);
if (removedUser == null) {
throw new IllegalArgumentException("Пользователь не найден.");
}
tokenStorage.remove(removedUser.getToken());  // Удаляем токен
return "Пользователь удалён.";
}

какой подход исполтзовать?

	Скорость
1 (Stream API)	Проверяет каждого пользователя и ищет токен.	O(n) (медленно при большом числе пользователей)
2 (Map)	Сразу ищет токен в Map, без проверки всех пользователей.	O(1) (быстро)
добавим Map
>>>>>>> 7dfa0dd (Добавлена заглушка на Java)
