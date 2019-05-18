# SPARK_TEST
Тестовое задание Siemens

## Задание
Реализовать распределенную версию градиентного спуска в Apache Spark на Java,
показать свойства реализованного алгоритма.

## Запуск
Для создания JAR требуестя выполнить команду <br>
```mvn package```

Для запуска Jar требуется выполнить команду <br>
```
spark-submit --class "com.manny.testSpark.Main" --master spark://127.0.0.1:7077 testSpark-1.0.jar <generate> <file> <iters> <step>
```
или
```
spark-submit --class "com.manny.testSpark.Main" --master spark://127.0.0.1:7077 testSpark-1.0.jar <generate> <count> <size> <iters>
```

где ``master spark://127.0.0.1:7077`` - URL-адрес главного узла Spark Cluster;<br>
``<generate>`` - флаг генерации значений, 1 - да, 0 - нет<br>
``<count>`` - кол-во переменных Х<br>
``<size>`` - кол-во строк данных, от 0 до size<br>
``<file>`` - путь к файлу со значениями;<br>
``<iters>`` - кол-во итераций; оптимальное значение для работы - 100<br>
``<step>`` - размер шага; оптимальное начение для работы - 0.1<br>

Файл со значениями выглядит следующим образом:
```
Y11 X11 X12 ... X1n
Y21 X21 X22 ... X2n
Ym1 Xm1 Xm2 ... Xmn
```


