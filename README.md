# Menhera-bot
Новая версия бота ассистента для студентов ITMO

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
