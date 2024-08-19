package depth.mvp.thinkerbell.domain.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    public List<BannerDTO> getAllBanners() {
        return bannerRepository.findAll().stream().map(banner -> new BannerDTO(
                banner.getId(),
                banner.getTitle(),
                banner.getS3Url(),
                banner.getNoticeUrl()
        )).collect(Collectors.toList());
    }
}