# Hollomon Client – Assignment 2  
**CS1812: Introduction to Object-Oriented Programming**

## Overview
This project implements a Java client for the **Hollomon Trading Card Game**, interacting with a central server hosted at `netsrv.cim.rhul.ac.uk` on port `1812`. Players can log in, receive free cards, buy/sell cards, and manage their collections. The goal is to collect as many unique cards as possible, weighted by rarity.

## File Structure
You are required to submit the following Java source files:

| File Name               | Description                                                       |
|------------------------|-------------------------------------------------------------------|
| `Rank.java`             | Enum defining card ranks: UNIQUE, RARE, UNCOMMON, COMMON.         |
| `Card.java`             | Class representing a trading card, implementing Comparable.       |
| `CardTest.java`         | Test class for `Card`, verifying all its methods.                 |
| `HollomonClient.java`   | Main client class handling server connection and commands.        |
| `HollomonClientTest.java`| Test class for `HollomonClient`, validating login and trading.    |
| `CardInputStream.java`  | Specialized input stream for reading card data from the server.   |

---

## Features Implemented

### ✅ Rank Enum
- Defines four ranks: **UNIQUE, RARE, UNCOMMON, COMMON**.
- Comparable and properly structured.

### ✅ Card Class
- Stores card **ID (long)**, **Name (String)**, **Rank (Rank enum)**, and **Price (long)**.
- Implements:
  - Custom **`toString()`**.
  - **`hashCode()`** and **`equals()`** based on ID, name, rank.
  - **Comparable**: Sorted by rank (descending), name, then ID.
  
### ✅ CardTest
- Comprehensive tests for:
  - Constructors
  - `equals`, `hashCode`
  - `compareTo`
  - Interaction with `HashSet` & `TreeSet`

### ✅ HollomonClient
- Handles:
  - **Connection setup**
  - **Login** (sends username/password, receives card list)
  - **Command execution**: `CREDITS`, `CARDS`, `OFFERS`, `BUY`, `SELL`
  - Connection closure
  
### ✅ HollomonClientTest
- Tests:
  - Login success/failure
  - Credit retrieval
  - Card retrieval
  - Buy/Sell functionality
  
### ✅ CardInputStream
- Wraps input stream with a **BufferedReader**.
- Implements:
  - **`readCard()`**: Parses card details from server.
  - **`readResponse()`**: Reads server responses.
  - **`close()`**: Properly closes streams.

---

## Compilation & Execution

### **Compile:**
```bash
$ javac *.java
```

### **Run CardTest:**
```bash
$ java -enableassertions CardTest
```

### **Run HollomonClientTest:**
```bash
$ java HollomonClientTest
```

### **Testing Tool:**
Verify progress using the provided JAR file:
```bash
$ java -jar assignment2TesterStudent.jar --checkconnect
$ java -jar assignment2TesterStudent.jar
```

---

## Requirements & Restrictions
- **No third-party libraries** allowed.
- Must **compile on NoMachine** using `javac`.
- Code must follow principles of:
  - Clean, modular, object-oriented design.
  - Good coding style and readability.
  - Error handling (e.g., failed connections, invalid server responses).

---

## Server Connection
- **Host:** `netsrv.cim.rhul.ac.uk`
- **Port:** `1812`
- **Credentials:** Provided individually (see Moodle feedback section).
- **Note:** Connection requires university network access (use **NoMachine** if off-campus).

---

## Polite Usage Notice
- Do **not** flood the server with rapid login attempts.
- Excessive activity may result in **IP bans** affecting yourself and others.

---

## Extra Credit Ideas
- Implement automated trading strategies (optional).
- Prioritize purchasing missing cards based on rank and uniqueness.
  
---
