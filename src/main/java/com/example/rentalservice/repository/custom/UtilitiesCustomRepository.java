package com.example.rentalservice.repository.custom;

import com.example.rentalservice.common.RepositoryUtils;
import com.example.rentalservice.model.search.PagingResponse;
import com.example.rentalservice.model.utilities.UtilitiesSearchReqDTO;
import com.example.rentalservice.model.utilities.UtilitiesSearchResDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
@RequiredArgsConstructor
public class UtilitiesCustomRepository {

    private final EntityManager entityManager;

    public PagingResponse<UtilitiesSearchResDTO> searchUtilities(UtilitiesSearchReqDTO req) {
        StringBuilder sql = new StringBuilder();
        StringBuilder countSql = new StringBuilder();

        Map<String, Object> queryParams = new HashMap<>();

        sql.append("select * from utility ");
        sql.append(" where 1=1 ");

        countSql.append("select count(*) from utility ");
        countSql.append(" where 1=1 ");

        if (StringUtils.isNotBlank(req.getId())) {
            sql.append(" and id = :id ");
            countSql.append(" and id = :id ");
            queryParams.put("id", req.getId());
        }

        if (StringUtils.isNotBlank(req.getName())) {
            sql.append(" and unaccent(lower(name)) like unaccent(lower('%'|| :name ||'%')) ");
            countSql.append(" and unaccent(lower(name)) like unaccent(lower('%'|| :name ||'%')) ");
            queryParams.put("name", req.getName());
        }

        sql.append(" order by name asc;");

        Query query = entityManager.createNativeQuery(sql.toString());
        RepositoryUtils.setQueryParameters(queryParams, query);
        query.setFirstResult(req.getPage() * req.getSize());
        query.setMaxResults(req.getSize());

        List<UtilitiesSearchResDTO> data = new ArrayList<>();
        Long total = 0L;

        List<Object[]> result = (List<Object[]>) query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
             data = result.stream()
                    .map(x -> UtilitiesSearchResDTO
                            .builder()
                            .id(RepositoryUtils.setValueForField(String.class, x[0]))
                            .name(RepositoryUtils.setValueForField(String.class, x[1]))
                            .build())
                    .collect(Collectors.toList());

             Query queryCount = entityManager.createNativeQuery(countSql.toString());
             RepositoryUtils.setQueryParameters(queryParams, queryCount);
             total = (Long) queryCount.getSingleResult();
        }

        return new PagingResponse<>(data, total);
    }
}
