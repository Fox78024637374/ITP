# Лабораторная работа 2: Классы и инкапсуляция

Условие: [`assignments/lab02-domain-model.md`](../assignments/lab02-domain-model.md).

Из корня репозитория запустите:

```bash
./mvnw -pl lab02 test
```

Windows PowerShell: `.\\mvnw.cmd -pl lab02 test`.

Основной код размещайте в `src/main/java/edu/course/lab02/`, тесты — в `src/test/java/edu/course/lab02/`. В начале каждого Java-файла укажите `package edu.course.lab02;`.

Модуль независим от остальных лабораторных. Если нужны модели из предыдущей работы, перенесите и при необходимости адаптируйте только нужные классы в этот модуль. Начальная успешная сборка не является выполнением задания: создайте реализацию и собственные тесты по условию.






# Лабораторная работа 2: Классы и инкапсуляция

## Инварианты BankAccount

- `balance >= 0` всегда.
- Начальный баланс не может быть отрицательным.
- `deposit(amount)` принимает только `amount > 0`.
- `withdraw(amount)` принимает только `amount > 0` и `amount <= balance`.
- Сеттера баланса нет.

## Инварианты DataSample

- `id != null`, `id.isBlank() == false`.
- `label != null`, `label.isBlank() == false`.
- `status != null`.
- `features != null`, `features.length > 0`.
- Массив копируется в конструкторе и при возврате из `getFeatures()`.
- `status` меняется только через `changeStatus`, `null` запрещён.
- `isReady() == true` только при `status == READY`.
- `meanFeatures()` возвращает среднее арифметическое.

## Дополнительно

- `SampleId` — неизменяемый record, `value` не может быть `null` или пустым.
- `normalized(min, max)` возвращает **новый** `DataSample`, исходный не меняется.