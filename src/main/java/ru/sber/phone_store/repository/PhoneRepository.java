package ru.sber.phone_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.phone_store.entity.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
