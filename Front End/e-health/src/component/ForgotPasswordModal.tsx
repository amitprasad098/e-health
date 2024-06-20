import React, { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import axios from "axios";

interface ForgotPasswordModalProps {
  show: boolean;
  handleClose: () => void;
}

const ForgotPasswordModal: React.FC<ForgotPasswordModalProps> = ({
  show,
  handleClose,
}) => {
  const [email, setEmail] = useState("");

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      // Make API call to reset password using GET request with email as Path Variable
      await axios.get(`http://localhost:8080/users/forgot-password/${email}`);
      // Close modal after successful submission
      handleClose();
    } catch (error) {
      console.error("Error resetting password:", error);
      // Handle error, if any
    }
  };

  const onChangeEmail = (value: any, divId: any) => {
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if(value && value != ""){
      if (emailRegex.test(value)) {
        document.getElementById(divId)?.classList.remove("required-class");
      }
      else {
        document.getElementById(divId)?.classList.add("required-class");
      }
    }
    setEmail(value); 
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Forgot Password</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          <Form.Group controlId="formEmail">
            <Form.Label>Email</Form.Label>
            <Form.Control
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => {onChangeEmail(e.target.value, "forgot-pass-modal-email")}}
              className="mb-3"
              id="forgot-pass-modal-email"
            />
          </Form.Group>
          <Button variant="primary" type="submit" disabled={!email}>
            Reset Password
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default ForgotPasswordModal;
