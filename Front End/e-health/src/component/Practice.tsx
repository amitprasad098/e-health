import React, { useState, useEffect } from "react";
import axios from "axios";
import { Card, Button, Modal, Form } from "react-bootstrap";
import "./Practice.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const Practice = () => {
  const [practices, setPractices] = useState<any[]>([]);
  const [showModal, setShowModal] = useState(false);
  const [appointmentDesc, setAppointmentDesc] = useState("");
  const [appointmentDate, setAppointmentDate] = useState("");
  const [selectedPracticeId, setSelectedPracticeId] = useState(0);
  const [signedInPatientId, setSignedInPatientId] = useState(0);

  useEffect(() => {
    const storedPatientDataString = localStorage.getItem("patientData");
    const storedPateintData = storedPatientDataString
      ? JSON.parse(storedPatientDataString)
      : [];
    setSignedInPatientId(storedPateintData.patientId);
    fetchPractices();
    console.log("i fire once");
  }, [signedInPatientId]);

  // useEffect(() => {
  //   fetchPractices();
  // }, [signedInPatientId]);
  // location.reload();

  const fetchPractices = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/practices/${signedInPatientId}`
      );
      console.log("fetch practises -> ", response);
      setPractices(response.data);
    } catch (error) {
      console.error("Error fetching practices:", error);
    }
  };

  const handleRegister = async (practiceId: any) => {
    try {
      // Replace 'patientId' with actual patient ID
      const response = await axios.post(
        "http://localhost:8080/users/practice/registration",
        {
          patientId: signedInPatientId,
          practiceId,
        }
      );
      console.log("Registration success:", response.data);
      toast.success("response.data");
      console.log(response.data);
      location.reload();
    } catch (error: any) {
      toast.error(error.response.data.message);
      console.error("Error registering:", error);
    }
  };

  const handleAppointment = async () => {
    try {
      // Replace 'patientId' and 'selectedPractice.id' with actual values
      const response = await axios.post("http://localhost:8080/appointments", {
        patientId: signedInPatientId,
        practiceId: selectedPracticeId,
        appointmentDescription: appointmentDesc,
        appointmentDate: appointmentDate,
      });
      toast.success("Appointment booked");
      console.log("Appointment booked:", response.data);
      handleCloseModal();
    } catch (error: any) {
      toast.error(error.response.data.message);
      console.error("Error booking appointment:", error);
    }
  };

  const handleModalShow = (practice: {
    practiceId: React.SetStateAction<number>;
  }) => {
    setSelectedPracticeId(practice.practiceId);
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setSelectedPracticeId(0);
    setAppointmentDesc("");
    setAppointmentDate("");
  };

  return (
    <div className="practice-container d-flex flex-column">
      <h2 className="mb-3">Practice Details</h2>
      <div className="row row-cols-1 row-cols-lg-3  row-cols-md-2 row-cols-sm-1  row-cols-xl-4 g-4">
        {practices.map((practice) => (
          <div className="col" key={"col-" + practice.practiceId}>
            <Card key={practice.practiceId} className="h-100">
              <Card.Img variant="top" src={practice.practiceImageUrl} />
              <Card.Body className="d-flex flex-column justify-content-between">
                <Card.Title>{practice.practiceName}</Card.Title>
                <Card.Text>{practice.practiceDescription}</Card.Text>
                <Card.Text>{practice.practiceAddress}</Card.Text>
                <Card.Text>{practice.practiceContactDetails}</Card.Text>
                <Button
                  variant={
                    practice.patientRegistrationStatus === "UNAVAILABLE" ||
                    practice.patientRegistrationStatus === "DECLINED"
                      ? "primary"
                      : "secondary"
                  }
                  onClick={() => handleRegister(practice.practiceId)}
                  disabled={
                    practice.patientRegistrationStatus === "APPROVED" ||
                    practice.patientRegistrationStatus === "PENDING"
                  }
                >
                  {practice.patientRegistrationStatus == "UNAVAILABLE" ||
                  practice.patientRegistrationStatus == "DECLINED"
                    ? "Register"
                    : practice.patientRegistrationStatus == "APPROVED"
                    ? "Registered"
                    : "Waiting approval"}
                </Button>
                <Button
                  variant={
                    practice.patientRegistrationStatus == "APPROVED"
                      ? "success"
                      : "secondary"
                  }
                  onClick={() => handleModalShow(practice)}
                  disabled={practice.patientRegistrationStatus !== "APPROVED"}
                >
                  {practice.patientRegistrationStatus == "UNAVAILABLE" ||
                  practice.patientRegistrationStatus == "DECLINED"
                    ? "Booking Not Available"
                    : practice.patientRegistrationStatus == "APPROVED"
                    ? "Book Appointment"
                    : "Waiting approval"}
                </Button>
              </Card.Body>
            </Card>
          </div>
        ))}
      </div>

      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>Book An Appointment</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="appointmentDesc">
              <Form.Label>Appointment Description</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter description"
                value={appointmentDesc}
                onChange={(e) => setAppointmentDesc(e.target.value)}
              />
            </Form.Group>
            <Form.Group controlId="appointmentDate">
              <Form.Label>Appointment Date</Form.Label>
              <Form.Control
                type="date"
                value={appointmentDate}
                onChange={(e) => setAppointmentDate(e.target.value)}
                min={new Date().toISOString().split("T")[0]}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Close
          </Button>
          <Button variant="success" onClick={handleAppointment}>
            Book
          </Button>
        </Modal.Footer>
      </Modal>
      <ToastContainer />
    </div>
  );
};

export default Practice;
