package com.chat.main.application.chat.infra.jpa;

import com.chat.main.application.chat.domain.ChatRoom;
import com.chat.main.application.chat.domain.ChatRoomId;
import com.chat.main.application.chat.domain.ChatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaChatRoomRepository extends JpaRepository<ChatRoom, ChatRoomId> {
    List<ChatRoom> findByMemberId(Long MemberId);

    @Query(value =
            """
            SELECT CR
              FROM ChatRoom CR
             WHERE CR.chatType = :chatType
               AND CR.memberId IN (:sendMemberId, :targetMemberId)
             GROUP BY CR.chatRoomId
            HAVING COUNT(DISTINCT CR.memberId) = 2
            """
    )
    Optional<ChatRoom> findByChatRoomByChatTypeAndTwoMembers(
            @Param("chatType") ChatType chatType,
            @Param("sendMemberId") Long sendMemberId,
            @Param("targetMemberId") Long targetMemberId
    );

}
