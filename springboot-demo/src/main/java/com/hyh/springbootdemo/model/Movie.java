package com.hyh.springbootdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author MLY
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {
    private String name;

    private String type;

    private List<Object> staff;
}
