package com.co2.mold.repositories;

import com.co2.mold.pojos.Dew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DewRepository extends JpaRepository<Dew, Long> {


}
