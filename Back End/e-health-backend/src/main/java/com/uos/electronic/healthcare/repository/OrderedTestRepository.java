package com.uos.electronic.healthcare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.uos.electronic.healthcare.entity.OrderedTest;

@Repository
public interface OrderedTestRepository extends JpaRepository<OrderedTest, Integer> {
	
	@Query("Select ot from OrderedTest ot where ot.appointmentBooking.appointmentBookingId = :appointmentBookingId")
	Optional<OrderedTest> findByAppointmentBookingId(int appointmentBookingId);
	
	@Query("Select ot from OrderedTest ot where ot.appointmentBooking.patientPracticeRegistration.practice.practiceId = :practiceId")
	List<OrderedTest> findByPracticeId(int practiceId);
	
	@Modifying
	@Transactional
	@Query(value = "delete from ordered_test where appointment_booking_id in\n"
			+ "(select appointment_booking_id from appointment_booking where patient_practice_registration_id in \n"
			+ "(select patient_practice_registration_id  from patient_practice_registration where patient_id in \n"
			+ "(select patient_id from patient where user_id = :userId)))", nativeQuery = true)
	void deleteRecordsByUserId(int userId);

}
