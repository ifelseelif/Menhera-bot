# Menhera-bot
Новая версия бота ассистента для студентов ITMO
<h3 align=center>
Поддерживаемые социальные сети:<br><br>
    <a href="https://vk.com/itmo_xcore"><img src="https://image.flaticon.com/icons/svg/906/906377.svg" width=75 height=75/></a>
    <a href="https://telegram.me/menheraitmoBot"><img src="https://image.flaticon.com/icons/svg/145/145813.svg" width=75 height=75/></a>
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
