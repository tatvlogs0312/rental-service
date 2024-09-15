package com.example.rentalservice.service.utilities;

import com.example.rentalservice.entity.Utilities;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.repository.UtilitiesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UtilityService {

    private final UtilitiesRepository utilitiesRepository;

    public Utilities getById(String id) {
        Optional<Utilities> utilities = utilitiesRepository.findById(id);
        if (utilities.isEmpty()) {
            throw new ApplicationException("Loại dịch vụ không tồn tại");
        }

        return utilities.get();
    }
}
