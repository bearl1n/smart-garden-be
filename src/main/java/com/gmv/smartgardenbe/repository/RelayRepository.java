package com.gmv.smartgardenbe.repository;

import com.gmv.smartgardenbe.entity.Relay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface RelayRepository extends JpaRepository<Relay, Long> {

    @Query(value = "select relay from Relay relay where relay.id in :id")
    Relay getById(@Param("id")  @NonNull Long relayId);

}
