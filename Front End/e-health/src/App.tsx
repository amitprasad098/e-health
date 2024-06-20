import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LandingPage from "./component/LandingPage";
import "./App.css";
import AdminDashboard from "./component/AdminDashboard";
import DoctorDashboard from "./component/DoctorDashboard";
import PatientDashboard from "./component/PatientDashboard";
import PractitionerDashboard from "./component/PractitionerDashboard";
import "bootstrap/dist/css/bootstrap.css";
import AppointmentDetails from "./component/AppointmentDetails";

const App = () => {
  return (
    <>

      <Router>
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/admin-dashboard" element={<AdminDashboard />} />
          <Route path="/patient-dashboard" element={<PatientDashboard />} />
          <Route
            path="/practitioner-dashboard"
            element={<PractitionerDashboard />}
          />
          <Route path="/doctor-dashboard" element={<DoctorDashboard />} />
          <Route
            path="/appointment-details/:appointmentBookingId"
            element={<AppointmentDetails />}
          />
        </Routes>
      </Router>
    </>
  );
};

export default App;
