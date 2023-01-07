package com.trading.day.member.repository;

import com.trading.day.member.domain.SocialMember;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName : com.trading.day.member.repository
 * fileName : SocialMemberRepository
 * author : taeil
 * date : 2023/01/07
 * description :
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 2023/01/07        taeil                   최초생성
 */
public interface SocialMemberRepository extends JpaRepository<SocialMember, String> {

}