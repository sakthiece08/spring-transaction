package org.teqmonic.springtransaction.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teqmonic.springtransaction.entity.AuditLog;

public interface AuditLogRepository  extends JpaRepository<AuditLog,Long> {
}
