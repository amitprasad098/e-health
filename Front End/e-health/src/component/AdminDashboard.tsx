import "./AdminDashboard.css";
import DashboardHeader from "./DashboardHeader";
import Footer from "./Footer";
import PracticeRegistrationRequests from "./PracticeRegistrationRequests";
import RegisterMedicalStaff from "./RegisterMedicalStaff";
import { Tab, Nav } from "react-bootstrap";
import logo from "../images/logo.png";

const AdminDashboard = () => {
  const localStorageItem = "adminData";

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
          {/* <button onClick={(e)=> test(e)}>cTest</button> */}
          <div className="burger" onClick={(e) => toggleHamburger(e)}>
            <span></span>
          </div>

          <Tab.Container
            id="admin-pills"
            defaultActiveKey="practice-registration-request"
          >
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
                  eventKey="practice-registration-request"
                  onClick={(e) => toggleHamburger(e)}
                >
                  Practice Registration Requests
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link
                  eventKey="register-medical-staff"
                  onClick={(e) => toggleHamburger(e)}
                >
                  Register Medical Staff
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
                  eventKey="practice-registration-request"
                >
                  {" "}
                  <PracticeRegistrationRequests />
                </Tab.Pane>
                <Tab.Pane
                  className="tab-content-pane"
                  eventKey="register-medical-staff"
                >
                  <RegisterMedicalStaff />
                </Tab.Pane>
              </Tab.Content>
            </div>
          </Tab.Container>
        </div>
        <div className="row m-0 footer-container">
          <Footer></Footer>
        </div>
      </div>
    </>
  );
};

export default AdminDashboard;
