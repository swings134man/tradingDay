package com.trading.day.jwtToken.repository;

import com.trading.day.jwtToken.domain.TokenManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * packageName : com.trading.day.member.repository
 * fileName : TokenManageJpaRepository
 * author : taeil
 * date : 2022/12/18
 * description :
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 2022/12/18        taeil                   최초생성
 */
public interface TokenManageJpaRepository extends JpaRepository<TokenManage, Long> {

    //Optional<TokenManage> findByMemberEntityMemberNo(Long memberNo);
//    TokenManage findByMemberNo(Long memberNo);
    Optional<TokenManage> findByUserName(String userName);

    // Batch - 업데이트 2주일 이전 Data 조회.
    @Query(value = "SELECT i FROM TokenManage i WHERE i.modifiedDate < :formatDate")
    List<TokenManage> findByModifiedDateLessWeek(@Param("formatDate") String formatDate);
}