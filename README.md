### Spring Transaction

#### Transaction helps to manage and maintains atomicity of multiple transactions performed within a method. So the data is
consistent and its integrity is maintained.

* REQUIRED : Join an existing transaction or create a new one if not exist
* REQUIRES_NEW : Always create a new transaction , suspending if any existing transaction
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






