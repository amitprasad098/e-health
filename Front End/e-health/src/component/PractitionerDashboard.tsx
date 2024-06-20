import { useEffect, useState } from "react";
import { Tab, Nav } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import PendingRegistrationRequestsTab from "./PendingRegistrationRequestsTab";
import "./PractitionerDashboard.css";
import TestAppointmentsTab from "./TestAppointmentsTab";
import DashboardHeader from "./DashboardHeader";
import Footer from "./Footer";
import logo from "../images/logo.png";
import UpdateStaffPasswordModal from "./UpdateStaffPasswordModal";

const PractitionerDashboard = () => {
  const localStorageItem = "practitionerData";
  const navigate = useNavigate();
  const [showUpdatePasswordModal, setShowUpdatePasswordModal] = useState(false);

  useEffect(() => {
    if (localStorage.getItem("practitionerData") == null) {
      navigate("/");
    }
  }, []);

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

  const handleCloseUpdatePasswordModal = () =>
    setShowUpdatePasswordModal(false);

  const handleShowUpdatePasswordModal = () => setShowUpdatePasswordModal(true);

  return (
    <>
      {/* <div className="practitioner-dashboard-container">
        <h2>Practitioner Dashboard</h2>
        <Tabs
          id="practitioner-tabs"
          activeKey={key}
          onSelect={(k: any) => setKey(k ?? "pending-requests")}
        >
          <Tab
            eventKey="pending-requests"
            title="Pending Registration Requests"
          >
            <PendingRegistrationRequestsTab></PendingRegistrationRequestsTab>
          </Tab>
          <Tab eventKey="test-appointments" title="Test Appointments">
            <TestAppointmentsTab></TestAppointmentsTab>
          </Tab>
        </Tabs>
      </div> */}

      {/* <div className="footer">
        <Footer></Footer>
      </div> */}

      <div>
        <div className="container-fluid full-page m-0 p-0">
          <div className="row m-0 p-0">
            <div className="burger" onClick={(e) => toggleHamburger(e)}>
              <span></span>
            </div>

            <Tab.Container
              id="practitioner-tabs"
              defaultActiveKey="pending-requests"
            >
              <Nav
                variant="pills"
                className="flex-column col-md-2 sidebar hide-sidebar"
              >
                <div className="sidebar-logo nav-item">
                  <img src={logo} alt="Logo" />
                </div>
                <Nav.Item>
                  <Nav.Link
                    eventKey="pending-requests"
                    onClick={(e) => toggleHamburger(e)}
                  >
                    Pending Registration Requests
                  </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                  <Nav.Link
                    eventKey="test-appointments"
                    onClick={(e) => toggleHamburger(e)}
                  >
                    Test Appointments
                  </Nav.Link>
                </Nav.Item>
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
              <div className="info-container col-md-10 m-0 p-0">
                <Tab.Content>
                  <Tab.Pane
                    className="tab-content-pane"
                    eventKey="pending-requests"
                  >
                    {" "}
                    <PendingRegistrationRequestsTab></PendingRegistrationRequestsTab>
                  </Tab.Pane>
                  <Tab.Pane
                    className="tab-content-pane"
                    eventKey="test-appointments"
                  >
                    <TestAppointmentsTab></TestAppointmentsTab>
                  </Tab.Pane>
                </Tab.Content>
              </div>
            </Tab.Container>
            <UpdateStaffPasswordModal
              role="practioner"
              show={showUpdatePasswordModal}
              handleClose={handleCloseUpdatePasswordModal}
            />
          </div>
          <div className="row m-0 footer-container">
            <Footer></Footer>
          </div>
        </div>
      </div>
    </>
  );
};

export default PractitionerDashboard;
