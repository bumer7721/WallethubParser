package com.ef.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.ef.db.model.BlockedIp;

public interface BlockedIpRepository extends CrudRepository<BlockedIp, Long> {

}
