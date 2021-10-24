package com.azubike.ellipsis.springsecuritybasic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.azubike.ellipsis.springsecuritybasic.model.Notice;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {

	@Query(value = "from Notice")
	List<Notice> findAllActiveNotices();

}