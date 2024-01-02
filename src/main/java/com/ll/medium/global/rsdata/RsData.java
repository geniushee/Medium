package com.ll.medium.global.rsdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RsData<T> {
    private T obj;
    private String msg;
    private String error;

    public RsData(T object){
        this.obj = object;
    }
}
