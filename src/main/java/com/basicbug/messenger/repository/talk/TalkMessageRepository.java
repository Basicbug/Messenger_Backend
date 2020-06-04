package com.basicbug.messenger.repository.talk;

import com.basicbug.messenger.model.message.TalkMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author JaewonChoi
 */
public interface TalkMessageRepository extends JpaRepository<TalkMessage, Long> {

}
