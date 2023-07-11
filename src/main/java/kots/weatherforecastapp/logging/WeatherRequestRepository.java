package kots.weatherforecastapp.logging;

import org.springframework.data.jpa.repository.JpaRepository;

interface WeatherRequestRepository extends JpaRepository<WeatherRequest, Long> {

}
