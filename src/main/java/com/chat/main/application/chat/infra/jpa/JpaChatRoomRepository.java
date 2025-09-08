package com.chat.main.application.chat.infra.jpa;

import com.chat.main.application.chat.domain.ChatRoom;
import com.chat.main.application.chat.domain.ChatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query(value =
            """
            SELECT CR
              FROM ChatRoom CR
              JOIN ChatRoomMember CRM
                ON CR.chatRoomId = CRM.chatRoomId
             WHERE CR.chatType = :chatType
               AND CRM.memberId IN (:sendMemberId, :targetMemberId)
             GROUP BY CR.chatRoomId
            HAVING COUNT(DISTINCT CRM.memberId) = 2
            """
    )
    Optional<ChatRoom> findByChatRoomByChatTypeAndTwoMembers(
            @Param("chatType") ChatType chatType,
            @Param("sendMemberId") Long sendMemberId,
            @Param("targetMemberId") Long targetMemberId
    );

}
