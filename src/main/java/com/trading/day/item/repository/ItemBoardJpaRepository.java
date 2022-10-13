package com.trading.day.item.repository;

import com.trading.day.item.domain.ItemBoard;
import com.trading.day.item.domain.ItemBoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBoardJpaRepository extends JpaRepository<ItemBoard, Long> {

    Page<ItemBoard> findAll(Pageable pageable); // 전체검색 페이징

}
