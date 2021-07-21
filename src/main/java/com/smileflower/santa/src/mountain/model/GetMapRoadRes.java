package com.smileflower.santa.src.mountain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetMapRoadRes {
    private List<GetRoadRes> road;
    private GetMapRes mountain;

    public GetMapRoadRes() {

    }
}
