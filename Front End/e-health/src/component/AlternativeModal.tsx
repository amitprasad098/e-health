import React, { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";

interface AlternativeModalProps {
  show: boolean;
  onClose: () => void;
  onSubmit: (appointmentBookingId: string, reason: string) => Promise<void>;
  appointmentBookingId: string;
}

const AlternativeModal: React.FC<AlternativeModalProps> = ({
  show,
  onClose,
  onSubmit,
  appointmentBookingId,
}) => {
  const [alternativeReason, setAlternativeReason] = useState<string>("");

  const handleSubmit = async () => {
    await onSubmit(appointmentBookingId, alternativeReason);
    onClose();
  };

  return (
    <Modal show={show} onHide={onClose}>
      <Modal.Header closeButton>
        <Modal.Title>Offer Alternative</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form.Control
          type="text"
          placeholder="Enter reason for offering alternative"
          value={alternativeReason}
          onChange={(e) => setAlternativeReason(e.target.value)}
        />
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onClose}>
          Close
        </Button>
        <Button variant="primary" onClick={handleSubmit}>
          Submit
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default AlternativeModal;
