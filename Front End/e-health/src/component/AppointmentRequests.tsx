import { useState, useEffect } from "react";
import axios from "axios";
import "./AppointmentRequests.css";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

interface PrescriptionResponse {
  prescriptionDetails: string;
  prescriptionDate: string;
}

interface ReferralResponse {
  specialistReferralDetails: string;
  specialistReferralDate: string;
}

interface MedicalHistoryResponse {
  medicalHistoryDetails: string;
  medicalHistoryDate: string;
}

interface TestOrderResponse {
  orderedTestDetails: string;
  orderedTestDate: string;
}

const AppointmentRequests = () => {
  const [appointments, setAppointments] = useState<any[]>([]);
  const [prescriptionDetails, setPrescriptionDetails] = useState<string>("");
  const [referralDetails, setReferralDetails] = useState<string>("");
  const [testDetails, setTestDetails] = useState<string>("");
  const [medicalHistory, setMedicalHistory] = useState<
    MedicalHistoryResponse[]
  >([]);
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  // const navigate = useNavigate();
  var signedInPatientId = 0;

  const viewMore = async (ppointmentBookingId: any) => {
    viewMoreController(ppointmentBookingId).then(() => {
      handleShow();
    });
  };

  const viewMoreController = async (appointmentBookingId: any) => {
    try {
      fetchPrescriptionData(appointmentBookingId).then(
        (data: PrescriptionResponse) => {
          setPrescriptionDetails(data.prescriptionDetails);
        }
      );

      // Fetch referral data
      fetchReferralData(appointmentBookingId).then((data: ReferralResponse) => {
        setReferralDetails(data.specialistReferralDetails);
      });

      // Fetch medical history data
      fetchMedicalHistoryData(appointmentBookingId).then((data) => {
        // Sort medical history records by date in descending order
        const sortedMedicalHistory = data.sort(
          (
            a: { medicalHistoryDate: string | number | Date },
            b: { medicalHistoryDate: string | number | Date }
          ) =>
            new Date(b.medicalHistoryDate).getTime() -
            new Date(a.medicalHistoryDate).getTime()
        );
        setMedicalHistory(sortedMedicalHistory);
      });

      // Fetch test order data
      fetchTestOrderData(appointmentBookingId).then(
        (data: TestOrderResponse) => {
          setTestDetails(data.orderedTestDetails);
        }
      );
    } catch (error) {
      console.error("Error -> ", error);
    }
  };

  const fetchPrescriptionData = async (appointmentBookingId: any) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/patients/prescription/${appointmentBookingId}`
      );
      return response.data; // Assuming the response is in the format { prescriptionDetails: string, prescriptionDate: string }
    } catch (error) {
      console.error("Error fetching prescription data:", error);
      throw error; // You may handle errors according to your application's logic
    }
  };

  // Function to fetch referral data
  const fetchReferralData = async (appointmentBookingId: any) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/patients/referral/${appointmentBookingId}`
      );
      return response.data; // Assuming the response is in the format { referralDetails: string, referralDate: string }
    } catch (error) {
      console.error("Error fetching referral data:", error);
      throw error;
    }
  };

  // Function to fetch medical history data
  const fetchMedicalHistoryData = async (appointmentBookingId: any) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/medical-history/${appointmentBookingId}`
      );
      return response.data; // Assuming the response is in the format { medicalHistoryDetails: string, medicalHistoryDate: string }
    } catch (error) {
      console.error("Error fetching medical history data:", error);
      throw error;
    }
  };

  // Function to fetch test order data
  const fetchTestOrderData = async (appointmentBookingId: any) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/patients/tests/${appointmentBookingId}`
      );
      return response.data; // Assuming the response is in the format { testName: string, testDate: string }
    } catch (error) {
      console.error("Error fetching test order data:", error);
      throw error;
    }
  };

  useEffect(() => {
    const storedPatientDataString = localStorage.getItem("patientData");
    const storedPatientData = storedPatientDataString
      ? JSON.parse(storedPatientDataString)
      : [];
    signedInPatientId = storedPatientData.patientId;

    const fetchAppointments = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/appointments/${signedInPatientId}`
        );
        setAppointments(response.data);
      } catch (error) {
        console.error("Error fetching appointments:", error);
      }
    };

    fetchAppointments();
  }, [signedInPatientId]);

  return (
    <div className="appointment-requests-container">
      <h2>Appointment Requests</h2>
      <table>
        <thead>
          <tr>
            <th>Patient Name</th>
            <th>Appointment Details</th>
            <th>Appointment Status</th>
            <th>Appointment Date</th>
            <th>Practitioner Feedback</th>
            <th>Details</th>
          </tr>
        </thead>
        <tbody>
          {appointments.map((appointment) => (
            <tr key={appointment.appointmentBookingId}>
              <td>
                {
                  appointment.patientPracticeRegistration.patient.user
                    .userFullName
                }
              </td>
              <td>{appointment.appointmentDetails}</td>
              <td>{appointment.appointmentStatus}</td>
              <td>
                {new Date(appointment.appointmentDate).toLocaleDateString(
                  "en-US",
                  { year: "numeric", month: "long", day: "numeric" }
                )}
              </td>
              <td>{appointment.practitionerFeedback}</td>
              <td>
                <button
                  className="btn btn-success"
                  onClick={() => viewMore(appointment.appointmentBookingId)}
                >
                  View More
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <Modal show={show} onHide={handleClose} id="appointment-details-modal">
        <Modal.Header closeButton>
          <Modal.Title>Appointment Details</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <p className="mb-1">
            <strong>Prescription details:</strong>
          </p>
          <p className="mb-2 ms-3">{prescriptionDetails}</p>

          <p className="mb-1">
            <strong>Referral Details:</strong>
          </p>
          <p className="mb-2 ms-3">{referralDetails}</p>

          <p className="mb-1">
            <strong>Test Details:</strong>
          </p>
          <p className="mb-2 ms-3">{testDetails}</p>

          <p className="mb-1">
            <strong>Medical History:</strong>
          </p>
          <ul id="medical-history-list">
            {medicalHistory.map((record, index) => (
              <li className="m-0 medical-history-record" key={index}>
                <p className="mb-1">
                  <strong>Date:</strong>{" "}
                  {new Date(record.medicalHistoryDate).toLocaleDateString(
                    "en-US",
                    {
                      year: "numeric",
                      month: "long",
                      day: "numeric",
                    }
                  )}
                </p>
                <p className="mb-2 ms-3">{record.medicalHistoryDetails}</p>
              </li>
            ))}
          </ul>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default AppointmentRequests;
