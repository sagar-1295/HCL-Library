package com.library.hcl.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.library.hcl.model.Member;

@Repository
public interface MemberRepo extends CrudRepository<Member, Integer>{

}
