package com.trading.day.common.note;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface NoteJpaRepository extends JpaRepository<Note, Long> {


    Optional<Note> findByReceiveMemberId(String receiveMemberId); // 받는사람 검색
    Page<Note> findByReceiveMemberId(String receiveMemberId, Pageable pageable); // 받는사람 페이징.

    @Transactional
    @Modifying
    @Query("delete from Note n where n.noteNo in :list")
    int deleteAllByIdInQuery(@Param("list") List<Long> list);
}
