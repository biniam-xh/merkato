package edu.mum.mercato.service;

import edu.mum.mercato.domain.Advert;

public interface AdvertService {
    public void saveAdvert(Advert advert);

    public Advert findOneAdvert();
}
