import { useState, useEffect } from "react";
import axios from "axios";
import "./AppointmentDetails.css";
import { useParams } from "react-router-dom";

// Example response structure for each API
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

const AppointmentDetails = () => {
  const [prescriptionDetails, setPrescriptionDetails] = useState<string>("");
  const [referralDetails, setReferralDetails] = useState<string>("");
  const [testDetails, setTestDetails] = useState<string>("");
  const [medicalHistory, setMedicalHistory] = useState<
    MedicalHistoryResponse[]
  >([]);
  const { appointmentBookingId } = useParams();

  // Fetch data for each section
  useEffect(() => {
    // Fetch prescription data
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
    fetchTestOrderData(appointmentBookingId).then((data: TestOrderResponse) => {
      setTestDetails(data.orderedTestDetails);
    });
  }, [appointmentBookingId]);

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

  // Render the UI with the extracted data
  return (
    <>
      <div className="appointment-details-container">
        <h2>Appointment Details</h2>
        <div className="col-md-12 singular-details">
          <div className="col-md-4 prescription-details">
            <h3>Prescription Details</h3>
            <p>{prescriptionDetails}</p>
          </div>
          <div className="col-md-4 referral-details">
            <h3>Referral Details</h3>
            <p>{referralDetails}</p>
          </div>
          <div className="col-md-4 test-details">
            <h3>Test Details</h3>
            <p>{testDetails}</p>
          </div>
        </div>
        <div className="multiple-details">
          <h3>Medical History</h3>
          {medicalHistory.map((record, index) => (
            <div key={index} className="medical-history-record">
              <p>Date: {record.medicalHistoryDate}</p>
              <p>{record.medicalHistoryDetails}</p>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default AppointmentDetails;
