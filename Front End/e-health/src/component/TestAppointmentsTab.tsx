import { useState, useEffect } from "react";
import { Table, Button } from "react-bootstrap";
import axios from "axios";
import "./TestAppointmentsTab.css";
import TestResultModal from "./TestResultModal";
import UpdateMedicalHistoryModal from "./UpdateMedicalHistoryModal";

const TestAppointmentsTab = () => {
  const [testAppointments, setTestAppointments] = useState<any[]>([]);
  const [showNotifyPatientModal, setShowNotifyPatientModal] =
    useState<boolean>(false);
  const [showMedicalHistoryModal, setShowMedicalHistoryModal] =
    useState<boolean>(false);
  var practitionerPracticeId = 0;

  useEffect(() => {
    // Fetch test appointments data from the backend
    const storedPractitionerDataString =
      localStorage.getItem("practitionerData");
    const storedPractitionerData = storedPractitionerDataString
      ? JSON.parse(storedPractitionerDataString)
      : [];
    practitionerPracticeId = storedPractitionerData?.practice?.practiceId;
    axios
      .get(
        `http://localhost:8080/appointments/requests/tests/${practitionerPracticeId}`
      )
      .then((response) => {
        setTestAppointments(response.data);
      })
      .catch((error) => {
        console.error("Error fetching test appointments:", error);
      });
  }, []);

  const handleCloseNotifyPatientModal = () => setShowNotifyPatientModal(false);
  const handleShowNotifyPatientModal = () => setShowNotifyPatientModal(true);

  const handleOpenMedicalHistoryModal = () => {
    setShowMedicalHistoryModal(true);
  };

  const handleCloseMedicalHistoryModal = () => {
    setShowMedicalHistoryModal(false);
  };

  const handleNotifyPatient = async (
    appointmentBookingId: any,
    testResult: string
  ) => {
    const postData = {
      appointmentBookingId: appointmentBookingId,
      testResult: testResult,
    };

    // Send POST request with alternative reason
    try {
      const response = await axios.post(
        "http://localhost:8080/patients/tests/result",
        postData
      );
      // Handle success response
      console.log("Email Test Result successful:", response.data);
      handleCloseNotifyPatientModal();
    } catch (error) {
      // Handle error
      console.error("Error sending test result email:", error);
    }
  };

  const handleUpdateMedicalHistory = async (
    appointmentBookingId: any,
    medicalHistoryDetails: string,
    medicalHistoryDate: string
  ) => {
    const postMedicalHistoryData = {
      appointmentBookingId: appointmentBookingId,
      medicalHistoryDetails: medicalHistoryDetails,
      medicalHistoryDate: medicalHistoryDate,
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/medical-history",
        postMedicalHistoryData
      );
      // Handle success response
      console.log("Update Medical History successful:", response.data);
      handleCloseMedicalHistoryModal();
    } catch (error) {
      // Handle error
      console.error("Error updating medical history:", error);
    }
  };

  return (
    <div className="test-appointment-container">
      <h2>Test Appointments</h2>
      <Table  bordered hover id="test-appointment-table">
        <thead>
          <tr>
            <th>Appointment ID</th>
            <th>Patient Name</th>
            <th>Doctor Name</th>
            <th>Test Details</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {testAppointments.map((appointment) => (
            <tr key={appointment.appointmentBooking.appointmentBookingId}>
              <td>{appointment.appointmentBooking.appointmentBookingId}</td>
              <td>
                {
                  appointment.appointmentBooking.patientPracticeRegistration
                    .patient.user.userFullName
                }
              </td>
              <td>{appointment.appointmentBooking.doctor.user.userFullName}</td>
              <td>{appointment.orderedTestDetails}</td>
              <td className="d-flex">
                <Button
                  variant="primary"
                  onClick={handleShowNotifyPatientModal}
                  className="m-2"
                >
                  Notify Patient
                </Button>
                <TestResultModal
                  show={showNotifyPatientModal}
                  onClose={handleCloseNotifyPatientModal}
                  onSubmit={handleNotifyPatient}
                  appointmentBookingId={
                    appointment.appointmentBooking.appointmentBookingId
                  }
                />
                <Button
                  variant="success"
                  onClick={handleOpenMedicalHistoryModal}
                  className="m-2"
                >
                  Update Medical History
                </Button>
                <UpdateMedicalHistoryModal
                  show={showMedicalHistoryModal}
                  onClose={handleCloseMedicalHistoryModal}
                  onSubmit={handleUpdateMedicalHistory}
                  appointmentBookingId={
                    appointment.appointmentBooking.appointmentBookingId
                  }
                />
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
};

export default TestAppointmentsTab;
