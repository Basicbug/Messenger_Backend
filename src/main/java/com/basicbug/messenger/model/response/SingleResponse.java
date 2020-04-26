package com.basicbug.messenger.model.response;

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
