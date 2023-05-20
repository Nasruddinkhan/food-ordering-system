# 1. Up the zookeeper from below command
docker-compose -f common.yml -f zookeeper.yml up

# 2. Check the health of zookeeper from below command
echo ruok | nc localhost 2181

# 3. Run the kafka cluster using below command
echo ruok | nc localhost 2181

# 4. Create the topic by using below command
docker-compose -f init_kafka.yml -f common.yml up