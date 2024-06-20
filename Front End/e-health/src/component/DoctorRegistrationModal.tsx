import React, { useState, useEffect } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import axios from "axios";

interface Props {
  show: boolean;
  handleClose: () => void;
}

const DoctorRegistrationModal: React.FC<Props> = ({ show, handleClose }) => {
  const [doctorName, setDoctorName] = useState("");
  const [doctorEmail, setDoctorEmail] = useState("");
  const [doctorPassword, setDoctorPassword] = useState("");
  const [practiceName, setPracticeName] = useState("");
  const [doctorDegree, setDoctorDegree] = useState("");
  const [doctorSpeciality, setDoctorSpeciality] = useState("");
  const [practices, setPractices] = useState<any[]>([]);

  useEffect(() => {
    // Fetch practice names from the server
    const fetchPractices = async () => {
      try {
        const response = await axios.get("http://localhost:8080/practices");
        setPractices(response.data);
      } catch (error) {
        console.error("Error fetching practices:", error);
      }
    };

    fetchPractices();
  }, []);

  const handleDoctorRegistration = async () => {
    try {
      // Check if all fields have values
      if (
        doctorName &&
        doctorEmail &&
        doctorPassword &&
        practiceName &&
        doctorDegree &&
        doctorSpeciality
      ) {
        // Make POST API call with the details
        const response = await axios.post(
          "http://localhost:8080/users/doctors",
          {
            doctorName,
            doctorEmail,
            doctorPassword,
            practiceName,
            doctorDegree,
            doctorSpeciality,
          }
        );
        console.log("Doctor registered successfully:", response.data);
        // Reset form fields and close modal
        setDoctorName("");
        setDoctorEmail("");
        setDoctorPassword("");
        setPracticeName("");
        setDoctorDegree("");
        setDoctorSpeciality("");
        handleClose();
      } else {
        // Show error message or alert to fill all fields
        alert("Please fill in all fields");
      }
    } catch (error) {
      console.error("Error registering doctor:", error);
      // Handle error gracefully
    }
  };

  // Function to check if all fields have values
  const areAllFieldsFilled = () => {
    return (
      doctorName &&
      doctorEmail &&
      doctorPassword &&
      practiceName &&
      doctorDegree &&
      doctorSpeciality
    );
  };

  // Function to clear modal values
  const clearModalValues = () => {
    setDoctorName("");
    setDoctorEmail("");
    setDoctorPassword("");
    setPracticeName("");
    setDoctorDegree("");
    setDoctorSpeciality("");
  };

  return (
    <Modal
      show={show}
      onHide={() => {
        clearModalValues();
        handleClose();
      }}
    >
      <Modal.Header closeButton>
        <Modal.Title>Register Doctor</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="doctorName">
            <Form.Label>Doctor Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter doctor name"
              value={doctorName}
              onChange={(e) => setDoctorName(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group controlId="doctorEmail">
            <Form.Label>Doctor Email</Form.Label>
            <Form.Control
              type="email"
              placeholder="Enter doctor email"
              value={doctorEmail}
              onChange={(e) => setDoctorEmail(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group controlId="doctorPassword">
            <Form.Label>Doctor Password</Form.Label>
            <Form.Control
              type="password"
              placeholder="Enter doctor password"
              value={doctorPassword}
              onChange={(e) => setDoctorPassword(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group controlId="practiceName">
            <Form.Label>Practice Name</Form.Label>
            <Form.Control
              as="select"
              value={practiceName}
              onChange={(e) => setPracticeName(e.target.value)}
              required
            >
              <option value="">Select practice</option>
              {practices.map((practice) => (
                <option key={practice.practiceId} value={practice.practiceName}>
                  {practice.practiceName}
                </option>
              ))}
            </Form.Control>
          </Form.Group>
          <Form.Group controlId="doctorDegree">
            <Form.Label>Doctor Degree</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter doctor degree"
              value={doctorDegree}
              onChange={(e) => setDoctorDegree(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group controlId="doctorSpeciality">
            <Form.Label>Doctor Speciality</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter doctor speciality"
              value={doctorSpeciality}
              onChange={(e) => setDoctorSpeciality(e.target.value)}
              required
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button
          variant="secondary"
          onClick={() => {
            clearModalValues();
            handleClose();
          }}
        >
          Close
        </Button>
        <Button
          variant="primary"
          onClick={handleDoctorRegistration}
          disabled={!areAllFieldsFilled()}
        >
          Register
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default DoctorRegistrationModal;
