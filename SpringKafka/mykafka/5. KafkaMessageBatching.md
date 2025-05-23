# Kafka Message Batching

- Kafka producers don't send one message at a time to the broker. Instead, they collect several messages into a batch
  before sending them. Each batch contains messages for the same topic and partition.

## Why Use Batching?

- **Fewer network calls:** Sending many messages together is more efficient than sending them one by one.
- **Faster processing:** Larger batches mean more messages can be handled in a shorter time.
- **Compression:** Kafka can compress batches, saving space and speeding up transfer.

## Example:

- Imagine you're mailing letters (messages) to friends (Kafka brokers)

### Without batching:
- You drive to the post office for each single letter
- Very inefficient - lots of trips!

### With batching:
- You collect several letters going to the same address
- Make one trip with multiple letters
- Much more efficient!

## Things to Keep in Mind:

- **If the batch is too large,** it might delay sending because it waits too long to fill up.
- **If the batch is too small,** it won't gain the benefits of batching.
- **Balance is key:** Configure the batch size based on your message rate and processing capacity.

# How Kafka Batching Works

## Key Concepts:
- **Batch =** Group of messages going to the same topic/partition
- **Batch Size =** How many messages to group together
- **Linger Time =** How long to wait for more messages before sending

# Benefits of Batching
- **Fewer Network Calls -** Less traffic between producer and broker
- **Higher Throughput -** More messages processed per second
- **Compression -** Batches can be compressed (like zipping a folder)
Smaller data size = faster transfer


# Basic Producer with Batching

```java
// Producer with batching configuration
Properties props = new Properties();
props.put("bootstrap.servers", "localhost:9092");
props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

// Batching settings
props.put("batch.size", 16384); // 16KB batch size
props.put("linger.ms", 100);    // Wait up to 100ms for more messages

KafkaProducer<String, String> producer = new KafkaProducer<>(props);

// Sending messages - they'll be batched automatically
for (int i = 0; i < 100; i++) {
    producer.send(new ProducerRecord<>("orders", "key-" + i, "order-" + i));
}

producer.close();

```

# Producer with Compression

```java
Properties props = new Properties();
// ... (basic config same as above)

// Batching + compression
props.put("compression.type", "snappy"); // Options: none, gzip, snappy, lz4, zstd
props.put("batch.size", 65536); // 64KB
props.put("linger.ms", 50);     // Wait 50ms

KafkaProducer<String, String> producer = new KafkaProducer<>(props);
```

# Monitoring Batch Behavior

```java
// Add these to your producer config to monitor batches
props.put("metrics.recording.level", "DEBUG");
props.put("metric.reporters", "your.metrics.reporter");

// Then send messages as normal
producer.send(new ProducerRecord<>("logs", "app-start", "Service started"));

```