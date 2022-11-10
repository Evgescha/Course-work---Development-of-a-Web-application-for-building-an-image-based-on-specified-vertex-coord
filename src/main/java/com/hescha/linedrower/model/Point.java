package com.hescha.linedrower.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {
    @NonNull
    private Integer pointNumber;

    @NonNull
    private Integer pointY;

    @NonNull
    private Integer pointX;
}
