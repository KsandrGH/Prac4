# Вариант №6 — Нормализация телефонных номеров в тексте

Программа читает текст из файла, находит телефонные номера (с возможными ошибками/разделителями),
меняет **код страны** на заданный (по умолчанию `+1`) и приводит номер к единому формату:

**`+1 (AAA) BBB-CC-DD`**

Пример:
- Было: `Звоните по номеру +79000000000`
- Стало: `Звоните по номеру +1 (900) 000-00-00`

## Что умеет находить (типичные «ошибки» записи)
Поддерживаются варианты записи, например:
- `+7 900 000 00 00`
- `8(900)000-00-00`
- `7-900-0000000`
- `9000000000` (10 цифр без кода)
- `+7(900) 0000000`
и т.п. — программа забирает **цифры**, игнорируя пробелы/скобки/дефисы/точки.

Правило обработки цифр:
- если цифр **10** → это локальный номер `AAA BBB CC DD`
- если цифр **11** и первая `7` или `8` → берём последние 10 цифр
- иначе совпадение оставляем как есть (чтобы не портить не‑телефонные числа)

## Что установить
### 1) Java (JDK)
- JDK 25 (LTS) (или новее), проект компилируется под Java 25.
Проверка:
```bash
java -version
javac -version
```

### 2) Maven
- Apache Maven 3.9+
Проверка:
```bash
mvn -version
```

### 3) Git (для отправки на GitHub)
Проверка:
```bash
git --version
```

## Сборка и запуск (терминал)

1) Собрать runnable jar:
```bash
mvn -q -DskipTests package
```

2) Запуск (вывод в консоль):
```bash
java -jar target/phone-variant6-1.0.0.jar data/input.txt
```

3) Запуск с записью в файл:
```bash
java -jar target/phone-variant6-1.0.0.jar data/input.txt data/output.txt
```

4) (Опционально) задать другой код страны:
```bash
java -jar target/phone-variant6-1.0.0.jar data/input.txt data/output.txt --cc=44
```
Тогда будет формат `+44 (AAA) BBB-CC-DD`.

## Как отправить на GitHub

```bash
git init
git add .
git commit -m "init: variant 6 phone normalization"
git branch -M main
git remote add origin https://github.com/<YOUR_LOGIN>/<REPO_NAME>.git
git push -u origin main
```

## Где смотреть основную логику
- `src/main/java/com/example/phone/PhoneNormalizer.java`
- `src/main/java/com/example/phone/Main.java`

