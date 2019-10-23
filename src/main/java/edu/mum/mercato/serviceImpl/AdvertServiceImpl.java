package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.domain.AddImage;
import edu.mum.mercato.domain.Advert;
import edu.mum.mercato.domain.ProductImage;
import edu.mum.mercato.repository.AddImageRepository;
import edu.mum.mercato.repository.AddressRepository;
import edu.mum.mercato.repository.AdvertRepository;
import edu.mum.mercato.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class AdvertServiceImpl implements AdvertService {

    @Autowired
    AdvertRepository  advertRepository;
    @Autowired
    AddImageRepository addImageRepository;

    @Override
    public void saveAdvert(Advert advert) {
        advert.setId(1);
        MultipartFile advertImage = advert.getImage();
        if (advertImage != null && !advertImage.isEmpty()) {
            try {

//                String url = "C:\\restImages\\" + productImage.getOriginalFilename();
                String dest = "C:\\Users\\handakina\\Documents\\GitHub\\merkato\\src\\main\\resources\\static\\images\\products\\"
                        + advertImage.getOriginalFilename();
                String url = "images\\products\\" + advertImage.getOriginalFilename();

                advertImage.transferTo(new File(dest));

                AddImage advImageOb = new AddImage();
                advImageOb.setId(1L);
                advImageOb.setImageURL(url);
                advert.setImageUrl(advImageOb);
                advImageOb.setAdvert(advert);
                addImageRepository.save(advImageOb);

            } catch (Exception e) {
                throw new RuntimeException("Product Image saving failed", e);
            }
        }

        advertRepository.save(advert);
    }

    @Override
    public Advert findOneAdvert() {
        return advertRepository.findFirstBy().orElse(null);
    }
}
