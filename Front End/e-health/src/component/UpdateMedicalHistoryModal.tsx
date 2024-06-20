import React, { useState } from "react";
import { Modal, Form, Button } from "react-bootstrap";

interface UpdateMedicalHistoryModalProps {
  show: boolean;
  onClose: () => void;
  onSubmit: (
    appointmentBookingId: string,
    medicalHistoryDetails: string,
    medicalHistoryDate: string
  ) => Promise<void>;
  appointmentBookingId: string;
}

const UpdateMedicalHistoryModal: React.FC<UpdateMedicalHistoryModalProps> = ({
  show,
  onClose,
  onSubmit,
  appointmentBookingId,
}) => {
  const [medicalHistoryDetails, setMedicalHistoryDetails] = useState("");
  const [medicalHistoryDate, setMedicalHistoryDate] = useState("");

  const handleSubmit = async () => {
    // Ensure both medicalHistoryDetails and medicalHistoryDate are provided when submitting
    await onSubmit(
      appointmentBookingId,
      medicalHistoryDetails,
      medicalHistoryDate
    );
    onClose();
    setMedicalHistoryDetails("");
    setMedicalHistoryDate("");
  };

  const handleClose = () => {
    onClose();
    setMedicalHistoryDetails("");
    setMedicalHistoryDate("");
  };

  return (
    <Modal show={show} onHide={onClose}>
      <Modal.Header closeButton>
        <Modal.Title>Update Medical History</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="medicalHistoryDetails">
            <Form.Label>Medical History Details</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              value={medicalHistoryDetails}
              onChange={(e) => setMedicalHistoryDetails(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group controlId="medicalHistoryDate">
            <Form.Label>Medical History Date</Form.Label>
            <Form.Control
              type="date"
              value={medicalHistoryDate}
              onChange={(e) => setMedicalHistoryDate(e.target.value)}
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

export default UpdateMedicalHistoryModal;
