import axios from "axios";
import React, { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";

interface Props {
  show: boolean;
  handleClose: () => void;
}

const PracticeRegistrationModal: React.FC<Props> = ({ show, handleClose }) => {
  const [practiceName, setPracticeName] = useState("");
  const [practiceDescription, setPracticeDescription] = useState("");
  const [practiceAddress, setPracticeAddress] = useState("");
  const [practiceContactDetails, setPracticeContactDetails] = useState("");
  const [practiceImageUrl, setPracticeImageUrl] = useState("");

  const handlePracticeRegistration = async () => {
    try {
      // Check if all fields have values
      if (
        practiceName &&
        practiceDescription &&
        practiceAddress &&
        practiceContactDetails &&
        practiceImageUrl
      ) {
        // Make POST API call with the details
        const response = await axios.post("http://localhost:8080/practices", {
          practiceName,
          practiceDescription,
          practiceAddress,
          practiceContactDetails,
          practiceImageUrl,
        });
        console.log("Practice registered successfully:", response.data);
        // Reset form fields and close modal
        setPracticeName("");
        setPracticeDescription("");
        setPracticeAddress("");
        setPracticeContactDetails("");
        setPracticeImageUrl("");
        handleClose();
      } else {
        // Show error message or alert to fill all fields
        alert("Please fill in all fields");
      }
    } catch (error) {
      console.error("Error registering practice:", error);
      // Handle error gracefully
    }
  };

  // Function to check if all fields have values
  const areAllFieldsFilled = () => {
    return (
      practiceName &&
      practiceDescription &&
      practiceAddress &&
      practiceContactDetails &&
      practiceImageUrl
    );
  };

  // Function to clear modal values
  const clearModalValues = () => {
    setPracticeName("");
    setPracticeDescription("");
    setPracticeAddress("");
    setPracticeContactDetails("");
    setPracticeImageUrl("");
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
        <Modal.Title>Register Practice</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="practiceName">
            <Form.Label>Practice Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter practice name"
              value={practiceName}
              onChange={(e) => setPracticeName(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group controlId="practiceDescription">
            <Form.Label>Practice Description</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter practice description"
              value={practiceDescription}
              onChange={(e) => setPracticeDescription(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group controlId="practiceAddress">
            <Form.Label>Practice Address</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter practice address"
              value={practiceAddress}
              onChange={(e) => setPracticeAddress(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group controlId="practiceContactDetails">
            <Form.Label>Practice Contact Details</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter practice contact details"
              value={practiceContactDetails}
              onChange={(e) => setPracticeContactDetails(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group controlId="practiceImageUrl">
            <Form.Label>Practice Image URL</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter practice image url"
              value={practiceImageUrl}
              onChange={(e) => setPracticeImageUrl(e.target.value)}
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
          onClick={handlePracticeRegistration}
          disabled={!areAllFieldsFilled()}
        >
          Register
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default PracticeRegistrationModal;
