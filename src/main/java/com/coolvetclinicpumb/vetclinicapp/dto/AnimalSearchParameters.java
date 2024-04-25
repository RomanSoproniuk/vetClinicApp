package com.coolvetclinicpumb.vetclinicapp.dto;

public record AnimalSearchParameters(String[] categories,
                                     String[] sex,
                                     String[] types) {
}
