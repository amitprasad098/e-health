import "./Footer.css";

const Footer = () => {
  return (
    <footer className="container-fluid ">
      <div className="row pt-1">
        <div className="col-md-6 d-flex justify-content-center text">
          <p>Your healthcare solution provider for all your needs.</p>
        </div>
        <div className="col-md-6 ">
          <div className="d-flex justify-content-center">
            <p className="mb-0">Email: info@healthcare.com</p>
          </div>
          <div className="d-flex justify-content-center">
            <p className="mb-0">Phone: 123-456-7890</p>
          </div>
        </div>
      </div>
      <div className="row">
        <div className="col text-center">
          <p className="">&copy; 2024 Healthcare App. All rights reserved.</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
