### Spring Transaction

#### Transaction helps to manage and maintains atomicity of multiple transactions performed within a method. So the data is
consistent and its integrity is maintained.

* REQUIRED : Join an existing transaction or create a new one if not exist (When all transactions will either succeed or failure completely)
  ![name-of-you-image](https://github.com/sakthiece08/spring-transaction/blob/master/src/main/resources/images/Required.jpg)
* REQUIRES_NEW : Always create a new transaction , suspending if any existing transaction.
  Use case: Audit log, this should be executed in a new txn regardless of status of outer txn

  ```
  Creating new transaction with name [org.teqmonic.springtransaction.service.OrderProcessingService.placeAnOrder]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
  [nio-8090-exec-1] o.s.orm.jpa.JpaTransactionManager        : Opened new EntityManager [SessionImpl(2009107534<open>)] for JPA transaction
  [nio-8090-exec-1] o.s.orm.jpa.JpaTransactionManager        : Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@7b3f1304]
  [nio-8090-exec-1] o.s.orm.jpa.JpaTransactionManager        : Found thread-bound EntityManager [SessionImpl(2009107534<open>)] for JPA transaction
  [nio-8090-exec-1] o.s.orm.jpa.JpaTransactionManager        : Participating in existing transaction

  [nio-8090-exec-1] o.s.orm.jpa.JpaTransactionManager        : Suspending current transaction, creating new transaction with name [org.teqmonic.springtransaction.handler.AuditLogHandler.logAuditDetails]
  [nio-8090-exec-1] o.s.orm.jpa.JpaTransactionManager        : Participating in existing transaction
  [nio-8090-exec-1] o.s.orm.jpa.JpaTransactionManager        : Initiating transaction commit
  [nio-8090-exec-1] o.s.orm.jpa.JpaTransactionManager        : Resuming suspended transaction after completion of inner transaction
  ```
  
* MANDATORY : Require an existing transaction , if nothing found it will throw exception
* NEVER : Ensure the method will run without transaction , throw an exception if found any
* NOT_SUPPORTED : Execute method without transaction, suspending any active transaction
* SUPPORTS : Supports if there is any active transaction , if not execute without transaction
* NESTED : Execute within a nested transaction, allowing nested transaction to rollback independently if there is any exception without impacting outer transaction


Transaction annotation with sample attributes:

Set readOnly as true only for Read only operations
```
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
```

### ISOLATION

Spring Boot uses transaction isolation levels to manage how concurrent transactions interact with each other, preventing data inconsistencies.

* DEFAULT(-1)
* READ_UNCOMMITTED(1), Allows dirty read
* READ_COMMITTED(2), Fetches last committed value from db, would have data discrepancy in case of multiple reads
* REPEATABLE_READ(4), shows consistent value for all repeatable reads
* SERIALIZABLE(8); One transaction will get execute at any point of time, other txn would wait

In Postgresql (Default isolation level)
```
show VARIABLES LIKE 'transaction_isolation';

output: READ-UNCOMMITTED
```

*  **_READ UNCOMMITTED_**

Update transaction_isolation:
```
SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
```

Transaction A trying to update a value. Before Transaction A commits, a concurrent Transaction B reads the value which is not yet committed 
by the other transaction causing Dirty read.

![name-of-you-image](https://github.com/sakthiece08/spring-transaction/blob/master/src/main/resources/images/Uncommited.jpg)

* **_READ COMMITTED_**

Transaction A trying to update a value.  concurrent Transaction B reads the last committed value and not the ongoing update which is not yet
committed by Transaction A.

![name-of-you-image](https://github.com/sakthiece08/spring-transaction/blob/master/src/main/resources/images/Committed.jpg)

* **_REPEATABLE READ_**

The Repeatable Read isolation level only sees data committed before the transaction began; it never sees either uncommitted data or changes committed by concurrent transactions during the transaction's execution.
It prevents non repeatable read.

![name-of-you-image](https://github.com/sakthiece08/spring-transaction/blob/master/src/main/resources/images/Repeatable_read.jpg)

* SERIALIZABLE

SERIALIZABLE is the highest level of isolation. 
It prevents all mentioned concurrency side effects, but can lead to the lowest concurrent access rate because it executes concurrent calls sequentially.
See order of execution of transactions below, no data inconsistency

![name-of-you-image](https://github.com/sakthiece08/spring-transaction/blob/master/src/main/resources/images/Serialzable.jpg)

**_Note:_**
* Postgres does not support READ_UNCOMMITTED isolation and falls back to READ_COMMITED instead.
* Also, Oracle does not support or allow READ_UNCOMMITTED.
* REPEATABLE_READ is the default level in Mysql. Oracle does not support REPEATABLE_READ.

**Non-repeatable read(fuzzy read)** is that a transaction reads the same row at least twice but the same row's data is different between the 1st and 2nd reads because other transactions update the same row's data and commit at the same time(concurrently).

**Phantom read** is that a transaction reads the same table at least twice but the number of the same table's rows is different between the 1st and 2nd reads because other transactions insert or delete rows and commit at the same time(concurrently).


**Summary**

* READ_UNCOMMITTED prevents nothing. It's the zero isolation level
* READ_COMMITTED prevents just one, i.e. Dirty reads
* REPEATABLE_READ prevents two anomalies: Dirty reads and Non-repeatable reads
* SERIALIZABLE prevents all three anomalies: Dirty reads, Non-repeatable reads and Phantom reads



### Links
http://localhost:8090/swagger-ui/index.html

### References
https://stackoverflow.com/questions/11043712/non-repeatable-read-vs-phantom-read
https://www.baeldung.com/spring-transactional-propagation-isolation







