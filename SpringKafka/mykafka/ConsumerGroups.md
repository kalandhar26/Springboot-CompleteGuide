# Kafka Consumer Groups and Rebalancing

## What is a Consumer Group?

- A Consumer Group is a collection of consumers working together to read data from a Kafka topic. Each consumer in the
  group reads from one or more partitions of the topic, but only one consumer in a group will read from a partition at a
  time.

## Why Consumer Groups?

- Imagine a hospital with 3 departments:
-- Billing department
-- Housekeeping department
-- Pharmacy department

- When a patient checks out, their information needs to go to all 3 departments. In Kafka terms:
- 1. The "patient-checkout" application publishes records to a Kafka topic
- 2. Each department has its own consumer group that reads these records

### Basic Concept
- A consumer group is a team of consumers working together to read from a topic
- Each partition in a topic is assigned to only one consumer in the group
- This allows parallel processing of messages

#### Hospital Example
Let's say:
-- The "patient-checkout" topic has 4 partitions (P0-P3)
-- The "billing" consumer group has 3 consumers (C1-C3)

##### Partition assignments might look like:
C1: P0 and P1
C2: P2
C3: P3

##### Scaling Consumers

- If patient volume increases:
- We can add C4 to the billing group
- Kafka will redistribute partitions:
- Now each consumer handles 1 partition

C1: P0
C2: P1
C3: P2
C4: P3

##### If a consumer crashes:
- Its partitions get redistributed to remaining consumers


# Consumer Group Rebalancing

## What is Rebalancing?
- When consumers join/leave the group, Kafka reassigns partitions. This is called rebalancing.

### How It Works
- One broker acts as the Group Coordinator
- One consumer is elected as the Group Leader

### When changes happen:
- Consumers temporarily stop processing
- The leader calculates new assignments
- Coordinator informs all consumers

# Example

## Initial state:

- 4 partitions (P0-P3)

- 2 consumers (C1: P0,P1; C2: P2,P3)

## When adding C3:

 - All consumers pause
- New assignments:
- C1: P0
- C2: P1
- C3: P2,P3

Consumers resume work


# Offset Management

## What are Offsets?
- Each message in a partition has an offset number (like a page number in a book)
- Consumers need to remember where they left off reading.

## The Problem
- If a consumer crashes, how does the new consumer know where to start reading?

## The Solution: Offset Commits
- Consumers periodically save their progress to a special Kafka topic (__consumer_offsets)
Example: "I've processed up to offset 6 in partition 2"

## Potential Issues

### Duplicate Processing

- Consumer processes up to offset 9 but only committed offset 6
- Crashes before committing
- New consumer starts from 7 → processes 7-9 again

### Missed Messages

- Consumer commits offset 10 but only processed up to 2 and Crashes
- New consumer starts from 11 → skips 3-10

#### How to Commit Offsets Safely
- Commit frequently (but not too frequently)
- Process and commit in the same transaction
- Use "auto.commit" carefully
- How to Commit Offsets Safely
- Synchronously commit: commit after processing messages (safe but slower)
- Asynchronously commit: commit in the background (faster but risky)
- Manually commit specific offsets: more control

# Key Takeaways
- Consumer Groups enable parallel processing
- Rebalancing happens when consumers join/leave
- Offset commits prevent losing your place

# Balance between:
- Frequent commits (reduces duplicates but slows processing)
- Infrequent commits (faster but risk more duplicates)