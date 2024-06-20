import { useState, useEffect } from "react";
import "./LandingPage.css";
import { useNavigate } from "react-router-dom";
import logo from "../images/logo.png";
// import { ToastContainer, toast } from 'react-toastify';
import axios from "axios";
import { Icon } from "react-icons-kit";
import { eyeOff } from "react-icons-kit/feather/eyeOff";
import { eye } from "react-icons-kit/feather/eye";
import ForgotPasswordModal from "./ForgotPasswordModal";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const LandingPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [type, setType] = useState("password");
  const [icon, setIcon] = useState(eyeOff);
  const [userRole, setUserRole] = useState("");
  const [roles, setRoles] = useState<any[]>([]);
  const [name, setName] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [address, setAddress] = useState("");
  const [dateOfBirth, setDateOfBirth] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [termsAccepted, setTermsAccepted] = useState(false);
  const [genderAtBirth, setGenderAtBirth] = useState("");
  const [contactNumber, setContactNumber] = useState("");
  const [showForgotPasswordModal, setShowForgotPasswordModal] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchRoles = async () => {
      try {
        const response = await axios.get("http://localhost:8080/users/types");
        setRoles(response.data);
      } catch (error) {
        console.error("Error fetching roles:", error);
      }
    };

    fetchRoles();
  }, []);

  const handleToggle = () => {
    if (type === "password") {
      setIcon(eye);
      setType("text");
    } else {
      setIcon(eyeOff);
      setType("password");
    }
  };

  const onChangeEmail = (value: any, divId: any) => {
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (emailRegex.test(value)) {
      document.getElementById(divId)?.classList.remove("required-class");
    } else {
      document.getElementById(divId)?.classList.add("required-class");
    }
    setEmail(value);
  };

  const isEmptyField = (targetEl: any) => {
    if (targetEl.value) {
      targetEl.classList.remove("required-class");
    }
  };

  const resetPassword = (val: any) => {
    if (val) {
      document.getElementById("eye-icon")?.classList.remove("required-class");
    }
  };

  const handleConfirmPasswordChange = (e: { target: { value: any } }) => {
    const { value } = e.target;
    if (value && value !== password) {
      setPasswordError("Passwords do not match");
    } else {
      setPasswordError("");
    }
    setConfirmPassword(value);
  };

  const handleShowForgotPasswordModal = () => {
    setShowForgotPasswordModal(true);
  };

  const handleCloseForgotPasswordModal = () => {
    setShowForgotPasswordModal(false);
  };
  const handleLogin = async (event: { preventDefault: () => void }) => {
    event.preventDefault();

    if (!(email && password && userRole)) {
      if (!email) {
        document
          .getElementById("email-sign-in")
          ?.classList.add("required-class");
      }
      if (!password) {
        document.getElementById("eye-icon")?.classList.add("required-class");
      }
      if (!userRole) {
        document
          .getElementById("role-sign-in")
          ?.classList.add("required-class");
      }
      toast.error("Enter required details");
      return;
    }

    try {
      // Make API call for data validation
      const response = await axios.post("http://localhost:8080/users/sign-in", {
        userEmail: email,
        userPassword: password,
        userRole: userRole,
      });

      if (userRole === "Admin") {
        localStorage.setItem("adminData", JSON.stringify(response.data));
        navigate("/admin-dashboard");
      }
      if (userRole === "Patient") {
        localStorage.setItem("patientData", JSON.stringify(response.data));
        navigate("/patient-dashboard");
      }
      if (userRole === "Doctor") {
        localStorage.setItem("doctorData", JSON.stringify(response.data));
        navigate("/doctor-dashboard");
      }
      if (userRole === "Practitioner") {
        localStorage.setItem("practitionerData", JSON.stringify(response.data));
        navigate("/practitioner-dashboard");
      }
    } catch (error: any) {
      toast.error(error.response.data.message);
      console.log("Error signing in:", error);
    }
  };

  const handleSignUp = async (event: { preventDefault: () => void }) => {
    event.preventDefault();

    if (password !== confirmPassword) {
      setPasswordError("Passwords do not match");
      return;
    }

    const postData = {
      patientName: name,
      patientEmail: email,
      patientContactNumber: contactNumber,
      patientPassword: password,
      patientAddress: address,
      patientDateOfBirth: dateOfBirth,
      patientGender: genderAtBirth,
    };

    if (
      !(
        name &&
        email &&
        password &&
        address &&
        contactNumber &&
        dateOfBirth &&
        genderAtBirth
      )
    ) {
      if (!name) {
        document
          .getElementById("name-sign-up")
          ?.classList.add("required-class");
      }
      if (!dateOfBirth) {
        document.getElementById("dob-sign-up")?.classList.add("required-class");
      }
      if (!genderAtBirth) {
        document
          .getElementById("gender-sign-up")
          ?.classList.add("required-class");
      }
      if (!address) {
        document
          .getElementById("address-sign-up")
          ?.classList.add("required-class");
      }
      if (!contactNumber) {
        document
          .getElementById("contact-sign-up")
          ?.classList.add("required-class");
      }
      if (!password) {
        document
          .getElementById("password-sign-up")
          ?.classList.add("required-class");
      }
      if (!email) {
        document
          .getElementById("email-sign-up")
          ?.classList.add("required-class");
      }
      toast.error("Enter required details");
      return;
    }

    try {
      await axios.post("http://localhost:8080/users", postData);
      toast.success("Sign Up Successfully");
      document.getElementById("sign-in-button")?.click();
      setAddress("");
      setConfirmPassword("");
      setContactNumber("");
      setName("");
      setDateOfBirth("");
      setGenderAtBirth("");
      setPasswordError("");
      setTermsAccepted(false);
    } catch (error: any) {
      toast.error(error.response.data.message);
      console.error("Error signing up:", error);
    }
  };
  const onClickSignIn = () => {
    event?.preventDefault();
    document.getElementById("sign-in-button")?.classList.add("active");
    document.getElementById("sign-up-button")?.classList.remove("active");
    document.getElementById("sign-in-form")?.classList.replace("hide", "show");
    document.getElementById("sign-up-form")?.classList.replace("show", "hide");
  };

  const onClickSignUp = () => {
    event?.preventDefault();
    document.getElementById("sign-in-button")?.classList.remove("active");
    document.getElementById("sign-up-button")?.classList.add("active");
    document.getElementById("sign-in-form")?.classList.replace("show", "hide");
    document.getElementById("sign-up-form")?.classList.replace("hide", "show");
  };

  return (
    <>
      <div className="container-fluid">
        <div className="row full-page">
          <div className="col-md-5 col-lg-4 left-description">
            <div className="d-flex flex-column align-items-center">
              <img src={logo} alt="Logo" className="logo" />
              <p className="fs-5 text-center">
                Welcome to E-Health, your trusted platform for managing your
                healthcare needs. Whether you're a patient seeking medical
                assistance, a doctor managing appointments, or a practitioner
                referring patients, we've got you covered.
              </p>
              <p className="fs-5 text-center">
                With our user-friendly interface and comprehensive features, you
                can easily register, book appointments, manage medical records,
                and more—all in one place. Our secure platform ensures the
                confidentiality of your data, while our intuitive design makes
                navigating the system a breeze.
              </p>
              {/* <p>
                  Join thousands of satisfied users who rely on E-Health for their
                  healthcare needs. Sign up today and experience the convenience of
                  modern healthcare management.
                </p> */}
            </div>
          </div>

          <div className="col-md-7 col-lg-8">
            <div className="col mob-logo">
              <img src={logo} alt="Logo" className="logo" />
            </div>
            <form className="sign-form-container">
              <div className="button-container d-flex justify-content-center">
                <button
                  id="sign-in-button"
                  className="flex-fill active"
                  onClick={onClickSignIn}
                >
                  SIGN IN
                </button>
                <button
                  id="sign-up-button"
                  className="flex-fill"
                  onClick={onClickSignUp}
                >
                  SIGN UP
                </button>
              </div>

              <div id="sign-in-form" className="show">
                <div className="mb-2">
                  <label htmlFor="email-sign-in" className="form-label">
                    {" "}
                    Email:
                  </label>
                  <input
                    type="email"
                    value={email}
                    onChange={(e) => {
                      onChangeEmail(e.target.value, "email-sign-in");
                    }}
                    required
                    className="form-control"
                    id="email-sign-in"
                    placeholder="name@example.com"
                  />
                </div>

                <div className="mb-2">
                  <label htmlFor="password-sign-in" className="form-label">
                    Password:
                  </label>
                  <div id="eye-icon" className="flex">
                    <input
                      id="password-sign-in"
                      type={type}
                      value={password}
                      onChange={(e) => {
                        setPassword(e.target.value);
                        resetPassword(e.target.value);
                      }}
                      placeholder="password"
                      required
                      className="form-control"
                    />
                    <span
                      className="flex justify-around items-center"
                      onClick={handleToggle}
                    >
                      <Icon className="absolute mr-10" icon={icon} size={25} />
                    </span>
                  </div>
                </div>

                <div className="mb-3">
                  <label htmlFor="role-sign-in" className="form-label">
                    Role:
                  </label>

                  <select
                    value={userRole}
                    onChange={(e) => {
                      setUserRole(e.target.value);
                      isEmptyField(e.target);
                    }}
                    required
                    id="role-sign-in"
                    className="form-control"
                  >
                    <option value="">Select Role</option>
                    {roles.map((role) => (
                      <option key={role.userTypeId} value={role.userTypeName}>
                        {role.userTypeName}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="mb-3"></div>

                <div className="mb-2 d-flex justify-content-evenly">
                  <button className="btn sign-in-button" onClick={handleLogin}>
                    Sign In
                  </button>
                  <button
                    className="btn forgot-pass-button"
                    onClick={handleShowForgotPasswordModal}
                  >
                    Forgot Password
                  </button>
                  <ForgotPasswordModal
                    show={showForgotPasswordModal}
                    handleClose={handleCloseForgotPasswordModal}
                  />
                </div>
              </div>

              <div id="sign-up-form" className="hide">
                <div className="row">
                  <div className="mb-2 col-xl-6 col-md-6">
                    <label htmlFor="name-sign-up" className="form-label">
                      Name:
                    </label>
                    <input
                      type="text"
                      value={name}
                      onChange={(e) => {
                        setName(e.target.value);
                        isEmptyField(e.target);
                      }}
                      required
                      className="form-control"
                      id="name-sign-up"
                      placeholder="John Doe"
                    />
                  </div>
                  <div className="mb-2 col-xl-6 col-md-6">
                    <label htmlFor="dob-sign-up" className="form-label">
                      Date of Birth:
                    </label>
                    <input
                      id="dob-sign-up"
                      type="date"
                      value={dateOfBirth}
                      onChange={(e) => {
                        setDateOfBirth(e.target.value);
                        isEmptyField(e.target);
                      }}
                      max={new Date().toISOString().split("T")[0]}
                      className="form-control"
                    />
                  </div>
                </div>

                <div className="row">
                  <div className="mb-2 col-xl-6 col-md-6">
                    <label htmlFor="gender-sign-up" className="form-label">
                      Gender At Birth:
                    </label>
                    <select
                      value={genderAtBirth}
                      onChange={(e) => {
                        setGenderAtBirth(e.target.value);
                        isEmptyField(e.target);
                      }}
                      required
                      className="form-select"
                      id="gender-sign-up"
                    >
                      <option value="" selected>
                        Select Gender
                      </option>
                      <option value="Male">Male</option>
                      <option value="Female">Female</option>
                    </select>
                  </div>
                  <div className="mb-2 col-xl-6 col-md-6">
                    <label htmlFor="address-sign-up" className="form-label">
                      Address:
                    </label>
                    <input
                      id="address-sign-up"
                      type="text"
                      value={address}
                      onChange={(e) => {
                        setAddress(e.target.value);
                        isEmptyField(e.target);
                      }}
                      required
                      className="form-control"
                      placeholder="address.."
                    />
                  </div>
                </div>
                <div className="row">
                  <div className="mb-2 col-xl-6 col-md-6">
                    <label htmlFor="contact-sign-up" className="form-label">
                      Contact Number:
                    </label>
                    <input
                      type="tel"
                      pattern="[0-9]{10}"
                      value={contactNumber}
                      onChange={(e) => {
                        setContactNumber(e.target.value);
                        isEmptyField(e.target);
                      }}
                      required
                      className="form-control"
                      id="contact-sign-up"
                      placeholder="Enter contact number"
                    />
                    <div id="contactHelpSignup" className="form-text">
                      Enter a 10-digit contact number
                    </div>
                  </div>
                  <div className="mb-2 col-xl-6 col-md-6">
                    <label htmlFor="email-sign-up" className="form-label">
                      Email:
                    </label>
                    <input
                      type="email"
                      value={email}
                      onChange={(e) => {
                        onChangeEmail(e.target.value, "email-sign-up");
                      }}
                      required
                      className="form-control"
                      id="email-sign-up"
                      placeholder="name@example.com"
                    />
                  </div>
                </div>

                <div className="row mb-2">
                  <div className="mb-2 col-xl-6 col-md-6">
                    <label htmlFor="password-sign-up" className="form-label">
                      Password:
                    </label>
                    <div id="eye-icon" className="flex">
                      <input
                        id="password-sign-up"
                        type={type}
                        value={password}
                        onChange={(e) => {
                          setPassword(e.target.value);
                          isEmptyField(e.target);
                        }}
                        placeholder="password"
                        required
                        className="form-control"
                      />
                      <span
                        className="flex justify-around items-center"
                        onClick={handleToggle}
                      >
                        <Icon
                          className="absolute mr-10"
                          icon={icon}
                          size={25}
                        />
                      </span>
                    </div>
                  </div>
                  <div className="mb-2 col-xl-6 col-md-6">
                    <label
                      htmlFor="confirm-password-sign-up"
                      className="form-label"
                    >
                      Confirm Password:
                    </label>

                    <div id="eye-icon" className="flex">
                      <input
                        id="confirm-password-sign-up"
                        type={type}
                        value={confirmPassword}
                        onChange={handleConfirmPasswordChange}
                        placeholder="confirm password"
                        required
                        className="form-control"
                      />
                      <span
                        className="flex justify-around items-center"
                        onClick={handleToggle}
                      >
                        <Icon
                          className="absolute mr-10"
                          icon={icon}
                          size={25}
                        />
                      </span>
                    </div>

                    {passwordError && (
                      <p style={{ color: "red", marginBottom: 0 }}>
                        {passwordError}
                      </p>
                    )}
                  </div>
                </div>

                <div className="mb-3 form-check">
                  <input
                    type="checkbox"
                    checked={termsAccepted}
                    onChange={(e) => setTermsAccepted(e.target.checked)}
                    required
                    className="form-check-input"
                    id="gdprSignup"
                  />
                  <label className="form-check-label" htmlFor="gdprSignup">
                    I consent to the website's use of my data for the purpose of
                    healthcare appointments, in accordance with GDPR guidelines.
                  </label>
                </div>

                <div className="mb-2 d-flex justify-content-center">
                  <button
                    className="btn sign-up-button"
                    onClick={handleSignUp}
                    disabled={!termsAccepted || passwordError != ""}
                  >
                    Sign Up
                  </button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
      <ToastContainer />
    </>
  );
};

export default LandingPage;
