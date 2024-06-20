import { useState, useEffect } from "react";
import { Button, Tab, Nav } from "react-bootstrap";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./DoctorDashboard.css";
import DashboardHeader from "./DashboardHeader";
import UpdateMedicalHistoryModal from "./UpdateMedicalHistoryModal";
import Footer from "./Footer";
import UploadPrescriptionModal from "./UploadPrescriptionModal";
import OrderedTestModal from "./OrderedTestModal";
import SpecialistReferralModal from "./SpecialistReferralModal";
import logo from "../images/logo.png";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import UpdateStaffPasswordModal from "./UpdateStaffPasswordModal";

const DoctorDashboard = () => {
  const [appointments, setAppointments] = useState<any[]>([]);
  const navigate = useNavigate();
  const [showMedicalHistoryModal, setShowMedicalHistoryModal] =
    useState<boolean>(false);
  const [showPrescriptionModal, setShowPrescriptionModal] =
    useState<boolean>(false);
  const [showOrderedTestModal, setShowOrderedTestModal] =
    useState<boolean>(false);
  const [showSpecialistReferralModal, setShowSpecialistReferralModal] =
    useState<boolean>(false);
  const localStorageItem = "doctorData";
  var signedInDoctorId = 0;
  const [showUpdatePasswordModal, setShowUpdatePasswordModal] = useState(false);

  useEffect(() => {
    // Fetch approved appointment requests from the API
    if (localStorage.getItem("doctorData") == null) {
      navigate("/");
    }

    const storedDoctorDataString = localStorage.getItem("doctorData");
    const storedDoctorData = storedDoctorDataString
      ? JSON.parse(storedDoctorDataString)
      : [];
    signedInDoctorId = storedDoctorData.doctorId;

    axios
      .get(`http://localhost:8080/appointments/requests/${signedInDoctorId}`)
      .then((response) => {
        setAppointments(response.data);
      })
      .catch((error) => {
        console.error("Error fetching appointments:", error);
      });
  }, []);

  const handleOpenMedicalHistoryModal = () => {
    setShowMedicalHistoryModal(true);
  };

  const handleCloseMedicalHistoryModal = () => {
    setShowMedicalHistoryModal(false);
  };

  const handleOpenPrescriptionModal = () => {
    setShowPrescriptionModal(true);
  };

  const handleClosePrescriptionModal = () => {
    setShowPrescriptionModal(false);
  };

  const handleOpenOrderedTestModal = () => {
    setShowOrderedTestModal(true);
  };

  const handleCloseOrderedTestModal = () => {
    setShowOrderedTestModal(false);
  };

  const handleOpenSpecialistReferralModal = () => {
    setShowSpecialistReferralModal(true);
  };

  const handleCloseSpecialistReferralModal = () => {
    setShowSpecialistReferralModal(false);
  };

  const handleCloseUpdatePasswordModal = () =>
    setShowUpdatePasswordModal(false);

  const handleShowUpdatePasswordModal = () => setShowUpdatePasswordModal(true);

  const handleUploadPrescription = async (
    appointmentBookingId: any,
    prescriptionDetails: string,
    prescriptionDate: string
  ) => {
    const postPrescriptionData = {
      appointmentBookingId: appointmentBookingId,
      prescriptionDetails: prescriptionDetails,
      prescriptionDate: prescriptionDate,
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/patients/prescription",
        postPrescriptionData
      );
      // Handle success response
      console.log("Upload Prescription successful:", response.data);
      toast.success("Prescription uploaded successfully");
      handleClosePrescriptionModal();
    } catch (error: any) {
      // Handle error
      toast.error(error.response.data.message);
      console.error("Error uploading prescription:", error);
    }
  };

  const handleUpdateMedicalHistory = async (
    appointmentBookingId: any,
    medicalHistoryDetails: string,
    medicalHistoryDate: string
  ) => {
    const postMedicalHistoryData = {
      appointmentBookingId: appointmentBookingId,
      medicalHistoryDetails: medicalHistoryDetails,
      medicalHistoryDate: medicalHistoryDate,
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/medical-history",
        postMedicalHistoryData
      );
      // Handle success response
      console.log("Update Medical History successful:", response.data);
      toast.success("Medical History updated successfully");
      handleCloseMedicalHistoryModal();
    } catch (error: any) {
      // Handle error
      toast.error(error.response.data.message);
      console.error("Error updating medical history:", error);
    }
  };

  const handleUploadSpecialistReferral = async (
    appointmentBookingId: any,
    specialistReferralDetails: string,
    specialistReferralDate: string,
    selectedDoctorId: any
  ) => {
    const postSpecialistReferralData = {
      appointmentBookingId: appointmentBookingId,
      specialistReferralDetails: specialistReferralDetails,
      specialistReferralDate: specialistReferralDate,
      referredDoctorId: selectedDoctorId,
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/patients/referral",
        postSpecialistReferralData
      );
      // Handle success response
      console.log("Upload Specialist Referral successful:", response.data);
      toast.success("Specialist Referred successfully");
      handleCloseSpecialistReferralModal();
    } catch (error: any) {
      // Handle error
      toast.error(error.response.data.message);
      console.error("Error uploading specialist referral:", error);
    }
  };

  const handleOrderTest = async (
    appointmentBookingId: any,
    orderedTestDetails: string,
    orderedTestDate: string
  ) => {
    const postOrderTestData = {
      appointmentBookingId: appointmentBookingId,
      orderedTestDetails: orderedTestDetails,
      orderedTestDate: orderedTestDate,
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/patients/tests",
        postOrderTestData
      );
      // Handle success response
      console.log("Order Test successful:", response.data);
      toast.success("Test ordered successfully");
      handleCloseOrderedTestModal();
    } catch (error: any) {
      // Handle error
      toast.error(error.response.data.message);
      console.error("Error ordering test:", error);
    }
  };

  const toggleHamburger = (ev: any) => {
    ev.preventDefault();
    if (window.innerWidth < 768) {
      const div = document.getElementsByClassName("sidebar")[0];
      div.classList.contains("hide-sidebar")
        ? div.classList.remove("hide-sidebar")
        : div.classList.add("hide-sidebar");
      const burger = document.getElementsByClassName("burger")[0];
      burger.classList.contains("clicked")
        ? burger.classList.remove("clicked")
        : burger.classList.add("clicked");
    }
  };

  return (
    <>
      <div className="container-fluid full-page m-0 p-0">
        <div className="row m-0 p-0">
          <div className="burger" onClick={(e) => toggleHamburger(e)}>
            <span></span>
          </div>

          <Tab.Container id="doctor-tabs" defaultActiveKey="doctor-dashboard">
            <Nav
              variant="pills"
              className="flex-column col-md-2 sidebar hide-sidebar"
            >
              <div className="sidebar-logo nav-item">
                <img src={logo} alt="Logo" />
              </div>
              <div className="align-items-end justify-content-center sign-out-button nav-item">
                <button
                  className="mb-3 change-password"
                  onClick={handleShowUpdatePasswordModal}
                >
                  {" "}
                  Change Password
                </button>
                <DashboardHeader localStorageItem={localStorageItem} />
              </div>
            </Nav>
            <div className="doctor-dashboard-container info-container col-md-10 m-0 p-0">
              <Tab.Content>
                <Tab.Pane
                  className="doctor-dashboard-content-pane"
                  eventKey="doctor-dashboard"
                >
                  <h2>Doctor Dashboard</h2>
                  <table className="table">
                    <thead>
                      <tr>
                        <th>Patient Name</th>
                        <th>Appointment Date</th>
                        <th>Appointment Details</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody>
                      {appointments.map((appointment) => (
                        <tr key={appointment.appointmentBookingId}>
                          <td>
                            {
                              appointment.patientPracticeRegistration.patient
                                .user.userFullName
                            }
                          </td>
                          <td>
                            {new Date(
                              appointment.appointmentDate
                            ).toLocaleDateString("en-US", {
                              year: "numeric",
                              month: "long",
                              day: "numeric",
                            })}
                          </td>
                          <td>{appointment.appointmentDetails}</td>
                          <td className="d-flex">
                            <Button
                              className="m-1"
                              variant="success"
                              onClick={handleOpenPrescriptionModal}
                            >
                              Upload Prescription
                            </Button>
                            <UploadPrescriptionModal
                              show={showPrescriptionModal}
                              onClose={handleClosePrescriptionModal}
                              onSubmit={handleUploadPrescription}
                              appointmentBookingId={
                                appointment.appointmentBookingId
                              }
                            />
                            <Button
                              className="m-1"
                              variant="success"
                              onClick={handleOpenMedicalHistoryModal}
                            >
                              Update Medical History
                            </Button>
                            <UpdateMedicalHistoryModal
                              show={showMedicalHistoryModal}
                              onClose={handleCloseMedicalHistoryModal}
                              onSubmit={handleUpdateMedicalHistory}
                              appointmentBookingId={
                                appointment.appointmentBookingId
                              }
                            />
                            <Button
                              className="m-1"
                              variant="success"
                              onClick={handleOpenSpecialistReferralModal}
                            >
                              Specialist Referral
                            </Button>
                            <SpecialistReferralModal
                              show={showSpecialistReferralModal}
                              onClose={handleCloseSpecialistReferralModal}
                              onSubmit={handleUploadSpecialistReferral}
                              appointmentBookingId={
                                appointment.appointmentBookingId
                              }
                            />
                            <Button
                              className="m-1"
                              variant="success"
                              onClick={handleOpenOrderedTestModal}
                            >
                              Order Test
                            </Button>
                            <OrderedTestModal
                              show={showOrderedTestModal}
                              onClose={handleCloseOrderedTestModal}
                              onSubmit={handleOrderTest}
                              appointmentBookingId={
                                appointment.appointmentBookingId
                              }
                            />
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </Tab.Pane>
              </Tab.Content>
            </div>
          </Tab.Container>
          <UpdateStaffPasswordModal
            role="doctor"
            show={showUpdatePasswordModal}
            handleClose={handleCloseUpdatePasswordModal}
          />
          <ToastContainer />
        </div>
        <div className="row m-0 footer-container">
          <Footer></Footer>
        </div>
      </div>
    </>
  );
};

export default DoctorDashboard;
