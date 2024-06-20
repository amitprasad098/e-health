import { useState } from "react";
import { Card, Button } from "react-bootstrap";
import PracticeRegistrationModal from "./PracticeRegistrationModal";
import DoctorRegistrationModal from "./DoctorRegistrationModal";
import PractitionerRegistrationModal from "./PractitionerRegistrationModal";
import UpdateStaffPasswordModal from "./UpdateStaffPasswordModal";
import './RegisterMedicalStaff.css'

const RegisterMedicalStaff = () => {
  const [showPracticeModal, setShowPracticeModal] = useState(false);
  const [showDoctorModal, setShowDoctorModal] = useState(false);
  const [showPractitionerModal, setShowPractitionerModal] = useState(false);
  const [showUpdatePasswordModal, setShowUpdatePasswordModal] = useState(false);

  const handleClosePracticeModal = () => setShowPracticeModal(false);
  const handleCloseDoctorModal = () => setShowDoctorModal(false);
  const handleClosePractitionerModal = () => setShowPractitionerModal(false);
  const handleCloseUpdatePasswordModal = () =>
    setShowUpdatePasswordModal(false);

  const handleShowPracticeModal = () => setShowPracticeModal(true);
  const handleShowDoctorModal = () => setShowDoctorModal(true);
  const handleShowPractitionerModal = () => setShowPractitionerModal(true);
  const handleShowUpdatePasswordModal = () => setShowUpdatePasswordModal(true);

  return (
    <>
        <div id="register-medical-staff-container" className="container-fluid">
      <div className="row">
      <h2 className="col" id="register-medical-staff-heading">Register Medical Staff</h2>
      </div>
      <div className="row">
        <div className="col-md-4">
          <Card>
            <Card.Body>
              <Card.Title>Register Practice</Card.Title>
              <Button variant="primary" onClick={handleShowPracticeModal}>
              Register
            </Button>
            </Card.Body>
          </Card>
        </div>
        <div className="col-md-4">
          <Card>
            <Card.Body>
              <Card.Title>Register Doctor</Card.Title>
              <Button variant="primary" onClick={handleShowDoctorModal}>
              Register
            </Button>
            </Card.Body>
          </Card>
        </div>
        <div className="col-md-4">
          <Card>
            <Card.Body>
              <Card.Title>Register Practitioner</Card.Title>
              <Button variant="primary" onClick={handleShowPractitionerModal}>
              Register
            </Button>
            </Card.Body>
          </Card>
        </div>
        <div className="col-md-4">
          <Card>
            <Card.Body>
              <Card.Title>Update Staff Password</Card.Title>
              <Button variant="primary" onClick={handleShowUpdatePasswordModal}>
              Update Password
            </Button>
            </Card.Body>
          </Card>
        </div>
      </div>
    </div>
          <PracticeRegistrationModal
        show={showPracticeModal}
        handleClose={handleClosePracticeModal}
      />
      <DoctorRegistrationModal
        show={showDoctorModal}
        handleClose={handleCloseDoctorModal}
      />
      <PractitionerRegistrationModal
        show={showPractitionerModal}
        handleClose={handleClosePractitionerModal}
      />
      <UpdateStaffPasswordModal
      role=""
        show={showUpdatePasswordModal}
        handleClose={handleCloseUpdatePasswordModal}
        />
    </>

  );
};

export default RegisterMedicalStaff;
