# Apache Kafka:

Apache Kafka is distributed, fault tolerance and highly scalable message broker and stream processing platform. It is designed to handle large volumes of data in streams in real time and fault tolerance manner.

# Zookeeper ( use KRaft)

Kafka relies on Zookeeper for distributed coordination and management of the Kafka Cluster. The Key role of Zookeeper is to manage Kafka Brokers.


Kafka can scale horizontally by adding more brokers to the cluster providing high throughput and low latency data processing.
Messages in kafka are persisted to disk providing durability even in the event of node failures.
Kafka provides configurable data retention policies allowing organizations to retain messages for specified duration.

# Kafka Cluster:

A Kafka Cluster is a group of brokers/servers working together for3 reasons

speed (low latency)
durability
scalability.

several data streams can be processed by separate servers, so if one fails, another server has the data backed up, ensuring stability - meaning data durability and availability.
Kafka also balances the load across multiple servers to provide scalability.

i.e Kafka Cluster is a group of brokers/servers working together to manage, storage and exchange of data in the form of messages.

# Kafka Brokers:

Kafka Broker is the core of the Kafka Cluster. The Cluster stores and manages the stream of Records.
With in the Broker we have topics. Topics in Kafka are used to categorize the messages.
With in the Broker we have partitions. Topics are divided into partitions allowing kafka to parallelize processing and scale horizontally.

Kafka brokers are servers with special jobs to do:

1.managing load balancing,
2. replication
3. Stream decoupling within the Kafka cluster.

How do they get these jobs done?

First of all, in order to start Kafka Cluster, the developer authenticates to a bootstrap server. These are the first servers in the cluster. Then, the brokers also balance the load and handle replication and those two features are key to Kafka's speed, scalability and stability.

A kafka broker is an individual kafka server that stores data and serves client request. Brokers with in the cluster communicate with each other to ensure data replication and maintain meta data. Each broker in a kafka cluster is assigned a unique identifier.

# Kafka Producer

An Kafka Producer is a client application that published events to a kafka cluster.

# Kafka Consumer:

An Kafka Producer is a client application that subscribes to events. Consumers can be part of consumer group allowing them to parallelize to messages.

# Kafka Topic:

A Kafka Topic is a logical channel or feed category to which records (messages) are published by producers and from which records are consumed by consumers.

Topics serve as a way to organize and categorize the stream of messages with in the kafka message system.

# Kafka Partition:

A Partition is a basic unit if parallelism and scalability. It is a way of horizontally dividing a topic into multiple independently managed units. Each partition is strictly ordered, immutable sequence of records and it plays a crucial role in distribution, parallel processing and fault tolerance of data with in a kafka cluster.

producers can write and consumers can read from different partitions concurrently allowing kafka to handle high volume of data by distributing the workload across multiple partitions. kafka achieves the scalability by distributing partitions across multiple brokers by allowing each broker to handle a subset of partitions. As the load increased we can more brokers to kafka cluster and partitions are automatically re-assign to maintain load balance and improve through put. Message with in a partition are strictly ordered based on their offset which is a unique identifier assigned to each message in the partition. This ordering is guaranteed per partition but not across partitions. Once a message written to partition and it becomes a part of immutable log. This characteristic simplifies the data processing and ensures consistency with in a partitions.

# Kafka Offsets
  
Offset is a sequence of unique ids given to messages as they arrive at a partition. Once the offset is assigned, it will never be changed. The first message get gets an offset zero. The next message receives an offset one and so on.. It represents the position or location of the messages in the partition log. Offsets are used to track the process of consumers and enable them to resume consumption from a specific point particularly at failure or restart.

## Lets understand how offsets work:

### Producers Perspective:

Producers sends a message to the Kafka Topic. The message is appendant to the log of the appropriate partition.
The Producer receives a acknowledgement once the message is successfully written to the partition.
At this point the message is considered to have an offset.


### Consumer Perspective:

Consumers track the offset at the last processed message in each partition they consume from. As the consumer process messages it updates its offset to reflect the position of the last successfully processed message. The offset is committed to the special kafka topic called consumer offsets topic, so these topic stores the mapping between consumer groups and their committed offsets.

In the event of consumer restart / failure, the consumer uses the committed offset from the special kafka topic called consumer offset to determine the last processed message for each partition. so then consumer consumes consumption from the stored offset ensuring that it continues from the point of the last successfully processed message.


### Offset Committing:

Consumers can write offsets periodically or after processing a batch of messages. This commit operation updates the stored off set or the kafka special offset called consumer offset topic.

Kafka provides different mechanism for committing

automatic committing or manual committing depending on the configuration.


# Consumer Groups:

Consumer Groups are a mechanism designed to enable parallel and scalable processing of messages across multiple instances of a consumer application. so consumer groups allow a set of consumers to work together to consume and process message from one or more partitions.

This mechanism is useful for achieving high throughput data processing and load balancing in s distributed systems.

A consumer group is a logical grouping of kafka consumers  that work together to consume and process message from one or more partitions of a topic. Each partition in a topic can be assigned to at most one consumer with in a consumer group.

consumer groups are especially beneficial in scenarios where there is a need of parallel and scalable processing of large volumes of data,
high throughput data streams need to be distributed and processed concurrently, where load balancing across multiple consumers is essential for efficient resource utilization.

# JSON Serialization in Apache Kafka and Spring Kafka

Apache Kafka stores and transports data in **byte format**. To handle this, Kafka provides several built-in **serializers and deserializers**. However, it does not include built-in support for JSON.

## Why Kafka Doesn’t Provide a JSON Serializer
Kafka is designed to be flexible and data format-agnostic. This means it doesn't enforce any specific data format like JSON, Avro, or Protobuf. Instead, it leaves the choice of serialization format up to the developers — so they can choose what works best for their applications.

By not including a JSON serializer, Kafka avoids forcing a standard and gives users the freedom to use any data structure or serialization mechanism.

# JSON Support in Spring Kafka

Spring Kafka does provide JSON serialization and deserialization out of the box. It can automatically convert:
- Java objects → JSON (using a JSON serializer)
- JSON → Java objects (using a JSON deserializer)

This makes it easy to send and receive Java objects as JSON messages in Kafka, with minimal configuration, just add a below properties

