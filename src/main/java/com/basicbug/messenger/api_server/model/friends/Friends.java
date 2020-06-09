package com.basicbug.messenger.api_server.model.friends;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

/**
 * @author JaewonChoi
 */

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friends")
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /*
    FIXME
    This field needs to be hide?
     */
    @Column(nullable = false, length = 30)
    private String uid;

    @Column(nullable = false, length = 30)
    private String frienduid;

    @CreatedDate
    private LocalDateTime meetAt;
}
