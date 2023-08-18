package com.ys.infrastructure.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {

    int status;
    Object data;
}
