# Kafka API's

## 1. Admin API - The "Manager" of Kafka

- Manages topics, brokers, and cluster configurations

## 2. Producer API - The "Publisher"

- Sends messages to Kafka topics

### What it handles automatically:
- Message serialization
- Partition selection
- Batching and compression
- Retries on failure

## Consumer API - The "Subscriber"
- Reads messages from Kafka topics 

### What it handles automatically:
- Consumer group coordination
- Partition rebalancing
- Offset management
- Message Deserialization

## Streams API - The "Processor"
- Transforms data between topics (real-time processing)
- Real-time analytics
- Fraud detection
- Data enrichment

## Connect API - The "Bridge"
- Connects Kafka to external systems (databases, etc.)

- These APIs work together to make Kafka a complete data streaming platform!
