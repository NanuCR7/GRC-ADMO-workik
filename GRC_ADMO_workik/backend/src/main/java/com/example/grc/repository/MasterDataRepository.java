package com.example.grc.repository;

import com.example.grc.domain.MasterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterDataRepository extends JpaRepository<MasterData, Long> {
    List<MasterData> findByCategory(String category);
}
