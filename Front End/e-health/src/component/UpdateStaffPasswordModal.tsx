import axios from "axios";
import React, { useState, useEffect } from "react";
import { Modal, Button, Form } from "react-bootstrap";

interface Props {
  show: boolean;
  role: string;
  handleClose: () => void;
}

const UpdateStaffPasswordModal: React.FC<Props> = ({
  show,
  handleClose,
  role,
}) => {
  const [userEmail, setUserEmail] = useState("");
  const [userPassword, setUserPassword] = useState("");

  useEffect(() => {
    // Fetch approved appointment requests from the API
    if (role == "doctor") {
      setUserEmail(
        JSON.parse(localStorage.getItem("doctorData") ?? "{}").user.userEmail
      );
    }

    if (role == "practioner") {
      setUserEmail(
        JSON.parse(localStorage.getItem("practitionerData") ?? "{}").user
          .userEmail
      );
    }
  });

  const handleUpdateStaffPassword = async () => {
    try {
      // Check if all fields have values
      if (userEmail && userPassword) {
        // Make POST API call with the details
        const response = await axios.post(
          "http://localhost:8080/users/password",
          {
            userEmail,
            userPassword,
          }
        );
        console.log("Practice registered successfully:", response.data);
        // Reset form fields and close modal
        setUserEmail("");
        setUserPassword("");
        handleClose();
      } else {
        // Show error message or alert to fill all fields
        // toast.error("Error in updating password");
        // alert("Please fill in all fields");
      }
    } catch (error) {
      console.error("Error updating password:", error);
      // Handle error gracefully
    }
  };

  // Function to check if all fields have values
  const areAllFieldsFilled = () => {
    return userEmail && userPassword;
  };

  // Function to clear modal values
  const clearModalValues = () => {
    setUserEmail("");
    setUserPassword("");
  };

  return (
    <>
      <Modal
        show={show}
        onHide={() => {
          clearModalValues();
          handleClose();
        }}
      >
        <Modal.Header closeButton>
          <Modal.Title>Update Staff Password</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="userEmail">
              <Form.Label>User Email</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter user email"
                value={userEmail}
                onChange={(e) => setUserEmail(e.target.value)}
                required
              />
            </Form.Group>
            <Form.Group controlId="userPassword">
              <Form.Label>User Password</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter user password"
                value={userPassword}
                onChange={(e) => setUserPassword(e.target.value)}
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
            onClick={handleUpdateStaffPassword}
            disabled={!areAllFieldsFilled()}
          >
            Register
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default UpdateStaffPasswordModal;
