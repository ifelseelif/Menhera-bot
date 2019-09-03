# Menhera-bot
Новая версия бота ассистента для студентов ITMO
<h3 align=center>
    <img src="https://travis-ci.org/AppLoidx/Menhera-bot.svg?branch=master" /><br>
Поддерживаемые социальные сети:<br><br>
    <a href="https://telegram.me/menheraitmoBot"><img src="https://image.flaticon.com/icons/svg/906/906377.svg" width=75 height=75/></a>
    <a href="https://vk.com/itmo_xcore"><img src="https://image.flaticon.com/icons/svg/145/145813.svg" width=75 height=75/></a>
</h3>
<h3 align=right><img src="https://sun9-33.userapi.com/c848616/v848616126/142d19/wP-FvHTZ9tc.jpg"></h3>

В проекте используется [Lombok](https://projectlombok.org/), поэтому не удивляйтесь аннотациям `@Getter`, `@Setter` или `@Data`.

## Создание команды
Если вы хотите создать свою команду,то вам необходимо:
* Создать класс, наслудующий `NamedCommand` и аннотированный `@Command`
```java
@Command
public class MyCommand extends NamedCommand{
    @Override
    public Message execute(Message message) {
        return null;
    }
}
```
* Переопределить методы `getName()` и `getAliases()`
```java
@Command
public class Bye extends NamedCommand{
    @Override
    public String getName() {
        return "bye";
    }

    @Override
    public Message execute(Message message) {

        Message newMessage = Message.reverseMessage(message);
        newMessage.setMessage("Bye bye!");
        return newMessage;
    }

    @Override
    public List<String> getAliases() {
        List<String> list = new ArrayList<>();
        list.add("away");
        list.add("good night");
        return list;
    }
}
```
* Добавить бизнес-логику в метод `execute`

  * Этот метод получает в качестве аргумента объект `Message`. 
Вернуть можно либо объект `Message`, который выполнится (отправится к тому, кто указан в поле `to`) или `null` (ничего не произойдет)

## Message
`Message` - это объект для пересылки данных между разными методами. Например, структура сообщений VK отличается от структуры Telegram, поэтому был создан общий объект.

**Message имеет 4 параметра**:
* `message` - сообщение которое он хранит
* `to` - адресат (vk id или telegram nickname)
* `from` - идентификатор отправителя (vk id или telegram nickname)
* `sourceType` - источник сообщения VK или Telegram

В последствии его можно передавать реализациям интерфейса `MessageSender`, который отправит сообщение, либо возвращать в методе `execute` у `@Command` класса.

Также `Message` содержит несоклько методов для работы:
* `.reverseMessage(Message)` - статический метод, который возвращает объект `Message` с ротированными `to` и `from` (меняет значения местами)
* `toLowerCase()` - переносит в нижний регистр текст сообщения (`deprecated`)
