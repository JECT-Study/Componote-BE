package ject.componote.domain.auth.domain;

import ject.componote.domain.auth.dto.find.MemberSummaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByProviderTypeAndSocialId(final String providerType, final String socialId);
    Optional<Member> findBySocialId(final String socialId);

    @Query("SELECT NEW ject.componote.domain.auth.dto.find.MemberSummaryDto(m.nickname, m.profileImage) " +
            "FROM Member m " +
            "WHERE m.id =:id")
    Optional<MemberSummaryDto> findSummaryById(@Param("id") final Long id);
}
