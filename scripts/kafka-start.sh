sudo sh $KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties &
sleep 100
sudo sh $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server-0.properties &
sudo sh $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server-0.properties &
sudo sh $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server-0.properties &

sleep 300

kafka-topics.sh --create --zookeeper localhost:2181 --topic invoice --partitions 5 --replication-factor 3 --config segment.bytes=1000000