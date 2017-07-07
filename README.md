# 学习scala和spark

## eclipse搭建scala环境

## 使用maven创建scala项目

## 使用maven引用spark开发包进行开发

## 本地运行 及在spark上运行
* conf.setMaster("local") 可以直接eclipse本地运行，不设置时，使用maven打成jar，通过spark-submit可运行

## 使用spark处理实际需求
* 使用 spark streaming接收kafka消息
	* 使用spark自带的kafkaWordCount例子运行，遇到的问题，首先是编译提示缺类，
	kafka/serializer/StringDecoder 对应 kafka_2.10-0.9.0.1.jar
	org.apache.spark.streaming.kafka.KafkaUtils 对应spark-streaming-kafka_2.10-1.5.2.jar
	* 使用spark-submmit运行时，提示缺少java.lang.NoClassDefFoundError: org/apache/spark/streaming/kafka/KafkaUtils$，使用--jars 将对应jar加进来也不行（其实好使，见下补充说明）
	解决办法：使用maven maven-assembly-plugin插件，将对应依赖打包进去，运行成功 
	见：https://stackoverflow.com/questions/27710887/kafkautils-class-not-found-in-spark-streaming#
	* 补充说明，之前引入jar包不好使是--jars的位置放在了后边，根据抛出来的异常，找到对应的jar，将其引入也可以正常运行，命令：`spark-submit --name test --class com.boco.ta.testscala.KafkaWordCount --jars spark-streaming-kafka_2.10-1.5.2.jar,kafka_2.10-0.9.0.1.jar,zkclient-0.7.jar,metrics-core-2.2.0.jar,kafka-clients-0.9.0.1.jar testscala-0.0.1-SNAPSHOT.jar`
* 使用spark streaming将kafka消息写入hdfs中


