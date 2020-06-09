package com.basicbug.messenger.api_server.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author JaewonChoi
 */

@Getter
@Setter
public class SingleResponse<T> extends CommonResponse {

    private T data;
}
