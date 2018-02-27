package com.ef.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ef.db.model.BlockedIp;
import com.ef.db.repository.BlockedIpRepository;
import com.ef.db.service.BlockedIpService;

@Service
public class BlockedServiceImpl extends BaseServiceImpl<BlockedIp> implements BlockedIpService {
	
	@Autowired
	private BlockedIpRepository blockedIpRepository;

	@Override
	protected BlockedIpRepository getRepository() {
		return blockedIpRepository;
	}
}
