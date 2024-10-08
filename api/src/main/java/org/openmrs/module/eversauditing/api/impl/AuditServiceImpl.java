package org.openmrs.module.eversauditing.api.impl;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.eversauditing.AuditEntity;
import org.openmrs.module.eversauditing.api.AuditService;
import org.openmrs.module.eversauditing.api.dao.AuditDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuditServiceImpl extends BaseOpenmrsService implements AuditService {
	
	private final AuditDao auditingDao;
	
	private final Logger logger = LoggerFactory.getLogger(AuditServiceImpl.class);
	
	public AuditServiceImpl(AuditDao auditingDao) {
		this.auditingDao = auditingDao;
	}
	
	public <T> List<AuditEntity<T>> getAllRevisions(Class<T> entityClass) {
		return auditingDao.getAllRevisions(entityClass);
	}
	
	@Override
	public <T> List<AuditEntity<T>> getAllRevisions(String entityClass) {
		try {
			Class clazz = Class.forName(entityClass);
			return getAllRevisions(clazz);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			return new ArrayList<>();
		}
	}
	
	public <T> T getRevisionById(Class<T> entityClass, int entityId, int revisionId) {
		T entity = auditingDao.getRevisionById(entityClass, entityId, revisionId);
		return entity;
	}
}
