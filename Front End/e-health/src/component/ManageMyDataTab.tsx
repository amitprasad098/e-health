import { useNavigate } from "react-router-dom";
import React, { useState } from "react";
import { Card, Button, Modal, Form } from "react-bootstrap";
import axios from "axios";
import "./ManageMyDataTab.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

interface ManageMyDataTabProps {
  userId: string;
}

const ManageMyDataTab: React.FC<ManageMyDataTabProps> = ({ userId }) => {
  const [showModal, setShowModal] = useState<boolean>(false);
  const [showDeleteModal, setShowDeleteModal] = useState<boolean>(false);
  const [patientName, setPatientName] = useState<string>("");
  const [patientEmail, setPatientEmail] = useState<string>("");
  const [patientContactNumber, setPatientContactNumber] = useState<string>("");
  const [patientAddress, setPatientAddress] = useState<string>("");
  const [patientPassword, setPatientPassword] = useState<string>("");
  const navigate = useNavigate();

  const handleViewRequest = async () => {
    try {
      await axios.get(`http://localhost:8080/users/data/${userId}`);
      toast.success("Data exported successfully");
    } catch (error) {
      toast.error("Error in exporting the data");
      console.error("Error during GDPR view request:", error);
    }
  };

  const handleUpdateRequest = () => {
    setShowModal(true);
  };

  const handleDeleteRequest = async () => {
    try {
      await axios.delete(`http://localhost:8080/users/data/${userId}`);
      localStorage.removeItem("patientData");
      handleCloseDeleteModal();
      navigate("/");
    } catch (error) {
      console.error("Error deleting data:", error);
      handleCloseDeleteModal();
      toast.error("Error in deleting the data");
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleCloseDeleteModal = () => setShowDeleteModal(false);
  const handleShowDeleteModal = () => setShowDeleteModal(true);

  const handleSubmitModal = async () => {
    try {
      const response = await axios.post("http://localhost:8080/users/data", {
        userId,
        patientName,
        patientEmail,
        patientContactNumber,
        patientAddress,
        patientPassword,
      });
      console.log("Response:", response.data);
      setShowModal(false);
      toast.success("Data updated successfully");
    } catch (error) {
      toast.error("Error in updating the data");
      console.error("Error:", error);
    }
  };

  return (
    <div id="manage-data-container" className="container-fluid">
      <div className="row">
        <h2 className="col" id="manage-data-heading">
          Manage My Data
        </h2>
      </div>
      <div className="row">
        <div className="col-md-4">
          <Card>
            <Card.Body>
              <Card.Title>Export My Data</Card.Title>
              <Card.Text>
                Click here to request access to view your personal data.
              </Card.Text>
              <Button variant="primary" onClick={handleViewRequest}>
                Export
              </Button>
            </Card.Body>
          </Card>
        </div>
        <div className="col-md-4">
          <Card>
            <Card.Body>
              <Card.Title>Update My Data</Card.Title>
              <Card.Text>
                Click here to request access to update your data.
              </Card.Text>
              <Button variant="primary" onClick={handleUpdateRequest}>
                Request Update
              </Button>
            </Card.Body>
          </Card>
        </div>
        <div className="col-md-4">
          <Card>
            <Card.Body>
              <Card.Title>Delete My Account</Card.Title>
              <Card.Text>
                Click here to request deletion of your personal data.
              </Card.Text>
              <Button variant="primary" onClick={handleShowDeleteModal}>
                Request Deletion
              </Button>
            </Card.Body>
          </Card>
        </div>
      </div>

      <div className="row" id="my-data-row">
        <div className="col-md-12 col-lg-11 my-data-container">
          <h2>My Data</h2>
          <div className="my-data-pane d-flex flex-wrap">
            <div className="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-4 p-2">
              Name:{" "}
              {
                JSON.parse(localStorage.getItem("patientData") ?? "{}").user
                  .userFullName
              }
            </div>
            <div className="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-4 p-2">
              Email:{" "}
              {
                JSON.parse(localStorage.getItem("patientData") ?? "{}").user
                  .userEmail
              }
            </div>
            <div className="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-4 p-2">
              Gender:{" "}
              {
                JSON.parse(localStorage.getItem("patientData") ?? "{}")
                  .patientGender
              }
            </div>
            <div className="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-4 p-2">
              Address:{" "}
              {
                JSON.parse(localStorage.getItem("patientData") ?? "{}")
                  .patientAddress
              }
            </div>
            <div className="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-4 p-2">
              Contact:{" "}
              {
                JSON.parse(localStorage.getItem("patientData") ?? "{}")
                  .patientContactNumber
              }
            </div>
            <div className="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-4 p-2">
              DOB:{" "}
              {new Date(
                JSON.parse(
                  localStorage.getItem("patientData") ?? "{}"
                ).patientDateOfBirth
              ).toLocaleDateString("en-US", {
                year: "numeric",
                month: "long",
                day: "numeric",
              })}
            </div>
          </div>
        </div>
      </div>
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>Update Patient Data</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="formPatientName">
              <Form.Label>Patient Name</Form.Label>
              <Form.Control
                type="text"
                value={patientName}
                onChange={(e) => setPatientName(e.target.value)}
              />
            </Form.Group>
            <Form.Group controlId="formPatientEmail">
              <Form.Label>Patient Email</Form.Label>
              <Form.Control
                type="email"
                value={patientEmail}
                onChange={(e) => setPatientEmail(e.target.value)}
              />
            </Form.Group>
            <Form.Group controlId="formPatientContactNumber">
              <Form.Label>Patient Contact Number</Form.Label>
              <Form.Control
                type="tel"
                value={patientContactNumber}
                onChange={(e) => setPatientContactNumber(e.target.value)}
              />
            </Form.Group>
            <Form.Group controlId="formPatientAddress">
              <Form.Label>Patient Address</Form.Label>
              <Form.Control
                type="text"
                value={patientAddress}
                onChange={(e) => setPatientAddress(e.target.value)}
              />
            </Form.Group>
            <Form.Group controlId="formPatientPassword">
              <Form.Label>Patient Password</Form.Label>
              <Form.Control
                type="text"
                value={patientPassword}
                onChange={(e) => setPatientPassword(e.target.value)}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Close
          </Button>
          <Button variant="primary" onClick={handleSubmitModal}>
            Submit
          </Button>
        </Modal.Footer>
      </Modal>

      <Modal show={showDeleteModal} onHide={handleCloseDeleteModal}>
        <Modal.Header closeButton>
          <Modal.Title>Confirm data deletion</Modal.Title>
        </Modal.Header>
        <Modal.Body>Are you sure you want to delete all the data?</Modal.Body>
        <Modal.Footer className="d-flex flex-row">
          <Button
            className="col"
            variant="secondary"
            onClick={handleCloseDeleteModal}
          >
            Close
          </Button>
          <Button
            className="col"
            variant="danger"
            onClick={handleDeleteRequest}
          >
            Delete
          </Button>
        </Modal.Footer>
      </Modal>
      <ToastContainer />
    </div>
  );
};

export default ManageMyDataTab;
