package com.boco.ta.testscala

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

/**
 * 说明：本地程序运行，加载hadoop的配置时报错，因为运行在本地，是找不到的，但不影响测试。 
 * 
 */
object WordCountApp {
  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      System.err.println("Usage: <file>")
      System.exit(1)
    }

    val conf = new SparkConf()
    conf.setAppName("TestSpark")
    //通过setMaster来设置程序要链接的Spark集群的Master的URL,如果设置为local, 
    //则代表Spark程序在本地运行，特别适合于机器配置条件非常差的情况。 
    conf.setMaster("local") //设置local使程序在本地运行，不需要安装Spark集群 
    var sc = new SparkContext(conf)
    var lines = sc.textFile(args(0), 1)
    //对每一行的字符串进行拆分并把所有行的拆分结果通过flat合并成一个大的集合  
    val words = lines.flatMap { line => line.split(" ") }

    /**
     * 4.2.在单词拆分的基础上对每个单词实例计数为1，也就是word => (word,1)
     */
    val pairs = words.map { word => (word, 1) }
    //对相同的key进行value的累积（包括Local和Reducer级别同时Reduce）  
    val wordCounts = pairs.reduceByKey(_ + _)
    //打印输出  
    wordCounts.foreach(pair => println(pair._1 + ":" + pair._2))

    //以下一行完成上述操作
    //    lines.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).collect().foreach(println)
    sc.stop()
  }
}