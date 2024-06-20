import { Tab, Nav } from "react-bootstrap";
import DashboardHeader from "./DashboardHeader";
import Practice from "./Practice";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import AppointmentRequests from "./AppointmentRequests";
import Footer from "./Footer";
import "./PatientDashboard.css";
import ManageMyDataTab from "./ManageMyDataTab";
import FindPharmaciesTab from "./FindPharmaciesTab"; // Import the new component
import logo from "../images/logo.png";

const PatientDashboard = () => {
  const localStorageItem = "patientData";
  const navigate = useNavigate();
  const [signedInUserId, setSignedInUserId] = useState("");

  useEffect(() => {
    if (localStorage.getItem("patientData") == null) {
      navigate("/");
    }
    const storedPatientDataString = localStorage.getItem("patientData");
    const storedPatientData = storedPatientDataString
      ? JSON.parse(storedPatientDataString)
      : [];
    setSignedInUserId(storedPatientData?.user?.userId);
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

  return (
    <div className="container-fluid full-page m-0 p-0">
      <div className="row m-0 p-0">
        {/* <button onClick={(e)=> test(e)}>cTest</button> */}
        <div className="burger" onClick={(e) => toggleHamburger(e)}>
          <span></span>
        </div>

        <Tab.Container id="patient-pills" defaultActiveKey="practice-details">
          {/*  */}
          <Nav
            variant="pills"
            className="flex-column col-md-2 sidebar hide-sidebar"
          >
            <div className="sidebar-logo nav-item">
              <img src={logo} alt="Logo" />
            </div>

            <Nav.Item>
              <Nav.Link
                eventKey="practice-details"
                onClick={(e) => toggleHamburger(e)}
              >
                Practice Details
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link
                eventKey="appointment-requests"
                onClick={(e) => toggleHamburger(e)}
              >
                Appointment Requests
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link
                eventKey="manage-data"
                onClick={(e) => toggleHamburger(e)}
              >
                Manage My Data
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link
                eventKey="find-pharmacies"
                onClick={(e) => toggleHamburger(e)}
              >
                Find Pharmacies
              </Nav.Link>
            </Nav.Item>
            <div className="align-items-end justify-content-center sign-out-button nav-item">
              <DashboardHeader localStorageItem={localStorageItem} />
            </div>
          </Nav>
          <div className="info-container col-md-10 m-0 p-0">
            <Tab.Content>
              <Tab.Pane
                className="tab-content-pane"
                eventKey="practice-details"
              >
                {" "}
                <Practice></Practice>
              </Tab.Pane>
              <Tab.Pane
                className="tab-content-pane"
                eventKey="appointment-requests"
              >
                <AppointmentRequests></AppointmentRequests>
              </Tab.Pane>
              <Tab.Pane className="tab-content-pane" eventKey="manage-data">
                <ManageMyDataTab userId={signedInUserId} />
              </Tab.Pane>
              <Tab.Pane className="tab-content-pane" eventKey="find-pharmacies">
                <FindPharmaciesTab />
              </Tab.Pane>
            </Tab.Content>
          </div>
        </Tab.Container>
      </div>
      <div className="row m-0 footer-container">
        <Footer></Footer>
      </div>
    </div>
  );
};

export default PatientDashboard;
