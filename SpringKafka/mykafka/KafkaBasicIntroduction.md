- springkafka-part3 is a consumer application which consumes messages from springkafka-part2 producer application

# 📦 Kafka Record (Message) - Simplified Explanation

A **Kafka record** is like a "message" that a producer sends to Kafka.  
It has **7 parts (attributes)**:

---

## 📝 1. Topic
- The "category" or "channel" where the record is stored.
- _Example_: `orders`, `logs`, `payments`.

---

## 📂 2. Partition
- A numbered section inside a topic (like a folder in a drawer).
- If you don’t specify a partition, Kafka decides where to put the record:
    - If there’s a **Key**, Kafka uses a **hash of the key** to choose a partition.
    - If there’s **no Key**, Kafka uses **round-robin** (take turns) to distribute records.

---

## 🔢 3. Offset
- A **unique ID number** for the record within its partition.
- Like a **serial number** in a folder.

---

## 🕒 4. Timestamp
- When the record was created.
- If you don’t set it, Kafka assigns the **current time**.

---

## 🏷️ 5. Key (Optional)
- A label for the record (not necessarily unique).
- If you use the **same key** for multiple records, they all go to the **same partition**.
- Helps **group related data** together.
- If there's **no key**, Kafka spreads records across partitions **evenly**.

---

## 🧾 6. Headers (Optional)
- Extra **metadata** in key-value pairs.
- Think of it like **tags or notes** attached to the message.

---

## 📨 7. Value
- The **actual data/message** (e.g., `{"user": "Alice", "action": "login"}`).
- It's a **byte array** and holds your **business data**.
- Even though it's technically optional, a record without a value is **useless** — there's no data!

---

## ✅ Key Takeaways

- `Partition + Offset` = Unique ID for a record  
  _(like “Folder 3, Item 5”)_

- **Key controls partitioning**:  
  Same key → Same partition

- **No key?**  
  Kafka distributes records evenly (round-robin)

- **Value is the important part** — your actual data

---

## 📦 Example Scenario

Imagine Kafka is a **post office**:

- **Topic** = Type of mail (e.g., `"Letters"` or `"Packages"`)
- **Partition** = Different **sorting bins** inside "Letters"
- **Key** = A ZIP code; all letters with the same ZIP go to the same bin
- **Offset** = The **order** in which letters arrived in the bin
- **Value** = The actual **letter inside the envelope**



===================


# Kafka: Topics, Partitions, and Offsets

In Kafka, **messages are written to a topic**.  
A **topic** is like a category or a folder for storing messages.

---

## **What is a Partition?**

Each topic is divided into one or more **partitions**.

- Think of a **partition** as a **log file** where messages are written one after another.
- Each partition is an **ordered sequence of messages**.
- New messages are always **appended to the end** of a partition.

### **Kafka assigns:**
- A **unique number** to every partition.
- A **unique offset** to every message inside the partition.

---

## **What is an Offset?**

- The **offset** is like an **index number** for a message in a partition.
- It starts from **0** for each partition.
- Every new message gets the **next offset value**.
- This offset **never resets** — it keeps increasing.

Together, the **partition number and offset** uniquely identify a Kafka message.  
Kafka also **stores the partition and offset info inside each message** itself.

---

## **Message Ordering**

- **Ordering is guaranteed within a partition**.  
  Messages in the same partition are read in the **exact order** they were written.

- **Ordering is NOT guaranteed across partitions**.  
  Since messages may go to different partitions, you **can't assume overall order**.

---

## **How Kafka Decides the Partition**

When a producer sends a message, there are 3 ways Kafka decides the partition:

1. **Producer specifies the partition** → Kafka uses it directly.
2. **Producer provides a key** → Kafka uses a **hash of the key** to choose a partition.
3. **No partition or key provided** → Kafka assigns a partition using **round-robin** (evenly distributes messages).

---

## **Why Partitions Matter**

### **Scalability:**
- Partitions allow Kafka to **scale horizontally**.
- If too many messages come in, partitions can be **spread across multiple brokers** (Kafka servers).
- This increases **performance** and **throughput**.

### **High Availability (Replication):**
- Partitions can be **replicated** to other brokers.
- This ensures that even if one broker fails, a **copy of the partition** is still available elsewhere.

---

## **Summary**

- **Topic**: A category (like `orders`, `logs`).
- **Partition**: A log file inside a topic. Messages are ordered **only within** a partition.
- **Offset**: A unique number for each message within a partition.
- **Partition + Offset = Unique ID** for a message.
- **Producer can control** where messages go using **partition** or **key**.
- Kafka handles **load balancing** and **fault tolerance** using **partitions and replication**.


================================

# Kafka Message Delivery Semantics

Kafka supports **3 ways to deliver messages**, each offering different trade-offs between **reliability** and **duplicate handling**.

---

## 1. **At Least Once** (Default)

✅ **Guarantee**: The message will be delivered **at least once** (but **may be duplicated**).

### 🔹 How it works:
- Producer sends a message to Kafka.
- Kafka broker stores the message and sends back an acknowledgment (ACK).
- If the ACK **fails** (e.g., network issue), the producer **retries**.
- This may result in **duplicate messages**.

### 🔹 Use Case:
Use this when **losing messages is unacceptable**  
(e.g., **payments**, **orders**).

### 🔹 Example:
- You send `"Order #123"` to Kafka.
- Kafka stores it but the ACK fails due to a network glitch.
- Producer retries → Kafka receives **two copies** of `"Order #123"` (duplicate).

---

## 2. **At Most Once**

✅ **Guarantee**: The message will be delivered **at most once** (no duplicates, but may be **lost**).

### 🔹 How it works:
- Producer sends a message **without retry**.
- If Kafka **fails before storing it**, the message is **lost**.
- This is achieved by setting `retries=0`.

### 🔹 Use Case:
Use this when **duplicates are worse than data loss**  
(e.g., **sensor data**, **logs**).

### 🔹 Example:
- You send `"Temp: 25°C"` to Kafka.
- Kafka crashes before saving it.
- Since retries are disabled, the message is **lost forever**.

---

## 3. **Exactly Once** (Idempotent Producer)

✅ **Guarantee**: The message is delivered **exactly once** (no duplicates, no loss).

### 🔹 How it works:
- The producer is configured with `enable.idempotence=true`.
- Kafka assigns a **unique Producer ID** and **sequence number** to each message.
- If a **duplicate message** (same ID + sequence number) is received, Kafka **ignores it**.

### 🔹 Use Case:
Use this in **critical systems** requiring **no loss** and **no duplicates**  
(e.g., **banking**, **transactions**).

### 🔹 Example:
- You send `"Transfer $100 to Alice"` with ID: `789`.
- If the message is sent again (retry), Kafka sees it's the same ID `789` and **stores only one copy**.

---

## ✅ Which One Should You Use?

| **Semantic**        | **Duplicates?** | **Message Loss?** | **When to Use**                   |
|---------------------|------------------|--------------------|------------------------------------|
| At Least Once       | ✅ Yes           | ❌ No              | Orders, Payment processing         |
| At Most Once        | ❌ No            | ✅ Yes             | Sensor data, Logs                  |
| Exactly Once        | ❌ No            | ❌ No              | Banking, Financial Transactions    |


=======================


