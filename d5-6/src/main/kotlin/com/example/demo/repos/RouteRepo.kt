package com.example.demo.repos

import com.example.demo.entity.AvailableRoutes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RouteRepo:JpaRepository<AvailableRoutes, String> {
}