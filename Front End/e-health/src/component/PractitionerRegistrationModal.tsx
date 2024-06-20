import React, { useState, useEffect } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import axios from "axios";

interface Props {
  show: boolean;
  handleClose: () => void;
}

const PractitionerRegistrationModal: React.FC<Props> = ({
  show,
  handleClose,
}) => {
  const [practitionerName, setPractitionerName] = useState("");
  const [practitionerEmail, setPractitionerEmail] = useState("");
  const [practitionerPassword, setPractitionerPassword] = useState("");
  const [practiceName, setPracticeName] = useState("");
  const [practitionerDegree, setPractitionerDegree] = useState("");
  const [practices, setPractices] = useState<any[]>([]);

  useEffect(() => {
    // Fetch practice names from the server
    const fetchPractices = async () => {
      try {
        const response = await axios.get("http://localhost:8080/new-practices");
        setPractices(response.data);
      } catch (error) {
        console.error("Error fetching practices:", error);
      }
    };

    fetchPractices();
  }, []);

  const handlePractitionerRegistration = async () => {
    try {
      // Check if all fields have values
      if (
        practitionerName &&
        practitionerEmail &&
        practitionerPassword &&
        practiceName &&
        practitionerDegree
      ) {
        // Make POST API call with the details
        const response = await axios.post(
          "http://localhost:8080/users/practitioners",
          {
            practitionerName,
            practitionerEmail,
            practitionerPassword,
            practiceName,
            practitionerDegree,
          }
        );
        console.log("Practitioner registered successfully:", response.data);
        // Reset form fields and close modal
        clearModalValues();
        handleClose();
      } else {
        // Show error message or alert to fill all fields
        alert("Please fill in all fields");
      }
    } catch (error) {
      console.error("Error registering practitioner:", error);
      // Handle error gracefully
    }
  };

  // Function to check if all fields have values
  const areAllFieldsFilled = () => {
    return (
      practitionerName &&
      practitionerEmail &&
      practitionerPassword &&
      practiceName &&
      practitionerDegree
    );
  };

  // Function to clear modal values
  const clearModalValues = () => {
    setPractitionerName("");
    setPractitionerEmail("");
    setPractitionerPassword("");
    setPracticeName("");
    setPractitionerDegree("");
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
        <Modal.Title>Register Practitioner</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="practitionerName">
            <Form.Label>Practitioner Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter practitioner name"
              value={practitionerName}
              onChange={(e) => setPractitionerName(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group controlId="practitionerEmail">
            <Form.Label>Practitioner Email</Form.Label>
            <Form.Control
              type="email"
              placeholder="Enter practitioner email"
              value={practitionerEmail}
              onChange={(e) => setPractitionerEmail(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group controlId="practitionerPassword">
            <Form.Label>Practitioner Password</Form.Label>
            <Form.Control
              type="password"
              placeholder="Enter practitioner password"
              value={practitionerPassword}
              onChange={(e) => setPractitionerPassword(e.target.value)}
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
          <Form.Group controlId="practitionerDegree">
            <Form.Label>Practitioner Degree</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter practitioner degree"
              value={practitionerDegree}
              onChange={(e) => setPractitionerDegree(e.target.value)}
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
          onClick={handlePractitionerRegistration}
          disabled={!areAllFieldsFilled()}
        >
          Register
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default PractitionerRegistrationModal;
