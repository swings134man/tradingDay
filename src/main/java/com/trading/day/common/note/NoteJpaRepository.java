package com.trading.day.common.note;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteJpaRepository extends JpaRepository<Note, Long> {


    Optional<Note> findByReceiveMemberId(String receiveMemberId);
}
