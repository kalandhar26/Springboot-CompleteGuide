# Message Broker

A Message Broker is a intermediatary software component responsible for facilitating communicating and data transfer
between different applications/systems

Ex: Apache Kafka, Amazon SQS, RabbitMQ.


# Key Components of Kafka:

1. Producers and Producer Group.
2. Kafka Cluster (Brokers) (Topics, Partitions, Offsets)
3. Consumers and Consumer Group.


# Apache Kafka

Apache Kafka is a **distributed**, **fault-tolerant**, and **highly scalable** message broker and stream processing platform. It is designed to handle large volumes of data in real-time streams with guaranteed fault tolerance.

---

## 1. Kafka Producer

A Kafka **Producer** is a client application that publishes events (messages) to a Kafka cluster.

---

## 2. Kafka Consumer

A Kafka **Consumer** is a client application that subscribes to events from Kafka topics. Consumers can be part of a **consumer group**, allowing parallel message processing.

---

## 3. Kafka Topic

A **Kafka Topic** is a logical channel or feed category to which records (messages) are published by producers and from which records are consumed by consumers.

Topics serve as a way to organize and categorize the stream of messages within the Kafka system.

---

## 4. Kafka Partition

A **Partition** is a basic unit of parallelism and scalability. It is a horizontal division of a topic into multiple independently managed units.

Each partition is a strictly ordered, immutable sequence of records. This structure supports:

- Distribution
- Parallel Processing
- Fault Tolerance

Kafka can handle high data volumes by distributing partitions across multiple brokers, allowing concurrent reading and writing.

Messages within a partition are strictly ordered by **offset** (a unique ID assigned to each message). Ordering is **guaranteed per partition**, not across partitions.

Once written, a message becomes part of an **immutable log**, simplifying data processing and ensuring consistency within partitions.

---

## 5. Kafka Offsets

An **offset** is a unique sequence number assigned to messages as they arrive in a partition.

### How Offsets Work

#### Producer Perspective:

- Sends a message to a Kafka topic.
- Message is appended to a partition log.
- Acknowledgment is returned after the message is written.
- The message is assigned an offset.

#### Consumer Perspective:

- Tracks the last processed message offset in each partition.
- Commits the offset to a special Kafka topic called **consumer offsets**.
- On failure/restart, the consumer reads from the last committed offset to resume processing.

### Offset Committing

Offsets can be committed:

- Automatically (periodic)
- Manually (after a batch or custom logic)

This ensures that consumers do not reprocess already-processed messages after restarts or failures.

---

## 6. Consumer Groups

**Consumer Groups** allow a group of consumers to work together to consume and process messages from one or more partitions.

- Each partition is assigned to **at most one consumer** in the group.
- Enables **parallel processing**, **load balancing**, and **high-throughput** message consumption.

Use cases include scenarios requiring:

- Parallel processing of large data volumes
- High throughput with load balancing across consumers

---

## 7. Kafka Brokers

A **Kafka broker** is a server that stores data and handles requests from producers and consumers. Each **Kafka cluster** consists of multiple brokers, each responsible for:

- Hosting **topics** and **partitions**
- Handling **data replication**
- Managing **load balancing**

Each broker:

- Stores data
- Serves client requests
- Manages partitions and topics
- Communicates with other brokers for data replication and metadata sharing
- Has a **unique identifier**

To start a Kafka cluster, developers authenticate to a **bootstrap server** (initial broker). Brokers ensure high availability and scalability.

---

## 8. Kafka Cluster

A **Kafka Cluster** is a group of brokers/servers that work together to manage storage and the exchange of messages.

Key characteristics:

- **Speed** (Low Latency)
- **Durability**
- **Scalability**

Multiple streams are handled across brokers. If one broker fails, another has the data, ensuring fault tolerance. Kafka also balances load across brokers.

---

## 9. Zookeeper (or KRaft Mode)

Traditionally, Kafka relied on **Zookeeper** for distributed coordination and broker management. It:

- Tracks broker metadata
- Manages cluster state

However, modern Kafka deployments are moving toward using **KRaft (Kafka Raft)** mode, which removes the need for external Zookeeper and provides internal consensus and metadata management.

---

## Additional Features of Kafka

- Kafka can **scale horizontally** by adding more brokers.
- Kafka **persists messages to disk**, ensuring durability.
- **Configurable data retention policies** let organizations retain messages for defined periods.
