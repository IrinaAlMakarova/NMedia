### 1.1. Android Studio, SDK, эмулятор и первое приложение

## Задача 1
1. Создайть проект с текстовой надписью на экране NMedia!
2. Изменить иконку приложения.

## Задача 2
Доверстайте приложение, чтобы получился следующий вид:
![](https://github.com/IrinaAlMakarova/NMedia/blob/main/pic/layout.png?raw=true)

Обратите внимание на блок с количеством просмотров. С ним есть небольшая проблема, поскольку он расположен справа. Если количество просмотров вырастет, например, до 500, то есть два варианта:
1. Установить фиксированное расстояние от текста до границы родителя, а саму иконку приклеить к границе текста.
2. Оставить достаточное количество места, чтобы поместилось и 500, и 1К. Тогда на всех карточках положение этого блока будет одинаковым.
Проведите небольшое исследование и посмотрите, каким образом это реализовано во ВКонтакте.

## Задача 3
1. При клике на like должна меняться не только картинка, но и число рядом с ней: лайкаете — увеличивается на 1, дизлайкаете — уменьшается на 1. 
2. При клике на share должно увеличиваться число рядом: 10 раз нажали на share — +10.
3. Если количество лайков, share или просмотров перевалило за 999, должно отображаться 1K и т. д., а не 1 000. Предыдущие функции должны работать: если у поста было 999 лайков и вы нажали like, то должно стать 1К, если убрали лайк, то снова 999.
   
   Обратите внимание:
1. 1.1К отображается по достижении 1 100.
2. После 10К сотни перестают отображаться.
3. После 1M сотни тысяч отображаются в формате 1.3M.
4. Подумайте, можно ли это вынести в какую-то функцию вместо того, чтобы хранить эту логику в Activity.

## Задача 4
Вам нужно переделать предыдущую реализацию на MVVM. Всё, что работало до этого: увеличение количества лайков, share и 1K — должно продолжать работать.

Опубликуйте изменения в вашем проекте на GitHub в ветке mvvm. Создайте из этой ветки Pull Request в master. Убедитесь, что apk собирается с помощью GitHub Actions и при установке в эмуляторе приложение работает корректно.

## Задача 5
Добавьте в свой проект реализацию отображения списков на базе RecyclerView и ListAdapter.
В примерах на лекции у нас был только OnLikeListener. Сделайте также OnShareListener.