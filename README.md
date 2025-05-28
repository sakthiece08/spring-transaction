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

### Links
http://localhost:8090/swagger-ui/index.html






