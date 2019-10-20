package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.repository.AddressRepository;
import edu.mum.mercato.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
}
