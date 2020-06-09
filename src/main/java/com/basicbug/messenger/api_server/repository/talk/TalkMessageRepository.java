package com.basicbug.messenger.api_server.repository.talk;

import com.basicbug.messenger.api_server.model.message.TalkMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author JaewonChoi
 */
public interface TalkMessageRepository extends JpaRepository<TalkMessage, Long> {

}
