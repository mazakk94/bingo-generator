# **Bingo Strip Rules**
**Requirements:**

* Generate a strip of 6 tickets
   - Tickets are created as strips of 6, because this allows every number from 1 to 90 to appear across all 6 tickets. If they buy a full strip of six it means that players are guaranteed to mark off a number every time a number is called.
* A bingo ticket consists of 9 columns and 3 rows.
* Each ticket row contains five numbers and four blank spaces
* Each ticket column consists of one, two or three numbers and never three blanks.
   - The first column contains numbers from 1 to 9 (only nine),
   - The second column numbers from 10 to 19 (ten), the third, 20 to 29 and so on up until
   - The last column, which contains numbers from 80 to 90 (eleven).
* Numbers in the ticket columns are ordered from top to bottom (ASC).
* There can be **no duplicate** numbers between 1 and 90 **in the strip** (since you generate 6 tickets with 15 numbers each)
---

# Bingo Strip Builder

This project generates a **Bingo strip** while adhering to specific rules and constraints. The process is divided into three distinct phases:



## **Phase 1: Prepare Numbers**

Prepare numbers that will be distributed over the Bingo strip. The numbers are organized into **9 columns** (each represented as a list).

---

## **Phase 2: Distribute Numbers**

Distribute the remaining available numbers across the **6 tickets** in the strip. Each ticket has its own rules for distribution:

### **First 3 Tickets**
- Each of the first 3 tickets can pick any random **6 numbers** from the pool of available numbers.
- No column can have more than **3 numbers**.

### **4th Ticket**
- If there are still **5 numbers** left in the **9th column** of available numbers, the ticket must take **at least one number** from this column.
- Otherwise, it follows the same rules as the first 3 tickets.

### **5th Ticket**
- Take **2 numbers** from each column where there are **4 numbers left**.
- Take **at least 1 number** from each column where there are still **3 numbers left**.
- Remaining numbers are distributed as usual.

### **6th Ticket**
- The 6th ticket receives **all remaining numbers**.


## **Phase 3: Fill Blank Spaces**

After all numbers are distributed, blank spaces are added to ensure compliance with Bingo formatting rules. This phase consists of the following steps:

### **Step 1: Blanks in the First Row**
- Pick **4 random columns** that have fewer than **3 numbers** and place a blank at the **top position** of these columns.

### **Step 2: Fill Single-Number Columns**
- For columns with only **1 number**, fill the **middle and last positions** with blanks.
- This ensures that numbers are distributed more evenly and avoids crowding the third row.

### **Step 3: Blanks in the Second Row**
- Add **4 blanks** to the **2nd row** by filling random columns that still have **2 numbers** with a blank in the **middle position**.
- Stop once there are **4 total blanks** in the second row.

### **Step 4: Fill Remaining Columns with Two Numbers**
- For columns that still have **2 numbers**, place a blank in the **last position**.

---

This process ensures the generation of valid Bingo strips while maintaining randomness and adhering to all distribution constraints. Each phase is designed to ensure compliance with Bingo rules while balancing randomness and structure.

## Prerequisites
- Ensure you have **Java 21** or a newer version installed.
- Install **Maven** for building the application.

## Building the Application
Run the following command to build the application:
```
mvn clean package
```

--- 
## Running the Application
After building the application, you can start it using:

```
java -jar target/bingo-0.0.1-SNAPSHOT.jar 
```

To specify count of strips, use first arg, for example
```
java -jar target/bingo-0.0.1-SNAPSHOT.jar 10000
```

## Expected output
Only **first** strip is printed out to show example generation output
```
Ticket 1:
 0 10 22 30  0 53 62  0  0
 7  0 25  0  0 54  0 71 88
 9  0  0 35 49 56 66  0  0

Ticket 2:
 8  0 23 33  0 52  0  0 81
 0 14  0 38 46  0 65 73  0
 0 15 24  0  0 58 67  0 85

Ticket 3:
 4  0 27 36  0 51 60  0  0
 0 11  0  0 41  0 64 77 86
 0 13 29 39  0 57  0  0 90

Ticket 4:
 2  0 20 31 47  0  0 76  0
 5 19  0 37  0 50  0 78  0
 6  0 21  0  0  0 63 79 82

Ticket 5:
 0  0  0 32 40  0 69 70 80
 0 16  0  0 42 59  0 72 89
 3 18 28  0 45  0  0 75  0

Ticket 6:
 0  0  0  0 43 55 61 74 83
 1 12  0  0 44  0 68  0 84
 0 17 26 34 48  0  0  0 87

Generated 10000 Bingo90 strips in 375 milliseconds
```