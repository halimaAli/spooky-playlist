package com.spooky.playlist.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Items<T> {
    private ArrayList<T> items;
}
