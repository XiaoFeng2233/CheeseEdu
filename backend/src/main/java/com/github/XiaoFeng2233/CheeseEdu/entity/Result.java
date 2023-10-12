package com.github.XiaoFeng2233.CheeseEdu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author XiaoFeng
 * @version 2.0
 * @date 2021/7/11 18:29
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public Result<T> success(T data) {
        return new Result<>(200,"success",data);
    }
    public Result<T> error(T data) {
        return new Result<>(-1,"error",data);
    }
    public Result<String> wrongParams(){
        return new Result<>(400,"error","wrong params");
    }
}
