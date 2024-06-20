import React, { useState, useEffect } from "react";
import { Modal, Form, Button } from "react-bootstrap";

interface SpecialistReferralModalProps {
  show: boolean;
  onClose: () => void;
  onSubmit: (
    appointmentBookingId: string,
    referralDetails: string,
    referralDate: string,
    doctorId: any
  ) => Promise<void>;
  appointmentBookingId: string;
}

const SpecialistReferralModal: React.FC<SpecialistReferralModalProps> = ({
  show,
  onClose,
  onSubmit,
  appointmentBookingId,
}) => {
  const [referralDetails, setReferralDetails] = useState("");
  const [referralDate, setReferralDate] = useState("");
  const [selectedPractice, setSelectedPractice] = useState("");
  const [practices, setPractices] = useState<any[]>([]);
  const [doctors, setDoctors] = useState<any[]>([]);
  const [selectedDoctorId, setSelectedDoctorId] = useState<number>(0);

  useEffect(() => {
    // Fetch practices from the API
    const fetchPractices = async () => {
      try {
        const response = await fetch("http://localhost:8080/practices");
        const data = await response.json();
        setPractices(data);
      } catch (error) {
        console.error("Error fetching practices:", error);
      }
    };

    fetchPractices();
  }, []);

  const handlePracticeChange = async (
    e: React.ChangeEvent<HTMLSelectElement>
  ) => {
    const practiceId = e.target.value;
    setSelectedPractice(practiceId);

    // Fetch doctors from the API based on selected practice
    try {
      const response = await fetch(
        `http://localhost:8080/appointments/requests/${practiceId}/doctors`
      );
      const data = await response.json();
      setDoctors(data);
    } catch (error) {
      console.error("Error fetching doctors:", error);
    }
  };

  const handleSubmit = async () => {
    await onSubmit(
      appointmentBookingId,
      referralDetails,
      referralDate,
      selectedDoctorId
    );
    onClose();
  };

  return (
    <Modal show={show} onHide={onClose}>
      <Modal.Header closeButton>
        <Modal.Title>Specialist Referral</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="referralDetails">
            <Form.Label>Referral Details</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              value={referralDetails}
              onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) =>
                setReferralDetails(e.target.value)
              }
              required
            />
          </Form.Group>
          <Form.Group controlId="referralDate">
            <Form.Label>Referral Date</Form.Label>
            <Form.Control
              type="date"
              value={referralDate}
              onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                setReferralDate(e.target.value)
              }
              required
              min={new Date().toISOString().split("T")[0]}
            />
          </Form.Group>
          <Form.Group controlId="practice">
            <Form.Label>Select Practice</Form.Label>
            <Form.Control
              as="select"
              value={selectedPractice}
              onChange={(e: any) => handlePracticeChange(e)}
              required
            >
              <option value="">Select Practice</option>
              {practices.map((practice) => (
                <option key={practice.practiceId} value={practice.practiceId}>
                  {practice.practiceName}
                </option>
              ))}
            </Form.Control>
          </Form.Group>
          <Form.Group controlId="doctor">
            <Form.Label>Select Doctor</Form.Label>
            <Form.Control
              as="select"
              value={selectedDoctorId}
              onChange={(e) =>
                setSelectedDoctorId(parseInt(e.target.value, 10))
              }
              required
            >
              <option value="">Select Doctor</option>
              {doctors.map((doctor) => (
                <option key={doctor.doctorId} value={doctor.doctorId}>
                  {doctor.user.userFullName}
                </option>
              ))}
            </Form.Control>
          </Form.Group>
        </Form>
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

export default SpecialistReferralModal;
