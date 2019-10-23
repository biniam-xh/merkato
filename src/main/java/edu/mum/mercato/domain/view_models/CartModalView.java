package edu.mum.mercato.domain.view_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartModalView {
    private String imageURL;
    private String title;
    private Long itemId;
}
