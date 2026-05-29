package com.thehecklers.sburrestdemo.repository;

import com.thehecklers.sburrestdemo.model.Clube;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubeRepository extends JpaRepository<Clube, Long> {
}