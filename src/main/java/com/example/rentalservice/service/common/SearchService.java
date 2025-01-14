package com.example.rentalservice.service.common;

import com.example.rentalservice.model.search.KeywordDTO;
import com.example.rentalservice.repository.custom.KeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final KeywordRepository keywordRepository;

    private final Pattern pricePattern = Pattern.compile("(\\d+\\.?\\d*)\\s*(triệu|trieu|tr|rưỡi|ruoi|m|M)", Pattern.CASE_INSENSITIVE);

    public KeywordDTO handleKeyword(String keyword) {
        keyword = keyword.toLowerCase();

        KeywordDTO keywordDTO = new KeywordDTO();

        Matcher priceMatcher = pricePattern.matcher(keyword);
        if (priceMatcher.find()) {
            Double price;
            if (keyword.contains("rưỡi") || keyword.contains("ruoi")) {
                price = Double.parseDouble(priceMatcher.group(1)) * 1000000 + 500000;
            } else {
                price = Double.parseDouble(priceMatcher.group(1)) * 1000000;
            }

//            if (keyword.contains("khoảng")) {
//                keywordDTO.setPriceFrom(price.longValue() - 1000000);
//                keywordDTO.setPriceTo(price.longValue() + 1000000);
//            } else {
//                keywordDTO.setPriceIs(price.longValue());
//            }

            keywordDTO.setPriceFrom(price.longValue() - 1000000);
            keywordDTO.setPriceTo(price.longValue() + 1000000);
        }

        //Roomtype
        for (String roomType : keywordRepository.roomTypes.keySet()) {
            if (keyword.toLowerCase().contains(roomType.toLowerCase())) {
                keywordDTO.setRoomType(keywordRepository.roomTypes.get(roomType));
                break;
            }
        }

        //Province
        for (String province : keywordRepository.provinces) {
            if (keyword.toLowerCase().contains(province.toLowerCase())) {
                keywordDTO.setProvince(province);
                break;
            }
        }

        //District
        for (String district : keywordRepository.districts) {
            if (keyword.toLowerCase().contains(district.toLowerCase())) {
                keywordDTO.setDistrict(district);
                break;
            }
        }

        //Ward
        for (String ward : keywordRepository.wards) {
            if (keyword.toLowerCase().contains(ward.toLowerCase())) {
                keywordDTO.setWard(ward);
                break;
            }
        }

        return keywordDTO;
    }
}
