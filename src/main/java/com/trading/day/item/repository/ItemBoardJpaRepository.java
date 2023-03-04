package com.trading.day.item.repository;

import com.trading.day.item.domain.ItemBoard;
import com.trading.day.item.domain.ItemBoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemBoardJpaRepository extends JpaRepository<ItemBoard, Long> {

    Page<ItemBoard> findAll(Pageable pageable); // 전체검색 페이징
    Page<ItemBoard> findByTitleContaining(String title, Pageable pageable); // 제목 검색 페이징
    Page<ItemBoard> findByWriterContaining(String writer, Pageable pageable); // 작성자 검색 페이징

    // Refactoring - fetch join - 2023.03.04(Refactoring)
    @Query(value = "select distinct b from ItemBoard b left join fetch b.replys where b.id = :id", nativeQuery = false)
    ItemBoard findByBoardRefactor(@Param("id") Long id);
}
