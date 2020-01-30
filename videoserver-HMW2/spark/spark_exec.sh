#!/bin/bash

#spark_exec

cd /usr/local/spark

./bin/spark-submit --master k8s://https://$(minikube ip):8443 --deploy-mode cluster --name spark-application --conf spark.kubernetes.container.image=m1c0l/spark:v1 --conf spark.kubernetes.executor.request.cores=0.1 --conf spark.kubernetes.authenticate.driver.serviceAccountName=spark --conf spark.kubernetes.driverEnv.KAFKA_ADDRESS=kafkaa:9092 --conf spark.kubernetes.driverEnv.KAFKA_GROUP_ID=spark-group --conf spark.kubernetes.driverEnv.KAFKA_MAIN_TOPIC=stats-topic --class com.dslab.SparkListener local:///opt/spark/myjar/spark-1.0-SNAPSHOT-jar-with-dependencies.jar
