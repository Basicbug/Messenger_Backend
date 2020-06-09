package com.basicbug.messenger.api_server.model.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JaewonChoi
 */

@Getter
@Setter
public class ListResponse<T> extends CommonResponse {

    private List<T> dataList;
}
