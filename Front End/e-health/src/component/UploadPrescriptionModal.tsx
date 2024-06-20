import React, { useState } from "react";
import { Modal, Form, Button } from "react-bootstrap";

interface UploadPrescriptionModalProps {
  show: boolean;
  onClose: () => void;
  onSubmit: (
    appointmentBookingId: string,
    prescriptionDetails: string,
    prescriptionDate: string
  ) => Promise<void>;
  appointmentBookingId: string;
}

const UploadPrescriptionModal: React.FC<UploadPrescriptionModalProps> = ({
  show,
  onClose,
  onSubmit,
  appointmentBookingId,
}) => {
  const [prescriptionDetails, setPrescriptionDetails] = useState("");
  const [prescriptionDate, setPrescriptionDate] = useState("");

  const handleSubmit = async () => {
    await onSubmit(appointmentBookingId, prescriptionDetails, prescriptionDate);
    onClose();
    setPrescriptionDetails("");
    setPrescriptionDate("");
  };

  const handleClose = () => {
    onClose();
    setPrescriptionDetails("");
    setPrescriptionDate("");
  };

  return (
    <Modal show={show} onHide={onClose}>
      <Modal.Header closeButton>
        <Modal.Title>Upload Prescription</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="prescriptionDetails">
            <Form.Label>Prescription Details</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              value={prescriptionDetails}
              onChange={(e) => setPrescriptionDetails(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group controlId="prescriptionDate">
            <Form.Label>Prescription Date</Form.Label>
            <Form.Control
              type="date"
              value={prescriptionDate}
              onChange={(e) => setPrescriptionDate(e.target.value)}
              required
              min={new Date().toISOString().split("T")[0]}
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
        <Button variant="primary" onClick={handleSubmit}>
          Update
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default UploadPrescriptionModal;
