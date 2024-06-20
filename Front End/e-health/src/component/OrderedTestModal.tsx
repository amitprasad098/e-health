import React, { useState } from "react";
import { Modal, Form, Button } from "react-bootstrap";

interface OrderedTestModalProps {
  show: boolean;
  onClose: () => void;
  onSubmit: (
    appointmentBookingId: string,
    orderedTestDetails: string,
    orderedTestDate: string
  ) => Promise<void>;
  appointmentBookingId: string;
}

const OrderedTestModal: React.FC<OrderedTestModalProps> = ({
  show,
  onClose,
  onSubmit,
  appointmentBookingId,
}) => {
  const [orderedTestDetails, setOrderedTestDetails] = useState("");
  const [orderedTestDate, setOrderedTestDate] = useState("");

  const handleSubmit = async () => {
    await onSubmit(appointmentBookingId, orderedTestDetails, orderedTestDate);
    onClose();
  };

  return (
    <Modal show={show} onHide={onClose}>
      <Modal.Header closeButton>
        <Modal.Title>Order Test</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="orderedTestDetails">
            <Form.Label>Order Test Details</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              value={orderedTestDetails}
              onChange={(e) => setOrderedTestDetails(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group controlId="orderedTestDate">
            <Form.Label>Ordered Test Date</Form.Label>
            <Form.Control
              type="date"
              value={orderedTestDate}
              onChange={(e) => setOrderedTestDate(e.target.value)}
              required
              min={new Date().toISOString().split("T")[0]}
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onClose}>
          Close
        </Button>
        <Button variant="primary" onClick={handleSubmit}>
          Update
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default OrderedTestModal;
