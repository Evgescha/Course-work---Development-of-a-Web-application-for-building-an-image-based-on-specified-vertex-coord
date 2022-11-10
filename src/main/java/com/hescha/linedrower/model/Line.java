package com.hescha.linedrower.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Line implements Comparable<Line> {
    private Integer lineNumber;
    private List<Point> points = new ArrayList<>();

    @Override
    public int compareTo(Line o) {
        return lineNumber.compareTo(o.lineNumber);
    }
}
