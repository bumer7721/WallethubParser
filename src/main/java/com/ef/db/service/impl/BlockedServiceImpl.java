package com.ef.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ef.db.model.BlockedIp;
import com.ef.db.repository.BlockedIpRepository;

@Service
public class BlockedServiceImpl extends BaseServiceImpl<BlockedIp> {
	
	@Autowired
	private BlockedIpRepository blockedIpRepository;

	@Override
	protected BlockedIpRepository getRepository() {
		return blockedIpRepository;
	}
}
