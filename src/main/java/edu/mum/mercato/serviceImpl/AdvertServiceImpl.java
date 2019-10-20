package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.repository.AddressRepository;
import edu.mum.mercato.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertServiceImpl implements AdvertService {

    @Autowired
    private AddressRepository addressRepository;
}
