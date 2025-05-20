# Kafka Transactions Guide

## Overview

Kafka transactions help ensure that a set of messages are sent to one or more topics/partitions atomically — meaning
either all succeed or none are visible to consumers.
Kafka transactions enable atomic writes across multiple topics and partitions, ensuring:

- **All operations succeed** (commit)
- **Or none are visible** (abort)

Key benefits:
✅ Exactly-once processing  
✅ Cross-partition/topic atomicity  
✅ Error recovery with rollbacks

## Prerequisites
Kafka Transactions rely on the Idempotent Producer
```
Producer config (REQUIRED)
props.put("enable.idempotence", "true");  // Prevent duplicates
props.put("transactional.id", "my-tx-id"); // Unique for each producer
```

# Transaction Lifecycle

## 1. initTransactions()
This initializes the producer for transactions.
- You must call this once per producer instance before beginning any transaction.
- Kafka assigns a unique Producer ID and tracks transactions.

```
producer.initTransactions();  // One-time setup
```
- Think of this like as telling Kafka, “Hey, I want to use transactions.”

## 2. beginTransaction()

This starts a new transaction.
- After this call, all messages sent are considered part of a single transaction.
- Any messages you send after this call are buffered as part of the ongoing transaction.

```
producer.beginTransaction();
```

## Send Messages
You send records like normal using send().

- All these messages are not visible to consumers until committed.
- If something goes wrong during this stage (e.g., exception, failure), you can abort the transaction.

```
producer.send(new ProducerRecord<>("bank-transactions", "acct1", "debit-100"));
producer.send(new ProducerRecord<>("bank-transactions", "acct2", "credit-100"));
```

## 4. commitTransaction()

- Kafka writes a transaction marker indicating successful commit.
- Now, consumers with isolation.level=read_committed can read the messages.
- This ensures atomicity — consumers see either all messages or none.

```
producer.commitTransaction();
```

## 5. abortTransaction()

- If anything goes wrong, this will discard all the messages sent in the current transaction.
- Messages are never visible to consumers.
- Kafka writes a "transaction aborted" marker.
- Use this to roll back the transaction.

```
producer.abortTransaction();
```

```java

KafkaProducer<String, String> producer = new KafkaProducer<>(props);

producer.initTransactions();

try {
    producer.beginTransaction();

    producer.send(new ProducerRecord<>("account", "A123", "debit-500"));
    producer.send(new ProducerRecord<>("account", "B456", "credit-500"));

    // If everything is OK
    producer.commitTransaction();
} catch (Exception e) {
    // Something went wrong
    producer.abortTransaction();
}

```

# Key Notes

- You must set enable.idempotence=true and a unique transactional.id in your Kafka Producer config.
- Consumers must set isolation.level=read_committed to avoid reading uncommitted messages.
- Transactions are mostly used with exactly-once semantics and stream processing.




