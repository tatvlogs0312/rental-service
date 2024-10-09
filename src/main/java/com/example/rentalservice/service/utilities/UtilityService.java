package com.example.rentalservice.service.utilities;

import com.example.rentalservice.model.search.PagingResponse;
import com.example.rentalservice.model.utilities.UtilitiesSearchReqDTO;
import com.example.rentalservice.model.utilities.UtilitiesSearchResDTO;
import com.example.rentalservice.repository.UtilitiesRepository;
import com.example.rentalservice.repository.custom.UtilitiesCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UtilityService {

    private final UtilitiesRepository utilitiesRepository;
    private final UtilitiesCustomRepository utilitiesCustomRepository;

    public PagingResponse<UtilitiesSearchResDTO> searchUtility(UtilitiesSearchReqDTO req) {
        if (req.getPage() == null) {
            req.setPage(0);
        }

        if (req.getSize() == null) {
            req.setSize(Integer.MAX_VALUE);
        }
        return utilitiesCustomRepository.searchUtilities(req);
    }
}
