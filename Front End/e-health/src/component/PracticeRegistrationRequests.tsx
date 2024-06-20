import { useState, useEffect } from "react";
import axios from "axios";
import "./PracticeRegistrationRequests.css";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const PracticeRegistrationRequests = () => {
  const [registrationRequests, setRegistrationRequests] = useState<any[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchRegistrationRequests = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/practice/registration/requests"
        );

        setRegistrationRequests(response.data);
      } catch (error: any) {
        console.error("Error fetching registration requests:", error);
        if (error.response && error.response.status === 401) {
          navigate("/");
        }
      }
    };

    fetchRegistrationRequests();
  }, [navigate]); // Include navigate in the dependency array to ensure it's updated when navigate changes

  const approveRegistration = async (patientPracticeRegistrationId: number) => {
    try {
      // Approve the registration request
      await axios.put(
        `http://localhost:8080/practice/registration/requests/approve/${patientPracticeRegistrationId}`
      );

      // Remove the approved request from the list
      setRegistrationRequests((prevRequests) =>
        prevRequests.filter(
          (request) =>
            request.patientPracticeRegistrationId !==
            patientPracticeRegistrationId
        )
      );
      toast.success("Approved registration request");
    } catch (error: any) {
      toast.error(error.response.data.message);
      console.error("Error approving registration request:", error);
      // Handle error gracefully
    }
  };

  const declineRegistration = async (patientPracticeRegistrationId: number) => {
    try {
      // Decline the registration request
      await axios.put(
        `http://localhost:8080/practice/registration/requests/decline/${patientPracticeRegistrationId}`
      );

      // Remove the declined request from the list
      setRegistrationRequests((prevRequests) =>
        prevRequests.filter(
          (request) =>
            request.patientPracticeRegistrationId !==
            patientPracticeRegistrationId
        )
      );

      toast.success("Registration request declined successfully");
    } catch (error: any) {
      toast.error(error.response.data.message);
      console.error("Error declining registration request:", error);
      // Handle error gracefully
    }
  };

  return (
    <div className="practise-registration-container">
      <h2>Practice Registration Requests</h2>
      <table>
        <thead>
          <tr>
            <th>User Name</th>
            <th>Date of Birth</th>
            <th>Address</th>
            <th>Practice Name</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {registrationRequests.map((request) => (
            <tr key={request.patientPracticeRegistrationId}>
              <td>{request.patient.user.userFullName}</td>
              <td>
                {new Date(
                  request.patient.patientDateOfBirth
                ).toLocaleDateString("en-US", {
                  year: "numeric",
                  month: "long",
                  day: "numeric",
                })}
              </td>
              <td>{request.patient.patientAddress}</td>
              <td>{request.practice.practiceName}</td>
              <td className="d-flex">
                <button
                  className="btn btn-success m-2"
                  onClick={() =>
                    approveRegistration(request.patientPracticeRegistrationId)
                  }
                >
                  Approve
                </button>
                <button
                  className="btn btn-danger m-2"
                  onClick={() =>
                    declineRegistration(request.patientPracticeRegistrationId)
                  }
                >
                  Decline
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <ToastContainer />
    </div>
  );
};

export default PracticeRegistrationRequests;
