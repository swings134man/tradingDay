package com.trading.day.common.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageFileJpaRepository extends JpaRepository<ImageFile,Long> {

    Optional<ImageFile> findByBoardId(Long boardId);

    @Query(value = "select i from ImageFile i where i.boardId = :boardId")
    Optional<ImageFile> findByBoardIdList(@Param("boardId") Long boardId);
}
