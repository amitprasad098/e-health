import React, { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";

interface TestResultModalProps {
  show: boolean;
  onClose: () => void;
  onSubmit: (appointmentBookingId: string, testResult: string) => Promise<void>;
  appointmentBookingId: string;
}

const TestResultModal: React.FC<TestResultModalProps> = ({
  show,
  onClose,
  onSubmit,
  appointmentBookingId,
}) => {
  const [testResult, setTestResult] = useState<string>("");

  const handleSubmit = async () => {
    await onSubmit(appointmentBookingId, testResult);
    onClose();
    setTestResult("");
  };

  const handleClose = () => {
    onClose();
    setTestResult("");
  };

  return (
    <Modal show={show} onHide={onClose}>
      <Modal.Header closeButton>
        <Modal.Title>Enter Test Result</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form.Control
          as="textarea"
          rows={3}
          placeholder="Enter test result"
          value={testResult}
          onChange={(e) => setTestResult(e.target.value)}
        />
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
        <Button variant="primary" onClick={handleSubmit}>
          Submit
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default TestResultModal;
