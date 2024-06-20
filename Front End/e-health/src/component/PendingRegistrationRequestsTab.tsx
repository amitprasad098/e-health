import { useState, useEffect } from "react";
import axios from "axios";
import "./PendingRegistrationRequestsTab.css";
import AlternativeModal from "./AlternativeModal";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const PendingRegistrationRequestsTab = () => {
  const [requests, setRequests] = useState<any[]>([]);
  const [doctors, setDoctors] = useState<any[]>([]);
  const [selectedDoctors, setSelectedDoctors] = useState<
    Record<number, string>
  >({});
  const [showModal, setShowModal] = useState<boolean>(false);
  var practitionerId = 0;
  var practitionerPracticeId = 0;

  useEffect(() => {
    // Fetch pending registration requests
    const storedPractitionerDataString =
      localStorage.getItem("practitionerData");
    const storedPractitionerData = storedPractitionerDataString
      ? JSON.parse(storedPractitionerDataString)
      : [];
    practitionerId = storedPractitionerData?.practitionerId;
    practitionerPracticeId = storedPractitionerData?.practice?.practiceId;
    axios
      .get(
        `http://localhost:8080/appointments/requests/${practitionerId}/pending`
      )
      .then((response) => {
        setRequests(response.data);
      })
      .catch((error) => {
        console.error("Error fetching pending requests:", error);
      });

    // Fetch available doctors
    axios
      .get(
        `http://localhost:8080/appointments/requests/${practitionerPracticeId}/doctors`
      )
      .then((response) => {
        setDoctors(response.data);
      })
      .catch((error) => {
        console.error("Error fetching available doctors:", error);
      });
  }, []);

  const handleCloseModal = () => setShowModal(false);
  const handleShowModal = () => setShowModal(true);

  const handleSubmitAlternative = async (
    appointmentBookingId: any,
    alternativeMessage: string
  ) => {
    const postData = {
      alternativeMessage: alternativeMessage,
      appointmentBookingId: appointmentBookingId,
    };

    // Send POST request with alternative reason
    try {
      const response = await axios.post(
        "http://localhost:8080/appointments/requests/alternative",
        postData
      );
      // Handle success response
      console.log("Offer alternative successful:", response.data);
      toast.success("Alternative offered successfully");
      handleCloseModal();
    } catch (error: any) {
      // Handle error
      console.error("Error offering alternative:", error);
      toast.error(error.response.data.message);
    }
  };

  const handleApprove = async (appointmentBookingId: any) => {
    // Make PUT API call to approve the request with the selected doctor ID
    const doctorId = selectedDoctors[appointmentBookingId];
    try {
      const response = await axios.put(
        `http://localhost:8080/appointments/requests/approve/${appointmentBookingId}/doctor/${doctorId}`
      );
      // Handle success response
      console.log("Appointment approved successfully:", response.data);
      toast.success("Appointment approved successfully");
    } catch (error: any) {
      // Handle error
      console.error("Error approving appointment:", error);
      toast.error(error.response.data.message);
    }
    console.log(
      "Approving request with ID:",
      appointmentBookingId,
      "and doctor ID:",
      doctorId
    );
  };

  const handleDecline = async (appointmentBookingId: any) => {
    try {
      const response = await axios.put(
        `http://localhost:8080/appointments/requests/decline/${appointmentBookingId}`
      );
      // Handle success response
      console.log("Appointment declined successfully:", response.data);
      toast.success("Appointment declined successfully");
    } catch (error: any) {
      // Handle error
      toast.error(error.response.data.message);
      console.error("Error approving appointment:", error);
    }
    // Make PUT API call to decline the request with the given ID
    console.log("Declining request with ID:", appointmentBookingId);
  };

  const handleDoctorSelect = (appointmentBookingId: any, doctorId: string) => {
    setSelectedDoctors({
      ...selectedDoctors,
      [appointmentBookingId]: doctorId,
    });
  };

  return (
    <div className="pending-registration-requests-container">
      <h2>Pending Registration Requests</h2>
      <table>
        <thead>
          <tr>
            <th>Booking ID</th>
            <th>Patient Name</th>
            <th>Practice Name</th>
            <th>Appointment Details</th>
            <th>Appointment Date</th>
            <th>Available Doctors</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {requests.map((request) => (
            <tr key={request.appointmentBookingId}>
              <td>{request.appointmentBookingId}</td>
              <td>
                {request.patientPracticeRegistration.patient.user.userFullName}
              </td>
              <td>
                {request.patientPracticeRegistration.practice.practiceName}
              </td>
              <td>{request.appointmentDetails}</td>
              <td>
                {new Date(request.appointmentDate).toLocaleDateString("en-US", {
                  year: "numeric",
                  month: "long",
                  day: "numeric",
                })}
              </td>
              <td>
                <select
                  value={selectedDoctors[request.appointmentBookingId] || ""}
                  onChange={(e) =>
                    handleDoctorSelect(
                      request.appointmentBookingId,
                      e.target.value
                    )
                  }
                >
                  <option value="">Select Doctor</option>
                  {doctors.map((doctor) => (
                    <option key={doctor.doctorId} value={doctor.doctorId}>
                      {doctor.user.userFullName}
                    </option>
                  ))}
                </select>
              </td>
              <td className="d-flex">
                <button
                  className="btn btn-success m-1"
                  onClick={() => handleApprove(request.appointmentBookingId)}
                  disabled={!selectedDoctors[request.appointmentBookingId]}
                >
                  Approve
                </button>
                <button
                  className="btn btn-danger m-1"
                  onClick={() => handleDecline(request.appointmentBookingId)}
                >
                  Decline
                </button>
                <button
                  className="btn btn-warning m-1"
                  onClick={handleShowModal}
                >
                  Alternative
                </button>
                <AlternativeModal
                  show={showModal}
                  onClose={handleCloseModal}
                  onSubmit={handleSubmitAlternative}
                  appointmentBookingId={request.appointmentBookingId}
                />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <ToastContainer />
    </div>
  );
};

export default PendingRegistrationRequestsTab;
