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
spark-submit --class "com.manny.testSpark.Main" --master spark://127.0.0.1:7077 testSpark-1.0.jar <count> <size> <iters>
```

где ``master spark://127.0.0.1:7077`` - URL-адрес главного узла Spark Cluster;<br>
``<count>`` - кол-во переменных Х<br>
``<size>`` - кол-во строк данных, от 0 до size<br>
``<iters>`` - кол-во итераций;<br>


