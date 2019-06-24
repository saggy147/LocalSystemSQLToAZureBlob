package com.syntel.practice.solutions.app;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Application {

	public static void main(String[] args) {

		SparkConf conf = new SparkConf().setAppName("DI-Cloud").setMaster("local[*]");
		SparkContext sc = new SparkContext(conf);
		sc.hadoopConfiguration().set("fs.wasb.impl", "org.apache.hadoop.fs.azure.NativeAzureFileSystem");
		sc.hadoopConfiguration().set("fs.AbstractFileSystem.wasb.impl", "org.apache.hadoop.fs.azure.Wasb");
		sc.hadoopConfiguration().set("fs.azure.account.key.myteststorageaccount1233.blob.core.windows.net",
				"3zOVRrDLVBEUX1NMXXWPCnyXI96jGQwiU+nIUs179tArH/96BALVgS8TNfxMrWVLUGFTCZSQ8owVTdUgiIeR9Q==");
		SparkSession sql = new SparkSession(sc);

		Dataset<Row> mysqldf = sql.read().format("jdbc").option("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
				//.option("url", "jdbc:mysql://10.119.32.97:3306/test").option("dbtable", "Employee").load();
				.option("url", "jdbc:sqlserver://SYNGDCL0327:61913;databaseName=AzureTrainingDB")
				.option("user", "AzureTrainingDBUser")
				.option("password","user")
				.option("dbtable", "dbo.Employee").load();
				
		mysqldf.show();

		mysqldf.toJavaRDD().saveAsTextFile("wasbs://mytestcontainer@myteststorageaccount1233.blob.core.windows.net/op");
		
	}
}

