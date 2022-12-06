package com.codevui.realworldapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codevui.realworldapp.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

    public Optional<Role> findByName(String name);
    
}
